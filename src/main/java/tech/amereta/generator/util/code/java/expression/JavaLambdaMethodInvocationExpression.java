package tech.amereta.generator.util.code.java.expression;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.expression.util.Operable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JavaLambdaMethodInvocationExpression extends Operable implements Expression {

    private String target;
    private String invoke;

    public static JavaLambdaMethodInvocationExpression builder() {
        return new JavaLambdaMethodInvocationExpression();
    }

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

    public String getTarget() {
        return target;
    }

    public JavaLambdaMethodInvocationExpression target(String target) {
        setTarget(target);
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInvoke() {
        return invoke;
    }

    public JavaLambdaMethodInvocationExpression invoke(String invoke) {
        setInvoke(invoke);
        return this;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}
