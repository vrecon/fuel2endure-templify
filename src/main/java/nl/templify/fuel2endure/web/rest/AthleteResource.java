package nl.templify.fuel2endure.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Athlete;
import nl.templify.fuel2endure.repository.AthleteRepository;
import nl.templify.fuel2endure.service.AthleteQueryService;
import nl.templify.fuel2endure.service.AthleteService;
import nl.templify.fuel2endure.service.criteria.AthleteCriteria;
import nl.templify.fuel2endure.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.templify.fuel2endure.domain.Athlete}.
 */
@RestController
@RequestMapping("/api")
public class AthleteResource {

    private final Logger log = LoggerFactory.getLogger(AthleteResource.class);

    private static final String ENTITY_NAME = "athlete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AthleteService athleteService;

    private final AthleteRepository athleteRepository;

    private final AthleteQueryService athleteQueryService;

    public AthleteResource(AthleteService athleteService, AthleteRepository athleteRepository, AthleteQueryService athleteQueryService) {
        this.athleteService = athleteService;
        this.athleteRepository = athleteRepository;
        this.athleteQueryService = athleteQueryService;
    }

    /**
     * {@code POST  /athletes} : Create a new athlete.
     *
     * @param athlete the athlete to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new athlete, or with status {@code 400 (Bad Request)} if the athlete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/athletes")
    public ResponseEntity<Athlete> createAthlete(@RequestBody Athlete athlete) throws URISyntaxException {
        log.debug("REST request to save Athlete : {}", athlete);
        if (athlete.getId() != null) {
            throw new BadRequestAlertException("A new athlete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Athlete result = athleteService.save(athlete);
        return ResponseEntity
            .created(new URI("/api/athletes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /athletes/:id} : Updates an existing athlete.
     *
     * @param id the id of the athlete to save.
     * @param athlete the athlete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athlete,
     * or with status {@code 400 (Bad Request)} if the athlete is not valid,
     * or with status {@code 500 (Internal Server Error)} if the athlete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/athletes/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable(value = "id", required = false) final Long id, @RequestBody Athlete athlete)
        throws URISyntaxException {
        log.debug("REST request to update Athlete : {}, {}", id, athlete);
        if (athlete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, athlete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!athleteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Athlete result = athleteService.save(athlete);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athlete.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /athletes/:id} : Partial updates given fields of an existing athlete, field will ignore if it is null
     *
     * @param id the id of the athlete to save.
     * @param athlete the athlete to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated athlete,
     * or with status {@code 400 (Bad Request)} if the athlete is not valid,
     * or with status {@code 404 (Not Found)} if the athlete is not found,
     * or with status {@code 500 (Internal Server Error)} if the athlete couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/athletes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Athlete> partialUpdateAthlete(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Athlete athlete
    ) throws URISyntaxException {
        log.debug("REST request to partial update Athlete partially : {}, {}", id, athlete);
        if (athlete.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, athlete.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!athleteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Athlete> result = athleteService.partialUpdate(athlete);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, athlete.getId().toString())
        );
    }

    /**
     * {@code GET  /athletes} : get all the athletes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of athletes in body.
     */
    @GetMapping("/athletes")
    public ResponseEntity<List<Athlete>> getAllAthletes(AthleteCriteria criteria) {
        log.debug("REST request to get Athletes by criteria: {}", criteria);
        List<Athlete> entityList = athleteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /athletes/count} : count all the athletes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/athletes/count")
    public ResponseEntity<Long> countAthletes(AthleteCriteria criteria) {
        log.debug("REST request to count Athletes by criteria: {}", criteria);
        return ResponseEntity.ok().body(athleteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /athletes/:id} : get the "id" athlete.
     *
     * @param id the id of the athlete to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the athlete, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/athletes/{id}")
    public ResponseEntity<Athlete> getAthlete(@PathVariable Long id) {
        log.debug("REST request to get Athlete : {}", id);
        Optional<Athlete> athlete = athleteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(athlete);
    }

    /**
     * {@code DELETE  /athletes/:id} : delete the "id" athlete.
     *
     * @param id the id of the athlete to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/athletes/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        log.debug("REST request to delete Athlete : {}", id);
        athleteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
