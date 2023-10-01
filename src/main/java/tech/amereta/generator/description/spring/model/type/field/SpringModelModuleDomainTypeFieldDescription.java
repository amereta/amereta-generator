package tech.amereta.generator.description.spring.model.type.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleDomainTypeFieldDescription {

    @NotNull(message = "domain field's name must not be null!")
    private String name;

    @NotNull(message = "domain field's dataType must not be null!")
    private SpringDataType dataType;

    private Object defaultValue;

    @Builder.Default
    private List<String> genericTypes = new LinkedList<>();

    @Builder.Default
    private List<String> modifiers = new LinkedList<>();

    private Integer length;

    @JsonProperty("transient")
    @Builder.Default
    private boolean isTransient = false;

    @JsonIgnore
    @Builder.Default
    private boolean primaryKey = false;

    @Builder.Default
    private boolean unique = false;

    @Builder.Default
    private boolean nullable = true;

    @Builder.Default
    private boolean updatable = true;

    @Builder.Default
    private boolean excludeFromJson = false;
}
