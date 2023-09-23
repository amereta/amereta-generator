package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.type.AbstractJavaModelModuleTypeDescription;
import tech.amereta.core.java.JavaCompilationUnit;

import java.util.List;

public abstract class AbstractSpringModuleTypeGenerator extends AbstractSpringSourceCodeGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModelModuleTypeDescription model);
}
