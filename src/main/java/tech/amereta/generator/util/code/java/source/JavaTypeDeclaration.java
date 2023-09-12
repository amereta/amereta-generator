package tech.amereta.generator.util.code.java.source;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import tech.amereta.generator.util.code.TypeDeclaration;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.declaration.JavaFieldDeclaration;
import tech.amereta.generator.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A declaration of a type written in Java.
 */
@Builder
@Getter
public final class JavaTypeDeclaration implements TypeDeclaration {

    @Default
    private List<JavaAnnotation> annotations = new ArrayList<>();
    @Default
    private final List<JavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
    @Default
    private final List<JavaMethodDeclaration> methodDeclarations = new ArrayList<>();
    private JavaModifier modifiers;
    private final JavaType type;
    private final String name;
    private String extendedClassName;
    private String implementedClassName;
    @Default
    private List<String> tailGenericTypes = new ArrayList<>();

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(this.type + " " + this.name);
        writer.print(implementedOrExtendedClassName());
        writer.println(" {");
        writer.println();
        writer.indented(() -> {
            this.fieldDeclarations.forEach(fieldDeclaration -> writer.println(fieldDeclaration.render()));
        });
        writer.indented(() -> {
            this.methodDeclarations.forEach(methodDeclaration -> writer.println(methodDeclaration.render()));
        });
        writer.print("}");
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).toList());
        imports.addAll(this.fieldDeclarations.stream().map(JavaFieldDeclaration::imports).flatMap(Collection::stream).toList());
        imports.addAll(this.methodDeclarations.stream().map(JavaMethodDeclaration::imports).flatMap(Collection::stream).toList());
        imports.addAll(tailGenericTypes);
        if (this.extendedClassName != null) imports.add(this.extendedClassName);
        if (this.implementedClassName != null) imports.add(this.implementedClassName);
        return new LinkedHashSet<>(imports);
    }

    private String implementedOrExtendedClassName() {
        String className = "";
        if (getExtendedClassName() != null)
            className += " extends " + JavaSourceCodeWriter.getUnqualifiedName(this.extendedClassName);
        else if (getImplementedClassName() != null)
            className += " implements " + JavaSourceCodeWriter.getUnqualifiedName(this.implementedClassName);
        if (!this.tailGenericTypes.isEmpty()) {
            className += renderGenericType();
        }
        return className;
    }

    private String renderGenericType() {
        return "<" + this.tailGenericTypes.stream()//
                .map(JavaSourceCodeWriter::getUnqualifiedName)//
                .collect(Collectors.joining(", ")) +//
                ">";
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
