package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.RacePlanForm;

/**
 * Service Interface for managing {@link RacePlanForm}.
 */
public interface RacePlanFormService {
    /**
     * Save a racePlanForm.
     *
     * @param racePlanForm the entity to save.
     * @return the persisted entity.
     */
    RacePlanForm save(RacePlanForm racePlanForm);

    /**
     * Partially updates a racePlanForm.
     *
     * @param racePlanForm the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RacePlanForm> partialUpdate(RacePlanForm racePlanForm);

    /**
     * Get all the racePlanForms.
     *
     * @return the list of entities.
     */
    List<RacePlanForm> findAll();

    /**
     * Get the "id" racePlanForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RacePlanForm> findOne(Long id);

    /**
     * Delete the "id" racePlanForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
