package tech.amereta.generator.service.spring.generator;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleFieldRelationDescription;
import tech.amereta.generator.description.spring.model.type.SpringRelation;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.util.StringFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public final class LiquibaseChangeLogGenerator implements ISoyConfiguration {

    private final LocalDateTime date = LocalDateTime.now();
    private final String timestamp = String.valueOf(date.getYear())
            + String.valueOf(date.getMonthValue())
            + String.valueOf(date.getDayOfMonth())
            + String.valueOf(date.getHour())
            + String.valueOf(date.getMinute())
            + String.valueOf(date.getNano() / 10000);

    private String name;

    private SpringDataType idType;

    private List<SpringModelModuleDomainTypeFieldDescription> fields;

    private List<SpringModelModuleFieldRelationDescription> relations;

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
                "fields", generateFields()
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/db/changelog/" + timestamp + "_" + StringFormatter.toPascalCase(name) + ".xml");
    }

    private List<String> generateFields() {
        final List<String> fields = new ArrayList<>();
        fields.add(idField());
        fields.addAll(
                this.fields.stream()
                        .filter(field -> !field.isTransient())
                        .map(this::generateField)
                        .toList()
        );
        fields.addAll(
                this.relations.stream()
                        .map(this::convertRelationToField)
                        .map(this::generateField)
                        .toList()
        );
        return fields;
    }

    private String idField() {
        return generateField(
                SpringModelModuleDomainTypeFieldDescription.builder()
                        .name("id")
                        .dataType(idType)
                        .primaryKey(true)
                        .nullable(false)
                        .build()
        );
    }

    private SpringModelModuleDomainTypeFieldDescription convertRelationToField(final SpringModelModuleFieldRelationDescription relationDescription) {
        return SpringModelModuleDomainTypeFieldDescription.builder()
                .name(relationDescription.getTo() + "Id")
                .dataType(relationDescription.getJoinDataType())
                .unique(relationDescription.getRelationType() == SpringRelation.ONE_TO_ONE)
                .nullable(true)
                .build();
    }

    private String generateField(final SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        return "\n\t\t\t<column name=\"" + StringFormatter.toSnakeCase(fieldDescription.getName()) + "\" type=\"" + resolveFieldType(fieldDescription.getDataType(), fieldDescription.getLength()) + "\"" +
                resolveConstraints(fieldDescription);
    }

    private String resolveConstraints(SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        if(fieldDescription.isPrimaryKey() || !fieldDescription.isNullable() || fieldDescription.isUnique()) {
            return ">\n\t\t\t\t<constraints " + resolvePrimaryKey(fieldDescription.isPrimaryKey()) + resolveNullable(fieldDescription.isNullable()) + resolveUnique(fieldDescription.isUnique(), fieldDescription.getName()) + "/>\n\t\t\t</column>";
        }
        return " />";
    }

    private String resolvePrimaryKey(boolean isPrimaryKey) {
        if (isPrimaryKey) {
            return "primaryKey=\"true\" ";
        }
        return "";
    }

    private String resolveNullable(boolean isNullable) {
        if(!isNullable) {
            return "nullable=\"false\" ";
        }
        return "";
    }

    private String resolveUnique(final Boolean isUnique, final String fieldName) {
        if (isUnique) {
            return "unique=\"true\" uniqueConstraintName=\"ux_" + StringFormatter.toSnakeCase(this.name) + "__" + StringFormatter.toSnakeCase(fieldName) + "\" ";
        }
        return "";
    }

    private String resolveFieldType(final SpringDataType dataType, final Integer length) {
        return switch (dataType) {
            case STRING -> "varchar(" + resolveFieldLength(length) + ")";
            case UUID -> "${uuidType}";
            case INTEGER -> "integer";
            case LONG -> "bigint";
            case FLOAT -> "${floatType}";
            case DOUBLE -> "double";
            case BIGDECIMAL -> "decimal(21,2)";
            case ZONED_DATETIME -> "${datetimeType}";
        };
    }

    private String resolveFieldLength(final Integer length) {
        return length != null ? String.valueOf(length) : "255";
    }
}
