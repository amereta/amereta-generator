package tech.amereta.generator.description.spring.db.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringDBModuleTypeDescription implements SpringModuleTypeDescription {

    @JsonProperty("name")
    private SpringDBModuleType type;

    private String username = "username";

    private String password = "password";
}
