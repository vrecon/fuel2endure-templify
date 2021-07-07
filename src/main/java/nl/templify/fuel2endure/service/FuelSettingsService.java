package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.FuelSettings;

/**
 * Service Interface for managing {@link FuelSettings}.
 */
public interface FuelSettingsService {
    /**
     * Save a fuelSettings.
     *
     * @param fuelSettings the entity to save.
     * @return the persisted entity.
     */
    FuelSettings save(FuelSettings fuelSettings);

    /**
     * Partially updates a fuelSettings.
     *
     * @param fuelSettings the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelSettings> partialUpdate(FuelSettings fuelSettings);

    /**
     * Get all the fuelSettings.
     *
     * @return the list of entities.
     */
    List<FuelSettings> findAll();

    /**
     * Get the "id" fuelSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelSettings> findOne(Long id);

    /**
     * Delete the "id" fuelSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
