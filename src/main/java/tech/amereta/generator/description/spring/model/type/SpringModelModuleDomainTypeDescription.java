package tech.amereta.generator.description.spring.model.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.generator.module.model.SpringModelModuleDomainTypeGenerator;
import tech.amereta.generator.util.DataTypeValidator;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpringModelModuleDomainTypeDescription extends SpringModelModuleTypeDescription {

    @JsonProperty("id")
    @DataTypeValidator(values = {SpringDataType.UUID, SpringDataType.LONG})
    private SpringDataType idType = SpringDataType.UUID;

    private Boolean authorizable = false;

    @NotNull(message = "domain's fields must not be null!")
    @Valid
    private List<SpringModelModuleDomainTypeFieldDescription> fields = new ArrayList<>();

    @Valid
    private List<SpringFieldRelationDescription> relations = new ArrayList<>();

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new SpringModelModuleDomainTypeGenerator();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class SpringFieldRelationDescription {

        @NotNull(message = "domain's relation type must not be null!")
        private Relation relationType;

        @NotNull(message = "domain's relation side must not be null!")
        private String to;

        private Boolean join = false;

        @Getter
        public enum Relation {

            ONE_TO_ONE("OneToOne"),
            ONE_TO_MANY("OneToMany"),
            MANY_TO_ONE("ManyToOne"),
            MANY_TO_MANY("ManyToMany");

            private final String name;

            Relation(String name) {
                this.name = name;
            }
        }
    }
}
