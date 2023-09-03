package tech.amereta.core.service.generator.spring.docker;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class DockerfileGenerator implements ISoyConfiguration {

    private String javaVersion;

    @Override
    public String getName() {
        return "website.amereta.generator.dockerfile";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("Dockerfile", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/Dockerfile.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "java", this.javaVersion);
    }

    @Override
    public Path getPath() throws IOException {
        return Path.of("Dockerfile");
    }
}
