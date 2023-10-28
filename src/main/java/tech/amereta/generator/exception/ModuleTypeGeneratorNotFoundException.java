package tech.amereta.generator.exception;

import tech.amereta.lang.description.spring.SpringModuleType;

public class ModuleTypeGeneratorNotFoundException extends AbstractBadRequestException {

    public ModuleTypeGeneratorNotFoundException(SpringModuleType module, String type) {
        super(String.format("couldn't find generator service for %s module %s type", module, type));
    }

    @Override
    public String getCode() {
        return "012";
    }
}
