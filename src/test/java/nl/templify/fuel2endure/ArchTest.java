package nl.templify.fuel2endure;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("nl.templify.fuel2endure");

        noClasses()
            .that()
            .resideInAnyPackage("nl.templify.fuel2endure.service..")
            .or()
            .resideInAnyPackage("nl.templify.fuel2endure.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..nl.templify.fuel2endure.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
