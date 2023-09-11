package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.ApplicationDescription;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.service.generator.spring.*;
import tech.amereta.core.util.code.java.JavaSourceCodeWriter;
import tech.amereta.core.util.code.java.source.JavaCompilationUnit;
import tech.amereta.core.util.code.java.source.JavaSourceCode;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public final class SpringBootApplicationGeneratorService implements ApplicationGenerator {

    @Autowired
    private JavaSourceCodeWriter sourceWriter;

    @Override
    public void generate(final ApplicationDescription applicationDescription, final OutputStream outputStream) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(applicationDescription);
        sourceWriter.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(generateCompilationUnits(springBootApplicationDescription))
                        .staticCompilationUnits(generateStaticUnits(springBootApplicationDescription))
                        .build(),
                outputStream
        );
    }

    private List<JavaCompilationUnit> generateCompilationUnits(final SpringBootApplicationDescription applicationDescription) {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(applicationDescription));
        compilationUnits.add(ApplicationConfigurationGenerator.generate(applicationDescription));
        compilationUnits.add(SecurityConfigurationGenerator.generate(applicationDescription));
        return compilationUnits;
    }

    private List<ISoyConfiguration> generateStaticUnits(final SpringBootApplicationDescription applicationDescription) {
        List<ISoyConfiguration> units = new ArrayList<>();
        units.add(generatePomXML(applicationDescription));
        units.addAll(generateApplicationProperties(applicationDescription));
        return units;
    }

    private ISoyConfiguration generatePomXML(final SpringBootApplicationDescription applicationDescription) {
        return PomGenerator.builder()
                .javaVersion(applicationDescription.getJavaVersion())
                .springVersion(applicationDescription.getSpringVersion())
                .name(applicationDescription.getName())
                .packageName(applicationDescription.getPackageName())
                .description(applicationDescription.getDescription())
                .build();
    }

    private List<ISoyConfiguration> generateApplicationProperties(final SpringBootApplicationDescription applicationDescription) {
        return List.of(
                ApplicationPropertiesGenerator.builder()
                        .name(applicationDescription.getName())
                        .port(applicationDescription.getPort())
                        .build()
        );
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescription applicationDescription) {
        return (SpringBootApplicationDescription) applicationDescription.getApplication();
    }
}
