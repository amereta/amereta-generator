package tech.amereta.core.service.generator.spring.code.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import tech.amereta.core.util.code.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.core.util.code.java.util.JavaAnnotation;
import tech.amereta.core.util.code.java.util.JavaModifier;
import tech.amereta.core.util.code.java.util.JavaType;
import tech.amereta.core.domain.description.java.field.JavaDAOFieldDescription;
import tech.amereta.core.domain.description.java.type.JavaDAOTypeDescription;
import tech.amereta.core.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class SpringDAOGenerator extends SpringTypeGenerator {

    private String projectName;

    @Override
    public JavaTypeDeclaration generateTypeDeclaration() {
        return JavaTypeDeclaration.builder()
                .type(JavaType.CLASS)
                .name(StringFormatter.toPascalCase(type.getName(), "Dao"))
                .extendedClassName(type.getExtendedClassName())
                .implementedClassName(type.getImplementedClassName())
                .modifiers(generateModifiers(type.getModifiers()))
                .annotations(generateAnnotations())
                .fieldDeclarations(generateFields())
                .methodDeclarations(generateMethods())
                .build();
    }

    @Override
    public List<JavaAnnotation> generateAnnotations() {
        final List<JavaAnnotation> annotations = new ArrayList<>();
        if (((JavaDAOTypeDescription) type).isStoreCreatedDate() || ((JavaDAOTypeDescription) type).isStoreUpdatedDate()) {
            final List<String> prop = new ArrayList<>();
            if (((JavaDAOTypeDescription) type).isStoreCreatedDate()) prop.add("createdAt");
            if (((JavaDAOTypeDescription) type).isStoreUpdatedDate()) prop.add("updatedAt");
            annotations.add(JavaAnnotation.builder()
                    .name("com.fasterxml.jackson.annotation.JsonIgnoreProperties")
                    .attributes(List.of(
                            JavaAnnotation.Attribute.builder()
                                    .name("value")
                                    .type(String.class)
                                    .values(prop)
                                    .build(),
                            JavaAnnotation.Attribute.builder()
                                    .name("allowGetters")
                                    .type(Boolean.class)
                                    .values(List.of("true"))
                                    .build()))
                    .build());
        }
        annotations.addAll(List.of(
                JavaAnnotation.builder()
                        .name("javax.persistence.Entity")
                        .build(),
                JavaAnnotation.builder()
                        .name("javax.persistence.Table")
                        .attributes(List.of(
                                JavaAnnotation.Attribute.builder()
                                        .name("name")
                                        .type(String.class)
                                        .values(List.of(super.type.getName().toUpperCase()))
                                        .build(),
                                JavaAnnotation.Attribute.builder()
                                        .name("schema")
                                        .type(String.class)
                                        .values(List.of(projectName.toUpperCase()))
                                        .build()))
                        .build(),
                JavaAnnotation.builder()
                        .name("javax.persistence.EntityListeners")
                        .attributes(List.of(
                                JavaAnnotation.Attribute.builder()
                                        .type(Class.class)
                                        .values(List.of("org.springframework.data.jpa.domain.support.AuditingEntityListener"))
                                        .build()))
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.Builder")
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.NoArgsConstructor")
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.AllArgsConstructor")
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.AllArgsConstructor")
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.Getter")
                        .build(),
                JavaAnnotation.builder()
                        .name("lombok.Setter")
                        .build()));
        return annotations;
    }

    @Override
    public List<JavaFieldDeclaration> generateFields() {
        final List<JavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
        fieldDeclarations.add(generatePrimaryKey());
        if (((JavaDAOTypeDescription) type).isStoreCreatedDate()) fieldDeclarations.add(createdAtField());
        if (((JavaDAOTypeDescription) type).isStoreUpdatedDate()) fieldDeclarations.add(updatedAtField());
        type.getFields().forEach(field -> {
            fieldDeclarations.add(
                    JavaFieldDeclaration.builder()
                            .name(field.getName())
                            .type(field.getType())
                            .value(field.getValue())
                            .genericTypes(field.getGenericTypes())
                            .modifiers(generateModifiers(field.getModifiers()))
                            .annotations(generateFieldsAnnotation((JavaDAOFieldDescription) field))
                            .build());
        });
        return fieldDeclarations;
    }

    private List<JavaAnnotation> generateFieldsAnnotation(JavaDAOFieldDescription field) {
        final List<JavaAnnotation> annotations = new ArrayList<>();
        if (field.getRelation() != null) {
            final JavaDAOFieldDescription.JavaFieldRelationDescription relationDescription = field.getRelation();
            if (relationDescription.isJoin()) {
                annotations.add(JavaAnnotation.builder()
                        .name("javax.persistence." + relationDescription.getType().toString())
                        .build());
                final List<JavaAnnotation.Attribute> attributes = new ArrayList<>();
                if (field.isUnique())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("unique")
                            .type(Boolean.class)
                            .values(List.of("true"))
                            .build());
                if (!field.isNullable())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("nullable")
                            .type(Boolean.class)
                            .values(List.of("false"))
                            .build());
                if (!field.isUpdatable())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("updatable")
                            .type(Boolean.class)
                            .values(List.of("false"))
                            .build());
                attributes.addAll(List.of(
                        JavaAnnotation.Attribute.builder()
                                .name("name")
                                .type(String.class)
                                .values(List.of(field.getName().concat("_id")))
                                .build(),
                        JavaAnnotation.Attribute.builder()
                                .name("referencedColumnName")
                                .type(String.class)
                                .values(List.of("id"))
                                .build()));
                annotations.add(JavaAnnotation.builder()
                        .name("javax.persistence.JoinColumn")
                        .attributes(attributes)
                        .build());
            } else {
                annotations.add(JavaAnnotation.builder()
                        .name("javax.persistence." + relationDescription.getType().toString())
                        .attributes(List.of(
                                JavaAnnotation.Attribute.builder()
                                        .name("mappedBy")
                                        .type(String.class)
                                        .values(List.of(relationDescription.getMappedBy()))
                                        .build()))
                        .build());
                annotations.add(JavaAnnotation.builder()
                        .name("javax.persistence.Cascade")
                        .attributes(List.of(
                                JavaAnnotation.Attribute.builder()
                                        .type(Enum.class)
                                        .values(List.of("org.hibernate.annotations.CascadeType.ALL"))
                                        .build()))
                        .build());
                final List<JavaAnnotation.Attribute> attributes = new ArrayList<>();
                if (field.isUnique())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("unique")
                            .type(Boolean.class)
                            .values(List.of("true"))
                            .build());
                if (!field.isNullable())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("nullable")
                            .type(Boolean.class)
                            .values(List.of("false"))
                            .build());
                if (!field.isUpdatable())
                    attributes.add(JavaAnnotation.Attribute.builder()
                            .name("updatable")
                            .type(Boolean.class)
                            .values(List.of("false"))
                            .build());
                annotations.add(JavaAnnotation.builder()
                        .name("javax.persistence.Column")
                        .attributes(attributes)
                        .build());
            }
        } else {
            final List<JavaAnnotation.Attribute> attributes = new ArrayList<>();
            if (field.isUnique())
                attributes.add(JavaAnnotation.Attribute.builder()
                        .name("unique")
                        .type(Boolean.class)
                        .values(List.of("true"))
                        .build());
            if (!field.isNullable())
                attributes.add(JavaAnnotation.Attribute.builder()
                        .name("nullable")
                        .type(Boolean.class)
                        .values(List.of("false"))
                        .build());
            annotations.add(JavaAnnotation.builder()
                    .name("javax.persistence.Column")
                    .attributes(attributes)
                    .build());
        }
        if (field.isExcludeFromJson()) {
            annotations.add(JavaAnnotation.builder()
                    .name("com.fasterxml.jackson.annotation.JsonProperty")
                    .attributes(List.of(
                            JavaAnnotation.Attribute.builder()
                                    .name("access")
                                    .type(Enum.class)
                                    .values(List.of("com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY"))
                                    .build()))
                    .build());
        }
        if (field.getValue() != null) {
            annotations.add(JavaAnnotation.builder()
                    .name("lombok.Builder.Default")
                    .build());
        }
        return annotations;
    }

    private JavaFieldDeclaration generatePrimaryKey() {
        return JavaFieldDeclaration.builder()
                .name("ID")
                .type("String")
                .modifiers(JavaModifier.builder()
                        .type(JavaModifier.METHOD_MODIFIERS)
                        .modifiers(Modifier.PRIVATE)
                        .build())
                .annotations(List.of(
                        JavaAnnotation.builder()
                                .name("javax.persistence.Id")
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.GeneratedValue")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("generator")
                                                .type(String.class)
                                                .values(List.of("uuid2"))
                                                .build()))
                                .build(),
                        JavaAnnotation.builder()
                                .name("org.hibernate.annotations.GenericGenerator")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("name")
                                                .type(String.class)
                                                .values(List.of("uuid2"))
                                                .build(),
                                        JavaAnnotation.Attribute.builder()
                                                .name("strategy")
                                                .type(String.class)
                                                .values(List.of("org.hibernate.id.UUIDGenerator"))
                                                .build()))
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.Column")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("updatable")
                                                .type(Boolean.class)
                                                .values(List.of("false"))
                                                .build(),
                                        JavaAnnotation.Attribute.builder()
                                                .name("nullable")
                                                .type(Boolean.class)
                                                .values(List.of("false"))
                                                .build(),
                                        JavaAnnotation.Attribute.builder()
                                                .name("columnDefinition")
                                                .type(String.class)
                                                .values(List.of("VARCHAR(255)"))
                                                .build()))
                                .build()))
                .build();
    }

    private JavaFieldDeclaration createdAtField() {
        return JavaFieldDeclaration.builder()
                .name("createdAt")
                .type("java.util.Date")
                .modifiers(JavaModifier.builder()
                        .type(JavaModifier.METHOD_MODIFIERS)
                        .modifiers(Modifier.PRIVATE)
                        .build())
                .annotations(List.of(
                        JavaAnnotation.builder()
                                .name("lombok.Builder.Default")
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.Temporal")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .type(Enum.class)
                                                .values(List.of("javax.persistence.TemporalType.TIMESTAMP"))
                                                .build()))
                                .build(),
                        JavaAnnotation.builder()
                                .name("org.springframework.data.annotation.CreatedDate")
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.Column")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("updatable")
                                                .type(Boolean.class)
                                                .values(List.of("false"))
                                                .build(),
                                        JavaAnnotation.Attribute.builder()
                                                .name("nullable")
                                                .type(Boolean.class)
                                                .values(List.of("false"))
                                                .build()))
                                .build()))
                .value(Date.class)
                .build();
    }

    private JavaFieldDeclaration updatedAtField() {
        return JavaFieldDeclaration.builder()
                .name("updatedAt")
                .type("java.util.Date")
                .modifiers(JavaModifier.builder()
                        .type(JavaModifier.METHOD_MODIFIERS)
                        .modifiers(Modifier.PRIVATE)
                        .build())
                .annotations(List.of(
                        JavaAnnotation.builder()
                                .name("lombok.Builder.Default")
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.Temporal")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .type(Enum.class)
                                                .values(List.of("javax.persistence.TemporalType.TIMESTAMP"))
                                                .build()))
                                .build(),
                        JavaAnnotation.builder()
                                .name("org.springframework.data.annotation.LastModifiedDate")
                                .build(),
                        JavaAnnotation.builder()
                                .name("javax.persistence.Column")
                                .attributes(List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("nullable")
                                                .type(Boolean.class)
                                                .values(List.of("false"))
                                                .build()))
                                .build()))
                .value(Date.class)
                .build();
    }

    @Override
    public List<JavaMethodDeclaration> generateMethods() {
        return new ArrayList<>();
    }

}
