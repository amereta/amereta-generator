package tech.amereta.generator.exception;

public class ApplicationCannotHaveTwoDifferentDatabasesException extends AbstractBadRequestException {

    @Override
    public String getCode() {
        return "002";
    }
}
