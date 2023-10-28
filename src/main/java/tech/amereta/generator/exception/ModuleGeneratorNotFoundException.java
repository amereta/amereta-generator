package tech.amereta.generator.exception;

import tech.amereta.lang.description.spring.SpringModuleType;

public class ModuleGeneratorNotFoundException extends AbstractBadRequestException {

    public ModuleGeneratorNotFoundException(SpringModuleType type) {
        super(String.format("couldn't find generator service for %s module", type));
    }

    @Override
    public String getCode() {
        return "011";
    }
}
