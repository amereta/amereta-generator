package tech.amereta.core.service.generator.spring.code;

import tech.amereta.core.service.writer.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.service.writer.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.service.writer.java.expression.JavaValueExpression;
import tech.amereta.core.service.writer.java.expression.JavaVariableExpression;
import tech.amereta.core.service.writer.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.service.writer.java.source.JavaCompilationUnit;
import tech.amereta.core.service.writer.java.source.JavaTypeDeclaration;
import tech.amereta.core.service.writer.java.statement.JavaExpressionStatement;
import tech.amereta.core.service.writer.java.util.JavaAnnotation;
import tech.amereta.core.service.writer.java.util.JavaModifier;
import tech.amereta.core.service.writer.java.util.JavaType;
import tech.amereta.core.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.List;

public class MainClassGenerator {

    public static JavaCompilationUnit generate(String name) {
        return JavaCompilationUnit.builder()
                .packageName("website.amereta.".concat(name.toLowerCase()))
                .name(StringFormatter.toPascalCase(name.concat("Application")))
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(StringFormatter.toPascalCase(name.concat("Application")))
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")
                                                .build()))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("main")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC | Modifier.STATIC)
                                                        .build())
                                                .returnType("void")
                                                .parameters(List.of(
                                                        JavaMethodDeclaration.Parameter.builder()
                                                                .type("java.lang.String[]")
                                                                .name("args")
                                                                .build()))
                                                .statements(List.of(
                                                        JavaExpressionStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()
                                                                        .target("org.springframework.boot.SpringApplication")
                                                                        .invokes(List.of(
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("run")
                                                                                        .arguments(List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .value(StringFormatter.toPascalCase(name.concat("Application")))
                                                                                                        .type(Class.class)
                                                                                                        .build(),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("args")
                                                                                                        .build()))
                                                                                        .build()))
                                                                        .build())
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }

}
