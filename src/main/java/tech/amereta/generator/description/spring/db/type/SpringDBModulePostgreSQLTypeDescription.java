package tech.amereta.generator.description.spring.db.type;

import lombok.NoArgsConstructor;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.db.SpringDBModulePostgreSQLTypeGenerator;

@NoArgsConstructor
public final class SpringDBModulePostgreSQLTypeDescription extends SpringDBModuleTypeDescription {

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringDBModulePostgreSQLTypeGenerator();
    }
}
