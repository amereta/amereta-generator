package tech.amereta.generator.description.spring.security;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.security.type.AbstractSpringSecurityModuleTypeDescription;
import tech.amereta.generator.description.spring.security.type.SpringSecurityModuleJWTTypeDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.security.SpringSecurityModuleGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringSecurityModuleDescription extends AbstractSpringModuleDescription {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "authenticator",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringSecurityModuleJWTTypeDescription.class, name = "JWT"),
    })
    @Valid
    private AbstractSpringSecurityModuleTypeDescription security;

    @Override
    public AbstractSpringModuleGenerator getGenerator() {
        return new SpringSecurityModuleGenerator();
    }
}
