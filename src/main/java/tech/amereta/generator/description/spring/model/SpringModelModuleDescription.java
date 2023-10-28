package tech.amereta.generator.description.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleEnumTypeDescription;

import java.lang.annotation.Annotation;
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
    @Valid
    private List<AbstractSpringModelModuleTypeDescription> models = new LinkedList<>();

    @JsonIgnore
    private Class<? extends Annotation> generator = SpringBootModelModuleGenerator.class;
}
