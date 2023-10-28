package tech.amereta.generator.exception;

import tech.amereta.generator.description.ApplicationType;

public class ApplicationGeneratorNotFoundException extends AbstractBadRequestException {

    public ApplicationGeneratorNotFoundException(ApplicationType applicationType) {
        super(String.format("couldn't find generator service for %s application type", applicationType));
    }

    @Override
    public String getCode() {
        return "010";
    }
}
