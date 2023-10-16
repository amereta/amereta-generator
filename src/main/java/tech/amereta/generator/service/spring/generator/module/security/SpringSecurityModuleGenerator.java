package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.security.SpringSecurityModuleDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;

import java.util.List;

public final class SpringSecurityModuleGenerator extends AbstractSpringModuleGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleDescription javaModuleDescription) {
        final SpringSecurityModuleDescription springSecurityModuleDescription = (SpringSecurityModuleDescription) javaModuleDescription;

        return springSecurityModuleDescription.getSecurity().getGenerator().generate(applicationDescription, springSecurityModuleDescription.getSecurity());
    }
}
