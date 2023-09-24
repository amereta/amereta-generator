package tech.amereta.generator.service.spring.generator.module.db;

import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleGenerator;

import java.util.List;

public final class SpringDBModuleGenerator extends AbstractSpringModuleGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleDescription javaModuleDescription) {
        final SpringDBModuleDescription springDBModuleDescription = (SpringDBModuleDescription) javaModuleDescription;

        return springDBModuleDescription.getDb().getGenerator().generate(applicationDescription, springDBModuleDescription.getDb());
    }
}
