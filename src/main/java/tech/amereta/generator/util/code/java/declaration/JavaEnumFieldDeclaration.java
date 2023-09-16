package tech.amereta.generator.util.code.java.declaration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tech.amereta.generator.util.code.Declaration;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
public class JavaEnumFieldDeclaration extends AbstractJavaFieldDeclaration {

    private String name;

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print(name.toUpperCase() + ",");
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        return Set.of();
    }
}
