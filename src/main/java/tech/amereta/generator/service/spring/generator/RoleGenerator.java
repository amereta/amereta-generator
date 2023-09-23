package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.core.java.declaration.JavaEnumFieldDeclaration;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;

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
                                        .modifiers(Modifier.PUBLIC))
                                .fieldDeclarations(List.of(
                                        JavaEnumFieldDeclaration.builder()
                                                .name("ROOT"),
                                        JavaEnumFieldDeclaration.builder()
                                                .name("ADMIN"),
                                        JavaEnumFieldDeclaration.builder()
                                                .name("USER")))));
    }
}
