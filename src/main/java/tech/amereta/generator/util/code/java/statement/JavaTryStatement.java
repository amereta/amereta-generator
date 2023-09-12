package tech.amereta.generator.util.code.java.statement;

import lombok.Builder;
import tech.amereta.generator.util.code.Statement;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.JavaSourceCodeWriter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record JavaTryStatement(List<Statement> statements, List<Catch> catches) implements Statement {

    @Builder
    public JavaTryStatement {
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

    @Builder
    public static class Catch {

        public String exception;
        public String name;
        public List<Statement> statements;

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

    }
}
