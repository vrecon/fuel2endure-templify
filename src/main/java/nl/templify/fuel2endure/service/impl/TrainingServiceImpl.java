package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Training;
import nl.templify.fuel2endure.repository.TrainingRepository;
import nl.templify.fuel2endure.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Training save(Training training) {
        log.debug("Request to save Training : {}", training);
        return trainingRepository.save(training);
    }

    @Override
    public Optional<Training> partialUpdate(Training training) {
        log.debug("Request to partially update Training : {}", training);

        return trainingRepository
            .findById(training.getId())
            .map(
                existingTraining -> {
                    if (training.getDate() != null) {
                        existingTraining.setDate(training.getDate());
                    }
                    if (training.getTrainingTypeCode() != null) {
                        existingTraining.setTrainingTypeCode(training.getTrainingTypeCode());
                    }
                    if (training.getDuration() != null) {
                        existingTraining.setDuration(training.getDuration());
                    }
                    if (training.getTrainingIntensityCode() != null) {
                        existingTraining.setTrainingIntensityCode(training.getTrainingIntensityCode());
                    }
                    if (training.getTemperature() != null) {
                        existingTraining.setTemperature(training.getTemperature());
                    }
                    if (training.getWeightBefore() != null) {
                        existingTraining.setWeightBefore(training.getWeightBefore());
                    }
                    if (training.getWeightAfter() != null) {
                        existingTraining.setWeightAfter(training.getWeightAfter());
                    }
                    if (training.getDrunk() != null) {
                        existingTraining.setDrunk(training.getDrunk());
                    }
                    if (training.getEaten() != null) {
                        existingTraining.setEaten(training.getEaten());
                    }
                    if (training.getMoistureLossPercentage() != null) {
                        existingTraining.setMoistureLossPercentage(training.getMoistureLossPercentage());
                    }
                    if (training.getMoistureLossPerHour() != null) {
                        existingTraining.setMoistureLossPerHour(training.getMoistureLossPerHour());
                    }
                    if (training.getDefaultMoisterLossPerHour() != null) {
                        existingTraining.setDefaultMoisterLossPerHour(training.getDefaultMoisterLossPerHour());
                    }
                    if (training.getDeltaMoisterLossPerHour() != null) {
                        existingTraining.setDeltaMoisterLossPerHour(training.getDeltaMoisterLossPerHour());
                    }
                    if (training.getExcluded() != null) {
                        existingTraining.setExcluded(training.getExcluded());
                    }
                    if (training.getComments() != null) {
                        existingTraining.setComments(training.getComments());
                    }
                    if (training.getCarboDrank() != null) {
                        existingTraining.setCarboDrank(training.getCarboDrank());
                    }

                    return existingTraining;
                }
            )
            .map(trainingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> findAll() {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Training> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
    }
}
