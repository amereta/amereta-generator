package tech.amereta.generator.description.spring.model.type;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.model.SpringModelModuleEnumTypeGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleEnumTypeDescription extends AbstractSpringModelModuleTypeDescription {

    @NotNull(message = "enum's fields must not be null!")
    @Valid
    private List<SpringModelModuleEnumTypeFieldDescription> fields;

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringModelModuleEnumTypeGenerator();
    }
}
