package tech.amereta.generator.description.spring.security.type;


import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.security.SpringSecurityModuleJWTTypeGenerator;

public class SpringSecurityModuleJWTTypeDescription extends AbstractSpringSecurityModuleTypeDescription {

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringSecurityModuleJWTTypeGenerator();
    }
}
