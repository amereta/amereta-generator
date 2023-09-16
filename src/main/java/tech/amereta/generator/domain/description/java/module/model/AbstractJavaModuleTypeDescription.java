package tech.amereta.generator.domain.description.java.module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.service.spring.AbstractSpringModuleTypeGenerator;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractJavaModuleTypeDescription {

    private JavaModelModuleType type;
    private String name;

    public abstract AbstractSpringModuleTypeGenerator getGenerator();
}
