package tech.amereta.generator.description.spring.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.model.SpringModelModuleDomainTypeGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleDomainTypeDescription extends AbstractSpringModelModuleTypeDescription {

    @JsonProperty("id")
    private String idType = "UUID";
    private Boolean authorizable = false;
    private List<SpringModelModuleDomainTypeFieldDescription> fields;

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringModelModuleDomainTypeGenerator();
    }
}
