package tech.amereta.generator.service.spring.generator.module.security.type;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.security.*;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;
import tech.amereta.lang.description.spring.SpringModuleTypeDescription;
import tech.amereta.lang.description.spring.security.type.SpringBootJWTSecurityModuleGenerator;
import tech.amereta.lang.description.spring.security.type.SpringSecurityModuleJWTTypeDescription;

import java.util.List;

@SpringBootJWTSecurityModuleGenerator
public final class SpringSecurityModuleJWTTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final SpringModuleTypeDescription typeDescription) {
        final SpringSecurityModuleJWTTypeDescription jwtTypeDescription = (SpringSecurityModuleJWTTypeDescription) typeDescription;

        return List.of(
                SecurityConfigurationGenerator.generate(applicationDescription, jwtTypeDescription),
                AuthenticableUserDetailsServiceGenerator.generate(applicationDescription),
                SpringSecurityAuditorAwareGenerator.generate(applicationDescription),
                RoleEnumGenerator.generate(applicationDescription),
                JWTModelGenerator.generate(applicationDescription),
                LoginViewModelGenerator.generate(applicationDescription),
                RegisterViewModelGenerator.generate(applicationDescription),
                AuthenticableDomainServiceGenerator.generate(applicationDescription),
                AuthenticateControllerGenerator.generate(applicationDescription)
        );
    }
}
