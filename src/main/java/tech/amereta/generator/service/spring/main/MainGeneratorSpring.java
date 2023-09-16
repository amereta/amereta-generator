package tech.amereta.generator.service.spring.main;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.generator.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.generator.util.code.java.expression.JavaMethodInvocationExpression;
import tech.amereta.generator.util.code.java.expression.JavaValueExpression;
import tech.amereta.generator.util.code.java.expression.JavaVariableExpression;
import tech.amereta.generator.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.statement.JavaExpressionStatement;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class MainGeneratorSpring extends AbstractSpringSourceCodeGenerator {

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
