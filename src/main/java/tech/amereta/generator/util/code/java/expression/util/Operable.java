package tech.amereta.generator.util.code.java.expression.util;

import tech.amereta.generator.util.code.java.util.JavaOperand;

public class Operable {

    private JavaOperand operand = null;

    public String render() {
        if (operand != null) {
            if (JavaOperand.NOT.equals(operand))
                return operand.getSymbol();
            return " " + operand.getSymbol() + " ";
        }
        return "";
    }

    public JavaOperand getOperand() {
        return operand;
    }

    public Operable operand(JavaOperand operand) {
        setOperand(operand);
        return this;
    }

    public void setOperand(JavaOperand operand) {
        this.operand = operand;
    }
}
