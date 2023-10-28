package tech.amereta.generator.description.spring.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModelModuleTypeDescription implements SpringModuleTypeDescription {

    private SpringModelType type;

    @NotNull(message = "model's name must not be null!")
    private String name;
}
