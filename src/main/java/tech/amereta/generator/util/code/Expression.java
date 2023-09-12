package tech.amereta.generator.util.code;

import java.util.Set;

/**
 * A Java expression.
 */
public interface Expression {

    String render();

    Set<String> imports();

}
