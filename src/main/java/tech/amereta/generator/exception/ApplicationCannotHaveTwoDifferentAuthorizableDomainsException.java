package tech.amereta.generator.exception;

public class ApplicationCannotHaveTwoDifferentAuthorizableDomainsException extends AbstractBadRequestException {

    @Override
    public String getCode() {
        return "001";
    }
}
