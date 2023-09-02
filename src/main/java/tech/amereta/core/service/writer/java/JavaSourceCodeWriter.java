package tech.amereta.core.service.writer.java;

import org.springframework.stereotype.Service;
import tech.amereta.core.service.writer.SourceCode;
import tech.amereta.core.service.writer.SourceCodeWriter;
import tech.amereta.core.service.writer.java.source.JavaCompilationUnit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A SourceCodeWriter that writes SourceCode in Java.
 */
@Service
public final class JavaSourceCodeWriter implements SourceCodeWriter {

    private final String sourceFileExtension;
    private final String sourcesDirectory;
    private final String testsDirectory;

    public JavaSourceCodeWriter() {
        this.sourceFileExtension = "java";
        this.sourcesDirectory = "src/main/java";
        this.testsDirectory = "src/test/java";
    }

    @Override
    public void writeSourceTo(SourceCode sourceCode, OutputStream outputStream) {
        try (ZipOutputStream zipOutput = new ZipOutputStream(outputStream)) {
            sourceCode.getCompilationUnits().stream().map(unit -> (JavaCompilationUnit) unit).forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(resolveFileName(unit, this.sourcesDirectory)));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            sourceCode.getTestCompilationUnits().stream().map(unit -> (JavaCompilationUnit) unit).forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(resolveFileName(unit, this.testsDirectory)));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            sourceCode.getStaticCompilationUnits().forEach(unit -> {
                try {
                    zipOutput.putNextEntry(new ZipEntry(unit.getPath().toString()));
                    write(zipOutput, unit.render());
                    zipOutput.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(ZipOutputStream zipOutputStream, String content) throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        final byte[] buffer = new byte[1024];
        int len;
        while ((len = byteArrayInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
    }

    private String resolveFileName(JavaCompilationUnit compilationUnit, String source) {
        final String file = compilationUnit.getName() + "." + this.sourceFileExtension;
        return resolve(resolvePackage(source, compilationUnit.getPackageName()), file);
    }

    private String resolvePackage(String directory, String packageName) {
        return resolve(directory, packageName.replace('.', '/'));
    }

    private String resolve(String directory, String path) {
        return directory.concat("/").concat(path);
    }

    public static String getUnqualifiedName(String name) {
        if (!name.contains(".")) {
            return name;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static boolean requiresImport(String name) {
        if (name == null || !name.contains(".")) {
            return false;
        }
        final String packageName = name.substring(0, name.lastIndexOf('.'));
        return !"java.lang".equals(packageName);
    }

}
