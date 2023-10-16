package tech.amereta.generator.service.spring.generator.module.model;

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

public final class AbstractTimestampedDomainGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "AbstractTimestampedDomain";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        System.out.println("hey");
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
                                        .implementedClassName("java.io.Serializable")
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.MappedSuperclass"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.experimental.SuperBuilder"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.Data"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.NoArgsConstructor"),
                                                        JavaAnnotation.builder()
                                                                .name("jakarta.persistence.EntityListeners")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .dataType(Class.class)
                                                                                        .values(List.of("org.springframework.data.jpa.domain.support.AuditingEntityListener"))
                                                                        )
                                                                ),
                                                        JavaAnnotation.builder()
                                                                .name("com.fasterxml.jackson.annotation.JsonIgnoreProperties")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("value")
                                                                                        .dataType(String.class)
                                                                                        .values(List.of("createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate")),
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("allowGetters")
                                                                                        .dataType(Boolean.class)
                                                                                        .values(List.of("true"))
                                                                        )
                                                                )

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
                                                                .name("createdBy")
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.data.annotation.CreatedBy"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("name")
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("created_by")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("length")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("50")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("nullable")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("false")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("updatable")
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
                                                                .dataType("java.time.Instant")
                                                                .name("createdDate")
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.data.annotation.CreatedDate"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("name")
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("created_date")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("nullable")
                                                                                                                .dataType(Boolean.class)
                                                                                                                .values(List.of("false")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("updatable")
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
                                                                .name("lastModifiedBy")
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.data.annotation.LastModifiedBy"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("name")
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("last_modified_by")),
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("length")
                                                                                                                .dataType(Integer.class)
                                                                                                                .values(List.of("50"))
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
                                                                .dataType("java.time.Instant")
                                                                .name("lastModifiedDate")
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.data.annotation.LastModifiedDate"),
                                                                                JavaAnnotation.builder()
                                                                                        .name("jakarta.persistence.Column")
                                                                                        .attributes(
                                                                                                List.of(
                                                                                                        JavaAnnotation.Attribute.builder()
                                                                                                                .name("name")
                                                                                                                .dataType(String.class)
                                                                                                                .values(List.of("last_modified_date"))
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
