package tech.amereta.generator.description.spring.model.type.field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.LinkedList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSpringModelModuleFieldDescription {

    private String name;
    private String dataType;
    private Object defaultValue;
    @Builder.Default
    private List<String> genericTypes = new LinkedList<>();
    @Builder.Default
    private List<String> modifiers = new LinkedList<>();
}
