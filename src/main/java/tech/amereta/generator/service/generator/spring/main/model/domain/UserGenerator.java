package tech.amereta.generator.service.generator.spring.main.model.domain;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.generator.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.code.java.declaration.JavaFieldDeclaration;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class UserGenerator extends AbstractSpringSourceCodeGenerator {

    protected static final String CLASS_NAME = "AbstractUser";

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
                                                .name("javax.persistence.MappedSuperclass")
                                                .build(),
                                        JavaAnnotation.builder()
                                                .name("lombok.experimental.SuperBuilder")
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
                                                                .name("javax.validation.constraints.NotNull")
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("javax.validation.constraints.Pattern")
                                                                .attributes(List.of(
                                                                        JavaAnnotation.Attribute.builder()
                                                                                .name("regexp")
                                                                                .type(String.class)
                                                                                .values(List.of("1"))
                                                                                .build()))
                                                                .build(),
                                                        JavaAnnotation.builder()
                                                                .name("javax.validation.constraints.Size")
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
                                                                .name("javax.persistence.Column")
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
                                                .build(),
                                        JavaFieldDeclaration.builder()
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PRIVATE)
                                                        .build())
                                                .type("String")
                                                .name("password")
                                                .build()))
                                .build()))
                .build();
    }
}
