package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Training;

/**
 * Service Interface for managing {@link Training}.
 */
public interface TrainingService {
    /**
     * Save a training.
     *
     * @param training the entity to save.
     * @return the persisted entity.
     */
    Training save(Training training);

    /**
     * Partially updates a training.
     *
     * @param training the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Training> partialUpdate(Training training);

    /**
     * Get all the trainings.
     *
     * @return the list of entities.
     */
    List<Training> findAll();

    /**
     * Get the "id" training.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Training> findOne(Long id);

    /**
     * Delete the "id" training.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
