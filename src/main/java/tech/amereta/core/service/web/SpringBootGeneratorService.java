package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.ApplicationDescription;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.service.generator.spring.SpringBootSourceCodeGenerator;
import tech.amereta.core.service.generator.spring.docker.DockerComposeGenerator;
import tech.amereta.core.service.generator.spring.docker.DockerfileGenerator;
import tech.amereta.core.service.generator.spring.pom.PomFileGenerator;
import tech.amereta.core.util.code.java.JavaSourceCodeWriter;
import tech.amereta.core.util.code.java.source.JavaSourceCode;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class SpringBootGeneratorService implements ApplicationGenerator {

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
                        .staticCompilationUnits(generateStaticUnits(applicationDescription))
                        .build(),
                outputStream
        );
    }

    private List<ISoyConfiguration> generateStaticUnits(final ApplicationDescription applicationDescription) throws IOException {
        return List.of(
//                generateApplicationPropertiesFile(applicationDescription),
                generatePomFile(applicationDescription)
//                generateAmeretaDotConf(applicationDescription),
//                generateDockerfile(applicationDescription),
//                generateDockerCompose(applicationDescription)
        );
    }

//        private ISoyConfiguration generateApplicationPropertiesFile(ApplicationDescription application) {
//        return ApplicationPropertiesFileGenerator.builder()//
//                .name(application.getName())//
////                .port(((SpringBootApplication) application.getApplication()).getPort())//
//                .port(PORT)//
//                .build();
//    }

    private ISoyConfiguration generatePomFile(final ApplicationDescription applicationDescription) {
        final SpringBootApplicationDescription application = getApplication(applicationDescription);
        return PomFileGenerator.builder()
                .javaVersion(application.getJavaVersion())
                .springVersion(application.getSpringVersion())
                .name(application.getName())
                .packageName(application.getPackageName())
                .description(application.getDescription())
                .build();
    }

//    private ISoyConfiguration generateAmeretaDotConf(ApplicationDescription application) throws IOException {
//        return AmeretaDotConfFileGenerator.builder()//
//                .yml(new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).writeValueAsString(application))//
//                .build();
//    }
//
//    private ISoyConfiguration generateDockerfile(ApplicationDescription application) {
//        return DockerfileGenerator.builder()
//                .javaVersion(getApplication(application).getJavaVersion())
//                .build();
//    }
//
//    private ISoyConfiguration generateDockerCompose(ApplicationDescription applicationDescription) {
//        final SpringBootApplicationDescription application = getApplication(applicationDescription);
//        return DockerComposeGenerator.builder()
//                .name(application.getName())
//                .port(application.getPort())
//                .build();
//    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescription applicationDescription) {
        return (SpringBootApplicationDescription) applicationDescription.getApplication();
    }
}
