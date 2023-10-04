package tech.amereta.generator.exception;

public class DuplicateDomainNameException extends AbstractBadRequestException {

    public DuplicateDomainNameException(String name) {
        super(String.format("Domain with name %s is already exist!", name));
    }

    @Override
    public String getCode() {
        return "004";
    }
}
