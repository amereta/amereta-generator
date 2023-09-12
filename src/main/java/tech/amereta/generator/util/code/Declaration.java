package tech.amereta.generator.util.code;

import java.util.Set;

public interface Declaration {

    String render();

    Set<String> imports();

}
