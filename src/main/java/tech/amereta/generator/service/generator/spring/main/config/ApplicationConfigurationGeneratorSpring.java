package tech.amereta.generator.service.generator.spring.main.config;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.service.generator.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaAnnotation;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

public final class ApplicationConfigurationGeneratorSpring extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "ApplicationConfiguration";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".config")
                .name(CLASS_NAME)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(CLASS_NAME)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.boot.context.properties.ConfigurationProperties")
                                                .attributes(List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .name("prefix")
                                                                .type(String.class)
                                                                .values(List.of("application"))
                                                                .build(),
                                                        JavaAnnotation.Attribute.builder()
                                                                .name("ignoreUnknownFields")
                                                                .type(Boolean.class)
                                                                .values(List.of("false"))
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }
}
