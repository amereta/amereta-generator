package tech.amereta.core.util.code.java.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.util.code.Expression;
import tech.amereta.core.util.code.Statement;
import tech.amereta.core.util.code.formatting.IndentingWriter;
import tech.amereta.core.util.code.formatting.SimpleIndentStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
public class JavaLambdaExpression implements Expression {

    @Builder.Default
    private final List<String> consumer = new LinkedList<>();
    @Builder.Default
    private final List<Statement> statements = new LinkedList<>();

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.indented(() -> {
            writer.println("(" + String.join(", ", consumer) + ") -> {");
            writer.indented(() -> {
                getStatements().forEach(statement -> writer.println(statement.render()));
            });
            writer.print("}");
        });
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        return statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toSet());
    }
}
