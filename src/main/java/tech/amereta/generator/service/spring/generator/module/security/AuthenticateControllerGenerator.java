package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.java.expression.*;
import tech.amereta.core.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.java.statement.JavaControlStatement;
import tech.amereta.core.java.statement.JavaDeclarationStatement;
import tech.amereta.core.java.statement.JavaExpressionStatement;
import tech.amereta.core.java.statement.JavaReturnStatement;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.List;

public final class AuthenticateControllerGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "AuthenticateController";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final SpringModelModuleDomainTypeDescription authenticableDomain = getAuthenticableDomain(applicationDescription);

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".web.controller")
                .name(CLASS_NAME)
                .typeDeclarations(
                        List.of(
                                JavaTypeDeclaration.builder()
                                        .type(JavaType.CLASS)
                                        .name(CLASS_NAME)
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC)
                                        )
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.web.bind.annotation.RestController"),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.web.bind.annotation.RequestMapping")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .dataType(String.class)
                                                                                        .values(List.of("/api"))
                                                                        )
                                                                ),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.extern.slf4j.Slf4j")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .name("authenticationManagerBuilder")
                                                                .dataType("org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.FIELD_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.beans.factory.annotation.Autowired")
                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name("jwtProvider")
                                                                .dataType("tech.amereta.starter.jwt.JWTProvider")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.FIELD_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.beans.factory.annotation.Autowired")
                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name(authenticableDomain.getName().toLowerCase() + "Service")
                                                                .dataType(basePackage(applicationDescription) + ".service." + StringFormatter.toPascalCase(authenticableDomain.getName()) + "Service")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.FIELD_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.beans.factory.annotation.Autowired")
                                                                        )
                                                                )
                                                )
                                        )
                                        .methodDeclarations(
                                                List.of(
                                                        authenticateMethod(applicationDescription),
                                                        registerMethod(applicationDescription, authenticableDomain),
                                                        activateMethod(applicationDescription, authenticableDomain)
                                                )
                                        )
                        )
                );
    }

    private static JavaMethodDeclaration authenticateMethod(final SpringBootApplicationDescription applicationDescription) {
        return JavaMethodDeclaration.builder()
                .name("authenticate")
                .returnType("org.springframework.http.ResponseEntity")
                .genericTypes(List.of(basePackage(applicationDescription) + ".model.JWT"))
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.METHOD_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                )
                .parameters(
                        List.of(
                                JavaMethodDeclaration.Parameter.builder()
                                        .name("loginVM")
                                        .type(basePackage(applicationDescription) + ".model.vm.LoginVM")
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.Valid"),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.web.bind.annotation.RequestBody")
                                                )
                                        )
                        )
                )
                .annotations(
                        List.of(
                                JavaAnnotation.builder()
                                        .name("org.springframework.web.bind.annotation.PostMapping")
                                        .attributes(
                                                List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .dataType(String.class)
                                                                .values(List.of("/authenticate"))
                                                )
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("log")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("debug")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .type(String.class)
                                                                                                        .value("request to authenticate: {}"),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("loginVM")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaDeclarationStatement.builder()
                                        .name("authenticationToken")
                                        .dataType("org.springframework.security.authentication.UsernamePasswordAuthenticationToken")
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                                        .initialized(true)
                                        .expression(
                                                JavaNewInstanceExpression.builder()
                                                        .name("org.springframework.security.authentication.UsernamePasswordAuthenticationToken")
                                                        .arguments(
                                                                List.of(
                                                                        JavaMethodInvocationExpression.builder()
                                                                                .target("loginVM")
                                                                                .invokes(
                                                                                        List.of(
                                                                                                JavaMethodInvoke.builder()
                                                                                                        .method("getUsername")
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvocationExpression.builder()
                                                                                .target("loginVM")
                                                                                .invokes(
                                                                                        List.of(
                                                                                                JavaMethodInvoke.builder()
                                                                                                        .method("getPassword")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaDeclarationStatement.builder()
                                        .name("authentication")
                                        .dataType("org.springframework.security.core.Authentication")
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                                        .initialized(true)
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("authenticationManagerBuilder")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("getObject"),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("authenticate")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("authenticationToken")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("org.springframework.security.core.context.SecurityContextHolder")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("getContext"),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("setAuthentication")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("authentication")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaDeclarationStatement.builder()
                                        .name("jwt")
                                        .dataType("String")
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                                        .initialized(true)
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("jwtProvider")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("generateToken")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("authentication"),
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("loginVM")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("isRememberMe")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaReturnStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("org.springframework.http.ResponseEntity")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("ok")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target(basePackage(applicationDescription) + ".model.JWT")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("builder"),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("token")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaVariableExpression.builder()
                                                                                                                                                        .variable("jwt")
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("build")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        )
                        )
                );
    }

    private static JavaMethodDeclaration registerMethod(final SpringBootApplicationDescription applicationDescription, SpringModelModuleDomainTypeDescription authenticableDomain) {
        return JavaMethodDeclaration.builder()
                .name("register")
                .returnType("void")
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.METHOD_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                )
                .parameters(
                        List.of(
                                JavaMethodDeclaration.Parameter.builder()
                                        .name("registerVM")
                                        .type(basePackage(applicationDescription) + ".model.vm.RegisterVM")
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.Valid"),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.web.bind.annotation.RequestBody")
                                                )
                                        )
                        )
                )
                .annotations(
                        List.of(
                                JavaAnnotation.builder()
                                        .name("org.springframework.web.bind.annotation.PostMapping")
                                        .attributes(
                                                List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .dataType(String.class)
                                                                .values(List.of("/register"))
                                                )
                                        ),
                                JavaAnnotation.builder()
                                        .name("org.springframework.web.bind.annotation.ResponseStatus")
                                        .attributes(
                                                List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .dataType(Enum.class)
                                                                .values(List.of("org.springframework.http.HttpStatus.CREATED"))
                                                )
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("log")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("debug")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .type(String.class)
                                                                                                        .value("request to register new " + authenticableDomain.getName().toLowerCase() + ": {}"),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("registerVM")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(authenticableDomain.getName().toLowerCase() + "Service")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("register")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("registerVM")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        )
                        )
                );
    }

    private static JavaMethodDeclaration activateMethod(final SpringBootApplicationDescription applicationDescription, SpringModelModuleDomainTypeDescription authenticableDomain) {
        return JavaMethodDeclaration.builder()
                .name("activate")
                .returnType("void")
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.METHOD_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                )
                .parameters(
                        List.of(
                                JavaMethodDeclaration.Parameter.builder()
                                        .name("key")
                                        .type("java.util.UUID")
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.web.bind.annotation.PathVariable")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("value")
                                                                                        .dataType(String.class)
                                                                                        .values(List.of("key"))
                                                                        )
                                                                )
                                                )
                                        )
                        )
                )
                .annotations(
                        List.of(
                                JavaAnnotation.builder()
                                        .name("org.springframework.web.bind.annotation.GetMapping")
                                        .attributes(
                                                List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .dataType(String.class)
                                                                .values(List.of("/activate/{key}"))
                                                )
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("log")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("debug")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .type(String.class)
                                                                                                        .value("request to activate " + authenticableDomain.getName().toLowerCase() + " for activation key {}"),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("key")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaDeclarationStatement.builder()
                                        .name(authenticableDomain.getName().toLowerCase())
                                        .dataType("java.util.Optional")
                                        .genericTypes(List.of(basePackage(applicationDescription) + ".model.domain." + StringFormatter.toPascalCase(authenticableDomain.getName())))
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                                        .initialized(true)
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(authenticableDomain.getName().toLowerCase() + "Service")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("activate")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("key")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaControlStatement.builder()
                                        .ifs(
                                                List.of(
                                                        JavaControlStatement.If.builder()
                                                                .condition(
                                                                        JavaBraceletExpression.builder()
                                                                                .bracelet(false)
                                                                                .expressions(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target(authenticableDomain.getName().toLowerCase())
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("isEmpty")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                )
                                                                )
                                                                .statements(
                                                                        List.of(
                                                                                JavaExpressionStatement.builder()
                                                                                        .expression(
                                                                                                JavaThrowExpression.builder()
                                                                                                        .name("tech.amereta.starter.exception.ActivationCodeIsWrongException")
                                                                                        )
                                                                        )
                                                                )
                                                )
                                        )
                        )
                );
    }
}
