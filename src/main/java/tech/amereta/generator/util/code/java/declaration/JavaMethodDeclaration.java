package tech.amereta.generator.util.code.java.declaration;

import tech.amereta.generator.util.code.Declaration;
import tech.amereta.generator.util.code.Statement;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Declaration of a method written in Java.
 */
public final class JavaMethodDeclaration implements Declaration {

    private List<JavaAnnotation> annotations = new ArrayList<>();
    private List<Parameter> parameters = new LinkedList<>();
    private List<Statement> statements = new LinkedList<>();
    private List<String> exceptions = new LinkedList<>();
    private String name;
    private String returnType;
    private JavaModifier modifiers;

    public static JavaMethodDeclaration builder() {
        return new JavaMethodDeclaration();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print(renderAnnotations());
        if (this.modifiers != null) writer.print(this.modifiers.render());
        writer.print(renderReturnType());
        writer.print(this.parameters.stream()
                .map((parameter) -> {
                    if (parameter.getGenericTypes().isEmpty())
                        return ((parameter.modifiers != null) ? parameter.modifiers.render() + " " : "") + renderSimpleParameter(parameter);
                    else
                        return ((parameter.modifiers != null) ? parameter.modifiers.render() + " " : "") + renderGenericParameter(parameter);
                })
                .collect(Collectors.joining(", ")));
        if (!this.exceptions.isEmpty()) {
            writer.print(") throws ");
            writer.print(renderExceptions());
            writer.println(" {");
        } else {
            writer.println(") {");
        }
        writer.indented(() -> {
            getStatements().forEach(statement -> writer.println(statement.render()));
        });
        writer.println("}");
        writer.println();
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.returnType)) imports.add(this.returnType);
        imports.addAll(this.annotations.stream().map(JavaAnnotation::imports).flatMap(Collection::stream).toList());
        // TODO: add imports for parameters generic types
        imports.addAll(this.parameters.stream().map(Parameter::getType).filter(JavaSourceCodeWriter::requiresImport).toList());
        imports.addAll(this.statements.stream().map(Statement::imports).flatMap(Collection::stream).toList());
        imports.addAll(exceptions.stream().filter(JavaSourceCodeWriter::requiresImport).toList());
        return new LinkedHashSet<>(imports);
    }

    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public JavaMethodDeclaration annotations(List<JavaAnnotation> annotations) {
        setAnnotations(annotations);
        return this;
    }

    public void setAnnotations(List<JavaAnnotation> annotations) {
        this.annotations = annotations;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public JavaMethodDeclaration parameters(List<Parameter> parameters) {
        setParameters(parameters);
        return this;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public JavaMethodDeclaration statements(List<Statement> statements) {
        setStatements(statements);
        return this;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public JavaMethodDeclaration exceptions(List<String> exceptions) {
        setExceptions(exceptions);
        return this;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    public String getName() {
        return name;
    }

    public JavaMethodDeclaration name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public JavaMethodDeclaration returnType(String returnType) {
        setReturnType(returnType);
        return this;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public JavaModifier getModifiers() {
        return modifiers;
    }

    public JavaMethodDeclaration modifiers(JavaModifier modifiers) {
        setModifiers(modifiers);
        return this;
    }

    public void setModifiers(JavaModifier modifiers) {
        this.modifiers = modifiers;
    }

    private String renderExceptions() {
        return this.exceptions.stream()
                .map(JavaSourceCodeWriter::getUnqualifiedName)
                .collect(Collectors.joining(", "));
    }

    private String renderGenericParameter(Parameter parameter) {
        return JavaSourceCodeWriter.getUnqualifiedName(parameter.getType())
                + "<" +
                parameter.getGenericTypes().stream()
                        .map(JavaSourceCodeWriter::getUnqualifiedName)
                        .collect(Collectors.joining(", "))
                + "> " + parameter.getName();
    }

    private String renderSimpleParameter(Parameter parameter) {
        return JavaSourceCodeWriter.getUnqualifiedName(parameter.getType()) + " " + parameter.getName();
    }

    private String renderReturnType() {
        return JavaSourceCodeWriter.getUnqualifiedName(this.returnType) + ifReturnValueWasEmptyDontAddWhiteSpace() + this.name + "(";
    }

    /**
     * For constructor methods, the return type is empty.
     *
     * @return If the return type is empty, return an empty string. Otherwise, return a whitespace.
     */
    private String ifReturnValueWasEmptyDontAddWhiteSpace() {
        return getReturnType().isEmpty() ? "" : " ";
    }

    private String renderAnnotations() {
        return this.annotations.stream().map(JavaAnnotation::render).collect(Collectors.joining());
    }

    public static class Parameter {

        private List<String> genericTypes = new LinkedList<>();
        private String type;
        private String name;
        private JavaModifier modifiers;

        public static Parameter builder() {
            return new Parameter();
        }

        public List<String> getGenericTypes() {
            return genericTypes;
        }

        public Parameter genericTypes(List<String> genericTypes) {
            setGenericTypes(genericTypes);
            return this;
        }

        public void setGenericTypes(List<String> genericTypes) {
            this.genericTypes = genericTypes;
        }

        public String getType() {
            return type;
        }

        public Parameter type(String type) {
            setType(type);
            return this;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Parameter name(String name) {
            setName(name);
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }

        public JavaModifier getModifiers() {
            return modifiers;
        }

        public Parameter modifiers(JavaModifier modifiers) {
            setModifiers(modifiers);
            return this;
        }

        public void setModifiers(JavaModifier modifiers) {
            this.modifiers = modifiers;
        }
    }
}
