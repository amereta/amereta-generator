package tech.amereta.core.service.writer.java.statement;

import lombok.Builder;
import tech.amereta.core.service.writer.Expression;
import tech.amereta.core.service.writer.Statement;

import java.util.Set;

/**
 * A return statement.
 */
public record JavaReturnStatement(Expression expression) implements Statement {

    @Builder
    public JavaReturnStatement {
    }

    @Override
    public String render() {
        return "return " + expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
