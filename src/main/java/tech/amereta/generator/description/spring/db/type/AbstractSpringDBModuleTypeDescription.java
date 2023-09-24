package tech.amereta.generator.description.spring.db.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringDBModuleTypeDescription extends AbstractSpringModuleTypeDescription {

    private SpringDBModuleType type;
}
