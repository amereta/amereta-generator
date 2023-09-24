package tech.amereta.generator.service.spring.generator;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder
public final class LiquibaseMasterGenerator implements ISoyConfiguration {

    private String dbType;
    private List<String> changelogs;

    @Override
    public String getName() {
        return "amereta.generator.master";
    }

    @Override
    public File getFile() throws IOException {
        File tempFile = File.createTempFile("master.xml", ".soy");
        tempFile.deleteOnExit();

        FileUtils.copyInputStreamToFile(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("templates/soy/master.xml.soy")
                ), tempFile
        );

        return tempFile;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of(
                "dbType", dbType,
                "changelogs", changelogs
        );
    }

    @Override
    public Path getPath() {
        return Path.of("src/main/resources/db/master.xml");
    }
}
