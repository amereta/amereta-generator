package tech.amereta.generator.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;

import java.util.Arrays;

public class DataTypeConstraintValidator implements ConstraintValidator<DataTypeValidator, SpringDataType> {

    private SpringDataType[] values;

    public final void initialize(final DataTypeValidator annotation) {
        values = annotation.values();
    }

    public final boolean isValid(final SpringDataType value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(this.values).anyMatch(item -> item == value);
    }
}
