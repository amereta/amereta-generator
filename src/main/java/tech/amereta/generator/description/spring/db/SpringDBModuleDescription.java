package tech.amereta.generator.description.spring.db;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.db.type.AbstractSpringDBModuleTypeDescription;
import tech.amereta.generator.description.spring.db.type.SpringDBModuleMySQLTypeDescription;
import tech.amereta.generator.description.spring.db.type.SpringDBModulePostgreSQLTypeDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.db.SpringDBModuleGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringDBModuleDescription extends AbstractSpringModuleDescription {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringDBModuleMySQLTypeDescription.class, name = "MYSQL"),
            @JsonSubTypes.Type(value = SpringDBModulePostgreSQLTypeDescription.class, name = "POSTGRESQL"),
    })
    private AbstractSpringDBModuleTypeDescription db;

    @Override
    public AbstractSpringModuleGenerator getGenerator() {
        return new SpringDBModuleGenerator();
    }
}
