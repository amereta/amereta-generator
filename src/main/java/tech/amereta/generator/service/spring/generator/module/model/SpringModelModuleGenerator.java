package tech.amereta.generator.service.spring.generator.module.model;

import org.springframework.beans.factory.annotation.Autowired;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringModuleType;
import tech.amereta.generator.description.spring.model.AbstractSpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.SpringBootModelModuleGenerator;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.exception.ModuleTypeGeneratorNotFoundException;
import tech.amereta.generator.service.BeanResolverService;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootModelModuleGenerator
public final class SpringModelModuleGenerator extends AbstractSpringModuleGenerator {

    @Autowired
    private BeanResolverService beanResolverService;

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleDescription moduleDescription) {
        final SpringModelModuleDescription springModelModuleDescription = (SpringModelModuleDescription) moduleDescription;

        return springModelModuleDescription.getModels()
                .stream()
                .map(model -> generateModels(applicationDescription, model))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<JavaCompilationUnit> generateModels(final SpringBootApplicationDescription applicationDescription,
                                                     final AbstractSpringModelModuleTypeDescription springModelModuleTypeDescription) {
        final AbstractSpringModuleTypeGenerator generator = beanResolverService
                .findOneByTypeAndAnnotation(
                        AbstractSpringModuleTypeGenerator.class,
                        springModelModuleTypeDescription.getGenerator()
                )
                .orElseThrow(() ->
                        new ModuleTypeGeneratorNotFoundException(SpringModuleType.MODEL, springModelModuleTypeDescription.getType().toString())
                );

        return generator.generate(applicationDescription, springModelModuleTypeDescription);
    }
}
