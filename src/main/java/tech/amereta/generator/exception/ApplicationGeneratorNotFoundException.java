package tech.amereta.generator.exception;

import tech.amereta.lang.description.ApplicationType;

public class ApplicationGeneratorNotFoundException extends AbstractBadRequestException {

    public ApplicationGeneratorNotFoundException(ApplicationType applicationType) {
        super(String.format("couldn't find generator service for %s application", applicationType));
    }

    @Override
    public String getCode() {
        return "010";
    }
}
