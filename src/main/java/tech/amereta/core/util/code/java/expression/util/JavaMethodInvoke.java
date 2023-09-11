package tech.amereta.core.util.code.java.expression.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tech.amereta.core.util.code.Expression;
import tech.amereta.core.util.code.formatting.IndentingWriter;
import tech.amereta.core.util.code.formatting.SimpleIndentStrategy;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
public final class JavaMethodInvoke {

    @Builder.Default
    private final List<Expression> arguments = new LinkedList<>();
    @Builder.Default
    private final Boolean breakLine = false;
    private final String method;

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
