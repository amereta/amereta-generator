package tech.amereta.generator.service.spring.generator.module.db;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;

import java.lang.reflect.Modifier;
import java.util.List;

public final class DataBaseConfigurationGenerator extends AbstractSpringSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final String className = "DatabaseConfiguration";
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
                                                                .name("org.springframework.context.annotation.Configuration"),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.data.jpa.repository.config.EnableJpaRepositories")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("value")
                                                                                        .dataType(String.class)
                                                                                        .values(List.of(basePackage(applicationDescription) + ".repository"))
                                                                        )
                                                                ),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.data.jpa.repository.config.EnableJpaAuditing")
                                                                .attributes(
                                                                        List.of(
                                                                                JavaAnnotation.Attribute.builder()
                                                                                        .name("auditorAwareRef")
                                                                                        .dataType(String.class)
                                                                                        .values(List.of("springSecurityAuditorAware"))
                                                                        )
                                                                ),
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.transaction.annotation.EnableTransactionManagement")
                                                )
                                        )
                        )
                );
    }
}
