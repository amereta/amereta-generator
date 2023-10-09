package tech.amereta.generator.service.spring.generator.module;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringModuleTypeDescription;
import tech.amereta.generator.service.spring.AbstractSpringSourceCodeGenerator;

import java.lang.reflect.Modifier;
import java.util.List;

public abstract class AbstractSpringModuleTypeGenerator extends AbstractSpringSourceCodeGenerator {

    public abstract List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final SpringModuleTypeDescription type);
}
