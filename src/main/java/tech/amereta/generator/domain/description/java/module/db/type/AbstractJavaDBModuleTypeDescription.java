package tech.amereta.generator.domain.description.java.module.db.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractJavaDBModuleTypeDescription {

    private JavaDBModuleType type;
}
