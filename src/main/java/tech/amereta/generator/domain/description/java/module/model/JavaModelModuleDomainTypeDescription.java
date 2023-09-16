package tech.amereta.generator.domain.description.java.module.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.amereta.generator.service.spring.AbstractSpringModuleTypeGenerator;
import tech.amereta.generator.service.spring.module.type.ModelModuleDomainTypeGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class JavaModelModuleDomainTypeDescription extends AbstractJavaModuleTypeDescription {

    private List<JavaModelModuleDomainTypeFieldDescription> fields;

    @Override
    public AbstractSpringModuleTypeGenerator getGenerator() {
        return new ModelModuleDomainTypeGenerator();
    }
}
