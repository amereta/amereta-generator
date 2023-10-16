package tech.amereta.generator.exception;

public class AuthorizableDomainNotFoundException extends AbstractBadRequestException {

    @Override
    public String getCode() {
        return "009";
    }
}
