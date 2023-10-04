package tech.amereta.generator.description.spring.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleFieldRelationDescription {

    @NotNull(message = "domain's relation type must not be null!")
    private SpringRelation relationType;

    @NotNull(message = "domain's relation side must not be null!")
    private String to;

    @Builder.Default
    private Boolean join = false;

    @JsonIgnore
    private SpringDataType joinDataType;
}
