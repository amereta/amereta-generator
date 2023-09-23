package tech.amereta.generator.util.code.java;

import tech.amereta.generator.util.code.TypeDeclaration;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.generator.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A declaration of a type written in Java.
 */
public final class JavaTypeDeclaration implements TypeDeclaration {

    private List<JavaAnnotation> annotations = new ArrayList<>();
    private List<AbstractJavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
    private List<JavaMethodDeclaration> methodDeclarations = new ArrayList<>();
    private List<String> tailGenericTypes = new ArrayList<>();
    private JavaModifier modifiers;
    private JavaType type;
    private String name;
    private String extendedClassName;
    private String implementedClassName;

    public static JavaTypeDeclaration builder() {
        return new JavaTypeDeclaration();
    }

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
        imports.addAll(this.fieldDeclarations.stream().map(AbstractJavaFieldDeclaration::imports).flatMap(Collection::stream).toList());
        imports.addAll(this.methodDeclarations.stream().map(JavaMethodDeclaration::imports).flatMap(Collection::stream).toList());
        imports.addAll(this.tailGenericTypes.stream().filter(JavaSourceCodeWriter::requiresImport).toList());
        if (JavaSourceCodeWriter.requiresImport(this.extendedClassName)) imports.add(this.extendedClassName);
        if (JavaSourceCodeWriter.requiresImport(this.implementedClassName)) imports.add(this.implementedClassName);
        return new LinkedHashSet<>(imports);
    }

    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public JavaTypeDeclaration annotations(List<JavaAnnotation> annotations) {
        setAnnotations(annotations);
        return this;
    }

    public void setAnnotations(List<JavaAnnotation> annotations) {
        this.annotations = annotations;
    }

    public List<AbstractJavaFieldDeclaration> getFieldDeclarations() {
        return fieldDeclarations;
    }

    public JavaTypeDeclaration fieldDeclarations(List<AbstractJavaFieldDeclaration> fieldDeclarations) {
        setFieldDeclarations(fieldDeclarations);
        return this;
    }

    public void setFieldDeclarations(List<AbstractJavaFieldDeclaration> fieldDeclarations) {
        this.fieldDeclarations = fieldDeclarations;
    }

    public List<JavaMethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    public JavaTypeDeclaration methodDeclarations(List<JavaMethodDeclaration> methodDeclarations) {
        setMethodDeclarations(methodDeclarations);
        return this;
    }

    public void setMethodDeclarations(List<JavaMethodDeclaration> methodDeclarations) {
        this.methodDeclarations = methodDeclarations;
    }

    public List<String> getTailGenericTypes() {
        return tailGenericTypes;
    }

    public JavaTypeDeclaration tailGenericTypes(List<String> tailGenericTypes) {
        setTailGenericTypes(tailGenericTypes);
        return this;
    }

    public void setTailGenericTypes(List<String> tailGenericTypes) {
        this.tailGenericTypes = tailGenericTypes;
    }

    public JavaModifier getModifiers() {
        return modifiers;
    }

    public JavaTypeDeclaration modifiers(JavaModifier modifiers) {
        setModifiers(modifiers);
        return this;
    }

    public void setModifiers(JavaModifier modifiers) {
        this.modifiers = modifiers;
    }

    public JavaType getType() {
        return type;
    }

    public JavaTypeDeclaration type(JavaType type) {
        setType(type);
        return this;
    }

    public void setType(JavaType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public JavaTypeDeclaration name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendedClassName() {
        return extendedClassName;
    }

    public JavaTypeDeclaration extendedClassName(String extendedClassName) {
        setExtendedClassName(extendedClassName);
        return this;
    }

    public void setExtendedClassName(String extendedClassName) {
        this.extendedClassName = extendedClassName;
    }

    public String getImplementedClassName() {
        return implementedClassName;
    }

    public JavaTypeDeclaration implementedClassName(String implementedClassName) {
        setImplementedClassName(implementedClassName);
        return this;
    }

    public void setImplementedClassName(String implementedClassName) {
        this.implementedClassName = implementedClassName;
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
        return "<" + this.tailGenericTypes.stream()
                .map(JavaSourceCodeWriter::getUnqualifiedName)
                .collect(Collectors.joining(", ")) +
                ">";
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

}
