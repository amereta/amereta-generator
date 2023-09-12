package tech.amereta.generator.util.code;

import java.util.Set;

public interface TypeDeclaration {

    String render();

    Set<String> imports();

}
