package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;

public abstract class AbstractSpringSourceCodeGenerator {

    public static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }

    public static boolean applicationHasDataBase(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getModules()
                .stream()
                .anyMatch(module -> module instanceof SpringDBModuleDescription);
    }
}
