package tech.amereta.generator.exception;

public class DuplicateEnumNameException extends AbstractBadRequestException {

    public DuplicateEnumNameException(String name) {
        super(String.format("Enum with name %s is already exist!", name));
    }

    @Override
    public String getCode() {
        return "005";
    }
}
