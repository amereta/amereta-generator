package tech.amereta.generator.service.spring.generator;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;

import java.lang.reflect.Modifier;
import java.util.List;

// TODO: Later!
public final class LiquibaseConfigurationGenerator extends AbstractSpringSourceCodeGenerator {

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        final String className = "LiquibaseConfiguration";
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
                                                                .name("org.springframework.context.annotation.Configuration")
                                                )
                                        )
                                        .fieldDeclarations(
                                                List.of(
                                                        JavaFieldDeclaration.builder()
                                                                .name("environment")
                                                                .dataType("org.springframework.core.env.Environment")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.FIELD_MODIFIERS)
                                                                                .modifiers(Modifier.PRIVATE)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.beans.factory.annotation.Autowired")
                                                                        )
                                                                )
                                                )
                                        )
                                        .methodDeclarations(
                                                List.of(
                                                        JavaMethodDeclaration.builder()
                                                                .name("liquibase")
                                                                .returnType("liquibase.integration.spring.SpringLiquibase")
                                                                .modifiers(
                                                                        JavaModifier.builder()
                                                                                .type(JavaModifier.METHOD_MODIFIERS)
                                                                                .modifiers(Modifier.PUBLIC)
                                                                )
                                                                .annotations(
                                                                        List.of(
                                                                                JavaAnnotation.builder()
                                                                                        .name("org.springframework.context.annotation.Bean")
                                                                        )
                                                                )
                                                )
                                        )
                        )
                );
    }
}
