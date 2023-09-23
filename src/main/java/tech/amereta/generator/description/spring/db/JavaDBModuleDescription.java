package tech.amereta.generator.description.spring.db;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.description.spring.AbstractJavaModuleDescription;
import tech.amereta.generator.description.spring.db.type.AbstractJavaDBModuleTypeDescription;
import tech.amereta.generator.description.spring.db.type.JavaDBModuleMySQLTypeDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.SpringDBModuleGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaDBModuleDescription extends AbstractJavaModuleDescription {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = JavaDBModuleMySQLTypeDescription.class, name = "MYSQL"),
    })
    private AbstractJavaDBModuleTypeDescription db;

    @Override
    public AbstractSpringModuleGenerator getGenerator() {
        return new SpringDBModuleGenerator();
    }
}
