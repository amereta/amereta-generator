package tech.amereta.generator.description.spring.model.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.service.spring.AbstractSpringModuleTypeGenerator;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractJavaModelModuleTypeDescription {

    private JavaModelModuleType type;
    private String name;

    public abstract AbstractSpringModuleTypeGenerator getGenerator();
}
