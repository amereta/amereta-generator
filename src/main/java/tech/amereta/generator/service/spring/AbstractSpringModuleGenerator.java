package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.AbstractJavaModuleDescription;
import tech.amereta.core.java.JavaCompilationUnit;

import java.util.List;

public abstract class AbstractSpringModuleGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModuleDescription javaModuleDescription);
}
