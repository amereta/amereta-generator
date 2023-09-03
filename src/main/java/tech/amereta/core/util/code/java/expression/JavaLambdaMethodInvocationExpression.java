package tech.amereta.core.util.code.java.expression;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.util.code.Expression;
import tech.amereta.core.util.code.java.JavaSourceCodeWriter;
import tech.amereta.core.util.code.java.expression.util.Operable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
public class JavaLambdaMethodInvocationExpression extends Operable implements Expression {

    private final String target;
    private final String invoke;

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + JavaSourceCodeWriter.getUnqualifiedName(this.target) + "::" + this.invoke;
        return JavaSourceCodeWriter.getUnqualifiedName(this.target) + "::" + this.invoke + super.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.target)) imports.add(this.target);
        return new LinkedHashSet<>(imports);
    }

}
