package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;
import tech.amereta.generator.description.spring.security.type.SpringSecurityModuleType;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;

import java.util.List;

public final class SpringSecurityModuleJWTTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final SpringModuleTypeDescription security) {
        return List.of(
                SecurityConfigurationGenerator.generate(applicationDescription, SpringSecurityModuleType.JWT),
                AuthenticableUserDetailsServiceGenerator.generate(applicationDescription),
                SpringSecurityAuditorAwareGenerator.generate(applicationDescription),
                RoleEnumGenerator.generate(applicationDescription)
        );
    }
}
