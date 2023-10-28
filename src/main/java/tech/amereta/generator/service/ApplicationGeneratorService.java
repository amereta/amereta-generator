package tech.amereta.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.amereta.generator.description.ApplicationDescriptionWrapper;
import tech.amereta.generator.exception.ApplicationGeneratorNotFoundException;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ApplicationGeneratorService {

    @Autowired
    private BeanResolverService beanResolverService;

    public void generate(final ApplicationDescriptionWrapper application, final OutputStream outputStream) throws IOException {
        final ApplicationGenerator generator = beanResolverService
                .findOneByTypeAndAnnotation(
                        ApplicationGenerator.class,
                        application.getApplication().getGenerator()
                )
                .orElseThrow(() ->
                        new ApplicationGeneratorNotFoundException(application.getApplicationType())
                );

        generator.generate(application, outputStream);
    }
}
