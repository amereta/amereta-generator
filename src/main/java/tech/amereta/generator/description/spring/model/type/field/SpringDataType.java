package tech.amereta.generator.description.spring.model.type.field;

public enum SpringDataType {

    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    FLOAT("Float"),
    DOUBLE("Double"),
    BIGDECIMAL("java.math.BigDecimal"),
    UUID("java.util.UUID"),
    ZONED_DATETIME("java.time.ZonedDateTime");

    private final String dataType;

    SpringDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
