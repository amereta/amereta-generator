package tech.amereta.generator.service;

import tech.amereta.generator.description.ApplicationDescriptionWrapper;

public interface ApplicationValidator {

    void validate(ApplicationDescriptionWrapper application);
}
