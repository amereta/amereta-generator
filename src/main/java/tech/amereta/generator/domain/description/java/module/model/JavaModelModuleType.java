package tech.amereta.generator.domain.description.java.module.model;

public enum JavaModelModuleType {

    DOMAIN("domain"),
    ENUM("enum");

    private final String packageName;

    JavaModelModuleType(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return packageName.split("\\.")[packageName.split("\\.").length - 1];
    }

    public String getPackageName() {
        return packageName;
    }

}
