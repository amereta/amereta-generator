package tech.amereta.generator.util.code.java.expression;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.java.expression.util.Operable;

import java.util.*;
import java.util.stream.Collectors;

public class JavaBraceletExpression extends Operable implements Expression {

    private List<Expression> expressions = new LinkedList<>();
    private boolean bracelet = true;

    public JavaBraceletExpression builder() {
        return new JavaBraceletExpression();
    }

    @Override
    public String render() {
        if ("!".equals(super.render()))
            return super.render() + ((bracelet) ? "(" : "") + expressions.stream().map(Expression::render).collect(Collectors.joining()) + ((bracelet) ? ")" : "");
        return ((bracelet) ? "(" : "") + expressions.stream().map(Expression::render).collect(Collectors.joining()) + ((bracelet) ? ")" : "") + super.render();
    }

    @Override
    public Set<String> imports() {
        return this.expressions.stream().map(Expression::imports).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public JavaBraceletExpression expressions(List<Expression> expressions) {
        setExpressions(expressions);
        return this;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public boolean isBracelet() {
        return bracelet;
    }

    public JavaBraceletExpression bracelet(boolean bracelet) {
        setBracelet(bracelet);
        return this;
    }

    public void setBracelet(boolean bracelet) {
        this.bracelet = bracelet;
    }
}
