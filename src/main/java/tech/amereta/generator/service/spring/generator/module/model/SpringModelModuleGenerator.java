package tech.amereta.generator.service.spring.generator.module.model;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;

import java.util.List;
import java.util.stream.Collectors;

public final class SpringModelModuleGenerator extends AbstractSpringModuleGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription springApplicationDescription,
                                              final AbstractSpringModuleDescription javaModuleDescription) {
        final SpringModelModuleDescription javaModelModuleDescription = (SpringModelModuleDescription) javaModuleDescription;

        return javaModelModuleDescription.getModels()
                .stream()
                .map(model -> model.getGenerator().generate(springApplicationDescription, model))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
