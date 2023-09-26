package tech.amereta.generator.service.spring.generator;

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

public final class AbstractUserGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "AbstractUser";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.domain")
                .name(CLASS_NAME)
                .typeDeclarations(
                        List.of(
                                JavaTypeDeclaration.builder()
                                        .type(JavaType.CLASS)
                                        .name(CLASS_NAME)
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC | Modifier.ABSTRACT)
                                        )
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.MappedSuperclass"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.experimental.SuperBuilder"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.NoArgsConstructor"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.Getter"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.Setter")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .dataType("String")
                                                                .name("username")
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
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("1"))
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
                                                                                        ),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("length")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("50")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("unique")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("true")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("nullable")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("false"))
                                                                                                )
                                                                                        )
                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .dataType("String")
                                                                .name("email")
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
                                                                                        ),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("length")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("254")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("unique")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("true"))
                                                                                                )
                                                                                        )
                                                                        )
                                                                ),
                                                        JavaFieldDeclaration.builder()
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .dataType("String")
                                                                .name("password")
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("com.fasterxml.jackson.annotation.JsonIgnore"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.NotNull"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.validation.constraints.Size")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("min")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("60")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("max")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("60"))
                                                                                                )
                                                                                        ),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("length")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("60")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("unique")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("true")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("nullable")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("false"))
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
