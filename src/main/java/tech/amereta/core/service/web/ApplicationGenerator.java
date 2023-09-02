package tech.amereta.core.service.web;

import tech.amereta.core.domain.model.ApplicationDescription;

import java.io.OutputStream;

public interface ApplicationGenerator {

    void generate(ApplicationDescription application, OutputStream outputStream);
}
