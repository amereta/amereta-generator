package tech.amereta.generator.description.spring.model.type;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SpringModelModuleTypeDescription implements SpringModuleTypeDescription {

    private SpringModelType type;

    @NotNull(message = "model's name must not be null!")
    private String name;
}
