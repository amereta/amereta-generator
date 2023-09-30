package tech.amereta.generator.service.spring.generator.module.model;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.exception.DomainIdDataTypeException;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.util.StringFormatter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SpringModelModuleDomainTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final SpringModuleTypeDescription typeDescription) {
        final SpringModelModuleDomainTypeDescription domainTypeDescription = (SpringModelModuleDomainTypeDescription) typeDescription;

        return applicationHasDataBase(applicationDescription) ?
                generateDBDomains(applicationDescription, domainTypeDescription) :
                generateSimpleDomain(applicationDescription, domainTypeDescription);
    }


    private List<JavaCompilationUnit> generateDBDomains(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        return List.of(
                generateDBDomain(applicationDescription, domainTypeDescription),
                generateRepository(applicationDescription, domainTypeDescription)
//                                generateController(field) TODO: ControllerGenerator
        );
    }

    private JavaCompilationUnit generateDBDomain(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        final String className = StringFormatter.toPascalCase(domainTypeDescription.getName());
        final JavaTypeDeclaration domain = generateClassDeclaration(className);
        final List<JavaAnnotation> annotations = generateDBDomainAnnotations(domainTypeDescription);

        if (domainTypeDescription.getAuthorizable()) {
            annotations.add(
                    JavaAnnotation.builder()
                            .name("lombok.EqualsAndHashCode")
                            .attributes(List.of(
                                            JavaAnnotation.Attribute.builder()
                                                    .name("callSuper")
                                                    .dataType(Boolean.class)
                                                    .values(List.of("true"))
                                    )
                            )
            );
            domain.setExtendedClassName("AbstractUser");
        }
        domain.setAnnotations(annotations);
        domain.setFieldDeclarations(generateDBDomainFields(domainTypeDescription));

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.domain")
                .name(className)
                .typeDeclarations(List.of(domain));
    }

    private JavaCompilationUnit generateRepository(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        final String domainName = StringFormatter.toPascalCase(domainTypeDescription.getName());
        final String className = domainName + "Repository";
        final JavaTypeDeclaration repository = generateInterfaceDeclaration(className);
        repository.setAnnotations(
                List.of(
                        JavaAnnotation.builder()
                                .name("org.springframework.stereotype.Repository")
                )
        );
        repository.setExtendedClassName("org.springframework.data.jpa.repository.JpaRepository");
        repository.setTailGenericTypes(
                List.of(
                        basePackage(applicationDescription) + ".model.domain." + domainName,
                        domainTypeDescription.getIdType().getDataType()
                )
        );
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".repository")
                .name(className)
                .typeDeclarations(List.of(repository));
    }

    private List<JavaCompilationUnit> generateSimpleDomain(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        final String className = StringFormatter.toPascalCase(domainTypeDescription.getName());
        final JavaTypeDeclaration domain = generateClassDeclaration(className);
        domain.setAnnotations(generateSimpleDomainAnnotations());
        domain.setFieldDeclarations(generateSimpleDomainFields(domainTypeDescription));

        return List.of(
                JavaCompilationUnit.builder()
                        .packageName(basePackage(applicationDescription) + ".model.domain")
                        .name(className)
                        .typeDeclarations(List.of(domain))
        );
    }

    private List<AbstractJavaFieldDeclaration> generateDBDomainFields(final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        final List<AbstractJavaFieldDeclaration> fieldDeclarations = new ArrayList<>();
        final JavaFieldDeclaration idField = generateIdField(domainTypeDescription.getIdType());
        fieldDeclarations.add(idField);
        fieldDeclarations.addAll(
                domainTypeDescription.getFields()
                        .stream()
                        .map(field -> generateField(field, generateDBFieldAnnotations(field)))
                        .toList()
        );
        fieldDeclarations.addAll(
                domainTypeDescription.getRelations()
                        .stream()
                        .map(relation -> generateRelationField(relation, domainTypeDescription.getName()))
                        .toList()
        );
        return fieldDeclarations;
    }

    private JavaFieldDeclaration generateRelationField(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, final String domainName) {
        final JavaFieldDeclaration javaFieldDeclaration = generateFieldDeclarationForRelation(relation);
        javaFieldDeclaration.setAnnotations(generateRelationAnnotations(relation, domainName));
        return javaFieldDeclaration;
    }

    private List<JavaAnnotation> generateRelationAnnotations(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, final String domainName) {
        final List<JavaAnnotation> relationAnnotation = new ArrayList<>(
                generateRelationAnnotation(relation, domainName)
        );
        relationAnnotation.add(generateJsonIgnore(relation, domainName));
        return relationAnnotation;
    }

    private List<JavaAnnotation> generateRelationAnnotation(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, final String domainName) {
        return switch (relation.getRelationType()) {
            case ONE_TO_ONE -> generateOneToOneAnnotation(relation, domainName);
            case ONE_TO_MANY -> generateOneToManyAnnotation(domainName);
            case MANY_TO_ONE -> generateManyToOneAnnotation();
            case MANY_TO_MANY -> generateManyToManyAnnotation(relation, domainName);
        };
    }

    private List<JavaAnnotation> generateManyToManyAnnotation(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, final String domainName) {
        final JavaAnnotation manyToManyAnnotation = JavaAnnotation.builder()
                .name("jakarta.persistence.ManyToMany");
        if (!relation.getJoin()) {
            manyToManyAnnotation.setAttributes(
                    List.of(
                            JavaAnnotation.Attribute.builder()
                                    .name("mappedBy")
                                    .dataType(String.class)
                                    .values(
                                            List.of(
                                                    StringFormatter.toPlural(
                                                            StringFormatter.toCamelCase(domainName)
                                                    )
                                            )
                                    )
                    )
            );
            return Collections.singletonList(manyToManyAnnotation);
        } else {
            return List.of(
                    manyToManyAnnotation,
                    JavaAnnotation.builder()
                            .name("jakarta.persistence.JoinTable")
                            .attributes(
                                    List.of(
                                            JavaAnnotation.Attribute.builder()
                                                    .name("name")
                                                    .dataType(String.class)
                                                    .values(List.of(StringFormatter.toSnakeCase(domainName) + "__" + StringFormatter.toSnakeCase(relation.getTo()))),
                                            JavaAnnotation.Attribute.builder()
                                                    .name("joinColumns")
                                                    .dataType(Annotation.class)
                                                    .values(
                                                            List.of(
                                                                    JavaAnnotation.builder()
                                                                            .name("jakarta.persistence.JoinColumn")
                                                                            .attributes(
                                                                                    List.of(
                                                                                            JavaAnnotation.Attribute.builder()
                                                                                                    .name("name")
                                                                                                    .dataType(String.class)
                                                                                                    .values(List.of(StringFormatter.toCamelCase(domainName) + "_id"))
                                                                                    )
                                                                            )
                                                            )
                                                    ),
                                            JavaAnnotation.Attribute.builder()
                                                    .name("inverseJoinColumns")
                                                    .dataType(Annotation.class)
                                                    .values(
                                                            List.of(
                                                                    JavaAnnotation.builder()
                                                                            .name("jakarta.persistence.JoinColumn")
                                                                            .attributes(
                                                                                    List.of(
                                                                                            JavaAnnotation.Attribute.builder()
                                                                                                    .name("name")
                                                                                                    .dataType(String.class)
                                                                                                    .values(List.of(StringFormatter.toCamelCase(relation.getTo()) + "_id"))
                                                                                    )
                                                                            )
                                                            )
                                                    )
                                    )
                            )
            );
        }
    }

    private List<JavaAnnotation> generateManyToOneAnnotation() {
        return Collections.singletonList(
                JavaAnnotation.builder().name("jakarta.persistence.ManyToOne")
        );
    }

    private List<JavaAnnotation> generateOneToManyAnnotation(final String domainName) {
        return Collections.singletonList(
                JavaAnnotation.builder()
                        .name("jakarta.persistence.OneToMany")
                        .attributes(
                                List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("mappedBy")
                                                .dataType(String.class)
                                                .values(List.of(StringFormatter.toCamelCase(domainName)))
                                )
                        )
        );
    }

    private List<JavaAnnotation> generateOneToOneAnnotation(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, final String domainName) {
        final JavaAnnotation oneToOneAnnotation = JavaAnnotation.builder()
                .name("jakarta.persistence.OneToOne");
        if (!relation.getJoin()) {
            oneToOneAnnotation.setAttributes(
                    List.of(
                            JavaAnnotation.Attribute.builder()
                                    .name("mappedBy")
                                    .dataType(String.class)
                                    .values(List.of(StringFormatter.toCamelCase(domainName)))
                    )
            );
            return Collections.singletonList(oneToOneAnnotation);
        } else {
            return List.of(
                    oneToOneAnnotation,
                    JavaAnnotation.builder()
                            .name("jakarta.persistence.JoinColumn")
                            .attributes(
                                    List.of(
                                            JavaAnnotation.Attribute.builder()
                                                    .name("unique")
                                                    .dataType(Boolean.class)
                                                    .values(List.of("true"))
                                    )
                            )
            );
        }
    }

    private JavaAnnotation generateJsonIgnore(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation, String domainName) {
        return JavaAnnotation.builder()
                .name("com.fasterxml.jackson.annotation.JsonIgnoreProperties")
                .attributes(List.of(
                                JavaAnnotation.Attribute.builder()
                                        .name("value")
                                        .dataType(String.class)
                                        .values(List.of(isManyToMany(relation) ? StringFormatter.toPlural(domainName) : domainName))
                        )
                );
    }

    private boolean isManyToMany(SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation) {
        return relation.getRelationType() == SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription.Relation.MANY_TO_MANY;
    }

    private JavaFieldDeclaration generateFieldDeclarationForRelation(final SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation) {
        final JavaFieldDeclaration field = JavaFieldDeclaration.builder()
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.FIELD_MODIFIERS)
                                .modifiers(Modifier.PRIVATE)
                )
                .dataType(resolveRelationDataType(relation))
                .name(resolveRelationFieldName(relation));

        if (isOneToManyOrManyToMany(relation)) {
            field.setGenericTypes(List.of(StringFormatter.toPascalCase(relation.getTo())));
        }

        return field;
    }

    private String resolveRelationFieldName(SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation) {
        return isOneToManyOrManyToMany(relation) ?
                StringFormatter.toPlural(StringFormatter.toCamelCase(relation.getTo()))
                : StringFormatter.toCamelCase(relation.getTo());
    }

    private String resolveRelationDataType(SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation) {
        return isOneToManyOrManyToMany(relation) ?
                "java.util.List" : StringFormatter.toPascalCase(relation.getTo());
    }

    private boolean isOneToManyOrManyToMany(SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription relation) {
        return relation.getRelationType() == SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription.Relation.ONE_TO_MANY || relation.getRelationType() == SpringModelModuleDomainTypeDescription.SpringFieldRelationDescription.Relation.MANY_TO_MANY;
    }

    private static JavaFieldDeclaration generateIdField(final SpringDataType idType) {
        return JavaFieldDeclaration.builder()
                .modifiers(JavaModifier.builder()
                        .type(JavaModifier.FIELD_MODIFIERS)
                        .modifiers(Modifier.PRIVATE))
                .dataType(idType.getDataType())
                .name("id")
                .annotations(generateIdAnnotations(idType));
    }

    private static List<JavaAnnotation> generateIdAnnotations(final SpringDataType idType) {
        return switch (idType) {
            case UUID -> generateUUIDAnnotations();
            case LONG -> generateLongAnnotations();
            default -> throw new DomainIdDataTypeException("Cannot create id with " + idType);
        };
    }

    private static List<JavaAnnotation> generateLongAnnotations() {
        return List.of(
                JavaAnnotation.builder()
                        .name("jakarta.persistence.Id"),
                JavaAnnotation.builder()
                        .name("jakarta.persistence.GeneratedValue")
                        .attributes(
                                List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("strategy")
                                                .dataType(Enum.class)
                                                .values(List.of("jakarta.persistence.GenerationType.SEQUENCE")),
                                        JavaAnnotation.Attribute.builder()
                                                .name("generator")
                                                .dataType(String.class)
                                                .values(List.of("sequenceGenerator"))
                                )
                        ),
                JavaAnnotation.builder()
                        .name("jakarta.persistence.SequenceGenerator")
                        .attributes(
                                List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("name")
                                                .dataType(String.class)
                                                .values(List.of("sequenceGenerator"))
                                )
                        )
        );
    }

    private static List<JavaAnnotation> generateUUIDAnnotations() {
        return List.of(
                JavaAnnotation.builder()
                        .name("jakarta.persistence.Id"),
                JavaAnnotation.builder()
                        .name("jakarta.persistence.GeneratedValue")
                        .attributes(
                                List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("strategy")
                                                .dataType(Enum.class)
                                                .values(List.of("jakarta.persistence.GenerationType.UUID"))
                                )
                        ),
                JavaAnnotation.builder()
                        .name("jakarta.persistence.Column")
                        .attributes(
                                List.of(
                                        JavaAnnotation.Attribute.builder()
                                                .name("name")
                                                .dataType(String.class)
                                                .values(List.of("id")),
                                        JavaAnnotation.Attribute.builder()
                                                .name("length")
                                                .dataType(Integer.class)
                                                .values(List.of("36"))
                                )
                        )
        );
    }

    private List<AbstractJavaFieldDeclaration> generateSimpleDomainFields(final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        return new ArrayList<>(domainTypeDescription.getFields()
                .stream()
                .map(field -> generateField(field, generateSimpleFieldAnnotations(field))
                )
                .toList());
    }

    private JavaFieldDeclaration generateField(SpringModelModuleDomainTypeFieldDescription field, List<JavaAnnotation> field1) {
        final JavaFieldDeclaration fieldDeclaration = generateFieldDeclaration(field);
        fieldDeclaration.setAnnotations(field1);
        return fieldDeclaration;
    }

    private List<JavaAnnotation> generateDBFieldAnnotations(final SpringModelModuleDomainTypeFieldDescription field) {
        final List<JavaAnnotation> annotations = new ArrayList<>(generateSimpleFieldAnnotations(field));
        List<JavaAnnotation.Attribute> columnAttributes = new ArrayList<>(List.of(
                JavaAnnotation.Attribute.builder()
                        .name("name")
                        .dataType(String.class)
                        .values(List.of(StringFormatter.toSnakeCase(field.getName())))
        ));
        if (field.getLength() != null) {
            columnAttributes.add(
                    JavaAnnotation.Attribute.builder()
                            .name("length")
                            .dataType(Integer.class)
                            .values(List.of(field.getLength().toString()))
            );
            annotations.add(
                    JavaAnnotation.builder()
                            .name("jakarta.validation.constraints.Size")
                            .attributes(
                                    List.of(
                                            JavaAnnotation.Attribute.builder()
                                                    .name("min")
                                                    .dataType(Integer.class)
                                                    .values(List.of(field.getLength().toString())),
                                            JavaAnnotation.Attribute.builder()
                                                    .name("max")
                                                    .dataType(Integer.class)
                                                    .values(List.of(field.getLength().toString()))
                                    )
                            )
            );
        }
        if (field.isUnique()) {
            columnAttributes.add(
                    JavaAnnotation.Attribute.builder()
                            .name("unique")
                            .dataType(Boolean.class)
                            .values(List.of("true"))
            );
        }
        if (!field.isNullable()) {
            columnAttributes.add(
                    JavaAnnotation.Attribute.builder()
                            .name("nullable")
                            .dataType(Boolean.class)
                            .values(List.of("false"))
            );
        }
        if (!field.isUpdatable()) {
            columnAttributes.add(
                    JavaAnnotation.Attribute.builder()
                            .name("updatable")
                            .dataType(Boolean.class)
                            .values(List.of("false"))
            );
        }
        if (field.isExcludeFromJson()) {
            annotations.add(
                    JavaAnnotation.builder()
                            .name("com.fasterxml.jackson.annotation.JsonIgnore")
            );
        }
        if (field.isTransient()) {
            annotations.add(
                    JavaAnnotation.builder()
                            .name("jakarta.persistence.Transient")
            );
        } else {
            annotations.add(
                    JavaAnnotation.builder()
                            .name("jakarta.persistence.Column")
                            .attributes(columnAttributes)
            );
        }
        return annotations;
    }

    private List<JavaAnnotation> generateSimpleFieldAnnotations(SpringModelModuleDomainTypeFieldDescription field) {
        final List<JavaAnnotation> annotations = new ArrayList<>();
        if (field.getDefaultValue() != null) {
            annotations.add(
                    JavaAnnotation.builder()
                            .name("lombok.Builder.Default")
            );
        }
        return annotations;
    }

    private JavaFieldDeclaration generateFieldDeclaration(final SpringModelModuleDomainTypeFieldDescription field) {
        return JavaFieldDeclaration.builder()
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.FIELD_MODIFIERS)
                                .modifiers(Modifier.PRIVATE)
                )
                .dataType(field.getDataType().getDataType())
                .name(field.getName())
                .value(field.getDefaultValue());
    }

    private static List<JavaAnnotation> generateDBDomainAnnotations(final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        final List<JavaAnnotation> annotations = new ArrayList<>(
                List.of(
                        JavaAnnotation.builder()
                                .name("jakarta.persistence.Table")
                                .attributes(
                                        List.of(
                                                JavaAnnotation.Attribute.builder()
                                                        .name("name")
                                                        .dataType(String.class)
                                                        .values(List.of(StringFormatter.toSnakeCase(domainTypeDescription.getName())))
                                        )
                                ),
                        JavaAnnotation.builder()
                                .name("jakarta.persistence.Entity")
                )
        );
        annotations.addAll(generateSimpleDomainAnnotations());
        return annotations;
    }

    private static List<JavaAnnotation> generateSimpleDomainAnnotations() {
        return List.of(
                JavaAnnotation.builder()
                        .name("lombok.Builder"),
                JavaAnnotation.builder()
                        .name("lombok.Data"),
                JavaAnnotation.builder()
                        .name("lombok.NoArgsConstructor"),
                JavaAnnotation.builder()
                        .name("lombok.AllArgsConstructor"));
    }
}
