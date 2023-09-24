package tech.amereta.generator.description.spring.db.type;

import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.db.SpringDBModuleMySQLTypeGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringDBModuleMySQLTypeDescription extends AbstractSpringDBModuleTypeDescription {

    @Builder.Default
    private Boolean hasCache = false;

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringDBModuleMySQLTypeGenerator();
    }
}
