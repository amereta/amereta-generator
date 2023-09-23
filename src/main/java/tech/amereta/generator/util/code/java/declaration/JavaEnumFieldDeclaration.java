package tech.amereta.generator.util.code.java.declaration;

import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;

import java.util.Set;

public final class JavaEnumFieldDeclaration extends AbstractJavaFieldDeclaration {

    private String name;

    public static JavaEnumFieldDeclaration builder() {
        return new JavaEnumFieldDeclaration();
    }

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

    public String getName() {
        return name;
    }

    public JavaEnumFieldDeclaration name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
