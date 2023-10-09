package tech.amereta.generator.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaSourceCode;
import tech.amereta.core.java.JavaSourceCodeWriter;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.ApplicationDescription;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.security.SpringSecurityModuleDescription;
import tech.amereta.generator.service.ApplicationGenerator;
import tech.amereta.generator.service.AsciiArtProviderService;
import tech.amereta.generator.service.spring.generator.*;
import tech.amereta.generator.service.spring.generator.module.security.SecurityConfigurationGeneratorSpring;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpringBootApplicationGeneratorService implements ApplicationGenerator {

    private static final JavaSourceCodeWriter JAVA_SOURCE_CODE_WRITER = new JavaSourceCodeWriter();

    @Autowired
    private AsciiArtProviderService asciiArtProviderService;

    @Override
    public void generate(final ApplicationDescription applicationDescription, final OutputStream outputStream) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(applicationDescription);
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
        compilationUnits.add(ApplicationConfigurationGeneratorSpring.generate(springApplicationDescription));
        if (AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription)) {
            compilationUnits.add(AbstractUserGenerator.generate(springApplicationDescription));
            compilationUnits.add(RoleGenerator.generate(springApplicationDescription));
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
        return javaModuleDescription.getGenerator().generate(springApplicationDescription, javaModuleDescription);
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
                ApplicationPropertiesGenerator.builder()
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
        final List<ISoyConfiguration> changelogs = new ArrayList<>(
                domains.stream()
                        .map(domain -> LiquibaseChangeLogGenerator.builder()
                                .name(domain.getName())
                                .idType(domain.getIdType())
                                .fields(domain.getFields())
                                .relations(
                                        domain.getRelations()
                                                .stream()
                                                .toList()
                                )
                                .build())
                        .toList()
        );
        changelogs.add(
                LiquibaseMasterGenerator.builder()
                        .dbType(dataBase.get().getDb().getType().toString())
                        .changelogs(changelogs.stream().map(cl -> cl.getPath().toString().replaceAll("src/main/resources/", "")).toList())
                        .build()
        );
        return changelogs;
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

    private SpringBootApplicationDescription getApplication(final ApplicationDescription applicationDescription) {
        return (SpringBootApplicationDescription) applicationDescription.getApplication();
    }
}
