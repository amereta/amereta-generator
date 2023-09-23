package tech.amereta.generator.service.spring.module;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.AbstractJavaModuleDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleGenerator;
import tech.amereta.core.java.JavaCompilationUnit;

import java.util.ArrayList;
import java.util.List;

public final class SpringDBModuleGenerator extends AbstractSpringModuleGenerator {

    @Override
    public List<JavaCompilationUnit> generate(SpringBootApplicationDescription applicationDescription, AbstractJavaModuleDescription javaModuleDescription) {
        return new ArrayList<>();
    }
}
