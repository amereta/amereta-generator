package tech.amereta.generator.description.spring.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.db.type.SpringDBModuleMySQLTypeDescription;
import tech.amereta.generator.description.spring.db.type.SpringDBModulePostgreSQLTypeDescription;

import java.lang.annotation.Annotation;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringDBModuleDescription extends AbstractSpringModuleDescription {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "name",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringDBModuleMySQLTypeDescription.class, name = "MYSQL"),
            @JsonSubTypes.Type(value = SpringDBModulePostgreSQLTypeDescription.class, name = "POSTGRESQL"),
    })
    @Valid
    private AbstractSpringDBModuleTypeDescription db;

    @JsonIgnore
    private Class<? extends Annotation> generator = SpringBootDBModuleGenerator.class;
}
