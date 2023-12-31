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
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleFieldRelationDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringRelation;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;
import tech.amereta.generator.service.ApplicationGenerator;
import tech.amereta.generator.service.AsciiArtProviderService;
import tech.amereta.generator.service.spring.generator.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
        compilationUnits.add(SecurityConfigurationGeneratorSpring.generate(springApplicationDescription));
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
        List<ISoyConfiguration> units = new ArrayList<>();
        units.add(generatePom(springApplicationDescription));
        units.add(generateBanner(springApplicationDescription));
        units.addAll(generateApplicationProperties(springApplicationDescription));
        if (AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription)) {
            units.addAll(generateLiquibaseFiles(springApplicationDescription));
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

    private ISoyConfiguration generatePom(final SpringBootApplicationDescription springApplicationDescription) {
        return PomGenerator.builder()
                .javaVersion(springApplicationDescription.getJavaVersion())
                .springVersion(springApplicationDescription.getSpringVersion())
                .name(springApplicationDescription.getName())
                .packageName(springApplicationDescription.getPackageName())
                .description(springApplicationDescription.getDescription())
                .hasDataBase(AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription))
                .dbType(AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription) ? AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription).getDb().getType().toString() : "")
                .build();
    }

    private ISoyConfiguration generateBanner(final SpringBootApplicationDescription springApplicationDescription) {
        return BannerGenerator.builder()
                .name(asciiArtProviderService.writeAscii(springApplicationDescription.getName()))
                .build();
    }

    private List<ISoyConfiguration> generateApplicationProperties(final SpringBootApplicationDescription springApplicationDescription) {
        return List.of(
                ApplicationPropertiesGenerator.builder()
                        .name(springApplicationDescription.getName())
                        .port(springApplicationDescription.getPort())
                        .hasDataBase(AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription))
                        .dbType(AbstractSpringSourceCodeGenerator.applicationHasDataBase(springApplicationDescription) ? AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription).getDb().getType().toString() : "")
                        .dbUsername(AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription).getDb().getUsername())
                        .dbPassword(AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription).getDb().getPassword())
                        .build()
        );
    }

    private List<ISoyConfiguration> generateLiquibaseFiles(final SpringBootApplicationDescription springApplicationDescription) {
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
                        .dbType(AbstractSpringSourceCodeGenerator.getDataBase(springApplicationDescription).getDb().getType().toString())
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
