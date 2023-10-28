package tech.amereta.generator.description.spring.db.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.db.AbstractSpringDBModuleTypeDescription;

import java.lang.annotation.Annotation;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class SpringDBModuleMySQLTypeDescription extends AbstractSpringDBModuleTypeDescription {

    @JsonIgnore
    private Class<? extends Annotation> generator = SpringBootMySQLDBModuleGenerator.class;
}
