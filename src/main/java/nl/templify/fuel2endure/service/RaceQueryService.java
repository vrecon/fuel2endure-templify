package nl.templify.fuel2endure.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import nl.templify.fuel2endure.domain.*; // for static metamodels
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.repository.RaceRepository;
import nl.templify.fuel2endure.service.criteria.RaceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Race} entities in the database.
 * The main input is a {@link RaceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Race} or a {@link Page} of {@link Race} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RaceQueryService extends QueryService<Race> {

    private final Logger log = LoggerFactory.getLogger(RaceQueryService.class);

    private final RaceRepository raceRepository;

    public RaceQueryService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    /**
     * Return a {@link List} of {@link Race} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Race> findByCriteria(RaceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Race> specification = createSpecification(criteria);
        return raceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Race} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Race> findByCriteria(RaceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Race> specification = createSpecification(criteria);
        return raceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RaceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Race> specification = createSpecification(criteria);
        return raceRepository.count(specification);
    }

    /**
     * Function to convert {@link RaceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Race> createSpecification(RaceCriteria criteria) {
        Specification<Race> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Race_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Race_.date));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Race_.name));
            }
            if (criteria.getLogging() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogging(), Race_.logging));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), Race_.weight));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), Race_.length));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), Race_.temperature));
            }
            if (criteria.getComp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getComp(), Race_.comp));
            }
            if (criteria.getSwimDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSwimDuration(), Race_.swimDuration));
            }
            if (criteria.getBikeDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBikeDuration(), Race_.bikeDuration));
            }
            if (criteria.getRunDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunDuration(), Race_.runDuration));
            }
            if (criteria.getGelCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelCarbo(), Race_.gelCarbo));
            }
            if (criteria.getDrinkCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDrinkCarbo(), Race_.drinkCarbo));
            }
            if (criteria.getDrinkOrgCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDrinkOrgCarbo(), Race_.drinkOrgCarbo));
            }
            if (criteria.getGelOrgCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelOrgCarbo(), Race_.gelOrgCarbo));
            }
            if (criteria.getGelsbike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsbike(), Race_.gelsbike));
            }
            if (criteria.getGelsrun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsrun(), Race_.gelsrun));
            }
            if (criteria.getSelectedOrgGelQuery() != null) {
                specification = specification.and(buildSpecification(criteria.getSelectedOrgGelQuery(), Race_.selectedOrgGelQuery));
            }
            if (criteria.getSelectedOrgDrankQuery() != null) {
                specification = specification.and(buildSpecification(criteria.getSelectedOrgDrankQuery(), Race_.selectedOrgDrankQuery));
            }
            if (criteria.getSportDrinkOrgBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSportDrinkOrgBike(), Race_.sportDrinkOrgBike));
            }
            if (criteria.getWaterOrgBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterOrgBike(), Race_.waterOrgBike));
            }
            if (criteria.getGelsOrgBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsOrgBike(), Race_.gelsOrgBike));
            }
            if (criteria.getSportDrinkOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSportDrinkOrgRun(), Race_.sportDrinkOrgRun));
            }
            if (criteria.getWaterOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterOrgRun(), Race_.waterOrgRun));
            }
            if (criteria.getGelsOrgRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsOrgRun(), Race_.gelsOrgRun));
            }
            if (criteria.getOrsBeforeStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsBeforeStart(), Race_.orsBeforeStart));
            }
            if (criteria.getSportDrinkToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSportDrinkToTakeBike(), Race_.sportDrinkToTakeBike));
            }
            if (criteria.getWaterToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterToTakeBike(), Race_.waterToTakeBike));
            }
            if (criteria.getExtraWaterToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraWaterToTakeBike(), Race_.extraWaterToTakeBike));
            }
            if (criteria.getOrsToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsToTakeBike(), Race_.orsToTakeBike));
            }
            if (criteria.getGelsToTakeBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsToTakeBike(), Race_.gelsToTakeBike));
            }
            if (criteria.getSportDrinkToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSportDrinkToTakeRun(), Race_.sportDrinkToTakeRun));
            }
            if (criteria.getWaterToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaterToTakeRun(), Race_.waterToTakeRun));
            }
            if (criteria.getExtraWaterToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraWaterToTakeRun(), Race_.extraWaterToTakeRun));
            }
            if (criteria.getOrsToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrsToTakeRun(), Race_.orsToTakeRun));
            }
            if (criteria.getCarboNeedDuringBikeMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBikeMin(), Race_.carboNeedDuringBikeMin));
            }
            if (criteria.getCarboNeedDuringBikeMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBikeMax(), Race_.carboNeedDuringBikeMax));
            }
            if (criteria.getCarboNeedDuringRunMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRunMin(), Race_.carboNeedDuringRunMin));
            }
            if (criteria.getCarboNeedDuringRunMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRunMax(), Race_.carboNeedDuringRunMax));
            }
            if (criteria.getDiffCarboRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiffCarboRun(), Race_.diffCarboRun));
            }
            if (criteria.getDiffMoisterRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiffMoisterRun(), Race_.diffMoisterRun));
            }
            if (criteria.getDifCarbo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDifCarbo(), Race_.difCarbo));
            }
            if (criteria.getDifMoister() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDifMoister(), Race_.difMoister));
            }
            if (criteria.getGelsToTakeRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGelsToTakeRun(), Race_.gelsToTakeRun));
            }
            if (criteria.getWeightLossDuringBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightLossDuringBike(), Race_.weightLossDuringBike));
            }
            if (criteria.getCarboNeedDuringRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarboNeedDuringRun(), Race_.carboNeedDuringRun));
            }
            if (criteria.getFluidNeedPerHourBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourBike(), Race_.fluidNeedPerHourBike));
            }
            if (criteria.getFluidNeedPerHourSwim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourSwim(), Race_.fluidNeedPerHourSwim));
            }
            if (criteria.getCarboNeedDuringBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarboNeedDuringBike(), Race_.carboNeedDuringBike));
            }
            if (criteria.getFluidNeedDuringBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFluidNeedDuringBike(), Race_.fluidNeedDuringBike));
            }
            if (criteria.getFluidNeedPerHourRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFluidNeedPerHourRun(), Race_.fluidNeedPerHourRun));
            }
            if (criteria.getFluidNeedDuringRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFluidNeedDuringRun(), Race_.fluidNeedDuringRun));
            }
            if (criteria.getWeightLossDuringRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightLossDuringRun(), Race_.weightLossDuringRun));
            }
            if (criteria.getActFluidNeedBike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActFluidNeedBike(), Race_.actFluidNeedBike));
            }
            if (criteria.getActFluidNeedRun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActFluidNeedRun(), Race_.actFluidNeedRun));
            }
            if (criteria.getRacePlanFormId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRacePlanFormId(),
                            root -> root.join(Race_.racePlanForms, JoinType.LEFT).get(RacePlanForm_.id)
                        )
                    );
            }
            if (criteria.getAthleteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAthleteId(), root -> root.join(Race_.athlete, JoinType.LEFT).get(Athlete_.id))
                    );
            }
        }
        return specification;
    }
}
