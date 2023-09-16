package tech.amereta.generator.service.spring.module.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.amereta.generator.domain.description.SpringBootApplicationDescription;
import tech.amereta.generator.domain.description.java.module.model.AbstractJavaModuleTypeDescription;
import tech.amereta.generator.domain.description.java.module.model.JavaModelModuleDomainTypeDescription;
import tech.amereta.generator.service.spring.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.util.StringFormatter;
import tech.amereta.generator.util.code.java.source.JavaCompilationUnit;
import tech.amereta.generator.util.code.java.source.JavaTypeDeclaration;
import tech.amereta.generator.util.code.java.util.JavaModifier;
import tech.amereta.generator.util.code.java.util.JavaType;

import java.lang.reflect.Modifier;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ModelModuleDomainTypeGenerator extends AbstractSpringModuleTypeGenerator {

    @Override
    public List<JavaCompilationUnit> generate(final SpringBootApplicationDescription applicationDescription, final AbstractJavaModuleTypeDescription model) {
        final JavaModelModuleDomainTypeDescription javaModelModuleDomainTypeDescription = (JavaModelModuleDomainTypeDescription) model;

        return List.of(
                generateDomain(applicationDescription, javaModelModuleDomainTypeDescription)
//                                generateRepository(field),
//                                generateController(field)
        );
    }

    private JavaCompilationUnit generateDomain(final SpringBootApplicationDescription applicationDescription, final JavaModelModuleDomainTypeDescription domainTypeDescription) {
        final String className = StringFormatter.toPascalCase(domainTypeDescription.getName());

        return JavaCompilationUnit.builder()
                .packageName(basePackage(applicationDescription) + ".model.domain")
                .name(className)
                .typeDeclarations(List.of(
                        JavaTypeDeclaration.builder()
                                .type(JavaType.CLASS)
                                .name(className)
                                .modifiers(JavaModifier.builder()
                                        .type(JavaModifier.TYPE_MODIFIERS)
                                        .modifiers(Modifier.PUBLIC)
                                        .build())
                                .build()))
                .build();
    }
}
