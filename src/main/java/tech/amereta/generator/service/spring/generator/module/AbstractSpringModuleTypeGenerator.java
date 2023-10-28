package tech.amereta.generator.service.spring.generator.module;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;
import tech.amereta.lang.description.spring.SpringModuleTypeDescription;

import java.util.List;

public abstract class AbstractSpringModuleTypeGenerator extends AbstractSpringSourceCodeGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final SpringModuleTypeDescription type);
}
