package tech.amereta.generator.util.code.java.statement;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.Statement;

import java.util.Set;


public final class JavaAssignStatement implements Statement {

    private String variable;
    private Expression expression;

    public static JavaAssignStatement builder() {
        return new JavaAssignStatement();
    }

    @Override
    public String render() {
        return this.variable + " = " + this.expression.render() + ";";
    }

    @Override
    public Set<String> imports() {
        return this.expression.imports();
    }

    public String getVariable() {
        return variable;
    }

    public JavaAssignStatement variable(String variable) {
        setVariable(variable);
        return this;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Expression getExpression() {
        return expression;
    }

    public JavaAssignStatement expression(Expression expression) {
        setExpression(expression);
        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
