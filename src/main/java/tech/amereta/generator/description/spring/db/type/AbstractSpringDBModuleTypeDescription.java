package tech.amereta.generator.description.spring.db.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringDBModuleTypeDescription implements AbstractSpringModuleTypeDescription {

    @JsonProperty("name")
    private SpringDBModuleType type;
}
