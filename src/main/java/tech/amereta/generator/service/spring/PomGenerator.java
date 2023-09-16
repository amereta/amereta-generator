package tech.amereta.generator.service.spring;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.generator.util.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public final class PomGenerator implements ISoyConfiguration {

    private String javaVersion;
    private String springVersion;
    private String name;
    private String packageName;
    private String description;
    private Boolean hasDataBase;

    @Override
    public String getName() {
        return "amereta.generator.pom";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("pom.xml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(//
                Objects.requireNonNull(//
                        getClass().getClassLoader().getResourceAsStream("templates/soy/pom.xml.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "java", javaVersion,
                "spring", springVersion,
                "name", name,
                "packageName", packageName,
                "description", description,
                "hasDataBase", hasDataBase);
    }

    @Override
    public Path getPath() {
        return Path.of("pom.xml");
    }

}
