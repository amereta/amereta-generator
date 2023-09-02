package tech.amereta.core.service.web;

import org.springframework.stereotype.Service;
import tech.amereta.core.domain.model.ApplicationDescription;

import java.io.OutputStream;

@Service
public class SpringBootGeneratorService implements ApplicationGenerator {

    @Override
    public void generate(ApplicationDescription applicationDescription, OutputStream outputStream) {
//        sourceWriter.writeSourceTo(//
//                JavaSourceCode.builder()//
//                        .compilationUnits(//
//                                SpringBootSourceCodeGenerator.builder()//
//                                        .springBootApplication((SpringBootApplicationDescription) applicationDescription)//
//                                        .build()//
//                                        .generate())//
//                        //.staticCompilationUnits(generateStaticUnits(application))//
//                        .build(), out);
    }
}
