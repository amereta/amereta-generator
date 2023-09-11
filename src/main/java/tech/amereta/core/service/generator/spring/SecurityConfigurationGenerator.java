package tech.amereta.core.service.generator.spring;

import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.util.code.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.util.code.java.expression.JavaValueExpression;
import tech.amereta.core.util.code.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.util.code.java.source.JavaCompilationUnit;
import tech.amereta.core.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.core.util.code.java.statement.JavaExpressionStatement;
import tech.amereta.core.util.code.java.util.JavaAnnotation;
import tech.amereta.core.util.code.java.util.JavaModifier;
import tech.amereta.core.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class SecurityConfigurationGenerator extends AbstractSourceCodeGenerator {

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
                                .extendedClassName("org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter")
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.context.annotation.Configuration")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("org.springframework.security.config.annotation.web.configuration.EnableWebSecurity")
                                                .build()))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("configure")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .modifiers(Modifier.PROTECTED)
                                                        .build())
                                                .returnType("void")
                                                .parameters(List.of(
                                                        JavaMethodDeclaration.Parameter.builder()
                                                                .type("org.springframework.security.config.annotation.web.builders.HttpSecurity")
                                                                .name("http")
                                                                .build()))
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("override")
                                                                .build()
                                                ))
                                                .statements(List.of(
                                                        JavaExpressionStatement.builder()
                                                                .expression(JavaMethodInvocationExpression.builder()
                                                                        .target("http")
                                                                        .invokes(List.of(
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("antMatchers")
                                                                                        .arguments(List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .value("/amereta")
                                                                                                        .type(String.class)
                                                                                                        .build()))
                                                                                        .breakLine(true)
                                                                                        .build(),
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("permitAll")
                                                                                        .build(),
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("and")
                                                                                        .breakLine(true)
                                                                                        .build(),
                                                                                JavaMethodInvoke.builder()
                                                                                        .method("sessionManagement")
                                                                                        .breakLine(true)
                                                                                        .build(),
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
                .build();
    }
}
