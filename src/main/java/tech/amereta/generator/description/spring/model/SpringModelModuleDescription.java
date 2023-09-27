package tech.amereta.generator.description.spring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.model.type.AbstractSpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleEnumTypeDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.generator.module.model.SpringModelModuleGenerator;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleDescription extends AbstractSpringModuleDescription {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "type",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringModelModuleDomainTypeDescription.class, name = "DOMAIN"),
            @JsonSubTypes.Type(value = SpringModelModuleEnumTypeDescription.class, name = "ENUM"),
    })
    private List<AbstractSpringModelModuleTypeDescription> models = new LinkedList<>();

    @Override
    public AbstractSpringModuleGenerator getGenerator() {
        return new SpringModelModuleGenerator();
    }
}
