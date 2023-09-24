package tech.amereta.generator.description.spring.model.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModelModuleTypeDescription implements AbstractSpringModuleTypeDescription {

    private SpringModelModuleType type;
    private String name;
}
