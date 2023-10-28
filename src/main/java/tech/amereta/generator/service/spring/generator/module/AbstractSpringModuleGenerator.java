package tech.amereta.generator.service.spring.generator.module;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.lang.description.spring.AbstractSpringModuleDescription;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;

import java.util.List;

public abstract class AbstractSpringModuleGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractSpringModuleDescription javaModuleDescription);
}
