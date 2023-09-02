package tech.amereta.core.domain.description.java.type;

import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.domain.description.java.JavaTypeDescription;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class JavaDAOTypeDescription extends JavaTypeDescription {

    @Builder.Default
    private boolean storeCreatedDate = false;
    @Builder.Default
    private boolean storeUpdatedDate = false;

}
