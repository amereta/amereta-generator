package tech.amereta.generator.description.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;

import java.lang.annotation.Annotation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModuleDescription {

    @JsonProperty("module")
    private SpringModuleType type;

    public abstract Class<? extends Annotation> getGenerator();
}
