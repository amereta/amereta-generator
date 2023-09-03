package tech.amereta.core.service.generator.spring;

import lombok.Builder;
import tech.amereta.core.domain.description.SpringBootApplicationDescription;
import tech.amereta.core.domain.description.java.JavaTypeDescription;
import tech.amereta.core.service.generator.spring.code.MainClassGenerator;
import tech.amereta.core.service.generator.spring.code.type.SpringDAOGenerator;
import tech.amereta.core.service.generator.spring.code.type.SpringRepositoryGenerator;
import tech.amereta.core.service.generator.spring.code.type.SpringTypeGenerator;
import tech.amereta.core.util.StringFormatter;
import tech.amereta.core.util.code.java.source.JavaCompilationUnit;

import java.util.ArrayList;
import java.util.List;

// TODO: the functions must be in an abstract class
@Builder
public class SpringBootSourceCodeGenerator {

    private SpringBootApplicationDescription springBootApplication;

    public List<JavaCompilationUnit> generate() {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        compilationUnits.add(MainClassGenerator.generate(springBootApplication));
        compilationUnits.addAll(generateObjects());
        return compilationUnits;
    }

    private List<JavaCompilationUnit> generateObjects() {
        final List<JavaCompilationUnit> compilationUnits = new ArrayList<>();
        if (springBootApplication.getTypes() != null) {
            springBootApplication.getTypes().forEach(type -> {
                final SpringTypeGenerator generator = createGenerator(type);
                assert generator != null;
                compilationUnits.add(
                        JavaCompilationUnit.builder()
                                .packageName(generatePackageName(type))
                                .name(StringFormatter.toPascalCase(type.getName(), type.getType().toString()))
                                .typeDeclarations(List.of(generator.generateTypeDeclaration()))
                                .build());
            });
        }
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
        return springBootApplication.getPackageName()
                + "."
                + springBootApplication.getName().toLowerCase()
                + "."
                + type.getType().getPackageName();
    }

}
