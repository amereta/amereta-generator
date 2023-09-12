package tech.amereta.generator.util.code.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.expression.util.Operable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaValueExpression extends Operable implements Expression {

    private final String value;
    private final Class<?> type;

    @Override
    public String render() {
        final String renderedValue;
        if (this.type == null && this.value.equals("null")) {
            renderedValue = "null";
        } else if (this.type != null && this.type.equals(Class.class)) {
            renderedValue = String.format("%s.class", JavaSourceCodeWriter.getUnqualifiedName(this.value));
        } else if (this.type != null && Enum.class.isAssignableFrom(this.type)) {
            String enumValue = this.value.substring(this.value.lastIndexOf(".") + 1);
            String enumClass = this.value.substring(0, this.value.lastIndexOf("."));
            renderedValue = String.format("%s.%s", JavaSourceCodeWriter.getUnqualifiedName(enumClass), enumValue);
        } else if (this.type != null && this.type.equals(String.class)) {
            renderedValue = String.format("\"%s\"", this.value);
        } else if (this.type != null && this.type.equals(Float.class)) {
            renderedValue = String.format("%sF", this.value);
        } else if (this.type != null && this.type.equals(Double.class)) {
            renderedValue = String.format("%sD", this.value);
        } else if (this.type != null && this.type.equals(Long.class)) {
            renderedValue = String.format("%sL", this.value);
        } else {
            renderedValue = String.format("%s", this.value);
        }
        if ("!".equals(super.render()))
            return super.render() + renderedValue;
        return renderedValue + super.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.value)) {
            if (this.type == Class.class) imports.add(this.value);
            if (Enum.class.isAssignableFrom(this.type))
                imports.add(this.value.substring(0, this.value.lastIndexOf(".")));
        }
        return new LinkedHashSet<>(imports);
    }

}
