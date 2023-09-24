package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.core.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.java.expression.JavaLambdaExpression;
import tech.amereta.core.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.java.expression.JavaValueExpression;
import tech.amereta.core.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.statement.JavaExpressionStatement;
import tech.amereta.core.java.statement.JavaReturnStatement;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;

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
                                        .modifiers(Modifier.PUBLIC))
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.security.config.annotation.web.configuration.EnableWebSecurity"),
                                        JavaAnnotation.builder()
                                                .name("org.springframework.context.annotation.Configuration")))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("filterChain")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC))
                                                .returnType("org.springframework.security.web.SecurityFilterChain")
                                                .parameters(List.of(
                                                        JavaMethodDeclaration.Parameter.builder()
                                                                .type("org.springframework.security.config.annotation.web.builders.HttpSecurity")
                                                                .name("http")))
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.context.annotation.Bean")))
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
                                                                                                                        .method("withDefaults"))))),
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
                                                                                                                                                                .type(Enum.class),
                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                .value("/**")
                                                                                                                                                                .type(String.class))),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("permitAll"),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("anyRequest")
                                                                                                                                                .breakLine(true),
                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                .method("authenticated")))))))),
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
                                                                                                                                                                .type(Enum.class))))))))))))),
                                                        JavaReturnStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()
                                                                        .target("http")
                                                                        .invokes(List.of(
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("build"))))))))));
    }
}
