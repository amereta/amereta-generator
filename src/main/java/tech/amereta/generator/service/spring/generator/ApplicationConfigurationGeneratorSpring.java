package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;

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
                                        .modifiers(Modifier.PUBLIC))
                                .annotations(List.of(
                                        JavaAnnotation.builder()
                                                .name("org.springframework.boot.context.properties.ConfigurationProperties")
                                                .attributes(List.of(
                                                        JavaAnnotation.Attribute.builder()
                                                                .name("prefix")
                                                                .dataType(String.class)
                                                                .values(List.of("application")),
                                                        JavaAnnotation.Attribute.builder()
                                                                .name("ignoreUnknownFields")
                                                                .dataType(Boolean.class)
                                                                .values(List.of("false"))))))));
    }
}
