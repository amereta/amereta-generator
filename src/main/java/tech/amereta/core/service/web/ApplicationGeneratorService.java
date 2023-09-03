package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.description.ApplicationDescription;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ApplicationGeneratorService {

    @Autowired
    private ApplicationContext context;

    public void generate(final ApplicationDescription application, final OutputStream outputStream) throws IOException {
        final ApplicationGenerator generator = (ApplicationGenerator) context.getBean(application.getApplication().getGenerator());
        generator.generate(application, outputStream);
    }
}
