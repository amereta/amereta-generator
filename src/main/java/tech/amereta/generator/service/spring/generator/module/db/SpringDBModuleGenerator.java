package tech.amereta.generator.service.spring.generator.module.db;

import org.springframework.beans.factory.annotation.Autowired;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.AbstractSpringDBModuleTypeDescription;
import tech.amereta.generator.description.spring.db.SpringBootDBModuleGenerator;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.generator.exception.ModuleTypeGeneratorNotFoundException;
import tech.amereta.generator.service.BeanResolverService;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;

import java.util.List;

@SpringBootDBModuleGenerator
public final class SpringDBModuleGenerator extends AbstractSpringModuleGenerator {

    @Autowired
    private BeanResolverService beanResolverService;

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleDescription moduleDescription) {
        final SpringDBModuleDescription springDBModuleDescription = (SpringDBModuleDescription) moduleDescription;
        final AbstractSpringDBModuleTypeDescription dbModuleTypeDescription = springDBModuleDescription.getDb();

        final AbstractSpringModuleTypeGenerator generator = beanResolverService
                .findOneByTypeAndAnnotation(
                        AbstractSpringModuleTypeGenerator.class,
                        dbModuleTypeDescription.getGenerator()
                )
                .orElseThrow(() ->
                        new ModuleTypeGeneratorNotFoundException(springDBModuleDescription.getType(), dbModuleTypeDescription.getType().toString())
                );

        return generator.generate(applicationDescription, dbModuleTypeDescription);
    }
}
