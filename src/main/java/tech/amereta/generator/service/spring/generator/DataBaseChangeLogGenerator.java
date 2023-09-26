package tech.amereta.generator.service.spring.generator;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.util.StringFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public final class DataBaseChangeLogGenerator implements ISoyConfiguration {

    private final LocalDateTime date = LocalDateTime.now();
    private final String timestamp = String.valueOf(date.getYear())
            + String.valueOf(date.getMonthValue())
            + String.valueOf(date.getDayOfMonth())
            + String.valueOf(date.getHour())
            + String.valueOf(date.getMinute())
            + String.valueOf(date.getNano() / 10000);
    private String name;
    private List<SpringModelModuleDomainTypeFieldDescription> fields;

    @Override
    public String getName() {
        return "amereta.generator.changelog";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("changelog.xml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/changelog.xml.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "name", StringFormatter.toSnakeCase(name),
                "timestamp", timestamp,
                "fields", generateColumns()
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/db/changelog/" + timestamp + "_" + StringFormatter.toPascalCase(name) + ".xml");
    }

    private List<String> generateColumns() {
        return this.fields.stream()
                .map(this::generateColumn)
                .toList();
    }

    private String generateColumn(final SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        return "\n\t\t\t<column name=\"" + fieldDescription.getName() + "\" type=\"" + resolveFieldType(fieldDescription.getDataType(), fieldDescription.getLength()) + "\">\n" +
                "\t\t\t\t<constraints nullable=\"" + fieldDescription.isNullable() + "\" " + resolveUnique(fieldDescription.isUnique()) + " />\n" +
                "\t\t\t</column>";
    }

    private String resolveUnique(final Boolean isUnique) {
        if (isUnique) {
            return "unique=\"true\" uniqueConstraintName=\"ux_" + StringFormatter.toSnakeCase(name) + "_" + StringFormatter.toSnakeCase(getName()) + "\"";
        }
        return "unique=\"false\"";
    }

    private String resolveFieldType(final String dataType, final Integer length) {
        return switch (dataType) {
            case "String" -> "varchar(" + resolveFieldLength(length) + ")";
            case "Double" -> "double";
            default -> throw new ClassCastException("There is no valid type " + dataType);
        };
    }

    private String resolveFieldLength(final Integer length) {
        return length != null ? String.valueOf(length) : "255";
    }
}
