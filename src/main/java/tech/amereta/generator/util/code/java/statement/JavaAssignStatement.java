package tech.amereta.generator.util.code.java.statement;

import lombok.Builder;
import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.Statement;

import java.util.Set;


public record JavaAssignStatement(String variable,
                                  Expression expression) implements Statement {

    @Builder
    public JavaAssignStatement {
    }

    @Override
    public String render() {
        return this.variable + " = " + this.expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

}
