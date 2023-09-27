package tech.amereta.generator.description.spring.model.type.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleDomainTypeFieldDescription {

    private String name;
    private String dataType;
    private Object defaultValue;
    private List<String> genericTypes = new LinkedList<>();
    private List<String> modifiers = new LinkedList<>();
    private Integer length;
    @JsonProperty("transient")
    private boolean isTransient = false;
    private boolean unique = false;
    private boolean nullable = true;
    private boolean updatable = true;
    private boolean excludeFromJson = false;
    private JavaFieldRelationDescription relation;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class JavaFieldRelationDescription {

        public enum Relation {

            ONE_TO_ONE("OneToOne"),
            ONE_TO_MANY("OneToMany"),
            MANY_TO_ONE("ManyToOne"),
            MANY_TO_MANY("ManyToMany");

            private final String name;

            Relation(String name) {
                this.name = name;
            }

            @JsonValue
            @Override
            public String toString() {
                return name;
            }

        }

        private Relation type;
        private String mappedBy;
        @Builder.Default
        private boolean join = false;

    }

}
