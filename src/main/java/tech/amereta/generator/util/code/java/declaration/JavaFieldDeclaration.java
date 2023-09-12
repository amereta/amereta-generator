package tech.amereta.generator.util.code.java.declaration;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import tech.amereta.generator.util.code.Declaration;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Declaration of a field written in Java.
 */
@Builder
@Getter
public final class JavaFieldDeclaration implements Declaration {

    @Default
    private final List<JavaAnnotation> annotations = new ArrayList<>();
    private final JavaModifier modifiers;
    private final String name;
    private final String type;
    private final Object value;
    @Default
    List<String> genericTypes = new ArrayList<>();

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(this.type));
        if (!this.genericTypes.isEmpty()) {
            writer.print(renderGenericType());
        }
        writer.print(" " + this.name);
        if (this.value != null) {
            writer.print(initValue());
        }
        writer.println(";");
        return writer.render();
    }

    private String renderGenericType() {
        return "<" + this.genericTypes.stream()
                .map(JavaSourceCodeWriter::getUnqualifiedName)
                .collect(Collectors.joining(", ")) +
                "> ";
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.type)) imports.add(this.type);
        if (this.value instanceof Class && JavaSourceCodeWriter.requiresImport(((Class<?>) this.value).getName()))
            imports.add(((Class<?>) this.value).getName());
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).toList());
        return new LinkedHashSet<>(imports);
    }

    private String initValue() {
        if (this.value instanceof Class)
            return " = new " + ((Class<?>) this.value).getSimpleName() + "()";
        else {
            String value = " = " + this.value;
            if (this.value instanceof Long)
                value += "L";
            else if (this.value instanceof Float)
                value += "F";
            else if (this.value instanceof Double)
                value += "D";

            return value;
        }
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
