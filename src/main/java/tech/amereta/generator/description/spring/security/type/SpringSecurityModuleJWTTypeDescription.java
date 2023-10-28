package tech.amereta.generator.description.spring.security.type;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.security.AbstractSpringSecurityModuleTypeDescription;

import java.lang.annotation.Annotation;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SpringSecurityModuleJWTTypeDescription extends AbstractSpringSecurityModuleTypeDescription {

    @JsonIgnore
    private Class<? extends Annotation> generator = SpringBootJWTSecurityModuleGenerator.class;
}
