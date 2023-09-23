package tech.amereta.generator.util.code.java;

import tech.amereta.generator.util.code.CompilationUnit;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Java-compilation unit that represents an individual source file.
 */
public final class JavaCompilationUnit implements CompilationUnit {

    private List<JavaTypeDeclaration> typeDeclarations = new ArrayList<>();
    private String packageName;
    private String name;

    public static JavaCompilationUnit builder() {
        return new JavaCompilationUnit();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.println("package " + this.packageName + ";");
        writer.println();
        writeImports(writer);
        getTypeDeclarations().forEach(typeDeclaration -> writer.println(typeDeclaration.render()));
        return writer.render();
    }

    public void writeImports(IndentingWriter writer) {
        final Set<String> imports = this.typeDeclarations.stream().map(JavaTypeDeclaration::imports).flatMap(Collection::stream).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        if (!imports.isEmpty()) {
            for (String importedType : imports) {
                writer.println("import " + importedType + ";");
            }
            writer.println();
        }
    }

    public List<JavaTypeDeclaration> getTypeDeclarations() {
        return Collections.unmodifiableList(this.typeDeclarations);
    }

    public JavaCompilationUnit typeDeclarations(List<JavaTypeDeclaration> typeDeclarations) {
        setTypeDeclarations(typeDeclarations);
        return this;
    }

    public void setTypeDeclarations(List<JavaTypeDeclaration> typeDeclarations) {
        this.typeDeclarations = typeDeclarations;
    }

    public String getPackageName() {
        return packageName;
    }

    public JavaCompilationUnit packageName(String packageName) {
        setPackageName(packageName);
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public JavaCompilationUnit name(String name) {
        setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
}
