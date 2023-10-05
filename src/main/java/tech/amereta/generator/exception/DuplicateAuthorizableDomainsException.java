package tech.amereta.generator.exception;

public class DuplicateAuthorizableDomainsException extends AbstractBadRequestException {

    public DuplicateAuthorizableDomainsException(String domainName) {
        super(String.format("%s cannot be an authorizable Domain, because there is already one exists!", domainName));
    }

    @Override
    public String getCode() {
        return "001";
    }
}
