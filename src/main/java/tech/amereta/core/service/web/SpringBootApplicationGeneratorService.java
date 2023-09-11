package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.ApplicationDescription;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.service.generator.spring.ApplicationPropertiesGenerator;
import tech.amereta.core.service.generator.spring.PomXmlGenerator;
import tech.amereta.core.service.generator.spring.SpringBootSourceCodeGenerator;
import tech.amereta.core.util.code.java.JavaSourceCodeWriter;
import tech.amereta.core.util.code.java.source.JavaSourceCode;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public final class SpringBootApplicationGeneratorService implements ApplicationGenerator {

    @Autowired
    private JavaSourceCodeWriter sourceWriter;

    @Override
    public void generate(final ApplicationDescription applicationDescription, final OutputStream outputStream) throws IOException {
        sourceWriter.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(
                                SpringBootSourceCodeGenerator.builder()
                                        .springBootApplication((SpringBootApplicationDescription) applicationDescription.getApplication())
                                        .build()
                                        .generate()
                        )
                        .staticCompilationUnits(generateStaticUnits(getApplication(applicationDescription)))
                        .build(),
                outputStream
        );
    }

    private List<ISoyConfiguration> generateStaticUnits(final SpringBootApplicationDescription applicationDescription) throws IOException {
        List<ISoyConfiguration> units = new ArrayList<>();
        units.add(generatePomXML(applicationDescription));
        units.addAll(generateApplicationProperties(applicationDescription));
        return units;
    }

    private ISoyConfiguration generatePomXML(final SpringBootApplicationDescription applicationDescription) {
        return PomXmlGenerator.builder()
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
                        .build()
        );
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescription applicationDescription) {
        return (SpringBootApplicationDescription) applicationDescription.getApplication();
    }
}
