package tech.amereta.core.service.generator.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.Application;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.service.generator.spring.code.SpringBootSourceCodeGenerator;
import tech.amereta.core.service.writer.java.JavaSourceCodeWriter;
import tech.amereta.core.service.writer.java.source.JavaSourceCode;

import java.io.OutputStream;

@Service
public class SpringBootApplicationGenerator {

    @Autowired
    private JavaSourceCodeWriter sourceWriter;

    public void generate(Application application, OutputStream out) {
        sourceWriter.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(
                                SpringBootSourceCodeGenerator.builder()
                                        .springBootApplication((SpringBootApplicationDescription) application)
                                        .build()
                                        .generate())
                        //.staticCompilationUnits(generateStaticUnits(application))
                        .build(), out);
    }

//
//    private List<ISoyConfiguration> generateStaticUnits(ApplicationDescription application) throws IOException {
//        return List.of(//
//                generateApplicationPropertiesFile(application),//
//                generatePomFile(application),//
//                generateSmoothyDotConf(application),//
//                generateDockerfile(application),//
//                generateDockerCompose(application));
//    }
//
//    private ISoyConfiguration generateApplicationPropertiesFile(ApplicationDescription application) {
//        return ApplicationPropertiesFileGenerator.builder()//
//                .name(application.getName())//
////                .port(((SpringBootApplication) application.getApplication()).getPort())//
//                .port(PORT)//
//                .build();
//    }
//
//    private ISoyConfiguration generatePomFile(ApplicationDescription application) {
//        return PomFileGenerator.builder()//
//                .javaVersion(((SpringBootApplication) application.getApplication()).getJavaVersion())//
//                .springVersion(((SpringBootApplication) application.getApplication()).getSpringVersion())//
//                .name(application.getName())//
//                .description(application.getDescription())//
//                .build();
//    }
//
//    private ISoyConfiguration generateSmoothyDotConf(ApplicationDescription application) throws IOException {
//        return SmoothyDotConfFileGenerator.builder()//
//                .yml(new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).writeValueAsString(application))//
//                .build();
//    }
//
//    private ISoyConfiguration generateDockerfile(ApplicationDescription application) {
//        return DockerfileGenerator.builder()//
//                .javaVersion(((SpringBootApplication) application.getApplication()).getJavaVersion())//
//                .build();
//    }
//
//    private ISoyConfiguration generateDockerCompose(ApplicationDescription application) {
//        return DockerComposeGenerator.builder()//
//                .name(application.getName())//
//                .port(((SpringBootApplication) application.getApplication()).getPort())//
//                .build();
//    }

}
