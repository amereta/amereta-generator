package tech.amereta.generator.util.code.java.declaration;

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
public final class JavaFieldDeclaration extends AbstractJavaFieldDeclaration {

    private List<JavaAnnotation> annotations = new ArrayList<>();
    private List<String> genericTypes = new ArrayList<>();
    private JavaModifier modifiers;
    private String name;
    private String dataType;
    private Object value;

    public static JavaFieldDeclaration builder() {
        return new JavaFieldDeclaration();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print(renderAnnotations());
        writer.print(this.modifiers.render());
        writer.print(JavaSourceCodeWriter.getUnqualifiedName(this.dataType));
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
        if (JavaSourceCodeWriter.requiresImport(this.dataType)) imports.add(this.dataType);
        if (this.value instanceof Class && JavaSourceCodeWriter.requiresImport(((Class<?>) this.value).getName()))
            imports.add(((Class<?>) this.value).getName());
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).toList());
        return new LinkedHashSet<>(imports);
    }

    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public JavaFieldDeclaration annotations(List<JavaAnnotation> annotations) {
        setAnnotations(annotations);
        return this;
    }

    public void setAnnotations(List<JavaAnnotation> annotations) {
        this.annotations = annotations;
    }

    public JavaModifier getModifiers() {
        return modifiers;
    }

    public JavaFieldDeclaration modifiers(JavaModifier modifiers) {
        setModifiers(modifiers);
        return this;
    }

    public void setModifiers(JavaModifier modifiers) {
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public JavaFieldDeclaration name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public JavaFieldDeclaration dataType(String dataType) {
        setDataType(dataType);
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public JavaFieldDeclaration value(Object value) {
        setValue(value);
        return this;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<String> getGenericTypes() {
        return genericTypes;
    }

    public JavaFieldDeclaration genericTypes(List<String> genericTypes) {
        setGenericTypes(genericTypes);
        return this;
    }

    public void setGenericTypes(List<String> genericTypes) {
        this.genericTypes = genericTypes;
    }

    private String initValue() {
        if (this.value instanceof Class)
            return " = new " + ((Class<?>) this.value).getSimpleName() + "()";
        else if (this.value instanceof String)
            return " = \"" + this.value + "\"";
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
