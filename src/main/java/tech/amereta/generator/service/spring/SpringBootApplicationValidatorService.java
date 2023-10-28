package tech.amereta.generator.service.spring;

import tech.amereta.generator.description.ApplicationDescriptionWrapper;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.SpringBootValidator;
import tech.amereta.generator.description.spring.model.AbstractSpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleEnumTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleFieldRelationDescription;
import tech.amereta.generator.description.spring.model.type.SpringRelation;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleDomainTypeFieldDescription;
import tech.amereta.generator.description.spring.model.type.field.SpringModelModuleEnumTypeFieldDescription;
import tech.amereta.generator.exception.DuplicateAuthorizableDomainsException;
import tech.amereta.generator.exception.DuplicateModelNameException;
import tech.amereta.generator.exception.ModelDuplicateFieldNameException;
import tech.amereta.generator.exception.RelationJoinException;
import tech.amereta.generator.service.ApplicationValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SpringBootValidator
public class SpringBootApplicationValidatorService implements ApplicationValidator {

    @Override
    public void validate(final ApplicationDescriptionWrapper applicationDescriptionWrapper) {
        final SpringBootApplicationDescription springBootApplicationDescription = getApplication(applicationDescriptionWrapper);

        final List<AbstractSpringModelModuleTypeDescription> models = extractModels(springBootApplicationDescription);

        validateModels(models);
        validateRelations(models);
    }

