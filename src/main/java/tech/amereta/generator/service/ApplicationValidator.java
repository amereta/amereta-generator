package tech.amereta.generator.service;

import tech.amereta.lang.description.ApplicationDescriptionWrapper;

public interface ApplicationValidator {

    void validate(ApplicationDescriptionWrapper application);
}
