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

public final class AuthenticableDomainServiceGenerator extends AbstractSpringSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final SpringModelModuleDomainTypeDescription authenticableDomain = getAuthenticableDomain(applicationDescription);
        final String className = StringFormatter.toPascalCase(authenticableDomain.getName()) + "Service";

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".service")
                .name(className)
                .typeDeclarations(
                        List.of(
                                JavaTypeDeclaration.builder()
                                        .type(JavaType.CLASS)
                                        .name(className)
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC)
                                        )
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.stereotype.Service"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.extern.slf4j.Slf4j")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .name(authenticableDomain.getName().toLowerCase() + "Repository")
                                                                .dataType(basePackage(applicationDescription) + ".repository." + StringFormatter.toPascalCase(authenticableDomain.getName()) + "Repository")
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
                                                                .name("passwordEncoder")
                                                                .dataType("org.springframework.security.crypto.password.PasswordEncoder")
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
                                                        registerMethod(applicationDescription, authenticableDomain),
                                                        activateMethod(applicationDescription, authenticableDomain),
                                                        checkExistenceOfAuthenticableDomain(applicationDescription, authenticableDomain)
                                                )
                                        )
                        )
                );
    }

    private static JavaMethodDeclaration registerMethod(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription authenticableDomain) {
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
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target("this")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("checkExistenceOf" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("registerVM")
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
                                JavaDeclarationStatement.builder()
                                        .name("new" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                        .dataType(basePackage(applicationDescription) + ".model.domain." + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                                        .initialized(true)
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(basePackage(applicationDescription) + ".model.domain." + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("builder"),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("username")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("registerVM")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("getUsername"),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("toLowerCase")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("email")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("registerVM")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("getEmail")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("password")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("passwordEncoder")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("encode")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                                                                        .target("registerVM")
                                                                                                                                                        .invokes(
                                                                                                                                                                List.of(
                                                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                                                .method("getPassword")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("language")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("java.util.Optional")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("ofNullable")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                                                                        .target("registerVM")
                                                                                                                                                        .invokes(
                                                                                                                                                                List.of(
                                                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                                                .method("getLanguage")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("orElse")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaValueExpression.builder()
                                                                                                                                                        .type(Enum.class)
                                                                                                                                                        .value("tech.amereta.starter.Constants.DEFAULT_LANGUAGE")
                                                                                                                                        )
                                                                                                                                )
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("activated")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaValueExpression.builder()
                                                                                                        .type(Boolean.class)
                                                                                                        .value("false")
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("activationKey")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("java.util.UUID")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("randomUUID")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("roles")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("java.util.Set")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("of")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaValueExpression.builder()
                                                                                                                                                        .type(Enum.class)
                                                                                                                                                        .value(basePackage(applicationDescription) + ".model.enumeration.Role." + StringFormatter.toSnakeCase(authenticableDomain.getName()).toUpperCase())
                                                                                                                                        )
                                                                                                                                )
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("build")
                                                                                .breakLine(true)
                                                                )
                                                        )
                                        ),
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(authenticableDomain.getName().toLowerCase() + "Repository")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("save")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("new" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        ),
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
                                                                                                        .value("new " + authenticableDomain.getName().toLowerCase() + " registered: {}"),
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("new" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                                                        )
                                                                                )
                                                                )
                                                        )
                                        )
                        )
                );
    }

    private static JavaMethodDeclaration activateMethod(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription authenticableDomain) {
        return JavaMethodDeclaration.builder()
                .name("activate")
                .returnType("java.util.Optional")
                .genericTypes(List.of(basePackage(applicationDescription) + ".model.domain." + StringFormatter.toPascalCase(authenticableDomain.getName())))
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
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaReturnStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(authenticableDomain.getName().toLowerCase() + "Repository")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("findOneByActivationKey")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaVariableExpression.builder()
                                                                                                        .variable("key")
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("map")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaLambdaExpression.builder()
                                                                                                        .consumer(List.of(authenticableDomain.getName().toLowerCase()))
                                                                                                        .statements(
                                                                                                                List.of(
                                                                                                                        JavaExpressionStatement.builder()
                                                                                                                                .expression(
                                                                                                                                        JavaMethodInvocationExpression.builder()
                                                                                                                                                .target(authenticableDomain.getName().toLowerCase())
                                                                                                                                                .invokes(
                                                                                                                                                        List.of(
                                                                                                                                                                JavaMethodInvoke.builder()
                                                                                                                                                                        .method("setActivated")
                                                                                                                                                                        .arguments(
                                                                                                                                                                                List.of(
                                                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                                                .type(Boolean.class)
                                                                                                                                                                                                .value("true")
                                                                                                                                                                                )
                                                                                                                                                                        )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                ),
                                                                                                                        JavaExpressionStatement.builder()
                                                                                                                                .expression(
                                                                                                                                        JavaMethodInvocationExpression.builder()
                                                                                                                                                .target(authenticableDomain.getName().toLowerCase())
                                                                                                                                                .invokes(
                                                                                                                                                        List.of(
                                                                                                                                                                JavaMethodInvoke.builder()
                                                                                                                                                                        .method("setActivationKey")
                                                                                                                                                                        .arguments(
                                                                                                                                                                                List.of(
                                                                                                                                                                                        JavaValueExpression.builder()
                                                                                                                                                                                                .value("null")
                                                                                                                                                                                )
                                                                                                                                                                        )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                ),
                                                                                                                        JavaExpressionStatement.builder()
                                                                                                                                .expression(
                                                                                                                                        JavaMethodInvocationExpression.builder()
                                                                                                                                                .target(authenticableDomain.getName().toLowerCase() + "Repository")
                                                                                                                                                .invokes(
                                                                                                                                                        List.of(
                                                                                                                                                                JavaMethodInvoke.builder()
                                                                                                                                                                        .method("save")
                                                                                                                                                                        .arguments(
                                                                                                                                                                                List.of(
                                                                                                                                                                                        JavaVariableExpression.builder()
                                                                                                                                                                                                .variable(authenticableDomain.getName().toLowerCase())
                                                                                                                                                                                )
                                                                                                                                                                        )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                ),
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
                                                                                                                                                                                                .value("activated " + authenticableDomain.getName().toLowerCase() + ": {}"),
                                                                                                                                                                                        JavaVariableExpression.builder()
                                                                                                                                                                                                .variable(authenticableDomain.getName().toLowerCase())
                                                                                                                                                                                )
                                                                                                                                                                        )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                ),
                                                                                                                        JavaReturnStatement.builder()
                                                                                                                                .expression(
                                                                                                                                        JavaVariableExpression.builder()
                                                                                                                                                .variable(authenticableDomain.getName().toLowerCase())
                                                                                                                                )
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

    private static JavaMethodDeclaration checkExistenceOfAuthenticableDomain(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription authenticableDomain) {
        return JavaMethodDeclaration.builder()
                .name("checkExistenceOf" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                .returnType("void")
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.METHOD_MODIFIERS)
                                .modifiers(Modifier.PRIVATE)
                )
                .parameters(
                        List.of(
                                JavaMethodDeclaration.Parameter.builder()
                                        .name("registerVM")
                                        .type(basePackage(applicationDescription) + ".model.vm.RegisterVM")
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.FIELD_MODIFIERS)
                                                        .modifiers(Modifier.FINAL)
                                        )
                        )
                )
                .statements(
                        List.of(
                                JavaExpressionStatement.builder()
                                        .expression(
                                                JavaMethodInvocationExpression.builder()
                                                        .target(authenticableDomain.getName().toLowerCase() + "Repository")
                                                        .invokes(
                                                                List.of(
                                                                        JavaMethodInvoke.builder()
                                                                                .method("findOneByUsername")
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target("registerVM")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("getUsername"),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("toLowerCase")
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                ),
                                                                        JavaMethodInvoke.builder()
                                                                                .method("ifPresent")
                                                                                .breakLine(true)
                                                                                .arguments(
                                                                                        List.of(
                                                                                                JavaLambdaExpression.builder()
                                                                                                        .consumer(List.of("existing" + StringFormatter.toPascalCase(authenticableDomain.getName())))
                                                                                                        .statements(
                                                                                                                List.of(
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
                                                                                                                                                                                                .target("existing" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                                                                                                                                                                .invokes(
                                                                                                                                                                                                        List.of(
                                                                                                                                                                                                                JavaMethodInvoke.builder()
                                                                                                                                                                                                                        .method("isActivated")
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
                                                                                                                                                                                                .name("tech.amereta.starter.exception.UsernameAlreadyUsedException")
                                                                                                                                                                                )
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                )
                                                                                                        )
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
                                                                                                        .target("org.springframework.util.StringUtils")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("hasText")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                                                                        .target("registerVM")
                                                                                                                                                        .invokes(
                                                                                                                                                                List.of(
                                                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                                                .method("getEmail")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                )
                                                                                                        )
                                                                                        )
                                                                                )
                                                                )
                                                                .statements(
                                                                        List.of(
                                                                                JavaExpressionStatement.builder()
                                                                                        .expression(
                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                        .target(authenticableDomain.getName().toLowerCase() + "Repository")
                                                                                                        .invokes(
                                                                                                                List.of(
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("findOneByEmailIgnoreCase")
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaMethodInvocationExpression.builder()
                                                                                                                                                        .target("registerVM")
                                                                                                                                                        .invokes(
                                                                                                                                                                List.of(
                                                                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                                                                .method("getEmail")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                        JavaMethodInvoke.builder()
                                                                                                                                .method("ifPresent")
                                                                                                                                .breakLine(true)
                                                                                                                                .arguments(
                                                                                                                                        List.of(
                                                                                                                                                JavaLambdaExpression.builder()
                                                                                                                                                        .consumer(List.of("existing" + StringFormatter.toPascalCase(authenticableDomain.getName())))
                                                                                                                                                        .statements(
                                                                                                                                                                List.of(
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
                                                                                                                                                                                                                                                .target("existing" + StringFormatter.toPascalCase(authenticableDomain.getName()))
                                                                                                                                                                                                                                                .invokes(
                                                                                                                                                                                                                                                        List.of(
                                                                                                                                                                                                                                                                JavaMethodInvoke.builder()
                                                                                                                                                                                                                                                                        .method("isActivated")
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
                                                                                                                                                                                                                                                .name("tech.amereta.starter.exception.EmailAlreadyUsedException")
                                                                                                                                                                                                                                )
                                                                                                                                                                                                                )
                                                                                                                                                                                                        )
                                                                                                                                                                                        )
                                                                                                                                                                                )
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                        )
                                                                                                                                )
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
}
