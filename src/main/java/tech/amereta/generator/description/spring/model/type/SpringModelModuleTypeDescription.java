package tech.amereta.generator.description.spring.model.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SpringModelModuleTypeDescription implements SpringModuleTypeDescription {

    private SpringModelModuleType type;
    private String name;
}
