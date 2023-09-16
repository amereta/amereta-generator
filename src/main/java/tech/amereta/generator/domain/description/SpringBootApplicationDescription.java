package tech.amereta.generator.domain.description;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.domain.description.java.module.db.JavaDBModuleDescription;
import tech.amereta.generator.domain.description.java.module.model.JavaModelModuleDescription;
import tech.amereta.generator.service.spring.SpringBootApplicationGeneratorService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpringBootApplicationDescription implements AbstractApplication {

    @NotNull
    private String name;

    @NotNull
    @JsonProperty("package")
    private String packageName;

    @Builder.Default
    private String description = "Generated by Amereta!";

    @Builder.Default
    private String javaVersion = "17";

    @Builder.Default
    private String springVersion = "3.1.3";

    @Builder.Default
    private String port = "8080";

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "module",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = JavaModelModuleDescription.class, name = "MODEL"),
            @JsonSubTypes.Type(value = JavaDBModuleDescription.class, name = "DB")
    })
    private List<AbstractJavaModuleDescription> modules;

    @Builder.Default
    @JsonIgnore
    private Class<?> generator = SpringBootApplicationGeneratorService.class;
}
