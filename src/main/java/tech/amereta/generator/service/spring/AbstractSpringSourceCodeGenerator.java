package tech.amereta.generator.service.spring;

import tech.amereta.core.java.JavaTypeDeclaration;
import tech.amereta.core.java.util.JavaModifier;
import tech.amereta.core.java.util.JavaType;
import tech.amereta.generator.description.spring.AbstractSpringModuleDescription;
import tech.amereta.generator.description.spring.SpringBootApplicationDescription;
import tech.amereta.generator.description.spring.db.SpringDBModuleDescription;
import tech.amereta.generator.description.spring.model.SpringModelModuleDescription;
import tech.amereta.generator.description.spring.model.AbstractSpringModelModuleTypeDescription;
import tech.amereta.generator.description.spring.model.type.SpringModelModuleDomainTypeDescription;
import tech.amereta.generator.description.spring.security.SpringSecurityModuleDescription;
import tech.amereta.generator.exception.ApplicationHaveTwoDifferentDatabasesException;
import tech.amereta.generator.exception.ApplicationHaveTwoDifferentSecurityAuthenticatorException;
import tech.amereta.generator.exception.AuthorizableDomainNotFoundException;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSpringSourceCodeGenerator {

    public static String basePackage(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getPackageName() + "." + applicationDescription.getName().toLowerCase();
    }

    public static boolean applicationHasSecurity(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getModules()
                .stream()
                .anyMatch(module -> module instanceof SpringSecurityModuleDescription);
    }

    public static Optional<SpringSecurityModuleDescription> getSecurityAuthenticator(final SpringBootApplicationDescription applicationDescription) {
        final List<AbstractSpringModuleDescription> authenticators = applicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringSecurityModuleDescription)
                .toList();
        if (authenticators.size() > 1) {
            throw new ApplicationHaveTwoDifferentSecurityAuthenticatorException();
        } else if (authenticators.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of((SpringSecurityModuleDescription) authenticators.get(0));
        }
    }

    public static boolean applicationHasDataBase(final SpringBootApplicationDescription applicationDescription) {
        return applicationDescription.getModules()
                .stream()
                .anyMatch(module -> module instanceof SpringDBModuleDescription);
    }

    public static Optional<SpringDBModuleDescription> getDataBase(final SpringBootApplicationDescription applicationDescription) {
        final List<AbstractSpringModuleDescription> databases = applicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringDBModuleDescription)
                .toList();
        if (databases.size() > 1) {
            throw new ApplicationHaveTwoDifferentDatabasesException();
        } else if (databases.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of((SpringDBModuleDescription) databases.get(0));
        }
    }

    public static SpringModelModuleDomainTypeDescription getAuthenticableDomain(final SpringBootApplicationDescription applicationDescription) {
        final List<AbstractSpringModelModuleTypeDescription> modelModules = applicationDescription.getModules()
                .stream()
                .filter(module -> module instanceof SpringModelModuleDescription)
                .map(module -> ((SpringModelModuleDescription) module).getModels())
                .flatMap(List::stream)
                .filter(model -> model instanceof SpringModelModuleDomainTypeDescription && ((SpringModelModuleDomainTypeDescription) model).getAuthenticable())
                .toList();
        if (modelModules.isEmpty()) {
            throw new AuthorizableDomainNotFoundException();
        }
        return (SpringModelModuleDomainTypeDescription) modelModules.get(0);
    }

    protected static JavaTypeDeclaration generateClassDeclaration(String className) {
        return JavaTypeDeclaration.builder()
                .type(JavaType.CLASS)
                .name(className)
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                );
    }

    protected static JavaTypeDeclaration generateEnumDeclaration(String className) {
        return JavaTypeDeclaration.builder()
                .type(JavaType.ENUM)
                .name(className)
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                );
    }

    protected static JavaTypeDeclaration generateInterfaceDeclaration(final String className) {
        return JavaTypeDeclaration.builder()
                .type(JavaType.INTERFACE)
                .name(className)
                .modifiers(
                        JavaModifier.builder()
                                .type(JavaModifier.TYPE_MODIFIERS)
                                .modifiers(Modifier.PUBLIC)
                );
    }
}
