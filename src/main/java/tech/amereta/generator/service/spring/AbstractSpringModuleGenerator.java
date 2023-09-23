package tech.amereta.generator.service.spring;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.util.code.java.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.util.JavaModifier;

import java.lang.reflect.Modifier;
import java.util.List;

public abstract class AbstractSpringModuleGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModuleDescription javaModuleDescription);
}
