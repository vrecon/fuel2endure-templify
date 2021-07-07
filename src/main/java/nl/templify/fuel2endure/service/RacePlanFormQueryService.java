package nl.templify.fuel2endure.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import nl.templify.fuel2endure.domain.*; // for static metamodels
import nl.templify.fuel2endure.domain.RacePlanForm;
import nl.templify.fuel2endure.repository.RacePlanFormRepository;
import nl.templify.fuel2endure.service.criteria.RacePlanFormCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RacePlanForm} entities in the database.
 * The main input is a {@link RacePlanFormCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RacePlanForm} or a {@link Page} of {@link RacePlanForm} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RacePlanFormQueryService extends QueryService<RacePlanForm> {

    private final Logger log = LoggerFactory.getLogger(RacePlanFormQueryService.class);

    private final RacePlanFormRepository racePlanFormRepository;

    public RacePlanFormQueryService(RacePlanFormRepository racePlanFormRepository) {
        this.racePlanFormRepository = racePlanFormRepository;
    }

    /**
     * Return a {@link List} of {@link RacePlanForm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RacePlanForm> findByCriteria(RacePlanFormCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RacePlanForm> specification = createSpecification(criteria);
        return racePlanFormRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RacePlanForm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RacePlanForm> findByCriteria(RacePlanFormCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RacePlanForm> specification = createSpecification(criteria);
        return racePlanFormRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RacePlanFormCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RacePlanForm> specification = createSpecification(criteria);
        return racePlanFormRepository.count(specification);
    }

    /**
     * Function to convert {@link RacePlanFormCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RacePlanForm> createSpecification(RacePlanFormCriteria criteria) {
        Specification<RacePlanForm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RacePlanForm_.id));
            }
            if (criteria.getComp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComp(), RacePlanForm_.comp));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RacePlanForm_.name));
            }
            if (criteria.getSelectedOrgGelQuery() != null) {
                specification = specification.and(buildSpecification(criteria.getSelectedOrgGelQuery(), RacePlanForm_.selectedOrgGelQuery));
            }
            if (criteria.getSelectedOrgDrankQuery() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getSelectedOrgDrankQuery(), RacePlanForm_.selectedOrgDrankQuery));
            }
            if (criteria.getOrsBeforeStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsBeforeStart(), RacePlanForm_.orsBeforeStart));
            }
            if (criteria.getDrinkCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDrinkCarbo(), RacePlanForm_.drinkCarbo));
            }
            if (criteria.getGelCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelCarbo(), RacePlanForm_.gelCarbo));
            }
            if (criteria.getDrinkOrgCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDrinkOrgCarbo(), RacePlanForm_.drinkOrgCarbo));
            }
            if (criteria.getGelOrgCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelOrgCarbo(), RacePlanForm_.gelOrgCarbo));
            }
            if (criteria.getSportDrinkOrgBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSportDrinkOrgBike(), RacePlanForm_.sportDrinkOrgBike));
            }
            if (criteria.getWaterOrgBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterOrgBike(), RacePlanForm_.waterOrgBike));
            }
            if (criteria.getGelsOrgBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsOrgBike(), RacePlanForm_.gelsOrgBike));
            }
            if (criteria.getSportDrinkOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSportDrinkOrgRun(), RacePlanForm_.sportDrinkOrgRun));
            }
            if (criteria.getWaterOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterOrgRun(), RacePlanForm_.waterOrgRun));
            }
            if (criteria.getGelsOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsOrgRun(), RacePlanForm_.gelsOrgRun));
            }
            if (criteria.getSportDrinkToTakeBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSportDrinkToTakeBike(), RacePlanForm_.sportDrinkToTakeBike));
            }
            if (criteria.getWaterToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterToTakeBike(), RacePlanForm_.waterToTakeBike));
            }
            if (criteria.getExtraWaterToTakeBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtraWaterToTakeBike(), RacePlanForm_.extraWaterToTakeBike));
            }
            if (criteria.getOrsToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsToTakeBike(), RacePlanForm_.orsToTakeBike));
            }
            if (criteria.getGelsToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsToTakeBike(), RacePlanForm_.gelsToTakeBike));
            }
            if (criteria.getSportDrinkToTakeRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSportDrinkToTakeRun(), RacePlanForm_.sportDrinkToTakeRun));
            }
            if (criteria.getWaterToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterToTakeRun(), RacePlanForm_.waterToTakeRun));
            }
            if (criteria.getExtraWaterToTakeRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtraWaterToTakeRun(), RacePlanForm_.extraWaterToTakeRun));
            }
            if (criteria.getOrsToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsToTakeRun(), RacePlanForm_.orsToTakeRun));
            }
            if (criteria.getGelsToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsToTakeRun(), RacePlanForm_.gelsToTakeRun));
            }
            if (criteria.getWeightLossDuringBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getWeightLossDuringBike(), RacePlanForm_.weightLossDuringBike));
            }
            if (criteria.getCarboNeedDuringRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRun(), RacePlanForm_.carboNeedDuringRun));
            }
            if (criteria.getFluidNeedPerHourBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourBike(), RacePlanForm_.fluidNeedPerHourBike));
            }
            if (criteria.getFluidNeedPerHourSwim() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourSwim(), RacePlanForm_.fluidNeedPerHourSwim));
            }
            if (criteria.getCarboNeedDuringBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBike(), RacePlanForm_.carboNeedDuringBike));
            }
            if (criteria.getFluidNeedDuringBike() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFluidNeedDuringBike(), RacePlanForm_.fluidNeedDuringBike));
            }
            if (criteria.getFluidNeedPerHourRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourRun(), RacePlanForm_.fluidNeedPerHourRun));
            }
            if (criteria.getFluidNeedDuringRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFluidNeedDuringRun(), RacePlanForm_.fluidNeedDuringRun));
            }
            if (criteria.getWeightLossDuringRun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getWeightLossDuringRun(), RacePlanForm_.weightLossDuringRun));
            }
            if (criteria.getDiffCarboRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiffCarboRun(), RacePlanForm_.diffCarboRun));
            }
            if (criteria.getDiffMoisterRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiffMoisterRun(), RacePlanForm_.diffMoisterRun));
            }
            if (criteria.getDifCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDifCarbo(), RacePlanForm_.difCarbo));
            }
            if (criteria.getDifMoister() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDifMoister(), RacePlanForm_.difMoister));
            }
            if (criteria.getActFluidNeedBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActFluidNeedBike(), RacePlanForm_.actFluidNeedBike));
            }
            if (criteria.getActFluidNeedRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActFluidNeedRun(), RacePlanForm_.actFluidNeedRun));
            }
            if (criteria.getCarboNeedDuringBikeMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBikeMin(), RacePlanForm_.carboNeedDuringBikeMin));
            }
            if (criteria.getCarboNeedDuringBikeMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBikeMax(), RacePlanForm_.carboNeedDuringBikeMax));
            }
            if (criteria.getCarboNeedDuringRunMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRunMin(), RacePlanForm_.carboNeedDuringRunMin));
            }
            if (criteria.getCarboNeedDuringRunMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRunMax(), RacePlanForm_.carboNeedDuringRunMax));
            }
            if (criteria.getStartGel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartGel(), RacePlanForm_.startGel));
            }
            if (criteria.getStartDrank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDrank(), RacePlanForm_.startDrank));
            }
            if (criteria.getRaceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRaceId(), root -> root.join(RacePlanForm_.race, JoinType.LEFT).get(Race_.id))
                    );
            }
        }
        return specification;
    }
}
