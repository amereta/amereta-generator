package tech.amereta.generator.description.spring.model.type;

public enum SpringModelModuleType {

    DOMAIN("domain"),
    ENUM("enum");

    private final String packageName;

    SpringModelModuleType(String packageName) {
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