    private List<AbstractSpringModelModuleTypeDescription> extractModels(SpringBootApplicationDescription springBootApplicationDescription) {
        return springBootApplicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringModelModuleDescription)
                .map(modelModule -> ((SpringModelModuleDescription) modelModule).getModels())
                .flatMap(List::stream)
                .toList();
    }

    private void validateModels(final List<AbstractSpringModelModuleTypeDescription> models) {
        final List<String> domainNames = new ArrayList<>();
        final List<String> enumNames = new ArrayList<>();
        final AtomicBoolean authorizableDomainExists = new AtomicBoolean(false);

        models.forEach(model -> {
            validateModel(model, authorizableDomainExists, domainNames, enumNames);
        });
    }

    private void validateModel(final AbstractSpringModelModuleTypeDescription model, final AtomicBoolean authorizableDomainExists, final List<String> domainNames, final List<String> enumNames) {
        switch (model.getType()) {
            case DOMAIN ->
                    validateDomain((SpringModelModuleDomainTypeDescription) model, authorizableDomainExists, domainNames);
            case ENUM -> validateEnum((SpringModelModuleEnumTypeDescription) model, enumNames);
        }
    }

    private void validateDomain(final SpringModelModuleDomainTypeDescription domainDescription, final AtomicBoolean authorizableDomainExists, final List<String> domainNames) {
        validateAuthenticableDomain(domainDescription, authorizableDomainExists);
        validateDomainName(domainDescription, domainNames);
        validateDomainFields(domainDescription);
    }

    private void validateAuthenticableDomain(final SpringModelModuleDomainTypeDescription domainDescription, final AtomicBoolean authorizableDomainExists) {
        if (domainDescription.getAuthenticable() && !authorizableDomainExists.get()) {
            domainDescription.setTimestamped(true);
            authorizableDomainExists.set(true);
        } else if (domainDescription.getAuthenticable()) {
            throw new DuplicateAuthorizableDomainsException(domainDescription.getName());
        }
    }

    private void validateDomainName(final SpringModelModuleDomainTypeDescription domainDescription, final List<String> domainNames) {
        if (!domainNames.contains(domainDescription.getName())) {
            domainNames.add(domainDescription.getName());
        } else {
            throw new DuplicateModelNameException("Domain", domainDescription.getName());
        }
    }

    private void validateDomainFields(final SpringModelModuleDomainTypeDescription domainDescription) {
        final List<SpringModelModuleDomainTypeFieldDescription> duplicateFields = findDuplicateDomainFields(domainDescription);

        if (!duplicateFields.isEmpty()) {
            throw new ModelDuplicateFieldNameException(domainDescription.getName(), duplicateFields.get(0).getName());
        }
    }

    private List<SpringModelModuleDomainTypeFieldDescription> findDuplicateDomainFields(final SpringModelModuleDomainTypeDescription domainDescription) {
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

    private void validateEnumName(final SpringModelModuleEnumTypeDescription enumDescription, final List<String> enumNames) {
        if (!enumNames.contains(enumDescription.getName())) {
            enumNames.add(enumDescription.getName());
        } else {
            throw new DuplicateModelNameException("Enum", enumDescription.getName());
        }
    }

    private void validateEnumFields(final SpringModelModuleEnumTypeDescription enumDescription) {
        final List<SpringModelModuleEnumTypeFieldDescription> duplicateFields = findDuplicateEnumFields(enumDescription);

        if (!duplicateFields.isEmpty()) {
            throw new ModelDuplicateFieldNameException(enumDescription.getName(), duplicateFields.get(0).getName());
        }
    }

    private List<SpringModelModuleEnumTypeFieldDescription> findDuplicateEnumFields(final SpringModelModuleEnumTypeDescription enumDescription) {
        return enumDescription.getFields()
                .stream()
                .collect(Collectors.groupingBy(SpringModelModuleEnumTypeFieldDescription::getName))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .flatMap(e -> e.getValue().stream())
                .toList();
    }

    private void validateRelations(final List<AbstractSpringModelModuleTypeDescription> models) {
        final Map<String, SpringModelModuleDomainTypeDescription> domainsWithName = convertToMapOfDomainsWithName(models);

        domainsWithName.forEach((name, domain) -> {
            domain.getRelations().forEach(relation -> {
                validateRelation(relation, domain, domainsWithName.get(relation.getTo()));
            });
        });
    }

    private void validateRelation(final SpringModelModuleFieldRelationDescription relation, final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain) {
        switch (relation.getRelationType()) {
            case ONE_TO_ONE -> validateOneToOneRelation(relation, thisSideDomain, otherSideDomain);
            case ONE_TO_MANY -> validateOneToManyRelation(thisSideDomain, otherSideDomain);
            case MANY_TO_ONE -> validateManyToOneRelation(relation, thisSideDomain, otherSideDomain);
            case MANY_TO_MANY -> validateManyToManyRelation(relation, thisSideDomain, otherSideDomain);
        }
    }

    private void validateOneToOneRelation(final SpringModelModuleFieldRelationDescription thisSideRelation, final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain) {
        final SpringRelation oneToOne = SpringRelation.ONE_TO_ONE;

        if (otherSideOfRelationDoesNotExists(thisSideDomain, otherSideDomain, oneToOne)) {
            otherSideDomain.getRelations().add(
                    createRelationDescription(thisSideDomain, oneToOne)
            );
            thisSideRelation.setJoin(true);
            thisSideRelation.setJoinDataType(otherSideDomain.getIdType());
        } else {
            final SpringModelModuleFieldRelationDescription otherSideRelation = findOtherSideOfRelation(thisSideDomain, otherSideDomain, oneToOne);

            if (haveBothSideOrNoSideJoin(thisSideRelation, otherSideRelation)) {
                throw new RelationJoinException(oneToOne.getName(), thisSideDomain.getName(), otherSideDomain.getName());
            } else if (thisSideRelation.getJoin()) {
                thisSideRelation.setJoinDataType(otherSideDomain.getIdType());
            } else if (otherSideRelation.getJoin()) {
                otherSideRelation.setJoinDataType(thisSideDomain.getIdType());
            }
        }
    }

    private void validateOneToManyRelation(final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain) {
        final SpringRelation manyToOne = SpringRelation.MANY_TO_ONE;

        if (otherSideOfRelationDoesNotExists(thisSideDomain, otherSideDomain, manyToOne)) {
            final SpringModelModuleFieldRelationDescription otherSideRelation = createRelationDescription(thisSideDomain, manyToOne);

            otherSideRelation.setJoinDataType(thisSideDomain.getIdType());
            otherSideDomain.getRelations().add(otherSideRelation);
        } else {
            final SpringModelModuleFieldRelationDescription otherSideRelation = findOtherSideOfRelation(thisSideDomain, otherSideDomain, manyToOne);

            otherSideRelation.setJoinDataType(thisSideDomain.getIdType());
        }
    }

    private void validateManyToOneRelation(final SpringModelModuleFieldRelationDescription thisSideRelation, final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain) {
        final SpringRelation oneToMany = SpringRelation.ONE_TO_MANY;

        thisSideRelation.setJoinDataType(otherSideDomain.getIdType());

        if (otherSideOfRelationDoesNotExists(thisSideDomain, otherSideDomain, oneToMany)) {
            otherSideDomain.getRelations().add(
                    createRelationDescription(thisSideDomain, oneToMany)
            );
        }
    }

    private void validateManyToManyRelation(final SpringModelModuleFieldRelationDescription thisSideRelation, final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain) {
        final SpringRelation manyToMany = SpringRelation.MANY_TO_MANY;

        if (otherSideOfRelationDoesNotExists(thisSideDomain, otherSideDomain, manyToMany)) {
            otherSideDomain.getRelations().add(
                    createRelationDescription(thisSideDomain, manyToMany)
            );
            thisSideRelation.setJoin(true);
            thisSideRelation.setJoinDataType(otherSideDomain.getIdType());
        } else {
            final SpringModelModuleFieldRelationDescription otherSideRelation = findOtherSideOfRelation(thisSideDomain, otherSideDomain, manyToMany);

            if (haveBothSideOrNoSideJoin(thisSideRelation, otherSideRelation)) {
                throw new RelationJoinException(manyToMany.getName(), thisSideDomain.getName(), otherSideDomain.getName());
            } else if (thisSideRelation.getJoin()) {
                thisSideRelation.setJoinDataType(otherSideDomain.getIdType());
            } else if (otherSideRelation.getJoin()) {
                otherSideRelation.setJoinDataType(thisSideDomain.getIdType());
            }
        }
    }

    private boolean haveBothSideOrNoSideJoin(SpringModelModuleFieldRelationDescription thisSideRelation, SpringModelModuleFieldRelationDescription otherSideRelation) {
        return (thisSideRelation.getJoin() && otherSideRelation.getJoin()) || (!thisSideRelation.getJoin() && !otherSideRelation.getJoin());
    }

    private boolean otherSideOfRelationDoesNotExists(final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain, final SpringRelation relationType) {
        return otherSideDomain.getRelations()
                .stream()
                .noneMatch(relation -> relation.getRelationType() == relationType && relation.getTo().equals(thisSideDomain.getName()));
    }

    private SpringModelModuleFieldRelationDescription findOtherSideOfRelation(final SpringModelModuleDomainTypeDescription thisSideDomain, final SpringModelModuleDomainTypeDescription otherSideDomain, final SpringRelation relationType) {
        return otherSideDomain.getRelations()
                .stream()
                .filter(relation -> relation.getRelationType() == relationType && relation.getTo().equals(thisSideDomain.getName()))
                .findAny()
                .orElseThrow();
    }

    private SpringModelModuleFieldRelationDescription createRelationDescription(final SpringModelModuleDomainTypeDescription otherSideDomain, final SpringRelation relationType) {
        return SpringModelModuleFieldRelationDescription.builder()
                .to(otherSideDomain.getName())
                .relationType(relationType)
                .build();
    }

    private Map<String, SpringModelModuleDomainTypeDescription> convertToMapOfDomainsWithName(List<AbstractSpringModelModuleTypeDescription> models) {
        return models.stream()
                .filter(model -> model instanceof SpringModelModuleDomainTypeDescription)
                .collect(
                        Collectors.toMap(
                                AbstractSpringModelModuleTypeDescription::getName,
                                model -> (SpringModelModuleDomainTypeDescription) model
                        )
                );
    }

    private SpringBootApplicationDescription getApplication(final ApplicationDescriptionWrapper applicationDescriptionWrapper) {
        return (SpringBootApplicationDescription) applicationDescriptionWrapper.getApplication();
    }
}
