package tech.amereta.generator.exception;

public class ApplicationHaveTwoDifferentDatabasesException extends AbstractBadRequestException {

    @Override
    public String getCode() {
        return "002";
    }
}
