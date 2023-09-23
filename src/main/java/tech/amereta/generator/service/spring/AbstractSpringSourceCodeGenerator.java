package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.JavaDBModuleDescription;

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
