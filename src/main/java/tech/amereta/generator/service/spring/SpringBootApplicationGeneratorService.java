package tech.amereta.generator.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaSourceCode;
import tech.amereta.core.java.JavaSourceCodeWriter;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.ApplicationDescriptionWrapper;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringBootGenerator;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.security.SpringSecurityModuleDescription;
import tech.amereta.generator.exception.ModuleGeneratorNotFoundException;
import tech.amereta.generator.service.ApplicationGenerator;
import tech.amereta.generator.service.AsciiArtProviderService;
import tech.amereta.generator.service.BeanResolverService;
import tech.amereta.generator.service.spring.generator.*;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.model.AbstractTimestampedDomainGenerator;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootGenerator
public class SpringBootApplicationGeneratorService implements ApplicationGenerator {

    private static final JavaSourceCodeWriter JAVA_SOURCE_CODE_WRITER = new JavaSourceCodeWriter();

    @Autowired
    private BeanResolverService beanResolverService;

    @Autowired
    private AsciiArtProviderService asciiArtProviderService;

    @Override
    public void generate(final ApplicationDescriptionWrapper applicationDescriptionWrapper, final OutputStream outputStream) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(applicationDescriptionWrapper);
        JAVA_SOURCE_CODE_WRITER.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(generateCompilationUnits(springBootApplicationDescription))
                        .testCompilationUnits(generateTestCompilationUnits(springBootApplicationDescription))
                        .staticCompilationUnits(generateStaticUnits(springBootApplicationDescription)),
                outputStream
        );
    }

    private List<JavaCompilationUnit> generateCompilationUnits(final SpringBootApplicationDescription springApplicationDescription) {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(AmeretaAnnotationGeneratorSpring.generate(springApplicationDescription));
        compilationUnits.add(MainGeneratorSpring.generate(springApplicationDescription));
        compilationUnits.add(SpringBootConfigurationGenerator.generate(springApplicationDescription));
        compilationUnits.add(ApplicationPropertiesGenerator.generate(springApplicationDescription));
        if (AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription)) {
            compilationUnits.add(AbstractTimestampedDomainGenerator.generate(springApplicationDescription));
        }
        compilationUnits.addAll(generateModules(springApplicationDescription));
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateTestCompilationUnits(final SpringBootApplicationDescription springApplicationDescription) {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainTestGeneratorSpring.generate(springApplicationDescription));
        return compilationUnits;
    }

    private List<ISoyConfiguration> generateStaticUnits(final SpringBootApplicationDescription springApplicationDescription) {
        final Optional<SpringDBModuleDescription> dataBase = AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription);
        final Optional<SpringSecurityModuleDescription> securityAuthenticator = AbstractSpringSourceCodeGenerator.getSecurityAuthenticator(springApplicationDescription);

        final List<ISoyConfiguration> units = new ArrayList<>();

        units.add(generatePom(springApplicationDescription, dataBase, securityAuthenticator));
        units.add(generateBanner(springApplicationDescription));
        units.addAll(generateApplicationProperties(springApplicationDescription, dataBase));
        if (AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription)) {
            units.addAll(generateLiquibaseFiles(springApplicationDescription, dataBase));
        }
        return units;
    }

    private List<JavaCompilationUnit> generateModules(final SpringBootApplicationDescription springApplicationDescription) {
        return springApplicationDescription.getModules()
                .stream()
                .map(module -> generateModule(springApplicationDescription, module))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<JavaCompilationUnit> generateModule(final SpringBootApplicationDescription springApplicationDescription,
                                                     final AbstractSpringModuleDescription javaModuleDescription) {
        final AbstractSpringModuleGenerator generator = beanResolverService
                .findOneByTypeAndAnnotation(
                        AbstractSpringModuleGenerator.class,
                        javaModuleDescription.getGenerator()
                )
                .orElseThrow(() ->
                        new ModuleGeneratorNotFoundException(javaModuleDescription.getType())
                );
        return generator.generate(springApplicationDescription, javaModuleDescription);
    }

    private ISoyConfiguration generatePom(final SpringBootApplicationDescription springApplicationDescription, final Optional<SpringDBModuleDescription> dataBase, final Optional<SpringSecurityModuleDescription> securityAuthenticator) {
        return PomGenerator.builder()
                .javaVersion(springApplicationDescription.getJavaVersion())
                .springVersion(springApplicationDescription.getSpringVersion())
                .ameretaVersion(springApplicationDescription.getAmeretaVersion())
                .name(springApplicationDescription.getName())
                .packageName(springApplicationDescription.getPackageName())
                .description(springApplicationDescription.getDescription())
                .hasSecurity(securityAuthenticator.isPresent())
                .securityAuthenticator(securityAuthenticator.isPresent() ? securityAuthenticator.get().getSecurity().getType().toString() : "")
                .hasDataBase(dataBase.isPresent())
                .dbType(dataBase.isPresent() ? dataBase.get().getDb().getType().toString() : "")
                .build();
    }

    private ISoyConfiguration generateBanner(final SpringBootApplicationDescription springApplicationDescription) {
        return BannerGenerator.builder()
                .name(asciiArtProviderService.writeAscii(springApplicationDescription.getName()))
                .build();
    }

    private List<ISoyConfiguration> generateApplicationProperties(final SpringBootApplicationDescription springApplicationDescription, final Optional<SpringDBModuleDescription> dataBase) {
        return List.of(
                ApplicationPropertiesYAMLGenerator.builder()
                        .name(springApplicationDescription.getName())
                        .port(springApplicationDescription.getPort())
                        .hasDataBase(dataBase.isPresent())
                        .dbType(dataBase.isPresent() ? dataBase.get().getDb().getType().toString() : "")
                        .dbUsername(dataBase.isPresent() ? dataBase.get().getDb().getUsername() : "")
                        .dbPassword(dataBase.isPresent() ? dataBase.get().getDb().getPassword() : "")
                        .build()
        );
    }

    private List<ISoyConfiguration> generateLiquibaseFiles(final SpringBootApplicationDescription springApplicationDescription, final Optional<SpringDBModuleDescription> dataBase) {
        final List<SpringModelModuleDomainTypeDescription> domains = extractDomainsFromApplicationDescription(springApplicationDescription);

        final List<ISoyConfiguration> liquibaseEntities = new ArrayList<>(
                domains.stream()
                        .map(domain -> LiquibaseChangeLogGenerator.builder()
                                .domainTypeDescription(domain)
                                .dbType(dataBase.orElseThrow().getDb().getType())
                                .build()
                        )
                        .toList()
        );

        liquibaseEntities.add(
                LiquibaseMasterGenerator.builder()
                        .dbType(dataBase.orElseThrow().getDb().getType().toString())
                        .changelogs(liquibaseEntities.stream().map(cl -> cl.getPath().toString().replaceAll("src/main/resources/", "")).toList())
                        .build()
        );

        if (AbstractSpringSourceCodeGenerator.applicationHasSecurity(springApplicationDescription)) {
            liquibaseEntities.add(
                    LiquibaseInitialDataGenerator.builder()
                            .owner(springApplicationDescription.getOwner())
                            .applicationName(springApplicationDescription.getName())
                            .domainTypeDescription(AbstractSpringSourceCodeGenerator.getAuthenticableDomain(springApplicationDescription))
                            .build()
            );
        }

        return liquibaseEntities;
    }

    private List<SpringModelModuleDomainTypeDescription> extractDomainsFromApplicationDescription(SpringBootApplicationDescription springApplicationDescription) {
        return springApplicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringModelModuleDescription)
                .map(module -> ((SpringModelModuleDescription) module).getModels())
                .flatMap(List::stream)
                .filter(model -> model instanceof SpringModelModuleDomainTypeDescription)
                .map(model -> ((SpringModelModuleDomainTypeDescription) model))
                .toList();
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescriptionWrapper applicationDescriptionWrapper) {
        return (SpringBootApplicationDescription) applicationDescriptionWrapper.getApplication();
    }
}
