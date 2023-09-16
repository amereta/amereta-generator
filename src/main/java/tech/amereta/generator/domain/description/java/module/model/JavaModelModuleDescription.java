package tech.amereta.generator.domain.description.java.module.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleGenerator;
import tech.amereta.generator.service.spring.module.SpringModelModuleGenerator;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaModelModuleDescription extends AbstractJavaModuleDescription {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = JavaModelModuleDomainTypeDescription.class, name = "DOMAIN"),
    })
    private List<AbstractJavaModuleTypeDescription> models = new LinkedList<>();

    @Override
    public AbstractSpringModuleGenerator getGenerator() {
        return new SpringModelModuleGenerator();
    }
}
