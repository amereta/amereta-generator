package tech.amereta.generator.description.spring.db.type;

import lombok.NoArgsConstructor;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.db.SpringDBModuleMySQLTypeGenerator;

@NoArgsConstructor
public final class SpringDBModuleMySQLTypeDescription extends AbstractSpringDBModuleTypeDescription {

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringDBModuleMySQLTypeGenerator();
    }
}
