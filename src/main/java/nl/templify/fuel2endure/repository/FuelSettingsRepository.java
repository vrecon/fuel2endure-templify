package nl.templify.fuel2endure.repository;

import nl.templify.fuel2endure.domain.FuelSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FuelSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelSettingsRepository extends JpaRepository<FuelSettings, Long>, JpaSpecificationExecutor<FuelSettings> {}
