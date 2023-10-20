package tech.amereta.generator.service.spring.generator;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
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

@AllArgsConstructor
public final class LiquibaseChangeLogGenerator implements ISoyConfiguration {

    private final LocalDateTime date = LocalDateTime.now();
    private final String timestamp = String.valueOf(date.getYear())
            + String.valueOf(date.getMonthValue())
            + String.valueOf(date.getDayOfMonth())
            + String.valueOf(date.getHour())
            + String.valueOf(date.getMinute())
            + String.valueOf(date.getNano() / 10000);

    private SpringModelModuleDomainTypeDescription domainTypeDescription;

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
                "name", StringFormatter.toSnakeCase(domainTypeDescription.getName()),
                "timestamp", timestamp,
                "idType", resolveLoadDataFieldType(domainTypeDescription.getIdType()),
                "fields", generateFields(),
                "manyToManyTables", generateManyToManyTables(),
                "constraints", generateConstraints(),
                "loadData", domainTypeDescription.getAuthenticable(),
                "loadDataFields", generateLoadDataFields()
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/db/changelog/" + timestamp + "_" + StringFormatter.toPascalCase(domainTypeDescription.getName()) + ".xml");
    }

    private List<String> generateFields() {
        final List<String> fields = new ArrayList<>();
        fields.add(idField());
        if (domainTypeDescription.getAuthenticable()) {
            fields.addAll(authenticableFields());
        }
        fields.addAll(domainFields());
        if (domainTypeDescription.getTimestamped()) {
            fields.addAll(timestampedFields());
        }
        fields.addAll(relationFields());
        return fields;
    }

    private String idField() {
        return generateField(
                SpringModelModuleDomainTypeFieldDescription.builder()
                        .name("id")
                        .dataType(domainTypeDescription.getIdType())
                        .primaryKey(true)
                        .nullable(false)
                        .build()
        );
    }

    private List<String> authenticableFields() {
        return List.of(
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("username")
                                .dataType(SpringDataType.STRING)
                                .length(50)
                                .nullable(false)
                                .unique(true)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("email")
                                .dataType(SpringDataType.STRING)
                                .length(254)
                                .unique(true)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("passwordHash")
                                .dataType(SpringDataType.STRING)
                                .length(60)
                                .nullable(false)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("language")
                                .dataType(SpringDataType.STRING)
                                .length(10)
                                .nullable(false)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("activated")
                                .dataType(SpringDataType.BOOLEAN)
                                .nullable(false)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("activationKey")
                                .dataType(SpringDataType.UUID)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("roles")
                                .dataType(SpringDataType.JSON)
                                .build()
                )
        );
    }

    private List<String> domainFields() {
        return domainTypeDescription.getFields().stream()
                .filter(field -> !field.isTransient())
                .map(this::generateField)
                .toList();
    }

    private List<String> timestampedFields() {
        return List.of(
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("createdBy")
                                .dataType(SpringDataType.STRING)
                                .length(50)
                                .nullable(false)
                                .updatable(false)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("createdDate")
                                .dataType(SpringDataType.INSTANT)
                                .nullable(false)
                                .updatable(false)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("lastModifiedBy")
                                .dataType(SpringDataType.STRING)
                                .length(50)
                                .build()
                ),
                generateField(
                        SpringModelModuleDomainTypeFieldDescription.builder()
                                .name("lastModifiedDate")
                                .dataType(SpringDataType.INSTANT)
                                .build()
                )
        );
    }

    private List<String> relationFields() {
        return domainTypeDescription.getRelations().stream()
                .filter(this::mustGenerateRelationColumns)
                .map(this::convertRelationToField)
                .map(this::generateField)
                .toList();
    }

    private List<String> generateConstraints() {
        return domainTypeDescription.getRelations().stream()
                .filter(this::mustGenerateRelationConstraints)
                .map(this::generateConstraint)
                .toList();
    }

    private List<String> generateManyToManyTables() {
        return domainTypeDescription.getRelations().stream()
                .filter(relation -> relation.getJoin() && relation.getRelationType() == SpringRelation.MANY_TO_MANY)
                .map(this::generateManyToManyTable)
                .toList();
    }

    private String generateManyToManyTable(final SpringModelModuleFieldRelationDescription relationDescription) {
        return "\n\t\t<createTable tableName=\"" + resolveManyToManyTableName(relationDescription) + "\">\n" +
                "\t\t\t<column name=\"" + StringFormatter.toSnakeCase(domainTypeDescription.getName()) + "_id\" type=\"" + resolveFieldType(domainTypeDescription.getIdType(), null) + "\">\n" +
                "\t\t\t\t<constraints nullable=\"false\"/>\n" +
                "\t\t\t</column>\n" +
                "\t\t\t<column name=\"" + StringFormatter.toSnakeCase(relationDescription.getTo()) + "_id\" type=\"" + resolveFieldType(relationDescription.getJoinDataType(), null) + "\">\n" +
                "\t\t\t\t<constraints nullable=\"false\"/>\n" +
                "\t\t\t</column>\n" +
                "\t\t</createTable>\n" +
                "\n\t\t<addPrimaryKey columnNames=\"" + StringFormatter.toSnakeCase(domainTypeDescription.getName()) + "_id, " + StringFormatter.toSnakeCase(relationDescription.getTo()) + "_id\" tableName=\"" + resolveManyToManyTableName(relationDescription) + "\"/>";
    }

    private String generateConstraint(final SpringModelModuleFieldRelationDescription relationDescription) {
        if (relationDescription.getRelationType() == SpringRelation.MANY_TO_MANY) {
            return foreignKeyConstraint(
                    StringFormatter.toSnakeCase(domainTypeDescription.getName()),
                    resolveManyToManyTableName(relationDescription),
                    resolveManyToManyTableName(relationDescription) + "__" + StringFormatter.toSnakeCase(domainTypeDescription.getName()),
                    StringFormatter.toSnakeCase(domainTypeDescription.getName())
            ) + foreignKeyConstraint(
                    StringFormatter.toSnakeCase(relationDescription.getTo()),
                    resolveManyToManyTableName(relationDescription),
                    resolveManyToManyTableName(relationDescription) + "__" + StringFormatter.toSnakeCase(relationDescription.getTo()),
                    StringFormatter.toSnakeCase(relationDescription.getTo())
            );
        }
        return foreignKeyConstraint(
                StringFormatter.toSnakeCase(relationDescription.getTo()),
                StringFormatter.toSnakeCase(domainTypeDescription.getName()),
                StringFormatter.toSnakeCase(domainTypeDescription.getName()) + "__" + StringFormatter.toSnakeCase(relationDescription.getTo()),
                StringFormatter.toSnakeCase(relationDescription.getTo())
        );
    }

    private String foreignKeyConstraint(final String baseColumnNames, final String baseTableName, final String constraintName, final String referencedTableName) {
        return "\n\t\t<addForeignKeyConstraint baseColumnNames=\"" + baseColumnNames + "_id\"\n" +
                "\t\t\t\t\t\t\t\t baseTableName=\"" + baseTableName + "\"\n" +
                "\t\t\t\t\t\t\t\t constraintName=\"fk_" + constraintName + "_id\"\n" +
                "\t\t\t\t\t\t\t\t referencedColumnNames=\"id\"\n" +
                "\t\t\t\t\t\t\t\t referencedTableName=\"" + referencedTableName + "\"/>";
    }

    private String resolveManyToManyTableName(final SpringModelModuleFieldRelationDescription relationDescription) {
        return StringFormatter.toSnakeCase(domainTypeDescription.getName()) + "__" + StringFormatter.toSnakeCase(relationDescription.getTo());
    }

    private boolean mustGenerateRelationColumns(final SpringModelModuleFieldRelationDescription relation) {
        return (relation.getJoin() && relation.getRelationType() == SpringRelation.ONE_TO_ONE)
                || relation.getRelationType() == SpringRelation.MANY_TO_ONE;
    }

    private boolean mustGenerateRelationConstraints(final SpringModelModuleFieldRelationDescription relation) {
        return mustGenerateRelationColumns(relation)
                || (relation.getJoin() && relation.getRelationType() == SpringRelation.MANY_TO_MANY);
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
                resolveFieldConstraints(fieldDescription);
    }

    private String resolveFieldConstraints(SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        if (fieldDescription.isPrimaryKey() || !fieldDescription.isNullable() || fieldDescription.isUnique()) {
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
        if (!isNullable) {
            return "nullable=\"false\" ";
        }
        return "";
    }

    private String resolveUnique(final Boolean isUnique, final String fieldName) {
        if (isUnique) {
            return "unique=\"true\" uniqueConstraintName=\"ux_" + StringFormatter.toSnakeCase(domainTypeDescription.getName()) + "__" + StringFormatter.toSnakeCase(fieldName) + "\" ";
        }
        return "";
    }

    private List<String> generateLoadDataFields() {
        return domainTypeDescription.getFields()
                .stream()
                .filter(field -> !field.isNullable())
                .map(this::generateLoadDataField)
                .toList();
    }

    private String generateLoadDataField(final SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        return "\n\t\t\t<column name=\"" + StringFormatter.toSnakeCase(fieldDescription.getName()) +  "\" type=\"" + resolveLoadDataFieldType(fieldDescription.getDataType()) + "\"/>";
    }

    private String resolveFieldType(final SpringDataType dataType, final Integer length) {
        return switch (dataType) {
            case JSON -> "json";
            case STRING -> "varchar(" + resolveFieldLength(length) + ")";
            case BOOLEAN -> "boolean";
            case UUID -> "${uuidType}";
            case INTEGER -> "integer";
            case LONG -> "bigint";
            case FLOAT -> "${floatType}";
            case DOUBLE -> "double";
            case BIGDECIMAL -> "decimal(21,2)";
            case ZONED_DATETIME -> "${datetimeType}";
            case INSTANT -> "timestamp";
        };
    }

    private String resolveLoadDataFieldType(final SpringDataType dataType) {
        return switch (dataType) {
            case JSON -> "other";
            case STRING -> "string";
            case BOOLEAN -> "boolean";
            case UUID -> "${uuidType}";
            case INTEGER, LONG, FLOAT, DOUBLE, BIGDECIMAL -> "numeric";
            case ZONED_DATETIME -> "${datetimeType}";
            case INSTANT -> "timestamp";
        };
    }

    private String resolveFieldLength(final Integer length) {
        return length != null ? String.valueOf(length) : "255";
    }
}
