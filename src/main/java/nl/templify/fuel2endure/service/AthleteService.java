package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Athlete;

/**
 * Service Interface for managing {@link Athlete}.
 */
public interface AthleteService {
    /**
     * Save a athlete.
     *
     * @param athlete the entity to save.
     * @return the persisted entity.
     */
    Athlete save(Athlete athlete);

    /**
     * Partially updates a athlete.
     *
     * @param athlete the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Athlete> partialUpdate(Athlete athlete);

    /**
     * Get all the athletes.
     *
     * @return the list of entities.
     */
    List<Athlete> findAll();

    /**
     * Get the "id" athlete.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Athlete> findOne(Long id);

    /**
     * Delete the "id" athlete.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
