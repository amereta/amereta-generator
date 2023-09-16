package tech.amereta.generator.service.spring;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.db.JavaDBModuleDescription;

public abstract class AbstractSpringSourceCodeGenerator {

    public static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }

    public static boolean applicationHasDataBase(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getModules()
                .stream()
                .anyMatch(module -> module instanceof JavaDBModuleDescription);
    }
}
