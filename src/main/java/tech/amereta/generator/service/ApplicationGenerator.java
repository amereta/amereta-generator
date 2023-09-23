package tech.amereta.generator.service;

import tech.amereta.generator.description.ApplicationDescription;

import java.io.IOException;
import java.io.OutputStream;

public interface ApplicationGenerator {

    void generate(ApplicationDescription application, OutputStream outputStream) throws IOException;
}
