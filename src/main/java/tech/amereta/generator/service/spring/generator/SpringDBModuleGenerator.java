package tech.amereta.generator.service.spring.generator;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.AbstractJavaModuleDescription;
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
