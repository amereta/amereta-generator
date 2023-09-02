package tech.amereta.core.domain.description.java;

public enum JavaObjectType {

    DAO("dao"),//
    REPOSITORY("dao.repository"),//
    SERVICE("dao.service"),//
    CONTROLLEER("controller");

    private final String packageName;

    JavaObjectType(String packageName) {
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
