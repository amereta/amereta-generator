package tech.amereta.generator.description.spring.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;
import tech.amereta.generator.description.spring.security.type.SpringSecurityModuleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringSecurityModuleTypeDescription implements SpringModuleTypeDescription {

    @JsonProperty("authenticator")
    private SpringSecurityModuleType type;

    private SpringSecurityEncoder encoder = SpringSecurityEncoder.BCRYPT;
}
