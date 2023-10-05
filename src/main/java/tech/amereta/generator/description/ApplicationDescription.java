package tech.amereta.generator.description;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDescription {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "applicationType"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpringBootApplicationDescription.class, name = "SPRING_BOOT")
    })
    @Valid
    private AbstractApplication application;
}
