package tech.amereta.core.service.writer;

import java.util.Set;

public interface Declaration {

    String render();

    Set<String> imports();

}
