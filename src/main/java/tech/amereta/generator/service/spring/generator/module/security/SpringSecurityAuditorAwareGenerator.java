package tech.amereta.generator.service.spring.generator.module.security;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.JavaMethodDeclaration;
import tech.amereta.core.java.expression.JavaMethodInvocationExpression;
import tech.amereta.core.java.expression.JavaValueExpression;
import tech.amereta.core.java.expression.util.JavaMethodInvoke;
import tech.amereta.core.java.statement.JavaReturnStatement;
import tech.amereta.core.java.util.JavaAnnotation;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;

import java.lang.reflect.Modifier;
import java.util.List;

public final class SpringSecurityAuditorAwareGenerator extends AbstractSpringSourceCodeGenerator {

    private static final String CLASS_NAME = "SpringSecurityAuditorAware";

    public static JavaCompilationUnit generate(final SpringBootApplicationDescription applicationDescription) {
        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".security")
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
                                        .implementedClassName("org.springframework.data.domain.AuditorAware")
                                        .tailGenericTypes(List.of("String"))
                                        .annotations(
                                                List.of(
                                                        JavaAnnotation.builder()
                                                                .name("org.springframework.stereotype.Component")
                                                )
                                        )
                                        .methodDeclarations(
                                                List.of(
                                                    JavaMethodDeclaration.builder()
                                                            .name("getCurrentAuditor")
                                                            .returnType("java.util.Optional")
                                                            .genericTypes(List.of("String"))
                                                            .modifiers(
                                                                    JavaModifier.builder()
                                                                            .type(JavaModifier.TYPE_MODIFIERS)
                                                                            .modifiers(Modifier.PUBLIC)
                                                            )
                                                            .annotations(
                                                                    List.of(
                                                                            JavaAnnotation.builder()
                                                                                    .name("Override"),
                                                                            JavaAnnotation.builder()
                                                                                    .name("jakarta.annotation.Nonnull")
                                                                    )
                                                            )
                                                            .statements(
                                                                    List.of(
                                                                            JavaReturnStatement.builder()
                                                                                    .expression(
                                                                                            JavaMethodInvocationExpression.builder()
                                                                                                    .target("java.util.Optional")
                                                                                                    .invokes(
                                                                                                            List.of(
                                                                                                                    JavaMethodInvoke.builder()
                                                                                                                            .method("of")
                                                                                                                            .arguments(
                                                                                                                                    List.of(
                                                                                                                                            JavaValueExpression.builder()
                                                                                                                                                    .type(Enum.class)
                                                                                                                                                    .value("tech.amereta.starter.Constants.SYSTEM")
                                                                                                                                    )
                                                                                                                            )

                                                                                                            )
                                                                                                    )
                                                                                    )
                                                                    )
                                                            )
                                                )
                                        )
                        )
                );
    }
}
