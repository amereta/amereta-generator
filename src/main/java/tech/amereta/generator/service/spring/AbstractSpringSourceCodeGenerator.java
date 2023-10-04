package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.generator.exception.ApplicationHaveTwoDifferentDatabasesException;

import java.util.List;

public abstract class AbstractSpringSourceCodeGenerator {

    public static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }

    public static boolean applicationHasDataBase(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getModules()
                .stream()
                .anyMatch(module -> module instanceof SpringDBModuleDescription);
    }

    public static SpringDBModuleDescription getDataBase(final SpringBootApplicationDescription applicationDescription) {
        final List<AbstractSpringModuleDescription> database = applicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringDBModuleDescription)
                .toList();
        if (database.size() > 1) {
            throw new ApplicationHaveTwoDifferentDatabasesException();
        }
        return (SpringDBModuleDescription) database.get(0);
    }
}
