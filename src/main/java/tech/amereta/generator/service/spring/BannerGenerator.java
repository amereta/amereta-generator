package tech.amereta.generator.service.spring;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public final class BannerGenerator implements ISoyConfiguration {

    private String name;

    @Override
    public String getName() {
        return "amereta.generator.banner";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("banner.txt", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/banner.txt.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "name", name
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/banner.txt");
    }
}
