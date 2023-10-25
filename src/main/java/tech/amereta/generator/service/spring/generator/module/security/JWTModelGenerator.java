package tech.amereta.generator.service.spring.generator.module.security;

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

public final class JWTModelGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "JWT";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model")
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
                                                                .name("lombok.Builder"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.Data"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.NoArgsConstructor"),
                                                        JavaAnnotation.builder()
                                                                .name("lombok.AllArgsConstructor")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .name("token")
                                                                .dataType("String")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.TYPE_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                )
                                        )
                        )
                );
    }
}
