package tech.amereta.generator.service.spring.generator.module;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;

import java.util.List;

public abstract class AbstractSpringModuleTypeGenerator extends AbstractSpringSourceCodeGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractSpringModuleTypeDescription type);
}
