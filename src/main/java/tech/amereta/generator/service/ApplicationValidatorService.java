package tech.amereta.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.generator.description.ApplicationDescriptionWrapper;
import tech.amereta.generator.exception.ApplicationGeneratorNotFoundException;

import java.util.Collection;
import java.util.Optional;

@Service
public class ApplicationValidatorService {

    @Autowired
    private BeanResolverService beanResolverService;

    public void validate(final ApplicationDescriptionWrapper application) {
        final Collection<ApplicationValidator> possibleApplicationValidators = beanResolverService
                .findGeneratorByAnnotation(
                        ApplicationValidator.class,
                        application.getApplication().getGenerator()
                );

        possibleApplicationValidators.stream()
                .findFirst()
                .ifPresent(
                        validator -> validator.validate(application)
                );
    }
}
