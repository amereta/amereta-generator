package tech.amereta.generator.description;

public interface AbstractApplication {

    String getOwner();

    String getName();

    Class<?> getGenerator();

    Class<?> getValidator();
}
