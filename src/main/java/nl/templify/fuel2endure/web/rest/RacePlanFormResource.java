package nl.templify.fuel2endure.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.templify.fuel2endure.domain.RacePlanForm;
import nl.templify.fuel2endure.repository.RacePlanFormRepository;
import nl.templify.fuel2endure.service.RacePlanFormQueryService;
import nl.templify.fuel2endure.service.RacePlanFormService;
import nl.templify.fuel2endure.service.criteria.RacePlanFormCriteria;
import nl.templify.fuel2endure.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.templify.fuel2endure.domain.RacePlanForm}.
 */
@RestController
@RequestMapping("/api")
public class RacePlanFormResource {

    private final Logger log = LoggerFactory.getLogger(RacePlanFormResource.class);

    private static final String ENTITY_NAME = "racePlanForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacePlanFormService racePlanFormService;

    private final RacePlanFormRepository racePlanFormRepository;

    private final RacePlanFormQueryService racePlanFormQueryService;

    public RacePlanFormResource(
        RacePlanFormService racePlanFormService,
        RacePlanFormRepository racePlanFormRepository,
        RacePlanFormQueryService racePlanFormQueryService
    ) {
        this.racePlanFormService = racePlanFormService;
        this.racePlanFormRepository = racePlanFormRepository;
        this.racePlanFormQueryService = racePlanFormQueryService;
    }

    /**
     * {@code POST  /race-plan-forms} : Create a new racePlanForm.
     *
     * @param racePlanForm the racePlanForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racePlanForm, or with status {@code 400 (Bad Request)} if the racePlanForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/race-plan-forms")
    public ResponseEntity<RacePlanForm> createRacePlanForm(@RequestBody RacePlanForm racePlanForm) throws URISyntaxException {
        log.debug("REST request to save RacePlanForm : {}", racePlanForm);
        if (racePlanForm.getId() != null) {
            throw new BadRequestAlertException("A new racePlanForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacePlanForm result = racePlanFormService.save(racePlanForm);
        return ResponseEntity
            .created(new URI("/api/race-plan-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /race-plan-forms/:id} : Updates an existing racePlanForm.
     *
     * @param id the id of the racePlanForm to save.
     * @param racePlanForm the racePlanForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racePlanForm,
     * or with status {@code 400 (Bad Request)} if the racePlanForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racePlanForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/race-plan-forms/{id}")
    public ResponseEntity<RacePlanForm> updateRacePlanForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RacePlanForm racePlanForm
    ) throws URISyntaxException {
        log.debug("REST request to update RacePlanForm : {}, {}", id, racePlanForm);
        if (racePlanForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, racePlanForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!racePlanFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RacePlanForm result = racePlanFormService.save(racePlanForm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racePlanForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /race-plan-forms/:id} : Partial updates given fields of an existing racePlanForm, field will ignore if it is null
     *
     * @param id the id of the racePlanForm to save.
     * @param racePlanForm the racePlanForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racePlanForm,
     * or with status {@code 400 (Bad Request)} if the racePlanForm is not valid,
     * or with status {@code 404 (Not Found)} if the racePlanForm is not found,
     * or with status {@code 500 (Internal Server Error)} if the racePlanForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/race-plan-forms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RacePlanForm> partialUpdateRacePlanForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RacePlanForm racePlanForm
    ) throws URISyntaxException {
        log.debug("REST request to partial update RacePlanForm partially : {}, {}", id, racePlanForm);
        if (racePlanForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, racePlanForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!racePlanFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RacePlanForm> result = racePlanFormService.partialUpdate(racePlanForm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racePlanForm.getId().toString())
        );
    }

    /**
     * {@code GET  /race-plan-forms} : get all the racePlanForms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racePlanForms in body.
     */
    @GetMapping("/race-plan-forms")
    public ResponseEntity<List<RacePlanForm>> getAllRacePlanForms(RacePlanFormCriteria criteria) {
        log.debug("REST request to get RacePlanForms by criteria: {}", criteria);
        List<RacePlanForm> entityList = racePlanFormQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /race-plan-forms/count} : count all the racePlanForms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/race-plan-forms/count")
    public ResponseEntity<Long> countRacePlanForms(RacePlanFormCriteria criteria) {
        log.debug("REST request to count RacePlanForms by criteria: {}", criteria);
        return ResponseEntity.ok().body(racePlanFormQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /race-plan-forms/:id} : get the "id" racePlanForm.
     *
     * @param id the id of the racePlanForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racePlanForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/race-plan-forms/{id}")
    public ResponseEntity<RacePlanForm> getRacePlanForm(@PathVariable Long id) {
        log.debug("REST request to get RacePlanForm : {}", id);
        Optional<RacePlanForm> racePlanForm = racePlanFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racePlanForm);
    }

    /**
     * {@code DELETE  /race-plan-forms/:id} : delete the "id" racePlanForm.
     *
     * @param id the id of the racePlanForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/race-plan-forms/{id}")
    public ResponseEntity<Void> deleteRacePlanForm(@PathVariable Long id) {
        log.debug("REST request to delete RacePlanForm : {}", id);
        racePlanFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
