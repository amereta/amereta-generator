package tech.amereta.generator.description.spring;

import java.lang.annotation.Annotation;

public interface SpringModuleTypeDescription {

    Class<? extends Annotation> getGenerator();
}
