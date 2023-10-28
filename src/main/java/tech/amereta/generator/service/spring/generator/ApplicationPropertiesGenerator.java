package tech.amereta.generator.service.spring.generator;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;

import java.lang.reflect.Modifier;
import java.util.List;

public final class ApplicationPropertiesGenerator extends AbstractSpringSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final String className = StringFormatter.toPascalCase(applicationDescription.getName()) + "Configuration";

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".config")
                .name(className)
                .typeDeclarations(
                        List.of(
                                JavaTypeDeclaration.builder()
                                        .type(JavaType.CLASS)
                                        .name(className)
                                        .modifiers(
                                                JavaModifier.builder()
                                                        .type(JavaModifier.TYPE_MODIFIERS)
                                                        .modifiers(Modifier.PUBLIC)
                                        )
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.boot.context.properties.ConfigurationProperties")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("prefix")
                                                                                        .dataType(String.class)
                                                                                        .values(List.of(StringFormatter.toKebabCase(applicationDescription.getName()))),
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("ignoreUnknownFields")
                                                                                        .dataType(Boolean.class)
                                                                                        .values(List.of("false"))
                                                                        )
                                                                )
                                                )
                                        )
                        )
                );
    }
}
