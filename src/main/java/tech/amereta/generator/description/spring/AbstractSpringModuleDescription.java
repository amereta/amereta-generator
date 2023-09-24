package tech.amereta.generator.description.spring;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModuleDescription {

    @JsonProperty("module")
    private SpringModuleType type;

    public abstract AbstractSpringModuleGenerator getGenerator();
}
