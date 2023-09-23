package tech.amereta.generator.util.code.java.statement;

import tech.amereta.generator.util.code.Statement;
import tech.amereta.generator.util.code.formatting.IndentingWriter;
import tech.amereta.generator.util.code.formatting.SimpleIndentStrategy;
import tech.amereta.generator.util.code.java.expression.JavaBraceletExpression;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JavaControlStatement implements Statement {

    private List<If> ifs;
    private Else anElse;

    public static JavaControlStatement builder() {
        return new JavaControlStatement();
    }

    @Override
    public String render() {
        final IndentingWriter writer = new IndentingWriter(new SimpleIndentStrategy(IndentingWriter.DEFAULT_INDENT));
        writer.print("if (");
        writer.print(ifs.get(0).condition.render());
        writer.println(") {");
        writer.indented(() -> {
            writer.println(ifs.get(0).render());
        });
        if (ifs.size() > 1) {
            for (int i = 1; i < ifs.size(); i++) {
                writer.print("} else if (");
                writer.print(ifs.get(i).condition.render());
                writer.println(") {");
                int finalI = i;
                writer.indented(() -> {
                    writer.println(ifs.get(finalI).render());
                });
            }
        } else {
            writer.print("}");
        }
        if (anElse != null) {
            writer.println("} else {");
            writer.indented(() -> {
                writer.println(anElse.render());
            });
            writer.print("}");
        }
        return writer.render();
    }

    @Override
    public Set<String> imports() {
        final List<String> imports = ifs.stream().flatMap(ifStatement -> ifStatement.imports().stream()).collect(Collectors.toList());
        if (anElse != null)
            imports.addAll(anElse.imports());
        return new LinkedHashSet<>(imports);
    }

    public List<If> getIfs() {
        return ifs;
    }

    public JavaControlStatement ifs(List<If> ifs) {
        setIfs(ifs);
        return this;
    }

    public void setIfs(List<If> ifs) {
        this.ifs = ifs;
    }

    public Else getAnElse() {
        return anElse;
    }

    public JavaControlStatement anElse(Else anElse) {
        setAnElse(anElse);
        return this;
    }

    public void setAnElse(Else anElse) {
        this.anElse = anElse;
    }

    public static class If {

        private JavaBraceletExpression condition;
        private List<Statement> statements;

        public static If builder() {
            return new If();
        }

        protected String render() {
            return statements.stream().map(Statement::render).collect(Collectors.joining(System.lineSeparator()));
        }

        protected Set<String> imports() {
            final List<String> imports = new ArrayList<>();
            imports.addAll(condition.imports());
            imports.addAll(statements.stream().map(Statement::imports).flatMap(Set::stream).toList());
            return new LinkedHashSet<>(imports);
        }

        public JavaBraceletExpression getCondition() {
            return condition;
        }

        public If condition(JavaBraceletExpression condition) {
            setCondition(condition);
            return this;
        }

        public void setCondition(JavaBraceletExpression condition) {
            this.condition = condition;
        }

        public List<Statement> getStatements() {
            return statements;
        }

        public If statements(List<Statement> statements) {
            setStatements(statements);
            return this;
        }

        public void setStatements(List<Statement> statements) {
            this.statements = statements;
        }
    }

    public static class Else {

        private List<Statement> statements;

        public static Else builder() {
            return new Else();
        }

        protected String render() {
            return statements.stream().map(Statement::render).collect(Collectors.joining());
        }

        protected Set<String> imports() {
            return statements.stream().map(Statement::imports).flatMap(Set::stream).collect(Collectors.toCollection(LinkedHashSet::new));
        }

        public List<Statement> getStatements() {
            return statements;
        }

        public Else statements(List<Statement> statements) {
            setStatements(statements);
            return this;
        }

        public void setStatements(List<Statement> statements) {
            this.statements = statements;
        }
    }
}
