package tech.amereta.generator.exception;

public class DomainIdDataTypeException extends AbstractBadRequestException {

    public DomainIdDataTypeException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "003";
    }
}
