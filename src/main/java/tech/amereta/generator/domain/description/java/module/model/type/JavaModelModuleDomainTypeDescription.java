package tech.amereta.generator.domain.description.java.module.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.domain.description.java.module.model.type.field.JavaModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.ModelModuleDomainTypeGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class JavaModelModuleDomainTypeDescription extends AbstractJavaModelModuleTypeDescription {

    @JsonProperty("id")
    private String idType = "UUID";
    private Boolean authorizable = false;
    private List<JavaModelModuleDomainTypeFieldDescription> fields;

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new ModelModuleDomainTypeGenerator();
    }
}
