package tech.amereta.core.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tech.amereta.core.domain.model.ApplicationDescription;

import java.io.OutputStream;

@Service
public class ApplicationGeneratorService {

    @Autowired
    private ApplicationContext context;

    public void generate(ApplicationDescription application, OutputStream outputStream) {
        final ApplicationGenerator generator = (ApplicationGenerator) context.getBean(application.getApplication().getGenerator());
        generator.generate(application, outputStream);
    }
}
