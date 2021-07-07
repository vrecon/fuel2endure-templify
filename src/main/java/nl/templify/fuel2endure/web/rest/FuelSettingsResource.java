package nl.templify.fuel2endure.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.templify.fuel2endure.domain.FuelSettings;
import nl.templify.fuel2endure.repository.FuelSettingsRepository;
import nl.templify.fuel2endure.service.FuelSettingsQueryService;
import nl.templify.fuel2endure.service.FuelSettingsService;
import nl.templify.fuel2endure.service.criteria.FuelSettingsCriteria;
import nl.templify.fuel2endure.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.templify.fuel2endure.domain.FuelSettings}.
 */
@RestController
@RequestMapping("/api")
public class FuelSettingsResource {

    private final Logger log = LoggerFactory.getLogger(FuelSettingsResource.class);

    private static final String ENTITY_NAME = "fuelSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelSettingsService fuelSettingsService;

    private final FuelSettingsRepository fuelSettingsRepository;

    private final FuelSettingsQueryService fuelSettingsQueryService;

    public FuelSettingsResource(
        FuelSettingsService fuelSettingsService,
        FuelSettingsRepository fuelSettingsRepository,
        FuelSettingsQueryService fuelSettingsQueryService
    ) {
        this.fuelSettingsService = fuelSettingsService;
        this.fuelSettingsRepository = fuelSettingsRepository;
        this.fuelSettingsQueryService = fuelSettingsQueryService;
    }

    /**
     * {@code POST  /fuel-settings} : Create a new fuelSettings.
     *
     * @param fuelSettings the fuelSettings to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelSettings, or with status {@code 400 (Bad Request)} if the fuelSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-settings")
    public ResponseEntity<FuelSettings> createFuelSettings(@RequestBody FuelSettings fuelSettings) throws URISyntaxException {
        log.debug("REST request to save FuelSettings : {}", fuelSettings);
        if (fuelSettings.getId() != null) {
            throw new BadRequestAlertException("A new fuelSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelSettings result = fuelSettingsService.save(fuelSettings);
        return ResponseEntity
            .created(new URI("/api/fuel-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-settings/:id} : Updates an existing fuelSettings.
     *
     * @param id the id of the fuelSettings to save.
     * @param fuelSettings the fuelSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelSettings,
     * or with status {@code 400 (Bad Request)} if the fuelSettings is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-settings/{id}")
    public ResponseEntity<FuelSettings> updateFuelSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelSettings fuelSettings
    ) throws URISyntaxException {
        log.debug("REST request to update FuelSettings : {}, {}", id, fuelSettings);
        if (fuelSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuelSettings result = fuelSettingsService.save(fuelSettings);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelSettings.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fuel-settings/:id} : Partial updates given fields of an existing fuelSettings, field will ignore if it is null
     *
     * @param id the id of the fuelSettings to save.
     * @param fuelSettings the fuelSettings to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelSettings,
     * or with status {@code 400 (Bad Request)} if the fuelSettings is not valid,
     * or with status {@code 404 (Not Found)} if the fuelSettings is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelSettings couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fuel-settings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FuelSettings> partialUpdateFuelSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelSettings fuelSettings
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuelSettings partially : {}, {}", id, fuelSettings);
        if (fuelSettings.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelSettings.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelSettings> result = fuelSettingsService.partialUpdate(fuelSettings);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelSettings.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-settings} : get all the fuelSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelSettings in body.
     */
    @GetMapping("/fuel-settings")
    public ResponseEntity<List<FuelSettings>> getAllFuelSettings(FuelSettingsCriteria criteria) {
        log.debug("REST request to get FuelSettings by criteria: {}", criteria);
        List<FuelSettings> entityList = fuelSettingsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /fuel-settings/count} : count all the fuelSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fuel-settings/count")
    public ResponseEntity<Long> countFuelSettings(FuelSettingsCriteria criteria) {
        log.debug("REST request to count FuelSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(fuelSettingsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fuel-settings/:id} : get the "id" fuelSettings.
     *
     * @param id the id of the fuelSettings to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelSettings, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-settings/{id}")
    public ResponseEntity<FuelSettings> getFuelSettings(@PathVariable Long id) {
        log.debug("REST request to get FuelSettings : {}", id);
        Optional<FuelSettings> fuelSettings = fuelSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelSettings);
    }

    /**
     * {@code DELETE  /fuel-settings/:id} : delete the "id" fuelSettings.
     *
     * @param id the id of the fuelSettings to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-settings/{id}")
    public ResponseEntity<Void> deleteFuelSettings(@PathVariable Long id) {
        log.debug("REST request to delete FuelSettings : {}", id);
        fuelSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
