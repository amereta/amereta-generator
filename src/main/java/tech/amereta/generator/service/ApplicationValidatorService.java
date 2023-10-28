package tech.amereta.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.generator.description.ApplicationDescriptionWrapper;

@Service
public class ApplicationValidatorService {

    @Autowired
    private BeanResolverService beanResolverService;

    public void validate(final ApplicationDescriptionWrapper application) {
        beanResolverService.findOneByTypeAndAnnotation(
                        ApplicationValidator.class,
                        application.getApplication().getGenerator()
                )
                .ifPresent(
                        validator -> validator.validate(application)
                );
    }
}
