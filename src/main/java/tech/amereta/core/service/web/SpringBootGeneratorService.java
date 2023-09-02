package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.ApplicationDescription;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.service.generator.spring.code.SpringBootSourceCodeGenerator;
import tech.amereta.core.service.writer.java.JavaSourceCodeWriter;
import tech.amereta.core.service.writer.java.source.JavaSourceCode;

import java.io.OutputStream;

@Service
public class SpringBootGeneratorService implements ApplicationGenerator {

    @Autowired
    private JavaSourceCodeWriter sourceWriter;

    @Override
    public void generate(ApplicationDescription applicationDescription, OutputStream outputStream) {
        sourceWriter.writeSourceTo(
                JavaSourceCode.builder()
                        .compilationUnits(
                                SpringBootSourceCodeGenerator.builder()
                                        .springBootApplication((SpringBootApplicationDescription) applicationDescription.getApplication())
                                        .build()
                                        .generate())
//                        .staticCompilationUnits(generateStaticUnits(application))
                        .build(),
                outputStream
        );
    }
}
