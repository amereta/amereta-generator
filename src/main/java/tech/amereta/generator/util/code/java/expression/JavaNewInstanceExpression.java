package tech.amereta.generator.util.code.java.expression;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.generator.util.code.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

public class JavaNewInstanceExpression extends Operable implements Expression {

    private List<Expression> arguments = new LinkedList<>();
    private List<JavaMethodInvoke> invokes = new LinkedList<>();
    private String name;

    public static JavaNewInstanceExpression builder() {
        return new JavaNewInstanceExpression();
    }

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + "new " + JavaSourceCodeWriter.getUnqualifiedName(this.name)
                    + "("
                    + this.arguments.stream().map(Expression::render).collect(Collectors.joining(", "))
                    + ")"
                    + this.invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining())
                    + super.render();
        return "new " + JavaSourceCodeWriter.getUnqualifiedName(this.name)
                + "("
                + this.arguments.stream().map(Expression::render).collect(Collectors.joining(", "))
                + ")"
                + this.invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining())
                + super.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.name)) imports.add(this.name);
        imports.addAll(this.arguments.stream().map(Expression::imports).flatMap(Collection::stream).toList());
        imports.addAll(this.invokes.stream().map(JavaMethodInvoke::imports).flatMap(Collection::stream).toList());
        return new LinkedHashSet<>(imports);
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public JavaNewInstanceExpression arguments(List<Expression> arguments) {
        setArguments(arguments);
        return this;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    public List<JavaMethodInvoke> getInvokes() {
        return invokes;
    }

    public JavaNewInstanceExpression invokes(List<JavaMethodInvoke> invokes) {
        setInvokes(invokes);
        return this;
    }

    public void setInvokes(List<JavaMethodInvoke> invokes) {
        this.invokes = invokes;
    }

    public String getName() {
        return name;
    }

    public JavaNewInstanceExpression name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
