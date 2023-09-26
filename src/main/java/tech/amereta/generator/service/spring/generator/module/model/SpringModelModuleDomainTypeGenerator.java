package tech.amereta.generator.service.spring.generator.module.model;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class SpringModelModuleDomainTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleTypeDescription model) {
        final SpringModelModuleDomainTypeDescription springModelModuleDomainTypeDescription = (SpringModelModuleDomainTypeDescription) model;

        return applicationHasDataBase(applicationDescription) ?
                generateDBDomains(applicationDescription, springModelModuleDomainTypeDescription) :
                generateSimpleDomain(applicationDescription, springModelModuleDomainTypeDescription);
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
                        calculateIdType(domainTypeDescription.getIdType())
                )
        );
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".repository")
                .name(className)
                .typeDeclarations(List.of(repository));
    }

    private List<JavaCompilationUnit> generateSimpleDomain(SpringBootApplicationDescription applicationDescription, SpringModelModuleDomainTypeDescription domainTypeDescription) {
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
                        .map(field -> {
                                    final JavaFieldDeclaration fieldDeclaration = generateFieldDeclaration(field);
                                    fieldDeclaration.setAnnotations(generateDBFieldAnnotations(field));
                                    return fieldDeclaration;
                                }
                        )
                        .toList()
        );
        return fieldDeclarations;
    }

    private static JavaFieldDeclaration generateIdField(final String idType) {
        return JavaFieldDeclaration.builder()
                .modifiers(JavaModifier.builder()
                        .type(JavaModifier.FIELD_MODIFIERS)
                        .modifiers(Modifier.PRIVATE))
                .dataType(calculateIdType(idType))
                .name("id")
                .annotations(generateIdAnnotations(idType));
    }

    private static List<JavaAnnotation> generateIdAnnotations(final String idType) {
        return switch (idType) {
            case "UUID" -> generateUUIDAnnotations();
            case "Long" -> generateLongAnnotations();
            default -> throw new ClassCastException("Cannot create idType with " + idType);
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

    private static String calculateIdType(String idType) {
        return "UUID".equals(idType) ? "java.util.UUID" : idType;
    }

    private List<AbstractJavaFieldDeclaration> generateSimpleDomainFields(final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        return new ArrayList<>(domainTypeDescription.getFields()
                .stream()
                .map(field -> {
                            final JavaFieldDeclaration fieldDeclaration = generateFieldDeclaration(field);
                            fieldDeclaration.setAnnotations(generateSimpleFieldAnnotations(field));
                            return fieldDeclaration;
                        }
                )
                .toList());
    }

    private List<JavaAnnotation> generateDBFieldAnnotations(SpringModelModuleDomainTypeFieldDescription field) {
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

    private JavaTypeDeclaration generateClassDeclaration(String className) {
        return JavaTypeDeclaration.builder()
                .type(JavaType.CLASS)
                .name(className)
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                );
    }

    private JavaTypeDeclaration generateInterfaceDeclaration(String className) {
        return JavaTypeDeclaration.builder()
                .type(JavaType.INTERFACE)
                .name(className)
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                );
    }

    private JavaFieldDeclaration generateFieldDeclaration(SpringModelModuleDomainTypeFieldDescription field) {
        return JavaFieldDeclaration.builder()
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.FIELD_MODIFIERS)
                                .modifiers(Modifier.PRIVATE)
                )
                .dataType(field.getDataType())
                .name(field.getName())
                .value(field.getDefaultValue());
    }

    private static List<JavaAnnotation> generateDBDomainAnnotations(SpringModelModuleDomainTypeDescription domainTypeDescription) {
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
