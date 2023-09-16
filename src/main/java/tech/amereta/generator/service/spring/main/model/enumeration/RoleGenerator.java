package tech.amereta.generator.service.spring.main.model.enumeration;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.code.java.declaration.JavaEnumFieldDeclaration;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class RoleGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "Role";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.enumeration")
                .name(CLASS_NAME)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.ENUM)
                                .name(CLASS_NAME)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .fieldDeclarations(List.of(
                                        JavaEnumFieldDeclaration.builder()
                                                .name("ROOT")
                                                .build(),
                                        JavaEnumFieldDeclaration.builder()
                                                .name("ADMIN")
                                                .build(),
                                        JavaEnumFieldDeclaration.builder()
                                                .name("USER")
                                                .build()))
                                .build()))
                .build();
    }
}
