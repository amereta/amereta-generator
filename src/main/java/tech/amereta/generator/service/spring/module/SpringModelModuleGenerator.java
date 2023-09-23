package tech.amereta.generator.service.spring.module;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.domain.description.java.module.model.JavaModelModuleDescription;
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
