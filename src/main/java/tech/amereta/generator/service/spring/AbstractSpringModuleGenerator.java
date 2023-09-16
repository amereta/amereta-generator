package tech.amereta.generator.service.spring;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.util.JavaModifier;

import java.lang.reflect.Modifier;
import java.util.List;

public abstract class AbstractSpringModuleGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModuleDescription javaModuleDescription);

    protected JavaModifier generateModifiers(List<String> modifiers) {
        return JavaModifier.builder()
                .type(JavaModifier.TYPE_MODIFIERS)
                .modifiers(modifiers.stream()
                        .map(this::decodeModifier)
                        .reduce(0, (a, b) -> a | b))
                .build();
    }

    private int decodeModifier(String modifier) {
        return switch (modifier) {
            case "public" -> Modifier.PUBLIC;
            case "protected" -> Modifier.PROTECTED;
            case "private" -> Modifier.PRIVATE;
            case "abstract" -> Modifier.ABSTRACT;
            case "static" -> Modifier.STATIC;
            case "final" -> Modifier.FINAL;
            case "strictfp" -> Modifier.STRICT;
            default -> 0;
        };
    }
}
