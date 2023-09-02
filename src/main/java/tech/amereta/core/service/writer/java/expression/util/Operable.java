package tech.amereta.core.service.writer.java.expression.util;

import lombok.Builder;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.service.writer.java.util.JavaOperand;

@SuperBuilder
public class Operable {

    @Builder.Default
    private JavaOperand operand = null;

    public String render() {
        if (operand != null) {
            if (JavaOperand.NOT.equals(operand))
                return operand.getSymbol();
            return " " + operand.getSymbol() + " ";
        }
        return "";
    }

}
