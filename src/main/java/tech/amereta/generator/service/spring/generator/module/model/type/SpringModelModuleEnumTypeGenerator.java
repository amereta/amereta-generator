package tech.amereta.generator.service.spring.generator.module.model.type;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaEnumFieldDeclaration;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.lang.description.spring.SpringBootApplicationDescription;
import tech.amereta.lang.description.spring.SpringModuleTypeDescription;
import tech.amereta.lang.description.spring.model.type.SpringBootEnumModelModuleGenerator;
import tech.amereta.lang.description.spring.model.type.SpringModelModuleEnumTypeDescription;
import tech.amereta.lang.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;

import java.util.ArrayList;
import java.util.List;

@SpringBootEnumModelModuleGenerator
public final class SpringModelModuleEnumTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final SpringModuleTypeDescription typeDescription) {
        final SpringModelModuleEnumTypeDescription enumTypeDescription = (SpringModelModuleEnumTypeDescription) typeDescription;

        return generateEnum(applicationDescription, enumTypeDescription);
    }

    private List<JavaCompilationUnit> generateEnum(final SpringBootApplicationDescription applicationDescription, final SpringModelModuleEnumTypeDescription enumTypeDescription) {
        final String className = StringFormatter.toPascalCase(enumTypeDescription.getName());
        final JavaTypeDeclaration domain = generateEnumDeclaration(className);
        domain.setFieldDeclarations(generateEnumFields(enumTypeDescription));

        return List.of(
                JavaCompilationUnit.builder()
                        .packageName(basePackage(applicationDescription) + ".model.enumeration")
                        .name(className)
                        .typeDeclarations(List.of(domain))
        );
    }

    private List<AbstractJavaFieldDeclaration> generateEnumFields(final SpringModelModuleEnumTypeDescription enumTypeDescription) {
        return new ArrayList<>(enumTypeDescription.getFields()
                .stream()
                .map(this::generateFieldDeclaration)
                .toList());
    }

    private JavaEnumFieldDeclaration generateFieldDeclaration(final SpringModelModuleEnumTypeFieldDescription field) {
        return JavaEnumFieldDeclaration.builder().name(field.getName().toUpperCase());
    }
}