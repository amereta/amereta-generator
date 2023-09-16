package tech.amereta.generator.service.spring.main.model.domain;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.code.java.declaration.JavaFieldDeclaration;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class UserGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "AbstractUser";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.domain")
                .name(CLASS_NAME)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(CLASS_NAME)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC | Modifier.ABSTRACT)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("jakarta.persistence.MappedSuperclass")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("lombok.experimental.SuperBuilder")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("lombok.NoArgsConstructor")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("lombok.Getter")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("lombok.Setter")
                                                .build()))
                                .fieldDeclarations(List.of(
                                        JavaFieldDeclaration.builder()
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PRIVATE)
                                                        .build())
                                                .type("String")
                                                .name("username")
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.NotNull")
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.Pattern")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("regexp")
                                                                                .type(String.class)
                                                                                .values(List.of("1"))
                                                                                .build()))
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.Size")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("min")
                                                                                .type(Integer.class)
                                                                                .values(List.of("1"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("max")
                                                                                .type(Integer.class)
                                                                                .values(List.of("50"))
                                                                                .build()))
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.Column")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("length")
                                                                                .type(Integer.class)
                                                                                .values(List.of("50"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("unique")
                                                                                .type(Boolean.class)
                                                                                .values(List.of("true"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("nullable")
                                                                                .type(Boolean.class)
                                                                                .values(List.of("false"))
                                                                                .build()))
                                                                .build()))
                                                .build(),
                                        JavaFieldDeclaration.builder()
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PRIVATE)
                                                        .build())
                                                .type("String")
                                                .name("email")
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.Email")
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.Size")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("min")
                                                                                .type(Integer.class)
                                                                                .values(List.of("5"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("max")
                                                                                .type(Integer.class)
                                                                                .values(List.of("254"))
                                                                                .build()))
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.Column")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("length")
                                                                                .type(Integer.class)
                                                                                .values(List.of("254"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("unique")
                                                                                .type(Boolean.class)
                                                                                .values(List.of("true"))
                                                                                .build()))
                                                                .build()))
                                                .build(),
                                        JavaFieldDeclaration.builder()
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PRIVATE)
                                                        .build())
                                                .type("String")
                                                .name("password")
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("com.fasterxml.jackson.annotation.JsonIgnore")
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.NotNull")
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.validation.constraints.Size")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("min")
                                                                                .type(Integer.class)
                                                                                .values(List.of("60"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("max")
                                                                                .type(Integer.class)
                                                                                .values(List.of("60"))
                                                                                .build()))
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.Column")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("length")
                                                                                .type(Integer.class)
                                                                                .values(List.of("60"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("unique")
                                                                                .type(Boolean.class)
                                                                                .values(List.of("true"))
                                                                                .build(),
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("nullable")
                                                                                .type(Boolean.class)
                                                                                .values(List.of("false"))
                                                                                .build()))
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }
}
