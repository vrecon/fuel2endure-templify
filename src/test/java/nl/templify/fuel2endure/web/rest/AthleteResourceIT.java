package nl.templify.fuel2endure.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import nl.templify.fuel2endure.IntegrationTest;
import nl.templify.fuel2endure.domain.Athlete;
import nl.templify.fuel2endure.domain.FuelSettings;
import nl.templify.fuel2endure.domain.Payment;
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.domain.Training;
import nl.templify.fuel2endure.domain.User;
import nl.templify.fuel2endure.domain.Voucher;
import nl.templify.fuel2endure.repository.AthleteRepository;
import nl.templify.fuel2endure.service.criteria.AthleteCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AthleteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AthleteResourceIT {

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;
    private static final Integer SMALLER_LENGTH = 1 - 1;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;
    private static final Float SMALLER_WEIGHT = 1F - 1F;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/athletes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAthleteMockMvc;

    private Athlete athlete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Athlete createEntity(EntityManager em) {
        Athlete athlete = new Athlete()
            .middleName(DEFAULT_MIDDLE_NAME)
            .length(DEFAULT_LENGTH)
            .weight(DEFAULT_WEIGHT)
            .status(DEFAULT_STATUS);
        return athlete;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Athlete createUpdatedEntity(EntityManager em) {
        Athlete athlete = new Athlete()
            .middleName(UPDATED_MIDDLE_NAME)
            .length(UPDATED_LENGTH)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS);
        return athlete;
    }

    @BeforeEach
    public void initTest() {
        athlete = createEntity(em);
    }

    @Test
    @Transactional
    void createAthlete() throws Exception {
        int databaseSizeBeforeCreate = athleteRepository.findAll().size();
        // Create the Athlete
        restAthleteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isCreated());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate + 1);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testAthlete.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testAthlete.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testAthlete.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createAthleteWithExistingId() throws Exception {
        // Create the Athlete with an existing ID
        athlete.setId(1L);

        int databaseSizeBeforeCreate = athleteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAthletes() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athlete.getId().intValue())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get the athlete
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL_ID, athlete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(athlete.getId().intValue()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getAthletesByIdFiltering() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        Long id = athlete.getId();

        defaultAthleteShouldBeFound("id.equals=" + id);
        defaultAthleteShouldNotBeFound("id.notEquals=" + id);

        defaultAthleteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAthleteShouldNotBeFound("id.greaterThan=" + id);

        defaultAthleteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAthleteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultAthleteShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the athleteList where middleName equals to UPDATED_MIDDLE_NAME
        defaultAthleteShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultAthleteShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the athleteList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultAthleteShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultAthleteShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the athleteList where middleName equals to UPDATED_MIDDLE_NAME
        defaultAthleteShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName is not null
        defaultAthleteShouldBeFound("middleName.specified=true");

        // Get all the athleteList where middleName is null
        defaultAthleteShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName contains DEFAULT_MIDDLE_NAME
        defaultAthleteShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the athleteList where middleName contains UPDATED_MIDDLE_NAME
        defaultAthleteShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllAthletesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultAthleteShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the athleteList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultAthleteShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length equals to DEFAULT_LENGTH
        defaultAthleteShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the athleteList where length equals to UPDATED_LENGTH
        defaultAthleteShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length not equals to DEFAULT_LENGTH
        defaultAthleteShouldNotBeFound("length.notEquals=" + DEFAULT_LENGTH);

        // Get all the athleteList where length not equals to UPDATED_LENGTH
        defaultAthleteShouldBeFound("length.notEquals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultAthleteShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the athleteList where length equals to UPDATED_LENGTH
        defaultAthleteShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length is not null
        defaultAthleteShouldBeFound("length.specified=true");

        // Get all the athleteList where length is null
        defaultAthleteShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length is greater than or equal to DEFAULT_LENGTH
        defaultAthleteShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the athleteList where length is greater than or equal to UPDATED_LENGTH
        defaultAthleteShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length is less than or equal to DEFAULT_LENGTH
        defaultAthleteShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the athleteList where length is less than or equal to SMALLER_LENGTH
        defaultAthleteShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length is less than DEFAULT_LENGTH
        defaultAthleteShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the athleteList where length is less than UPDATED_LENGTH
        defaultAthleteShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where length is greater than DEFAULT_LENGTH
        defaultAthleteShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the athleteList where length is greater than SMALLER_LENGTH
        defaultAthleteShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight equals to DEFAULT_WEIGHT
        defaultAthleteShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight equals to UPDATED_WEIGHT
        defaultAthleteShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight not equals to DEFAULT_WEIGHT
        defaultAthleteShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight not equals to UPDATED_WEIGHT
        defaultAthleteShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultAthleteShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the athleteList where weight equals to UPDATED_WEIGHT
        defaultAthleteShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight is not null
        defaultAthleteShouldBeFound("weight.specified=true");

        // Get all the athleteList where weight is null
        defaultAthleteShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultAthleteShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight is greater than or equal to UPDATED_WEIGHT
        defaultAthleteShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight is less than or equal to DEFAULT_WEIGHT
        defaultAthleteShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight is less than or equal to SMALLER_WEIGHT
        defaultAthleteShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight is less than DEFAULT_WEIGHT
        defaultAthleteShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight is less than UPDATED_WEIGHT
        defaultAthleteShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where weight is greater than DEFAULT_WEIGHT
        defaultAthleteShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the athleteList where weight is greater than SMALLER_WEIGHT
        defaultAthleteShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllAthletesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status equals to DEFAULT_STATUS
        defaultAthleteShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the athleteList where status equals to UPDATED_STATUS
        defaultAthleteShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAthletesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status not equals to DEFAULT_STATUS
        defaultAthleteShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the athleteList where status not equals to UPDATED_STATUS
        defaultAthleteShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAthletesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAthleteShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the athleteList where status equals to UPDATED_STATUS
        defaultAthleteShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAthletesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status is not null
        defaultAthleteShouldBeFound("status.specified=true");

        // Get all the athleteList where status is null
        defaultAthleteShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAthletesByStatusContainsSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status contains DEFAULT_STATUS
        defaultAthleteShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the athleteList where status contains UPDATED_STATUS
        defaultAthleteShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAthletesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList where status does not contain DEFAULT_STATUS
        defaultAthleteShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the athleteList where status does not contain UPDATED_STATUS
        defaultAthleteShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAthletesByFuelSettingsIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        FuelSettings fuelSettings = FuelSettingsResourceIT.createEntity(em);
        em.persist(fuelSettings);
        em.flush();
        athlete.setFuelSettings(fuelSettings);
        athleteRepository.saveAndFlush(athlete);
        Long fuelSettingsId = fuelSettings.getId();

        // Get all the athleteList where fuelSettings equals to fuelSettingsId
        defaultAthleteShouldBeFound("fuelSettingsId.equals=" + fuelSettingsId);

        // Get all the athleteList where fuelSettings equals to (fuelSettingsId + 1)
        defaultAthleteShouldNotBeFound("fuelSettingsId.equals=" + (fuelSettingsId + 1));
    }

    @Test
    @Transactional
    void getAllAthletesByInternalUserIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        User internalUser = UserResourceIT.createEntity(em);
        em.persist(internalUser);
        em.flush();
        athlete.setInternalUser(internalUser);
        athleteRepository.saveAndFlush(athlete);
        Long internalUserId = internalUser.getId();

        // Get all the athleteList where internalUser equals to internalUserId
        defaultAthleteShouldBeFound("internalUserId.equals=" + internalUserId);

        // Get all the athleteList where internalUser equals to (internalUserId + 1)
        defaultAthleteShouldNotBeFound("internalUserId.equals=" + (internalUserId + 1));
    }

    @Test
    @Transactional
    void getAllAthletesByRaceIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        Race race = RaceResourceIT.createEntity(em);
        em.persist(race);
        em.flush();
        athlete.addRace(race);
        athleteRepository.saveAndFlush(athlete);
        Long raceId = race.getId();

        // Get all the athleteList where race equals to raceId
        defaultAthleteShouldBeFound("raceId.equals=" + raceId);

        // Get all the athleteList where race equals to (raceId + 1)
        defaultAthleteShouldNotBeFound("raceId.equals=" + (raceId + 1));
    }

    @Test
    @Transactional
    void getAllAthletesByTrainingIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        Training training = TrainingResourceIT.createEntity(em);
        em.persist(training);
        em.flush();
        athlete.addTraining(training);
        athleteRepository.saveAndFlush(athlete);
        Long trainingId = training.getId();

        // Get all the athleteList where training equals to trainingId
        defaultAthleteShouldBeFound("trainingId.equals=" + trainingId);

        // Get all the athleteList where training equals to (trainingId + 1)
        defaultAthleteShouldNotBeFound("trainingId.equals=" + (trainingId + 1));
    }

    @Test
    @Transactional
    void getAllAthletesByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        athlete.addPayment(payment);
        athleteRepository.saveAndFlush(athlete);
        Long paymentId = payment.getId();

        // Get all the athleteList where payment equals to paymentId
        defaultAthleteShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the athleteList where payment equals to (paymentId + 1)
        defaultAthleteShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    @Test
    @Transactional
    void getAllAthletesByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        Voucher voucher = VoucherResourceIT.createEntity(em);
        em.persist(voucher);
        em.flush();
        athlete.setVoucher(voucher);
        athleteRepository.saveAndFlush(athlete);
        Long voucherId = voucher.getId();

        // Get all the athleteList where voucher equals to voucherId
        defaultAthleteShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the athleteList where voucher equals to (voucherId + 1)
        defaultAthleteShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAthleteShouldBeFound(String filter) throws Exception {
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athlete.getId().intValue())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAthleteShouldNotBeFound(String filter) throws Exception {
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAthleteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAthlete() throws Exception {
        // Get the athlete
        restAthleteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Update the athlete
        Athlete updatedAthlete = athleteRepository.findById(athlete.getId()).get();
        // Disconnect from session so that the updates on updatedAthlete are not directly saved in db
        em.detach(updatedAthlete);
        updatedAthlete.middleName(UPDATED_MIDDLE_NAME).length(UPDATED_LENGTH).weight(UPDATED_WEIGHT).status(UPDATED_STATUS);

        restAthleteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAthlete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAthlete))
            )
            .andExpect(status().isOk());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testAthlete.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testAthlete.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testAthlete.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, athlete.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(athlete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(athlete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAthleteWithPatch() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Update the athlete using partial update
        Athlete partialUpdatedAthlete = new Athlete();
        partialUpdatedAthlete.setId(athlete.getId());

        partialUpdatedAthlete.middleName(UPDATED_MIDDLE_NAME).length(UPDATED_LENGTH);

        restAthleteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAthlete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAthlete))
            )
            .andExpect(status().isOk());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testAthlete.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testAthlete.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testAthlete.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAthleteWithPatch() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Update the athlete using partial update
        Athlete partialUpdatedAthlete = new Athlete();
        partialUpdatedAthlete.setId(athlete.getId());

        partialUpdatedAthlete.middleName(UPDATED_MIDDLE_NAME).length(UPDATED_LENGTH).weight(UPDATED_WEIGHT).status(UPDATED_STATUS);

        restAthleteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAthlete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAthlete))
            )
            .andExpect(status().isOk());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testAthlete.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testAthlete.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testAthlete.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, athlete.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(athlete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(athlete))
            )
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();
        athlete.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAthleteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        int databaseSizeBeforeDelete = athleteRepository.findAll().size();

        // Delete the athlete
        restAthleteMockMvc
            .perform(delete(ENTITY_API_URL_ID, athlete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
