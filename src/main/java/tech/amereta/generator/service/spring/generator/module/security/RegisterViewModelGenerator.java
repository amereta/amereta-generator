package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RegisterViewModelGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "RegisterVM";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final SpringModelModuleDomainTypeDescription domainTypeDescription = getAuthenticableDomain(applicationDescription);

        final List<AbstractJavaFieldDeclaration> fieldDeclarations = new ArrayList<>(authenticableDomainMandatoryFields());

        fieldDeclarations.addAll(domainNotNullFields(domainTypeDescription));

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
                                        .fieldDeclarations(fieldDeclarations)
                        )
                );
    }

    private static List<AbstractJavaFieldDeclaration> authenticableDomainMandatoryFields() {
        return List.of(
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
                        .name("language")
                        .dataType("tech.amereta.starter.model.enumeration.Language")
                        .modifiers(
                                JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PRIVATE)
                        )
        );
    }

    private static List<AbstractJavaFieldDeclaration> domainNotNullFields(final SpringModelModuleDomainTypeDescription domainTypeDescription) {
        return domainTypeDescription.getFields()
                .stream()
                .filter(field -> !field.isNullable())
                .map(RegisterViewModelGenerator::convertFieldDescriptionToDeclaration)
                .toList();
    }

    private static AbstractJavaFieldDeclaration convertFieldDescriptionToDeclaration(final SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        final List<JavaAnnotation> annotations = new ArrayList<>(
                Collections.singleton(
                        JavaAnnotation.builder()
                                .name("jakarta.validation.constraints.NotNull")
                )
        );
        if (fieldDescription.getLength() != null) {
            annotations.add(
                    JavaAnnotation.builder()
                    .name("jakarta.validation.constraints.Size")
                    .attributes(
                            List.of(
                                    JavaAnnotation.Attribute.builder()
                                            .name("min")
                                            .dataType(Integer.class)
                                            .values(List.of(fieldDescription.getLength().toString())),
                                    JavaAnnotation.Attribute.builder()
                                            .name("max")
                                            .dataType(Integer.class)
                                            .values(List.of(fieldDescription.getLength().toString()))
                            )
                    )
            );
        }
        return JavaFieldDeclaration.builder()
                .name(fieldDescription.getName())
                .dataType(fieldDescription.getDataType().getDataType())
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PRIVATE)
                )
                .annotations(annotations);
    }
}
