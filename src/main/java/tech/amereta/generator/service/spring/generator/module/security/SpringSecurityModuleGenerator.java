package tech.amereta.generator.service.spring.generator.module.security;

import org.springframework.beans.factory.annotation.Autowired;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.security.AbstractSpringSecurityModuleTypeDescription;
import tech.amereta.generator.description.spring.security.SpringBootSecurityModuleGenerator;
import tech.amereta.generator.description.spring.security.SpringSecurityModuleDescription;
import tech.amereta.generator.exception.ModuleTypeGeneratorNotFoundException;
import tech.amereta.generator.service.BeanResolverService;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;

import java.util.List;

@SpringBootSecurityModuleGenerator
public final class SpringSecurityModuleGenerator extends AbstractSpringModuleGenerator {

    @Autowired
    private BeanResolverService beanResolverService;

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleDescription moduleDescription) {
        final SpringSecurityModuleDescription springSecurityModuleDescription = (SpringSecurityModuleDescription) moduleDescription;
        final AbstractSpringSecurityModuleTypeDescription securityModuleTypeDescription = springSecurityModuleDescription.getSecurity();

        final AbstractSpringModuleTypeGenerator generator = beanResolverService
                .findOneByTypeAndAnnotation(
                        AbstractSpringModuleTypeGenerator.class,
                        securityModuleTypeDescription.getGenerator()
                )
                .orElseThrow(() ->
                        new ModuleTypeGeneratorNotFoundException(springSecurityModuleDescription.getType(), securityModuleTypeDescription.getType().toString())
                );

        return generator.generate(applicationDescription, securityModuleTypeDescription);
    }
}
