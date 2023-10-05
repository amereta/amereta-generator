package tech.amereta.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tech.amereta.generator.description.ApplicationDescription;

@Service
public class ApplicationValidatorService {

    @Autowired
    private ApplicationContext context;

    public void validate(final ApplicationDescription application) {
        final ApplicationValidator validator = (ApplicationValidator) context.getBean(application.getApplication().getValidator());
        validator.validate(application);
    }
}
