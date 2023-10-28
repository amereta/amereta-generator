package tech.amereta.generator.description.spring.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.model.AbstractSpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;

import java.lang.annotation.Annotation;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleEnumTypeDescription extends AbstractSpringModelModuleTypeDescription {

    @NotNull(message = "enum's fields must not be null!")
    @Valid
    private List<SpringModelModuleEnumTypeFieldDescription> fields;

    @JsonIgnore
    private Class<? extends Annotation> generator = SpringBootEnumModelModuleGenerator.class;
}
