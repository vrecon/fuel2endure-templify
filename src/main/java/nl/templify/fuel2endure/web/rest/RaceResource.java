package nl.templify.fuel2endure.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.repository.RaceRepository;
import nl.templify.fuel2endure.service.RaceQueryService;
import nl.templify.fuel2endure.service.RaceService;
import nl.templify.fuel2endure.service.criteria.RaceCriteria;
import nl.templify.fuel2endure.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.templify.fuel2endure.domain.Race}.
 */
@RestController
@RequestMapping("/api")
public class RaceResource {

    private final Logger log = LoggerFactory.getLogger(RaceResource.class);

    private static final String ENTITY_NAME = "race";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaceService raceService;

    private final RaceRepository raceRepository;

    private final RaceQueryService raceQueryService;

    public RaceResource(RaceService raceService, RaceRepository raceRepository, RaceQueryService raceQueryService) {
        this.raceService = raceService;
        this.raceRepository = raceRepository;
        this.raceQueryService = raceQueryService;
    }

    /**
     * {@code POST  /races} : Create a new race.
     *
     * @param race the race to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new race, or with status {@code 400 (Bad Request)} if the race has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/races")
    public ResponseEntity<Race> createRace(@RequestBody Race race) throws URISyntaxException {
        log.debug("REST request to save Race : {}", race);
        if (race.getId() != null) {
            throw new BadRequestAlertException("A new race cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Race result = raceService.save(race);
        return ResponseEntity
            .created(new URI("/api/races/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /races/:id} : Updates an existing race.
     *
     * @param id the id of the race to save.
     * @param race the race to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated race,
     * or with status {@code 400 (Bad Request)} if the race is not valid,
     * or with status {@code 500 (Internal Server Error)} if the race couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/races/{id}")
    public ResponseEntity<Race> updateRace(@PathVariable(value = "id", required = false) final Long id, @RequestBody Race race)
        throws URISyntaxException {
        log.debug("REST request to update Race : {}, {}", id, race);
        if (race.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, race.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Race result = raceService.save(race);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, race.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /races/:id} : Partial updates given fields of an existing race, field will ignore if it is null
     *
     * @param id the id of the race to save.
     * @param race the race to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated race,
     * or with status {@code 400 (Bad Request)} if the race is not valid,
     * or with status {@code 404 (Not Found)} if the race is not found,
     * or with status {@code 500 (Internal Server Error)} if the race couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/races/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Race> partialUpdateRace(@PathVariable(value = "id", required = false) final Long id, @RequestBody Race race)
        throws URISyntaxException {
        log.debug("REST request to partial update Race partially : {}, {}", id, race);
        if (race.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, race.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Race> result = raceService.partialUpdate(race);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, race.getId().toString())
        );
    }

    /**
     * {@code GET  /races} : get all the races.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of races in body.
     */
    @GetMapping("/races")
    public ResponseEntity<List<Race>> getAllRaces(RaceCriteria criteria) {
        log.debug("REST request to get Races by criteria: {}", criteria);
        List<Race> entityList = raceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /races/count} : count all the races.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/races/count")
    public ResponseEntity<Long> countRaces(RaceCriteria criteria) {
        log.debug("REST request to count Races by criteria: {}", criteria);
        return ResponseEntity.ok().body(raceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /races/:id} : get the "id" race.
     *
     * @param id the id of the race to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the race, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/races/{id}")
    public ResponseEntity<Race> getRace(@PathVariable Long id) {
        log.debug("REST request to get Race : {}", id);
        Optional<Race> race = raceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(race);
    }

    /**
     * {@code DELETE  /races/:id} : delete the "id" race.
     *
     * @param id the id of the race to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/races/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        log.debug("REST request to delete Race : {}", id);
        raceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
