package tech.amereta.generator.service.generator.spring;

import tech.amereta.generator.domain.description.SpringBootApplicationDescription;

public abstract class AbstractSpringSourceCodeGenerator {

    protected static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }
}
