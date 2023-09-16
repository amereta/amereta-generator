package tech.amereta.generator.domain.description.java.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleGenerator;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractJavaModuleDescription {

    @JsonProperty("module")
    private JavaModuleType type;

    public abstract AbstractSpringModuleGenerator getGenerator();
}
