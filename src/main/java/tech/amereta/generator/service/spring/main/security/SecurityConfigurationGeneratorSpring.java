package tech.amereta.generator.service.spring.main.security;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.generator.util.code.java.expression.JavaLambdaExpression;
import tech.amereta.generator.util.code.java.expression.JavaMethodInvocationExpression;
import tech.amereta.generator.util.code.java.expression.JavaValueExpression;
import tech.amereta.generator.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.statement.JavaExpressionStatement;
import tech.amereta.generator.util.code.java.statement.JavaReturnStatement;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class SecurityConfigurationGeneratorSpring extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "SecurityConfiguration";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".security")
                .name(CLASS_NAME)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(CLASS_NAME)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.security.config.annotation.web.configuration.EnableWebSecurity")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("org.springframework.context.annotation.Configuration")
                                                .build()))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("filterChain")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC)
                                                        .build())
                                                .returnType("org.springframework.security.web.SecurityFilterChain")
                                                .parameters(List.of(
                                                        JavaMethodDeclaration.Parameter.builder()
                                                                .type("org.springframework.security.config.annotation.web.builders.HttpSecurity")
                                                                .name("http")
                                                                .build()))
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.context.annotation.Bean")
                                                                .build()
                                                ))
                                                .exceptions(List.of("java.lang.Exception"))
                                                .statements(List.of(
                                                        JavaExpressionStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()
                                                                        .target("http")
                                                                        .invokes(List.of(
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("csrf")
                                                                                        .breakLine(true)
                                                                                        .arguments(List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("org.springframework.security.config.Customizer")
                                                                                                        .invokes(List.of(
                                                                                                                JavaMethodInvoke.builder()
                                                                                                                        .method("withDefaults")
                                                                                                                        .build()))
                                                                                                        .build()))
                                                                                        .build(),
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("authorizeHttpRequests")
                                                                                        .breakLine(true)
                                                                                        .arguments(List.of(
                                                                                                JavaLambdaExpression.builder()
                                                                                                        .consumer(List.of("authorize"))
                                                                                                        .statements(List.of(
                                                                                                                JavaExpressionStatement.builder()
                                                                                                                        .expression(JavaMethodInvocationExpression.builder()
                                                                                                                                .target("authorize")
                                                                                                                                .invokes(List.of(
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("requestMatchers")
                                                                                                                                                .breakLine(true)
                                                                                                                                                .arguments(List.of(
                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                .value("org.springframework.http.HttpMethod.OPTIONS")
                                                                                                                                                                .type(Enum.class)
                                                                                                                                                                .build(),
                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                .value("/**")
                                                                                                                                                                .type(String.class)
                                                                                                                                                                .build()))
                                                                                                                                                .build(),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("permitAll")
                                                                                                                                                .build(),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("anyRequest")
                                                                                                                                                .breakLine(true)
                                                                                                                                                .build(),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("authenticated")
                                                                                                                                                .build()))
                                                                                                                                .build())
                                                                                                                        .build()))
                                                                                                        .build()))
                                                                                        .build(),
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("sessionManagement")
                                                                                        .breakLine(true)
                                                                                        .arguments(List.of(
                                                                                                JavaLambdaExpression.builder()
                                                                                                        .consumer(List.of("manager"))
                                                                                                        .statements(List.of(
                                                                                                                JavaExpressionStatement.builder()
                                                                                                                        .expression(JavaMethodInvocationExpression.builder()
                                                                                                                                .target("manager")
                                                                                                                                .invokes(List.of(
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("sessionCreationPolicy")
                                                                                                                                                .arguments(List.of(
                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                .value("org.springframework.security.config.http.SessionCreationPolicy.STATELESS")
                                                                                                                                                                .type(Enum.class)
                                                                                                                                                                .build()))
                                                                                                                                                .build()))
                                                                                                                                .build())
                                                                                                                        .build()))
                                                                                                        .build()))
                                                                                        .build()))
                                                                        .build())
                                                                .build(),
                                                        JavaReturnStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()
                                                                        .target("http")
                                                                        .invokes(List.of(
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("build")
                                                                                        .build()))
                                                                        .build())
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }
}
