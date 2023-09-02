package tech.amereta.core.service.generator.spring.code;

import lombok.Builder;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.domain.description.java.JavaTypeDescription;
import tech.amereta.core.service.generator.spring.code.type.SpringDAOGenerator;
import tech.amereta.core.service.generator.spring.code.type.SpringRepositoryGenerator;
import tech.amereta.core.service.generator.spring.code.type.SpringTypeGenerator;
import tech.amereta.core.service.writer.java.source.JavaCompilationUnit;
import tech.amereta.core.util.StringFormatter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class SpringBootSourceCodeGenerator {

    private SpringBootApplicationDescription springBootApplication;

    public List<JavaCompilationUnit> generate() {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(springBootApplication.getName()));
        compilationUnits.addAll(generateObjects());
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateObjects() {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        springBootApplication.getTypes().forEach(type -> {
            SpringTypeGenerator generator = createGenerator(type);
            assert generator != null;
            compilationUnits.add(
                    JavaCompilationUnit.builder()
                            .packageName(generatePackageName(type))
                            .name(StringFormatter.toPascalCase(type.getName(), type.getType().toString()))
                            .typeDeclarations(List.of(generator.generateTypeDeclaration()))
                            .build());
        });
        return compilationUnits;
    }

    private SpringTypeGenerator createGenerator(JavaTypeDescription type) {
        return switch (type.getType()) {
            case DAO -> SpringDAOGenerator.builder()
                    .projectName(springBootApplication.getName())
                    .type(type).build();
            case REPOSITORY -> SpringRepositoryGenerator.builder()
                    .projectName(springBootApplication.getName())
                    .type(type).build();
            default -> null;
        };
    }

    private String generatePackageName(JavaTypeDescription type) {
        return "website.amereta."
                .concat(springBootApplication.getName().toLowerCase())
                .concat(".")
                .concat(type.getType().getPackageName());
    }

}
