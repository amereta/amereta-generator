package tech.amereta.generator.service;

import tech.amereta.generator.description.ApplicationDescriptionWrapper;

import java.io.IOException;
import java.io.OutputStream;

public interface ApplicationGenerator {

    void generate(ApplicationDescriptionWrapper application, OutputStream outputStream) throws IOException;
}
