package tech.amereta.generator.util.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.generator.util.code.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
public class JavaNewInstanceExpression extends Operable implements Expression {

    @Builder.Default
    private final List<Expression> arguments = new LinkedList<>();
    @Builder.Default
    private final List<JavaMethodInvoke> invokes = new LinkedList<>();
    private final String name;

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + "new " + JavaSourceCodeWriter.getUnqualifiedName(this.name)//
                    + "("//
                    + this.arguments.stream().map(Expression::render).collect(Collectors.joining(", "))//
                    + ")"//
                    + this.invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining())//
                    + super.render();
        return "new " + JavaSourceCodeWriter.getUnqualifiedName(this.name)//
                + "("//
                + this.arguments.stream().map(Expression::render).collect(Collectors.joining(", "))//
                + ")"//
                + this.invokes.stream().map(JavaMethodInvoke::render).collect(Collectors.joining())//
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

}
