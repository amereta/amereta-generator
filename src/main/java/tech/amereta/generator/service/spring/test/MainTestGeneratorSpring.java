package tech.amereta.generator.service.spring.test;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.generator.util.code.java.declaration.JavaMethodDeclaration;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class MainTestGeneratorSpring extends AbstractSpringSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final String className = StringFormatter.toPascalCase(applicationDescription.getName()).concat("ApplicationTests");

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription))
                .name(className)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(className)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.boot.test.context.SpringBootTest")
                                                .build()))
                                .methodDeclarations(List.of(
                                        JavaMethodDeclaration.builder()
                                                .name("contextLoads")
                                                .modifiers(JavaModifier.builder()
                                                        .type(JavaModifier.METHOD_MODIFIERS)
                                                        .build())
                                                .returnType("void")
                                                .annotations(List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.junit.jupiter.api.Test")
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }
}
