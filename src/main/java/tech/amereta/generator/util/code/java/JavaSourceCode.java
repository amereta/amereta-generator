package tech.amereta.generator.util.code.java;

import tech.amereta.generator.util.code.CompilationUnit;
import tech.amereta.generator.util.code.SourceCode;
import tech.amereta.generator.util.soy.ISoyConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java SourceCode.
 */
public final class JavaSourceCode implements SourceCode {

    private List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
    private List<JavaCompilationUnit> testCompilationUnits = new ArrayList<>();
    private List<ISoyConfiguration> staticCompilationUnits = new ArrayList<>();

    public static JavaSourceCode builder() {
        return new JavaSourceCode();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        return this.compilationUnits.stream().map(CompilationUnit.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<CompilationUnit> getTestCompilationUnits() {
        return this.testCompilationUnits.stream().map(CompilationUnit.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<ISoyConfiguration> getStaticCompilationUnits() {
        return staticCompilationUnits;
    }

    public JavaSourceCode compilationUnits(List<JavaCompilationUnit> compilationUnits) {
        setCompilationUnits(compilationUnits);
        return this;
    }

    public void setCompilationUnits(List<JavaCompilationUnit> compilationUnits) {
        this.compilationUnits = compilationUnits;
    }

    public JavaSourceCode testCompilationUnits(List<JavaCompilationUnit> testCompilationUnits) {
        setTestCompilationUnits(testCompilationUnits);
        return this;
    }

    public void setTestCompilationUnits(List<JavaCompilationUnit> testCompilationUnits) {
        this.testCompilationUnits = testCompilationUnits;
    }

    public JavaSourceCode staticCompilationUnits(List<ISoyConfiguration> staticCompilationUnits) {
        setStaticCompilationUnits(staticCompilationUnits);
        return this;
    }

    public void setStaticCompilationUnits(List<ISoyConfiguration> staticCompilationUnits) {
        this.staticCompilationUnits = staticCompilationUnits;
    }
}
