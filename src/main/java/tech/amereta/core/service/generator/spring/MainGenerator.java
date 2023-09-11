package tech.amereta.core.service.generator.spring;

import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.util.StringFormatter;
import tech.amereta.core.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.util.code.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.util.code.java.expression.JavaValueExpression;
import tech.amereta.core.util.code.java.expression.JavaVariableExpression;
import tech.amereta.core.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.util.code.java.source.JavaCompilationUnit;
import tech.amereta.core.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.core.util.code.java.statement.JavaExpressionStatement;
import tech.amereta.core.util.code.java.util.JavaAnnotation;
import tech.amereta.core.util.code.java.util.JavaModifier;
import tech.amereta.core.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class MainGenerator extends AbstractSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final String className = StringFormatter.toPascalCase(applicationDescription.getName()).concat("Application");

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription))
                .name(className)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(className)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("GeneratedByAmereta")
                                                .build(),
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
                                                                                                        .value(className)
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
