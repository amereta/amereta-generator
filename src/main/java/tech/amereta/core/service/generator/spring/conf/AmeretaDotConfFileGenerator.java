package tech.amereta.core.service.generator.spring.conf;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public class AmeretaDotConfFileGenerator implements ISoyConfiguration {

    private String yml;

    @Override
    public String getName() {
        return "amereta.generator.conf";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("amereta.conf", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/amereta.conf.soy")), tempFile);

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "yml", yml);
    }

    @Override
    public Path getPath() {
        return Path.of("amereta.conf");
    }

}
