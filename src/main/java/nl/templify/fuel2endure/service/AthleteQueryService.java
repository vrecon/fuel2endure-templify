package nl.templify.fuel2endure.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import nl.templify.fuel2endure.domain.*; // for static metamodels
import nl.templify.fuel2endure.domain.Athlete;
import nl.templify.fuel2endure.repository.AthleteRepository;
import nl.templify.fuel2endure.service.criteria.AthleteCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Athlete} entities in the database.
 * The main input is a {@link AthleteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Athlete} or a {@link Page} of {@link Athlete} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AthleteQueryService extends QueryService<Athlete> {

    private final Logger log = LoggerFactory.getLogger(AthleteQueryService.class);

    private final AthleteRepository athleteRepository;

    public AthleteQueryService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * Return a {@link List} of {@link Athlete} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Athlete> findByCriteria(AthleteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Athlete} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Athlete> findByCriteria(AthleteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AthleteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Athlete> specification = createSpecification(criteria);
        return athleteRepository.count(specification);
    }

    /**
     * Function to convert {@link AthleteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Athlete> createSpecification(AthleteCriteria criteria) {
        Specification<Athlete> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Athlete_.id));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Athlete_.middleName));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), Athlete_.length));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Athlete_.weight));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Athlete_.status));
            }
            if (criteria.getFuelSettingsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuelSettingsId(),
                            root -> root.join(Athlete_.fuelSettings, JoinType.LEFT).get(FuelSettings_.id)
                        )
                    );
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(Athlete_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getRaceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRaceId(), root -> root.join(Athlete_.races, JoinType.LEFT).get(Race_.id))
                    );
            }
            if (criteria.getTrainingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTrainingId(), root -> root.join(Athlete_.trainings, JoinType.LEFT).get(Training_.id))
                    );
            }
            if (criteria.getPaymentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPaymentId(), root -> root.join(Athlete_.payments, JoinType.LEFT).get(Payment_.id))
                    );
            }
            if (criteria.getVoucherId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVoucherId(), root -> root.join(Athlete_.voucher, JoinType.LEFT).get(Voucher_.id))
                    );
            }
        }
        return specification;
    }
}
