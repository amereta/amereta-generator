package tech.amereta.generator.description.spring.model.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModelModuleTypeDescription extends AbstractSpringModuleTypeDescription {

    private SpringModelModuleType type;
    private String name;
}
