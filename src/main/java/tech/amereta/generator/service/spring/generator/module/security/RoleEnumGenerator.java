package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.JavaEnumFieldDeclaration;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;
import tech.amereta.lang.description.spring.model.type.SpringModelModuleDomainTypeDescription;

import java.lang.reflect.Modifier;
import java.util.List;

public final class RoleEnumGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "Role";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final SpringModelModuleDomainTypeDescription authenticableDomain = getAuthenticableDomain(applicationDescription);

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.enumeration")
                .name(CLASS_NAME)
                .typeDeclarations(
                        List.of(
                                JavaTypeDeclaration.builder()
                                        .type(JavaType.ENUM)
                                        .name(CLASS_NAME)
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC)
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaEnumFieldDeclaration.builder()
                                                                .name("ADMIN"),
                                                        JavaEnumFieldDeclaration.builder()
                                                                .name(StringFormatter.toSnakeCase(authenticableDomain.getName()).toUpperCase())
                                                )
                                        )
                        )
                );
    }
}
