package tech.amereta.generator.util.code;

import tech.amereta.generator.util.soy.ISoyConfiguration;

import java.util.List;

public interface SourceCode {

    List<CompilationUnit> getCompilationUnits();

    List<CompilationUnit> getTestCompilationUnits();

    List<ISoyConfiguration> getStaticCompilationUnits();
}
