package tech.amereta.generator.description;

public interface AbstractApplication {

    String getName();

    Class<?> getGenerator();

    Class<?> getValidator();
}
