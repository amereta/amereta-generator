package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.core.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.java.expression.JavaValueExpression;
import tech.amereta.core.java.expression.JavaVariableExpression;
import tech.amereta.core.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.statement.JavaExpressionStatement;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;

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
                                        .modifiers(Modifier.PUBLIC))
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("GeneratedByAmereta"),
                                        JavaAnnotation.builder()
                                                .name("org.springframework.boot.autoconfigure.SpringBootApplication")))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("main")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC | Modifier.STATIC))
                                                .returnType("void")
                                                .parameters(List.of(
                                                        JavaMethodDeclaration.Parameter.builder()
                                                                .type("java.lang.String[]")
                                                                .name("args")))
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
                                                                                                        .type(Class.class),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("args"))))))))))));
    }

}
