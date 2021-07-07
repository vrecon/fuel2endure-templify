package nl.templify.fuel2endure.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import nl.templify.fuel2endure.domain.*; // for static metamodels
import nl.templify.fuel2endure.domain.Training;
import nl.templify.fuel2endure.repository.TrainingRepository;
import nl.templify.fuel2endure.service.criteria.TrainingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Training} entities in the database.
 * The main input is a {@link TrainingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Training} or a {@link Page} of {@link Training} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingQueryService extends QueryService<Training> {

    private final Logger log = LoggerFactory.getLogger(TrainingQueryService.class);

    private final TrainingRepository trainingRepository;

    public TrainingQueryService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Return a {@link List} of {@link Training} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Training> findByCriteria(TrainingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Training} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Training> findByCriteria(TrainingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Training> createSpecification(TrainingCriteria criteria) {
        Specification<Training> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Training_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Training_.date));
            }
            if (criteria.getTrainingTypeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrainingTypeCode(), Training_.trainingTypeCode));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Training_.duration));
            }
            if (criteria.getTrainingIntensityCode() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTrainingIntensityCode(), Training_.trainingIntensityCode));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), Training_.temperature));
            }
            if (criteria.getWeightBefore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightBefore(), Training_.weightBefore));
            }
            if (criteria.getWeightAfter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightAfter(), Training_.weightAfter));
            }
            if (criteria.getDrunk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDrunk(), Training_.drunk));
            }
            if (criteria.getEaten() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEaten(), Training_.eaten));
            }
            if (criteria.getMoistureLossPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMoistureLossPercentage(), Training_.moistureLossPercentage));
            }
            if (criteria.getMoistureLossPerHour() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMoistureLossPerHour(), Training_.moistureLossPerHour));
            }
            if (criteria.getDefaultMoisterLossPerHour() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDefaultMoisterLossPerHour(), Training_.defaultMoisterLossPerHour)
                    );
            }
            if (criteria.getDeltaMoisterLossPerHour() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDeltaMoisterLossPerHour(), Training_.deltaMoisterLossPerHour));
            }
            if (criteria.getExcluded() != null) {
                specification = specification.and(buildSpecification(criteria.getExcluded(), Training_.excluded));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Training_.comments));
            }
            if (criteria.getCarboDrank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarboDrank(), Training_.carboDrank));
            }
            if (criteria.getAthleteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAthleteId(), root -> root.join(Training_.athlete, JoinType.LEFT).get(Athlete_.id))
                    );
            }
        }
        return specification;
    }
}
