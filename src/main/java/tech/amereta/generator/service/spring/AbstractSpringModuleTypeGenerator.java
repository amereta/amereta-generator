package tech.amereta.generator.service.spring;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.model.type.AbstractJavaModelModuleTypeDescription;
import tech.amereta.generator.util.code.java.JavaCompilationUnit;

import java.util.List;

public abstract class AbstractSpringModuleTypeGenerator extends AbstractSpringSourceCodeGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModelModuleTypeDescription model);
}
