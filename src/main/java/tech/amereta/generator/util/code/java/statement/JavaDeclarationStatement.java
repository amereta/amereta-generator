package tech.amereta.generator.util.code.java.statement;

import tech.amereta.generator.util.code.Expression;
import tech.amereta.generator.util.code.Statement;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;
import tech.amereta.generator.util.code.java.util.JavaModifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public final class JavaDeclarationStatement implements Statement {

    private List<String> genericTypes;
    private JavaModifier modifiers;
    private String name;
    private String dataType;
    private Expression expression;
    private boolean initialized;

    public static JavaDeclarationStatement builder() {
        return new JavaDeclarationStatement();
    }

    @Override
    public String render() {
        final StringBuilder str = new StringBuilder();
        if (modifiers != null) str.append(this.modifiers.render());
        str.append(JavaSourceCodeWriter.getUnqualifiedName(this.dataType));
        if (this.genericTypes != null) {
            str.append("<").append(this.genericTypes.stream()
                            .map(JavaSourceCodeWriter::getUnqualifiedName)
                            .collect(Collectors.joining(", ")))
                    .append("> ");
        }
        str.append(" ").append(this.name);
        if (this.initialized) str.append(" = ").append(this.expression.render());
        str.append(";");
        return str.toString();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        if (JavaSourceCodeWriter.requiresImport(this.dataType)) imports.add(this.dataType);
        if (this.initialized) imports.addAll(this.expression.imports());
        return new LinkedHashSet<>(imports);
    }

    public List<String> getGenericTypes() {
        return genericTypes;
    }

    public JavaDeclarationStatement genericTypes(List<String> genericTypes) {
        setGenericTypes(genericTypes);
        return this;
    }

    public void setGenericTypes(List<String> genericTypes) {
        this.genericTypes = genericTypes;
    }

    public JavaModifier getModifiers() {
        return modifiers;
    }

    public JavaDeclarationStatement modifiers(JavaModifier modifiers) {
        setModifiers(modifiers);
        return this;
    }

    public void setModifiers(JavaModifier modifiers) {
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public JavaDeclarationStatement name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public JavaDeclarationStatement dataType(String dataType) {
        setDataType(dataType);
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Expression getExpression() {
        return expression;
    }

    public JavaDeclarationStatement expression(Expression expression) {
        setExpression(expression);
        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public JavaDeclarationStatement initialized(boolean initialized) {
        setInitialized(initialized);
        return this;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
