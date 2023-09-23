package tech.amereta.generator.util.code.java.expression.util;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.formatting.IndentingWriter;

import java.util.*;
import java.util.stream.Collectors;

public final class JavaMethodInvoke {

    private List<Expression> arguments = new LinkedList<>();
    private Boolean breakLine = false;
    private String method;

    public static JavaMethodInvoke builder() {
        return new JavaMethodInvoke();
    }

    public String render() {
        return printTabIfBreakLine()
                + "."
                + getUnqualifiedName(method)
                + "("
                + arguments.stream().map(Expression::render).collect(Collectors.joining(", "))
                + ")";
    }

    public Set<String> imports() {
        return this.arguments.stream().map(Expression::imports).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public JavaMethodInvoke arguments(List<Expression> arguments) {
        setArguments(arguments);
        return this;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    public Boolean getBreakLine() {
        return breakLine;
    }

    public JavaMethodInvoke breakLine(Boolean breakLine) {
        setBreakLine(breakLine);
        return this;
    }

    public void setBreakLine(Boolean breakLine) {
        this.breakLine = breakLine;
    }

    public String getMethod() {
        return method;
    }

    public JavaMethodInvoke method(String method) {
        setMethod(method);
        return this;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    private String getUnqualifiedName(String name) {
        if (!name.contains(".") || name.split("\\.").length <= 2) {
            return name;
        }
        return name.split("\\.")[name.split("\\.").length - 2] + "." + name.split("\\.")[name.split("\\.").length - 1];
    }

    private String printTabIfBreakLine() {
        return (breakLine) ? "\n" + IndentingWriter.DEFAULT_INDENT : "";
    }
}
