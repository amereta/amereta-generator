package tech.amereta.generator.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DataTypeConstraintValidator.class)
public @interface DataTypeValidator {
    String message() default "dataType is not valid here!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    SpringDataType[] values();
}
