package tech.amereta.core.service.writer;

import tech.amereta.core.service.writer.soy.ISoyConfiguration;

import java.util.List;

public interface SourceCode {

    List<CompilationUnit> getCompilationUnits();

    List<CompilationUnit> getTestCompilationUnits();

    List<ISoyConfiguration> getStaticCompilationUnits();
}
