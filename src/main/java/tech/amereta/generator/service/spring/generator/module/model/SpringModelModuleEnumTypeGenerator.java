package tech.amereta.generator.service.spring.generator.module.model;

import tech.amereta.core.java.JavaCompilationUnit;
import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.declaration.AbstractJavaFieldDeclaration;
import tech.amereta.core.java.declaration.JavaEnumFieldDeclaration;
import tech.amereta.core.java.declaration.JavaFieldDeclaration;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.generator.description.spring.AbstractSpringModuleTypeDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleEnumTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;
import tech.amereta.generator.service.spring.generator.module.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.util.StringFormatter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class SpringModelModuleEnumTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription,
                                              final AbstractSpringModuleTypeDescription typeDescription) {
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
