package tech.amereta.core.service.generator.spring;

import tech.amereta.core.domain.description.SpringBootApplicationDescription;

public abstract class AbstractSourceCodeGenerator {

    protected static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }
}
