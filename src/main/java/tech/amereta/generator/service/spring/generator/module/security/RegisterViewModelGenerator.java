package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;

import java.lang.reflect.Modifier;
import java.util.List;

public final class RegisterViewModelGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "RegisterVM";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.vm")
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
                                                                .name("lombok.Data"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.NoArgsConstructor")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .name("username")
                                                                .dataType("String")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.NotNull"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Pattern")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("regexp")
                                                                                                                .dataType(Enum.class)
                                                                                                                .values(List.of("tech.amereta.starter.Constants.USERNAME_REGEX"))
                                                                                                )
                                                                                        ),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Size")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("min")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("1")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("max")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("50"))
                                                                                                )
                                                                                        )

                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name("email")
                                                                .dataType("String")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Email"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Size")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("min")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("5")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("max")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("254"))
                                                                                                )
                                                                                        )
                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name("password")
                                                                .dataType("String")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.NotNull"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Size")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("min")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("4")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("max")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("100"))
                                                                                                )
                                                                                        )

                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name("rememberMe")
                                                                .dataType("boolean")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("com.fasterxml.jackson.annotation.JsonProperty")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("remember_me"))
                                                                                                )
                                                                                        )

                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .name("language")
                                                                .dataType("tech.amereta.starter.model.enumeration.Language")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                )
                                        )
                        )
                );
    }
}
