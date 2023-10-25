package tech.amereta.generator.exception;

public class ApplicationHaveTwoDifferentSecurityAuthenticatorException extends AbstractBadRequestException {

    @Override
    public String getCode() {
        return "008";
    }
}
