package tech.amereta.generator.description.spring.db.type;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaDBModuleMySQLTypeDescription extends AbstractJavaDBModuleTypeDescription {

    @Builder.Default
    private Boolean hasCache = false;
}
