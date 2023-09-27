package tech.amereta.generator.description.spring.model.type.field;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleEnumTypeFieldDescription {

    @NotNull(message = "enum field's name must not be null!")
    private String name;
}
