package tech.amereta.generator.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.generator.domain.description.ApplicationDescription;
import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.service.ApplicationGenerator;
import tech.amereta.generator.service.spring.main.AmeretaAnnotationGeneratorSpring;
import tech.amereta.generator.service.spring.main.MainGeneratorSpring;
import tech.amereta.generator.service.spring.main.config.ApplicationConfigurationGeneratorSpring;
import tech.amereta.generator.service.spring.main.model.domain.UserGenerator;
import tech.amereta.generator.service.spring.main.security.SecurityConfigurationGeneratorSpring;
import tech.amereta.generator.service.spring.test.MainTestGeneratorSpring;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaSourceCode;
import tech.amereta.generator.util.soy.ISoyConfiguration;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpringBootApplicationGeneratorService implements ApplicationGenerator {

    @Autowired
    private JavaSourceCodeWriter sourceWriter;

    @Override
    public void generate(final ApplicationDescription springApplicationDescription, final OutputStream outputStream) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(springApplicationDescription);
        sourceWriter.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(generateCompilationUnits(springBootApplicationDescription))
                        .testCompilationUnits(generateTestCompilationUnits(springBootApplicationDescription))
                        .staticCompilationUnits(generateStaticUnits(springBootApplicationDescription))
                        .build(),
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
        System.out.println(generateModules(springApplicationDescription));
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
        units.add(generatePomXML(springApplicationDescription));
        units.addAll(generateApplicationProperties(springApplicationDescription));
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

    private ISoyConfiguration generatePomXML(final SpringBootApplicationDescription springApplicationDescription) {
        return PomGenerator.builder()
                .javaVersion(springApplicationDescription.getJavaVersion())
                .springVersion(springApplicationDescription.getSpringVersion())
                .name(springApplicationDescription.getName())
                .packageName(springApplicationDescription.getPackageName())
                .description(springApplicationDescription.getDescription())
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

    private SpringBootApplicationDescription getApplication(final ApplicationDescription springApplicationDescription) {
        return (SpringBootApplicationDescription) springApplicationDescription.getApplication();
    }
}
