package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.AbstractJavaModuleDescription;
import tech.amereta.generator.description.spring.model.JavaModelModuleDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleGenerator;
import tech.amereta.core.java.JavaCompilationUnit;

import java.util.List;
import java.util.stream.Collectors;

public final class SpringModelModuleGenerator extends AbstractSpringModuleGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription springApplicationDescription,
                                              final AbstractJavaModuleDescription javaModuleDescription) {
        final JavaModelModuleDescription javaModelModuleDescription = (JavaModelModuleDescription) javaModuleDescription;

        return javaModelModuleDescription.getModels()
                .stream()
                .map(model -> model.getGenerator().generate(springApplicationDescription, model))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
