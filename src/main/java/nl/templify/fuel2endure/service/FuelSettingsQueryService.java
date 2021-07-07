package nl.templify.fuel2endure.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import nl.templify.fuel2endure.domain.*; // for static metamodels
import nl.templify.fuel2endure.domain.FuelSettings;
import nl.templify.fuel2endure.repository.FuelSettingsRepository;
import nl.templify.fuel2endure.service.criteria.FuelSettingsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FuelSettings} entities in the database.
 * The main input is a {@link FuelSettingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuelSettings} or a {@link Page} of {@link FuelSettings} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuelSettingsQueryService extends QueryService<FuelSettings> {

    private final Logger log = LoggerFactory.getLogger(FuelSettingsQueryService.class);

    private final FuelSettingsRepository fuelSettingsRepository;

    public FuelSettingsQueryService(FuelSettingsRepository fuelSettingsRepository) {
        this.fuelSettingsRepository = fuelSettingsRepository;
    }

    /**
     * Return a {@link List} of {@link FuelSettings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuelSettings> findByCriteria(FuelSettingsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FuelSettings> specification = createSpecification(criteria);
        return fuelSettingsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FuelSettings} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuelSettings> findByCriteria(FuelSettingsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuelSettings> specification = createSpecification(criteria);
        return fuelSettingsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuelSettingsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FuelSettings> specification = createSpecification(criteria);
        return fuelSettingsRepository.count(specification);
    }

    /**
     * Function to convert {@link FuelSettingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuelSettings> createSpecification(FuelSettingsCriteria criteria) {
        Specification<FuelSettings> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FuelSettings_.id));
            }
            if (criteria.getCarbohydratesGel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarbohydratesGel(), FuelSettings_.carbohydratesGel));
            }
            if (criteria.getCarbohydratesSportDrank() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getCarbohydratesSportDrank(), FuelSettings_.carbohydratesSportDrank)
                    );
            }
            if (criteria.getSelectedOwnGelItem() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSelectedOwnGelItem(), FuelSettings_.selectedOwnGelItem));
            }
            if (criteria.getSelectedOwnDrinkItem() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSelectedOwnDrinkItem(), FuelSettings_.selectedOwnDrinkItem));
            }
        }
        return specification;
    }
}
