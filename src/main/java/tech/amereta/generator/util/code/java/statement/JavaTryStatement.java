package tech.amereta.generator.util.code.java.statement;

import tech.amereta.generator.util.code.Statement;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JavaTryStatement implements Statement {

    private List<Statement> statements;
    private List<Catch> catches;

    public static JavaTryStatement builder() {
        return new JavaTryStatement();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.println("try {");
        writer.indented(() -> {
            writer.println(statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator())));
        });
        writer.print("} ");
        writer.print(catches.stream().map(Catch::render).collect(Collectors.joining()));
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = new ArrayList<>();
        imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).toList());
        imports.addAll(catches.stream().map(Catch::imports).flatMap(Set::stream).toList());
        return new LinkedHashSet<>(imports);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public JavaTryStatement statements(List<Statement> statements) {
        setStatements(statements);
        return this;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Catch> getCatches() {
        return catches;
    }

    public JavaTryStatement catches(List<Catch> catches) {
        setCatches(catches);
        return this;
    }

    public void setCatches(List<Catch> catches) {
        this.catches = catches;
    }

    public static class Catch {

        private String exception;
        private String name;
        private List<Statement> statements;

        public static Catch builder() {
            return new Catch();
        }

        public String render() {
            IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
            writer.println("catch (" + JavaSourceCodeWriter.getUnqualifiedName(exception) + " " + name + ") {");
            writer.indented(() -> {
                writer.println(statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator())));
            });
            writer.print("} ");
            return writer.render();
        }

        public Set<String> imports() {
            List<String> imports = new ArrayList<>();
            if (JavaSourceCodeWriter.requiresImport(exception)) imports.add(exception);
            imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).toList());
            return new LinkedHashSet<>(imports);
        }

        public String getException() {
            return exception;
        }

        public Catch exception(String exception) {
            setException(exception);
            return this;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        public String getName() {
            return name;
        }

        public Catch name(String name) {
            setName(name);
            return this;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Statement> getStatements() {
            return statements;
        }

        public Catch statements(List<Statement> statements) {
            setStatements(statements);
            return this;
        }

        public void setStatements(List<Statement> statements) {
            this.statements = statements;
        }
    }
}
