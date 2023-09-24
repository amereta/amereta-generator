package tech.amereta.generator.service.spring.generator.module.db;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.service.spring.generator.DataBaseConfigurationGenerator;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;

import java.util.List;

public final class SpringDBModulePostgreSQLTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleTypeDescription db) {
        return List.of(
                DataBaseConfigurationGenerator.generate(applicationDescription)
        );
    }
}
