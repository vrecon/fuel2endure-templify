package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Race;

/**
 * Service Interface for managing {@link Race}.
 */
public interface RaceService {
    /**
     * Save a race.
     *
     * @param race the entity to save.
     * @return the persisted entity.
     */
    Race save(Race race);

    /**
     * Partially updates a race.
     *
     * @param race the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Race> partialUpdate(Race race);

    /**
     * Get all the races.
     *
     * @return the list of entities.
     */
    List<Race> findAll();

    /**
     * Get the "id" race.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Race> findOne(Long id);

    /**
     * Delete the "id" race.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
