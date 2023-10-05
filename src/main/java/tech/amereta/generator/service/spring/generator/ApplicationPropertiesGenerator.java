package tech.amereta.generator.service.spring.generator;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public final class ApplicationPropertiesGenerator implements ISoyConfiguration {

    private String name;

    private String port;

    private Boolean hasDataBase;

    private String dbType;

    private String dbUsername;

    private String dbPassword;

    @Override
    public String getName() {
        return "amereta.generator.properties";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("application.yml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/application.yml.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "name", name,
                "port", port,
                "hasDataBase", hasDataBase,
                "dbType", dbType,
                "dbName", name.toLowerCase(),
                "dbUsername", dbUsername,
                "dbPassword", dbPassword
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/config/application.yml");
    }
}
