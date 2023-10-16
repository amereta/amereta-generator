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
public final class PomGenerator implements ISoyConfiguration {

    private String javaVersion;

    private String springVersion;

    private String ameretaVersion;

    private String name;

    private String packageName;

    private String description;

    private Boolean hasSecurity;

    private String securityAuthenticator;

    private Boolean hasDataBase;

    private String dbType;

    @Override
    public String getName() {
        return "amereta.generator.pom";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("pom.xml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/pom.xml.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "javaVersion", javaVersion,
                "springVersion", springVersion,
                "ameretaVersion", ameretaVersion,
                "name", name,
                "packageName", packageName,
                "description", description,
                "hasSecurity", hasSecurity,
                "securityAuthenticator", securityAuthenticator,
                "hasDataBase", hasDataBase,
                "dbType", dbType
        );
    }

    @Override
    public Path getPath() {
        return Path.of("pom.xml");
    }

}
