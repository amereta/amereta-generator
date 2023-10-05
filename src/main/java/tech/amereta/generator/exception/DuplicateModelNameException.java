package tech.amereta.generator.exception;

public class DuplicateModelNameException extends AbstractBadRequestException {

    public DuplicateModelNameException(String model, String name) {
        super(String.format("%s with name %s is already exist!", model, name));
    }

    @Override
    public String getCode() {
        return "004";
    }
}
