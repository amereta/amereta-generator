package tech.amereta.core.service.generator.spring;

import lombok.Builder;
import org.apache.commons.io.FileUtils;
import tech.amereta.core.util.soy.ISoyConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

@Builder
public final class PomXmlGenerator implements ISoyConfiguration {

    // TODO: the output file needs indents reformation

    private String javaVersion;
    private String springVersion;
    private String name;
    private String packageName;
    private String description;

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
                "description", description);
    }

    @Override
    public Path getPath() {
        return Path.of("pom.xml");
    }

}
