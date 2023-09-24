package tech.amereta.generator.service.spring.generator.module;

import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.core.java.JavaCompilationUnit;

import java.util.List;

public abstract class AbstractSpringModuleGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractSpringModuleDescription javaModuleDescription);
}
