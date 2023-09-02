package tech.amereta.core.service.writer.java.statement;

import lombok.Builder;
import tech.amereta.core.service.writer.Expression;
import tech.amereta.core.service.writer.Statement;

import java.util.Set;

/**
 * A statement that contains a single expression.
 */
public record JavaExpressionStatement(Expression expression) implements Statement {

    @Builder
    public JavaExpressionStatement {
    }

    @Override
    public String render() {
        return expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
