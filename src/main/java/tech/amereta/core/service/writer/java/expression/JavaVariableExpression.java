package tech.amereta.core.service.writer.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.service.writer.Expression;
import tech.amereta.core.service.writer.java.expression.util.Operable;

import java.util.LinkedHashSet;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaVariableExpression extends Operable implements Expression {

    private final String variable;

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + variable;
        return variable + super.render();
    }

    @Override
    public Set<String> imports() {
        return new LinkedHashSet<>();
    }

}
