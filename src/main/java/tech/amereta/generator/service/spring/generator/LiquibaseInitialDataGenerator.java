package tech.amereta.generator.service.spring.generator;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import tech.amereta.core.soy.ISoyConfiguration;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringDataType;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.util.StringFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Builder
public final class LiquibaseInitialDataGenerator implements ISoyConfiguration {

    private SpringModelModuleDomainTypeDescription domainTypeDescription;

    private String owner;

    private String applicationName;

    @Override
    public String getName() {
        return "amereta.generator.data";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("authenticableDomainData.csv", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/authenticableDomainData.csv.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "id", resolveId(),
                "username", resolveUsername(),
                "email", resolveEmail(),
                "language", "ENGLISH",
                "role", domainTypeDescription.getName().toUpperCase(),
                "fieldsName", resolveMandatoryFieldsName(),
                "fieldsData", resolveMandatoryFieldsData()
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/db/data/" + domainTypeDescription.getName().toLowerCase() + ".csv");
    }

    private String resolveId() {
        if(domainTypeDescription.getIdType() == SpringDataType.UUID) {
            return UUID.randomUUID().toString();
        } else {
            return "1";
        }
    }

    private String resolveUsername() {
        return StringUtils.hasText(owner) ? owner.toLowerCase() : domainTypeDescription.getName().toLowerCase();
    }

    private String resolveEmail() {
        StringBuilder stringBuilder = new StringBuilder("@" + StringFormatter.toKebabCase(applicationName).toLowerCase() + ".email");
        if(StringUtils.hasText(owner)) {
            return stringBuilder.insert(0, owner.toLowerCase()).toString();
        }
        return stringBuilder.insert(0, domainTypeDescription.getName().toLowerCase()).toString();
    }

    private List<String> resolveMandatoryFieldsName() {
        return domainTypeDescription.getFields()
                .stream()
                .filter(field -> !field.isNullable())
                .map(SpringModelModuleDomainTypeFieldDescription::getName)
                .toList();
    }

    private List<String> resolveMandatoryFieldsData() {
        return domainTypeDescription.getFields()
                .stream()
                .filter(field -> !field.isNullable())
                .map(this::resolveMandatoryFieldData)
                .toList();
    }

    private String resolveMandatoryFieldData(SpringModelModuleDomainTypeFieldDescription fieldDescription) {
        return switch (fieldDescription.getDataType()) {
            case JSON -> "[\"json\"]";
            case STRING -> "string";
            case BOOLEAN -> "true";
            case UUID -> UUID.randomUUID().toString();
            case INTEGER, LONG, FLOAT, DOUBLE, BIGDECIMAL -> "0";
            case ZONED_DATETIME -> "${datetimeType}";
            case INSTANT -> "timestamp";
        };
    }
}
