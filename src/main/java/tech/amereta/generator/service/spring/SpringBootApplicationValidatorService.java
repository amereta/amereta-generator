package tech.amereta.generator.service.spring;

import org.springframework.stereotype.Service;
import tech.amereta.generator.description.ApplicationDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleEnumTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;
import tech.amereta.generator.exception.DuplicateAuthorizableDomainsException;
import tech.amereta.generator.exception.DuplicateDomainNameException;
import tech.amereta.generator.exception.DuplicateEnumNameException;
import tech.amereta.generator.exception.ModelDuplicateFieldNameException;
import tech.amereta.generator.service.ApplicationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class SpringBootApplicationValidatorService implements ApplicationValidator {

    @Override
    public void validate(final ApplicationDescription applicationDescription) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(applicationDescription);
        final List<String> domainNames = new ArrayList<>();
        final List<String> enumNames = new ArrayList<>();
        final AtomicBoolean authorizableDomainExists = new AtomicBoolean(false);

        springBootApplicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringModelModuleDescription)
                .map(modelModule -> ((SpringModelModuleDescription) modelModule).getModels())
                .flatMap(List::stream)
                .forEach(model -> {
                    validateModel(model, authorizableDomainExists, domainNames, enumNames);
                });
    }

    private void validateModel(final SpringModelModuleTypeDescription model, final AtomicBoolean authorizableDomainExists, final List<String> domainNames, final List<String> enumNames) {
        switch (model.getType()) {
            case DOMAIN ->
                    validateDomain((SpringModelModuleDomainTypeDescription) model, authorizableDomainExists, domainNames);
            case ENUM -> validateEnum((SpringModelModuleEnumTypeDescription) model, enumNames);
        }
    }

    private void validateDomain(final SpringModelModuleDomainTypeDescription domainDescription, final AtomicBoolean authorizableDomainExists, final List<String> domainNames) {
        validateAuthorizableDomain(domainDescription, authorizableDomainExists);
        validateDomainName(domainDescription, domainNames);
        validateDomainFields(domainDescription);
    }

    private void validateAuthorizableDomain(final SpringModelModuleDomainTypeDescription domainDescription, final AtomicBoolean authorizableDomainExists) {
        if (domainDescription.getAuthorizable() && !authorizableDomainExists.get()) {
            authorizableDomainExists.set(true);
        } else if (domainDescription.getAuthorizable()) {
            throw new DuplicateAuthorizableDomainsException(domainDescription.getName());
        }
    }

    private void validateDomainName(final SpringModelModuleDomainTypeDescription domainDescription, final List<String> domainNames) {
        if (!domainNames.contains(domainDescription.getName())) {
            domainNames.add(domainDescription.getName());
        } else {
            throw new DuplicateDomainNameException(domainDescription.getName());
        }
    }

    private void validateDomainFields(final SpringModelModuleDomainTypeDescription domainDescription) {
        final List<SpringModelModuleDomainTypeFieldDescription> duplicateFields = findDuplicateDomainFields(domainDescription);

        if (!duplicateFields.isEmpty()) {
            throw new ModelDuplicateFieldNameException(domainDescription.getName(), duplicateFields.get(0).getName());
        }
    }

    private static List<SpringModelModuleDomainTypeFieldDescription> findDuplicateDomainFields(final SpringModelModuleDomainTypeDescription domainDescription) {
        return domainDescription.getFields()
                .stream()
                .collect(Collectors.groupingBy(SpringModelModuleDomainTypeFieldDescription::getName))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .toList();
    }

    private void validateEnum(final SpringModelModuleEnumTypeDescription enumDescription, final List<String> enumNames) {
        validateEnumName(enumDescription, enumNames);
        validateEnumFields(enumDescription);
    }

    private static void validateEnumName(final SpringModelModuleEnumTypeDescription enumDescription, final List<String> enumNames) {
        if (!enumNames.contains(enumDescription.getName())) {
            enumNames.add(enumDescription.getName());
        } else {
            throw new DuplicateEnumNameException(enumDescription.getName());
        }
    }

    private void validateEnumFields(final SpringModelModuleEnumTypeDescription enumDescription) {
        final List<SpringModelModuleEnumTypeFieldDescription> duplicateFields = findDuplicateEnumFields(enumDescription);

        if (!duplicateFields.isEmpty()) {
            throw new ModelDuplicateFieldNameException(enumDescription.getName(), duplicateFields.get(0).getName());
        }
    }

    private static List<SpringModelModuleEnumTypeFieldDescription> findDuplicateEnumFields(final SpringModelModuleEnumTypeDescription enumDescription) {
        return enumDescription.getFields()
                .stream()
                .collect(Collectors.groupingBy(SpringModelModuleEnumTypeFieldDescription::getName))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .toList();
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescription applicationDescription) {
        return (SpringBootApplicationDescription) applicationDescription.getApplication();
    }
}
