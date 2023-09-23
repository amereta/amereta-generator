package tech.amereta.generator.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.generator.domain.description.ApplicationDescription;
import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.domain.description.java.module.model.JavaModelModuleDescription;
import tech.amereta.generator.domain.description.java.module.model.type.JavaModelModuleDomainTypeDescription;
import tech.amereta.generator.service.ApplicationGenerator;
import tech.amereta.generator.service.AsciiArtProviderService;
import tech.amereta.generator.service.spring.main.AmeretaAnnotationGeneratorSpring;
import tech.amereta.generator.service.spring.main.MainGeneratorSpring;
import tech.amereta.generator.service.spring.main.config.ApplicationConfigurationGeneratorSpring;
import tech.amereta.generator.service.spring.main.model.domain.UserGenerator;
import tech.amereta.generator.service.spring.main.model.enumeration.RoleGenerator;
import tech.amereta.generator.service.spring.main.security.SecurityConfigurationGeneratorSpring;
import tech.amereta.generator.service.spring.test.MainTestGeneratorSpring;
import tech.amereta.core.java.JavaSourceCodeWriter;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaSourceCode;
import tech.amereta.core.soy.ISoyConfiguration;

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
    public void generate(final ApplicationDescription springApplicationDescription, final OutputStream outputStream) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(springApplicationDescription);
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
        compilationUnits.add(UserGenerator.generate(springApplicationDescription));
        compilationUnits.add(RoleGenerator.generate(springApplicationDescription));
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
        units.addAll(generateLiquibaseFiles(springApplicationDescription));
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
                                                     final AbstractJavaModuleDescription javaModuleDescription) {
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
                        .build()
        );
    }

    private List<ISoyConfiguration> generateLiquibaseFiles(final SpringBootApplicationDescription springApplicationDescription) {
        final List<ISoyConfiguration> changelogs = new ArrayList<>(
                springApplicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof JavaModelModuleDescription)
                .map(module -> ((JavaModelModuleDescription) module).getModels())
                .flatMap(List::stream)
                .filter(model -> model instanceof JavaModelModuleDomainTypeDescription)
                .map(domain -> DataBaseChangeLogGenerator.builder()
                        .name(domain.getName())
                        .fields(((JavaModelModuleDomainTypeDescription) domain).getFields())
                        .build())
                .toList()
        );
        changelogs.add(
                LiquibaseMasterGenerator.builder()
                        .changelogs(changelogs.stream().map(cl -> cl.getPath().toString().replaceAll("src/main/resources/", "")).toList())
                        .build()
        );
        return changelogs;
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescription springApplicationDescription) {
        return (SpringBootApplicationDescription) springApplicationDescription.getApplication();
    }
}
