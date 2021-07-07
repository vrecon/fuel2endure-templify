package nl.templify.fuel2endure.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import nl.templify.fuel2endure.IntegrationTest;
import nl.templify.fuel2endure.domain.Athlete;
import nl.templify.fuel2endure.domain.Training;
import nl.templify.fuel2endure.repository.TrainingRepository;
import nl.templify.fuel2endure.service.criteria.TrainingCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TRAINING_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_TYPE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final Integer DEFAULT_TRAINING_INTENSITY_CODE = 1;
    private static final Integer UPDATED_TRAINING_INTENSITY_CODE = 2;
    private static final Integer SMALLER_TRAINING_INTENSITY_CODE = 1 - 1;

    private static final Integer DEFAULT_TEMPERATURE = 1;
    private static final Integer UPDATED_TEMPERATURE = 2;
    private static final Integer SMALLER_TEMPERATURE = 1 - 1;

    private static final Float DEFAULT_WEIGHT_BEFORE = 1F;
    private static final Float UPDATED_WEIGHT_BEFORE = 2F;
    private static final Float SMALLER_WEIGHT_BEFORE = 1F - 1F;

    private static final Float DEFAULT_WEIGHT_AFTER = 1F;
    private static final Float UPDATED_WEIGHT_AFTER = 2F;
    private static final Float SMALLER_WEIGHT_AFTER = 1F - 1F;

    private static final Integer DEFAULT_DRUNK = 1;
    private static final Integer UPDATED_DRUNK = 2;
    private static final Integer SMALLER_DRUNK = 1 - 1;

    private static final Integer DEFAULT_EATEN = 1;
    private static final Integer UPDATED_EATEN = 2;
    private static final Integer SMALLER_EATEN = 1 - 1;

    private static final Float DEFAULT_MOISTURE_LOSS_PERCENTAGE = 1F;
    private static final Float UPDATED_MOISTURE_LOSS_PERCENTAGE = 2F;
    private static final Float SMALLER_MOISTURE_LOSS_PERCENTAGE = 1F - 1F;

    private static final Float DEFAULT_MOISTURE_LOSS_PER_HOUR = 1F;
    private static final Float UPDATED_MOISTURE_LOSS_PER_HOUR = 2F;
    private static final Float SMALLER_MOISTURE_LOSS_PER_HOUR = 1F - 1F;

    private static final Float DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR = 1F;
    private static final Float UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR = 2F;
    private static final Float SMALLER_DEFAULT_MOISTER_LOSS_PER_HOUR = 1F - 1F;

    private static final Float DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR = 1F;
    private static final Float UPDATED_DELTA_MOISTER_LOSS_PER_HOUR = 2F;
    private static final Float SMALLER_DELTA_MOISTER_LOSS_PER_HOUR = 1F - 1F;

    private static final Boolean DEFAULT_EXCLUDED = false;
    private static final Boolean UPDATED_EXCLUDED = true;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_CARBO_DRANK = 1;
    private static final Integer UPDATED_CARBO_DRANK = 2;
    private static final Integer SMALLER_CARBO_DRANK = 1 - 1;

    private static final String ENTITY_API_URL = "/api/trainings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .date(DEFAULT_DATE)
            .trainingTypeCode(DEFAULT_TRAINING_TYPE_CODE)
            .duration(DEFAULT_DURATION)
            .trainingIntensityCode(DEFAULT_TRAINING_INTENSITY_CODE)
            .temperature(DEFAULT_TEMPERATURE)
            .weightBefore(DEFAULT_WEIGHT_BEFORE)
            .weightAfter(DEFAULT_WEIGHT_AFTER)
            .drunk(DEFAULT_DRUNK)
            .eaten(DEFAULT_EATEN)
            .moistureLossPercentage(DEFAULT_MOISTURE_LOSS_PERCENTAGE)
            .moistureLossPerHour(DEFAULT_MOISTURE_LOSS_PER_HOUR)
            .defaultMoisterLossPerHour(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR)
            .deltaMoisterLossPerHour(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR)
            .excluded(DEFAULT_EXCLUDED)
            .comments(DEFAULT_COMMENTS)
            .carboDrank(DEFAULT_CARBO_DRANK);
        return training;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .date(UPDATED_DATE)
            .trainingTypeCode(UPDATED_TRAINING_TYPE_CODE)
            .duration(UPDATED_DURATION)
            .trainingIntensityCode(UPDATED_TRAINING_INTENSITY_CODE)
            .temperature(UPDATED_TEMPERATURE)
            .weightBefore(UPDATED_WEIGHT_BEFORE)
            .weightAfter(UPDATED_WEIGHT_AFTER)
            .drunk(UPDATED_DRUNK)
            .eaten(UPDATED_EATEN)
            .moistureLossPercentage(UPDATED_MOISTURE_LOSS_PERCENTAGE)
            .moistureLossPerHour(UPDATED_MOISTURE_LOSS_PER_HOUR)
            .defaultMoisterLossPerHour(UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR)
            .deltaMoisterLossPerHour(UPDATED_DELTA_MOISTER_LOSS_PER_HOUR)
            .excluded(UPDATED_EXCLUDED)
            .comments(UPDATED_COMMENTS)
            .carboDrank(UPDATED_CARBO_DRANK);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        restTrainingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTraining.getTrainingTypeCode()).isEqualTo(DEFAULT_TRAINING_TYPE_CODE);
        assertThat(testTraining.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTraining.getTrainingIntensityCode()).isEqualTo(DEFAULT_TRAINING_INTENSITY_CODE);
        assertThat(testTraining.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testTraining.getWeightBefore()).isEqualTo(DEFAULT_WEIGHT_BEFORE);
        assertThat(testTraining.getWeightAfter()).isEqualTo(DEFAULT_WEIGHT_AFTER);
        assertThat(testTraining.getDrunk()).isEqualTo(DEFAULT_DRUNK);
        assertThat(testTraining.getEaten()).isEqualTo(DEFAULT_EATEN);
        assertThat(testTraining.getMoistureLossPercentage()).isEqualTo(DEFAULT_MOISTURE_LOSS_PERCENTAGE);
        assertThat(testTraining.getMoistureLossPerHour()).isEqualTo(DEFAULT_MOISTURE_LOSS_PER_HOUR);
        assertThat(testTraining.getDefaultMoisterLossPerHour()).isEqualTo(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getDeltaMoisterLossPerHour()).isEqualTo(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getExcluded()).isEqualTo(DEFAULT_EXCLUDED);
        assertThat(testTraining.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testTraining.getCarboDrank()).isEqualTo(DEFAULT_CARBO_DRANK);
    }

    @Test
    @Transactional
    void createTrainingWithExistingId() throws Exception {
        // Create the Training with an existing ID
        training.setId(1L);

        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].trainingTypeCode").value(hasItem(DEFAULT_TRAINING_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].trainingIntensityCode").value(hasItem(DEFAULT_TRAINING_INTENSITY_CODE)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].weightBefore").value(hasItem(DEFAULT_WEIGHT_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].weightAfter").value(hasItem(DEFAULT_WEIGHT_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].drunk").value(hasItem(DEFAULT_DRUNK)))
            .andExpect(jsonPath("$.[*].eaten").value(hasItem(DEFAULT_EATEN)))
            .andExpect(jsonPath("$.[*].moistureLossPercentage").value(hasItem(DEFAULT_MOISTURE_LOSS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].moistureLossPerHour").value(hasItem(DEFAULT_MOISTURE_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].defaultMoisterLossPerHour").value(hasItem(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaMoisterLossPerHour").value(hasItem(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].excluded").value(hasItem(DEFAULT_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].carboDrank").value(hasItem(DEFAULT_CARBO_DRANK)));
    }

    @Test
    @Transactional
    void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL_ID, training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.trainingTypeCode").value(DEFAULT_TRAINING_TYPE_CODE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.trainingIntensityCode").value(DEFAULT_TRAINING_INTENSITY_CODE))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE))
            .andExpect(jsonPath("$.weightBefore").value(DEFAULT_WEIGHT_BEFORE.doubleValue()))
            .andExpect(jsonPath("$.weightAfter").value(DEFAULT_WEIGHT_AFTER.doubleValue()))
            .andExpect(jsonPath("$.drunk").value(DEFAULT_DRUNK))
            .andExpect(jsonPath("$.eaten").value(DEFAULT_EATEN))
            .andExpect(jsonPath("$.moistureLossPercentage").value(DEFAULT_MOISTURE_LOSS_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.moistureLossPerHour").value(DEFAULT_MOISTURE_LOSS_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.defaultMoisterLossPerHour").value(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.deltaMoisterLossPerHour").value(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.excluded").value(DEFAULT_EXCLUDED.booleanValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.carboDrank").value(DEFAULT_CARBO_DRANK));
    }

    @Test
    @Transactional
    void getTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        Long id = training.getId();

        defaultTrainingShouldBeFound("id.equals=" + id);
        defaultTrainingShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date equals to DEFAULT_DATE
        defaultTrainingShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the trainingList where date equals to UPDATED_DATE
        defaultTrainingShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date not equals to DEFAULT_DATE
        defaultTrainingShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the trainingList where date not equals to UPDATED_DATE
        defaultTrainingShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date in DEFAULT_DATE or UPDATED_DATE
        defaultTrainingShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the trainingList where date equals to UPDATED_DATE
        defaultTrainingShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date is not null
        defaultTrainingShouldBeFound("date.specified=true");

        // Get all the trainingList where date is null
        defaultTrainingShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date is greater than or equal to DEFAULT_DATE
        defaultTrainingShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the trainingList where date is greater than or equal to UPDATED_DATE
        defaultTrainingShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date is less than or equal to DEFAULT_DATE
        defaultTrainingShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the trainingList where date is less than or equal to SMALLER_DATE
        defaultTrainingShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date is less than DEFAULT_DATE
        defaultTrainingShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the trainingList where date is less than UPDATED_DATE
        defaultTrainingShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where date is greater than DEFAULT_DATE
        defaultTrainingShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the trainingList where date is greater than SMALLER_DATE
        defaultTrainingShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode equals to DEFAULT_TRAINING_TYPE_CODE
        defaultTrainingShouldBeFound("trainingTypeCode.equals=" + DEFAULT_TRAINING_TYPE_CODE);

        // Get all the trainingList where trainingTypeCode equals to UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldNotBeFound("trainingTypeCode.equals=" + UPDATED_TRAINING_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode not equals to DEFAULT_TRAINING_TYPE_CODE
        defaultTrainingShouldNotBeFound("trainingTypeCode.notEquals=" + DEFAULT_TRAINING_TYPE_CODE);

        // Get all the trainingList where trainingTypeCode not equals to UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldBeFound("trainingTypeCode.notEquals=" + UPDATED_TRAINING_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode in DEFAULT_TRAINING_TYPE_CODE or UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldBeFound("trainingTypeCode.in=" + DEFAULT_TRAINING_TYPE_CODE + "," + UPDATED_TRAINING_TYPE_CODE);

        // Get all the trainingList where trainingTypeCode equals to UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldNotBeFound("trainingTypeCode.in=" + UPDATED_TRAINING_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode is not null
        defaultTrainingShouldBeFound("trainingTypeCode.specified=true");

        // Get all the trainingList where trainingTypeCode is null
        defaultTrainingShouldNotBeFound("trainingTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode contains DEFAULT_TRAINING_TYPE_CODE
        defaultTrainingShouldBeFound("trainingTypeCode.contains=" + DEFAULT_TRAINING_TYPE_CODE);

        // Get all the trainingList where trainingTypeCode contains UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldNotBeFound("trainingTypeCode.contains=" + UPDATED_TRAINING_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeCode does not contain DEFAULT_TRAINING_TYPE_CODE
        defaultTrainingShouldNotBeFound("trainingTypeCode.doesNotContain=" + DEFAULT_TRAINING_TYPE_CODE);

        // Get all the trainingList where trainingTypeCode does not contain UPDATED_TRAINING_TYPE_CODE
        defaultTrainingShouldBeFound("trainingTypeCode.doesNotContain=" + UPDATED_TRAINING_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration equals to DEFAULT_DURATION
        defaultTrainingShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the trainingList where duration equals to UPDATED_DURATION
        defaultTrainingShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration not equals to DEFAULT_DURATION
        defaultTrainingShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the trainingList where duration not equals to UPDATED_DURATION
        defaultTrainingShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultTrainingShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the trainingList where duration equals to UPDATED_DURATION
        defaultTrainingShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration is not null
        defaultTrainingShouldBeFound("duration.specified=true");

        // Get all the trainingList where duration is null
        defaultTrainingShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration is greater than or equal to DEFAULT_DURATION
        defaultTrainingShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the trainingList where duration is greater than or equal to UPDATED_DURATION
        defaultTrainingShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration is less than or equal to DEFAULT_DURATION
        defaultTrainingShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the trainingList where duration is less than or equal to SMALLER_DURATION
        defaultTrainingShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration is less than DEFAULT_DURATION
        defaultTrainingShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the trainingList where duration is less than UPDATED_DURATION
        defaultTrainingShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where duration is greater than DEFAULT_DURATION
        defaultTrainingShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the trainingList where duration is greater than SMALLER_DURATION
        defaultTrainingShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode equals to DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.equals=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode equals to UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.equals=" + UPDATED_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode not equals to DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.notEquals=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode not equals to UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.notEquals=" + UPDATED_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode in DEFAULT_TRAINING_INTENSITY_CODE or UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.in=" + DEFAULT_TRAINING_INTENSITY_CODE + "," + UPDATED_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode equals to UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.in=" + UPDATED_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode is not null
        defaultTrainingShouldBeFound("trainingIntensityCode.specified=true");

        // Get all the trainingList where trainingIntensityCode is null
        defaultTrainingShouldNotBeFound("trainingIntensityCode.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode is greater than or equal to DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.greaterThanOrEqual=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode is greater than or equal to UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.greaterThanOrEqual=" + UPDATED_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode is less than or equal to DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.lessThanOrEqual=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode is less than or equal to SMALLER_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.lessThanOrEqual=" + SMALLER_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode is less than DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.lessThan=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode is less than UPDATED_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.lessThan=" + UPDATED_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingIntensityCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingIntensityCode is greater than DEFAULT_TRAINING_INTENSITY_CODE
        defaultTrainingShouldNotBeFound("trainingIntensityCode.greaterThan=" + DEFAULT_TRAINING_INTENSITY_CODE);

        // Get all the trainingList where trainingIntensityCode is greater than SMALLER_TRAINING_INTENSITY_CODE
        defaultTrainingShouldBeFound("trainingIntensityCode.greaterThan=" + SMALLER_TRAINING_INTENSITY_CODE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature equals to DEFAULT_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature equals to UPDATED_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature not equals to DEFAULT_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature not equals to UPDATED_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the trainingList where temperature equals to UPDATED_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature is not null
        defaultTrainingShouldBeFound("temperature.specified=true");

        // Get all the trainingList where temperature is null
        defaultTrainingShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature is less than DEFAULT_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature is less than UPDATED_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where temperature is greater than DEFAULT_TEMPERATURE
        defaultTrainingShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the trainingList where temperature is greater than SMALLER_TEMPERATURE
        defaultTrainingShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore equals to DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.equals=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore equals to UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.equals=" + UPDATED_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore not equals to DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.notEquals=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore not equals to UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.notEquals=" + UPDATED_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore in DEFAULT_WEIGHT_BEFORE or UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.in=" + DEFAULT_WEIGHT_BEFORE + "," + UPDATED_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore equals to UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.in=" + UPDATED_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore is not null
        defaultTrainingShouldBeFound("weightBefore.specified=true");

        // Get all the trainingList where weightBefore is null
        defaultTrainingShouldNotBeFound("weightBefore.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore is greater than or equal to DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.greaterThanOrEqual=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore is greater than or equal to UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.greaterThanOrEqual=" + UPDATED_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore is less than or equal to DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.lessThanOrEqual=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore is less than or equal to SMALLER_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.lessThanOrEqual=" + SMALLER_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore is less than DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.lessThan=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore is less than UPDATED_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.lessThan=" + UPDATED_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightBeforeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightBefore is greater than DEFAULT_WEIGHT_BEFORE
        defaultTrainingShouldNotBeFound("weightBefore.greaterThan=" + DEFAULT_WEIGHT_BEFORE);

        // Get all the trainingList where weightBefore is greater than SMALLER_WEIGHT_BEFORE
        defaultTrainingShouldBeFound("weightBefore.greaterThan=" + SMALLER_WEIGHT_BEFORE);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter equals to DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.equals=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter equals to UPDATED_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.equals=" + UPDATED_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter not equals to DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.notEquals=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter not equals to UPDATED_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.notEquals=" + UPDATED_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter in DEFAULT_WEIGHT_AFTER or UPDATED_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.in=" + DEFAULT_WEIGHT_AFTER + "," + UPDATED_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter equals to UPDATED_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.in=" + UPDATED_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter is not null
        defaultTrainingShouldBeFound("weightAfter.specified=true");

        // Get all the trainingList where weightAfter is null
        defaultTrainingShouldNotBeFound("weightAfter.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter is greater than or equal to DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.greaterThanOrEqual=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter is greater than or equal to UPDATED_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.greaterThanOrEqual=" + UPDATED_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter is less than or equal to DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.lessThanOrEqual=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter is less than or equal to SMALLER_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.lessThanOrEqual=" + SMALLER_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter is less than DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.lessThan=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter is less than UPDATED_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.lessThan=" + UPDATED_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByWeightAfterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where weightAfter is greater than DEFAULT_WEIGHT_AFTER
        defaultTrainingShouldNotBeFound("weightAfter.greaterThan=" + DEFAULT_WEIGHT_AFTER);

        // Get all the trainingList where weightAfter is greater than SMALLER_WEIGHT_AFTER
        defaultTrainingShouldBeFound("weightAfter.greaterThan=" + SMALLER_WEIGHT_AFTER);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk equals to DEFAULT_DRUNK
        defaultTrainingShouldBeFound("drunk.equals=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk equals to UPDATED_DRUNK
        defaultTrainingShouldNotBeFound("drunk.equals=" + UPDATED_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk not equals to DEFAULT_DRUNK
        defaultTrainingShouldNotBeFound("drunk.notEquals=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk not equals to UPDATED_DRUNK
        defaultTrainingShouldBeFound("drunk.notEquals=" + UPDATED_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk in DEFAULT_DRUNK or UPDATED_DRUNK
        defaultTrainingShouldBeFound("drunk.in=" + DEFAULT_DRUNK + "," + UPDATED_DRUNK);

        // Get all the trainingList where drunk equals to UPDATED_DRUNK
        defaultTrainingShouldNotBeFound("drunk.in=" + UPDATED_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk is not null
        defaultTrainingShouldBeFound("drunk.specified=true");

        // Get all the trainingList where drunk is null
        defaultTrainingShouldNotBeFound("drunk.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk is greater than or equal to DEFAULT_DRUNK
        defaultTrainingShouldBeFound("drunk.greaterThanOrEqual=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk is greater than or equal to UPDATED_DRUNK
        defaultTrainingShouldNotBeFound("drunk.greaterThanOrEqual=" + UPDATED_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk is less than or equal to DEFAULT_DRUNK
        defaultTrainingShouldBeFound("drunk.lessThanOrEqual=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk is less than or equal to SMALLER_DRUNK
        defaultTrainingShouldNotBeFound("drunk.lessThanOrEqual=" + SMALLER_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk is less than DEFAULT_DRUNK
        defaultTrainingShouldNotBeFound("drunk.lessThan=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk is less than UPDATED_DRUNK
        defaultTrainingShouldBeFound("drunk.lessThan=" + UPDATED_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByDrunkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where drunk is greater than DEFAULT_DRUNK
        defaultTrainingShouldNotBeFound("drunk.greaterThan=" + DEFAULT_DRUNK);

        // Get all the trainingList where drunk is greater than SMALLER_DRUNK
        defaultTrainingShouldBeFound("drunk.greaterThan=" + SMALLER_DRUNK);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten equals to DEFAULT_EATEN
        defaultTrainingShouldBeFound("eaten.equals=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten equals to UPDATED_EATEN
        defaultTrainingShouldNotBeFound("eaten.equals=" + UPDATED_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten not equals to DEFAULT_EATEN
        defaultTrainingShouldNotBeFound("eaten.notEquals=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten not equals to UPDATED_EATEN
        defaultTrainingShouldBeFound("eaten.notEquals=" + UPDATED_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten in DEFAULT_EATEN or UPDATED_EATEN
        defaultTrainingShouldBeFound("eaten.in=" + DEFAULT_EATEN + "," + UPDATED_EATEN);

        // Get all the trainingList where eaten equals to UPDATED_EATEN
        defaultTrainingShouldNotBeFound("eaten.in=" + UPDATED_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten is not null
        defaultTrainingShouldBeFound("eaten.specified=true");

        // Get all the trainingList where eaten is null
        defaultTrainingShouldNotBeFound("eaten.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten is greater than or equal to DEFAULT_EATEN
        defaultTrainingShouldBeFound("eaten.greaterThanOrEqual=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten is greater than or equal to UPDATED_EATEN
        defaultTrainingShouldNotBeFound("eaten.greaterThanOrEqual=" + UPDATED_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten is less than or equal to DEFAULT_EATEN
        defaultTrainingShouldBeFound("eaten.lessThanOrEqual=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten is less than or equal to SMALLER_EATEN
        defaultTrainingShouldNotBeFound("eaten.lessThanOrEqual=" + SMALLER_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten is less than DEFAULT_EATEN
        defaultTrainingShouldNotBeFound("eaten.lessThan=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten is less than UPDATED_EATEN
        defaultTrainingShouldBeFound("eaten.lessThan=" + UPDATED_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByEatenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where eaten is greater than DEFAULT_EATEN
        defaultTrainingShouldNotBeFound("eaten.greaterThan=" + DEFAULT_EATEN);

        // Get all the trainingList where eaten is greater than SMALLER_EATEN
        defaultTrainingShouldBeFound("eaten.greaterThan=" + SMALLER_EATEN);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage equals to DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.equals=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage equals to UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.equals=" + UPDATED_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage not equals to DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.notEquals=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage not equals to UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.notEquals=" + UPDATED_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage in DEFAULT_MOISTURE_LOSS_PERCENTAGE or UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound(
            "moistureLossPercentage.in=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE + "," + UPDATED_MOISTURE_LOSS_PERCENTAGE
        );

        // Get all the trainingList where moistureLossPercentage equals to UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.in=" + UPDATED_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage is not null
        defaultTrainingShouldBeFound("moistureLossPercentage.specified=true");

        // Get all the trainingList where moistureLossPercentage is null
        defaultTrainingShouldNotBeFound("moistureLossPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage is greater than or equal to DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.greaterThanOrEqual=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage is greater than or equal to UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.greaterThanOrEqual=" + UPDATED_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage is less than or equal to DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.lessThanOrEqual=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage is less than or equal to SMALLER_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.lessThanOrEqual=" + SMALLER_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage is less than DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.lessThan=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage is less than UPDATED_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.lessThan=" + UPDATED_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPercentage is greater than DEFAULT_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldNotBeFound("moistureLossPercentage.greaterThan=" + DEFAULT_MOISTURE_LOSS_PERCENTAGE);

        // Get all the trainingList where moistureLossPercentage is greater than SMALLER_MOISTURE_LOSS_PERCENTAGE
        defaultTrainingShouldBeFound("moistureLossPercentage.greaterThan=" + SMALLER_MOISTURE_LOSS_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour equals to DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.equals=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour equals to UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.equals=" + UPDATED_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour not equals to DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.notEquals=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour not equals to UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.notEquals=" + UPDATED_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour in DEFAULT_MOISTURE_LOSS_PER_HOUR or UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.in=" + DEFAULT_MOISTURE_LOSS_PER_HOUR + "," + UPDATED_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour equals to UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.in=" + UPDATED_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour is not null
        defaultTrainingShouldBeFound("moistureLossPerHour.specified=true");

        // Get all the trainingList where moistureLossPerHour is null
        defaultTrainingShouldNotBeFound("moistureLossPerHour.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour is greater than or equal to DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.greaterThanOrEqual=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour is greater than or equal to UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.greaterThanOrEqual=" + UPDATED_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour is less than or equal to DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.lessThanOrEqual=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour is less than or equal to SMALLER_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.lessThanOrEqual=" + SMALLER_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour is less than DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.lessThan=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour is less than UPDATED_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.lessThan=" + UPDATED_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByMoistureLossPerHourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where moistureLossPerHour is greater than DEFAULT_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("moistureLossPerHour.greaterThan=" + DEFAULT_MOISTURE_LOSS_PER_HOUR);

        // Get all the trainingList where moistureLossPerHour is greater than SMALLER_MOISTURE_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("moistureLossPerHour.greaterThan=" + SMALLER_MOISTURE_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour equals to DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.equals=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour equals to UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.equals=" + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour not equals to DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.notEquals=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour not equals to UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.notEquals=" + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour in DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR or UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound(
            "defaultMoisterLossPerHour.in=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR + "," + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        );

        // Get all the trainingList where defaultMoisterLossPerHour equals to UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.in=" + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour is not null
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.specified=true");

        // Get all the trainingList where defaultMoisterLossPerHour is null
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour is greater than or equal to DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.greaterThanOrEqual=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour is greater than or equal to UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.greaterThanOrEqual=" + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour is less than or equal to DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.lessThanOrEqual=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour is less than or equal to SMALLER_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.lessThanOrEqual=" + SMALLER_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour is less than DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.lessThan=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour is less than UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.lessThan=" + UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDefaultMoisterLossPerHourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where defaultMoisterLossPerHour is greater than DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("defaultMoisterLossPerHour.greaterThan=" + DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where defaultMoisterLossPerHour is greater than SMALLER_DEFAULT_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("defaultMoisterLossPerHour.greaterThan=" + SMALLER_DEFAULT_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour equals to DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.equals=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour equals to UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.equals=" + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour not equals to DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.notEquals=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour not equals to UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.notEquals=" + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour in DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR or UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound(
            "deltaMoisterLossPerHour.in=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR + "," + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        );

        // Get all the trainingList where deltaMoisterLossPerHour equals to UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.in=" + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour is not null
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.specified=true");

        // Get all the trainingList where deltaMoisterLossPerHour is null
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour is greater than or equal to DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.greaterThanOrEqual=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour is greater than or equal to UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.greaterThanOrEqual=" + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour is less than or equal to DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.lessThanOrEqual=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour is less than or equal to SMALLER_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.lessThanOrEqual=" + SMALLER_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour is less than DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.lessThan=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour is less than UPDATED_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.lessThan=" + UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByDeltaMoisterLossPerHourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where deltaMoisterLossPerHour is greater than DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldNotBeFound("deltaMoisterLossPerHour.greaterThan=" + DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);

        // Get all the trainingList where deltaMoisterLossPerHour is greater than SMALLER_DELTA_MOISTER_LOSS_PER_HOUR
        defaultTrainingShouldBeFound("deltaMoisterLossPerHour.greaterThan=" + SMALLER_DELTA_MOISTER_LOSS_PER_HOUR);
    }

    @Test
    @Transactional
    void getAllTrainingsByExcludedIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where excluded equals to DEFAULT_EXCLUDED
        defaultTrainingShouldBeFound("excluded.equals=" + DEFAULT_EXCLUDED);

        // Get all the trainingList where excluded equals to UPDATED_EXCLUDED
        defaultTrainingShouldNotBeFound("excluded.equals=" + UPDATED_EXCLUDED);
    }

    @Test
    @Transactional
    void getAllTrainingsByExcludedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where excluded not equals to DEFAULT_EXCLUDED
        defaultTrainingShouldNotBeFound("excluded.notEquals=" + DEFAULT_EXCLUDED);

        // Get all the trainingList where excluded not equals to UPDATED_EXCLUDED
        defaultTrainingShouldBeFound("excluded.notEquals=" + UPDATED_EXCLUDED);
    }

    @Test
    @Transactional
    void getAllTrainingsByExcludedIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where excluded in DEFAULT_EXCLUDED or UPDATED_EXCLUDED
        defaultTrainingShouldBeFound("excluded.in=" + DEFAULT_EXCLUDED + "," + UPDATED_EXCLUDED);

        // Get all the trainingList where excluded equals to UPDATED_EXCLUDED
        defaultTrainingShouldNotBeFound("excluded.in=" + UPDATED_EXCLUDED);
    }

    @Test
    @Transactional
    void getAllTrainingsByExcludedIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where excluded is not null
        defaultTrainingShouldBeFound("excluded.specified=true");

        // Get all the trainingList where excluded is null
        defaultTrainingShouldNotBeFound("excluded.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments equals to DEFAULT_COMMENTS
        defaultTrainingShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the trainingList where comments equals to UPDATED_COMMENTS
        defaultTrainingShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments not equals to DEFAULT_COMMENTS
        defaultTrainingShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the trainingList where comments not equals to UPDATED_COMMENTS
        defaultTrainingShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultTrainingShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the trainingList where comments equals to UPDATED_COMMENTS
        defaultTrainingShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments is not null
        defaultTrainingShouldBeFound("comments.specified=true");

        // Get all the trainingList where comments is null
        defaultTrainingShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments contains DEFAULT_COMMENTS
        defaultTrainingShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the trainingList where comments contains UPDATED_COMMENTS
        defaultTrainingShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllTrainingsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where comments does not contain DEFAULT_COMMENTS
        defaultTrainingShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the trainingList where comments does not contain UPDATED_COMMENTS
        defaultTrainingShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank equals to DEFAULT_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.equals=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank equals to UPDATED_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.equals=" + UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank not equals to DEFAULT_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.notEquals=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank not equals to UPDATED_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.notEquals=" + UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank in DEFAULT_CARBO_DRANK or UPDATED_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.in=" + DEFAULT_CARBO_DRANK + "," + UPDATED_CARBO_DRANK);

        // Get all the trainingList where carboDrank equals to UPDATED_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.in=" + UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank is not null
        defaultTrainingShouldBeFound("carboDrank.specified=true");

        // Get all the trainingList where carboDrank is null
        defaultTrainingShouldNotBeFound("carboDrank.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank is greater than or equal to DEFAULT_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.greaterThanOrEqual=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank is greater than or equal to UPDATED_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.greaterThanOrEqual=" + UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank is less than or equal to DEFAULT_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.lessThanOrEqual=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank is less than or equal to SMALLER_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.lessThanOrEqual=" + SMALLER_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank is less than DEFAULT_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.lessThan=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank is less than UPDATED_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.lessThan=" + UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByCarboDrankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where carboDrank is greater than DEFAULT_CARBO_DRANK
        defaultTrainingShouldNotBeFound("carboDrank.greaterThan=" + DEFAULT_CARBO_DRANK);

        // Get all the trainingList where carboDrank is greater than SMALLER_CARBO_DRANK
        defaultTrainingShouldBeFound("carboDrank.greaterThan=" + SMALLER_CARBO_DRANK);
    }

    @Test
    @Transactional
    void getAllTrainingsByAthleteIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        Athlete athlete = AthleteResourceIT.createEntity(em);
        em.persist(athlete);
        em.flush();
        training.setAthlete(athlete);
        trainingRepository.saveAndFlush(training);
        Long athleteId = athlete.getId();

        // Get all the trainingList where athlete equals to athleteId
        defaultTrainingShouldBeFound("athleteId.equals=" + athleteId);

        // Get all the trainingList where athlete equals to (athleteId + 1)
        defaultTrainingShouldNotBeFound("athleteId.equals=" + (athleteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingShouldBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].trainingTypeCode").value(hasItem(DEFAULT_TRAINING_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].trainingIntensityCode").value(hasItem(DEFAULT_TRAINING_INTENSITY_CODE)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].weightBefore").value(hasItem(DEFAULT_WEIGHT_BEFORE.doubleValue())))
            .andExpect(jsonPath("$.[*].weightAfter").value(hasItem(DEFAULT_WEIGHT_AFTER.doubleValue())))
            .andExpect(jsonPath("$.[*].drunk").value(hasItem(DEFAULT_DRUNK)))
            .andExpect(jsonPath("$.[*].eaten").value(hasItem(DEFAULT_EATEN)))
            .andExpect(jsonPath("$.[*].moistureLossPercentage").value(hasItem(DEFAULT_MOISTURE_LOSS_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].moistureLossPerHour").value(hasItem(DEFAULT_MOISTURE_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].defaultMoisterLossPerHour").value(hasItem(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].deltaMoisterLossPerHour").value(hasItem(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].excluded").value(hasItem(DEFAULT_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].carboDrank").value(hasItem(DEFAULT_CARBO_DRANK)));

        // Check, that the count call also returns 1
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingShouldNotBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .date(UPDATED_DATE)
            .trainingTypeCode(UPDATED_TRAINING_TYPE_CODE)
            .duration(UPDATED_DURATION)
            .trainingIntensityCode(UPDATED_TRAINING_INTENSITY_CODE)
            .temperature(UPDATED_TEMPERATURE)
            .weightBefore(UPDATED_WEIGHT_BEFORE)
            .weightAfter(UPDATED_WEIGHT_AFTER)
            .drunk(UPDATED_DRUNK)
            .eaten(UPDATED_EATEN)
            .moistureLossPercentage(UPDATED_MOISTURE_LOSS_PERCENTAGE)
            .moistureLossPerHour(UPDATED_MOISTURE_LOSS_PER_HOUR)
            .defaultMoisterLossPerHour(UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR)
            .deltaMoisterLossPerHour(UPDATED_DELTA_MOISTER_LOSS_PER_HOUR)
            .excluded(UPDATED_EXCLUDED)
            .comments(UPDATED_COMMENTS)
            .carboDrank(UPDATED_CARBO_DRANK);

        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTraining.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTraining.getTrainingTypeCode()).isEqualTo(UPDATED_TRAINING_TYPE_CODE);
        assertThat(testTraining.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTraining.getTrainingIntensityCode()).isEqualTo(UPDATED_TRAINING_INTENSITY_CODE);
        assertThat(testTraining.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testTraining.getWeightBefore()).isEqualTo(UPDATED_WEIGHT_BEFORE);
        assertThat(testTraining.getWeightAfter()).isEqualTo(UPDATED_WEIGHT_AFTER);
        assertThat(testTraining.getDrunk()).isEqualTo(UPDATED_DRUNK);
        assertThat(testTraining.getEaten()).isEqualTo(UPDATED_EATEN);
        assertThat(testTraining.getMoistureLossPercentage()).isEqualTo(UPDATED_MOISTURE_LOSS_PERCENTAGE);
        assertThat(testTraining.getMoistureLossPerHour()).isEqualTo(UPDATED_MOISTURE_LOSS_PER_HOUR);
        assertThat(testTraining.getDefaultMoisterLossPerHour()).isEqualTo(UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getDeltaMoisterLossPerHour()).isEqualTo(UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testTraining.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTraining.getCarboDrank()).isEqualTo(UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void putNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, training.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(training))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(training))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .date(UPDATED_DATE)
            .trainingTypeCode(UPDATED_TRAINING_TYPE_CODE)
            .trainingIntensityCode(UPDATED_TRAINING_INTENSITY_CODE)
            .temperature(UPDATED_TEMPERATURE)
            .weightAfter(UPDATED_WEIGHT_AFTER)
            .eaten(UPDATED_EATEN)
            .moistureLossPercentage(UPDATED_MOISTURE_LOSS_PERCENTAGE)
            .moistureLossPerHour(UPDATED_MOISTURE_LOSS_PER_HOUR)
            .excluded(UPDATED_EXCLUDED)
            .comments(UPDATED_COMMENTS);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTraining.getTrainingTypeCode()).isEqualTo(UPDATED_TRAINING_TYPE_CODE);
        assertThat(testTraining.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTraining.getTrainingIntensityCode()).isEqualTo(UPDATED_TRAINING_INTENSITY_CODE);
        assertThat(testTraining.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testTraining.getWeightBefore()).isEqualTo(DEFAULT_WEIGHT_BEFORE);
        assertThat(testTraining.getWeightAfter()).isEqualTo(UPDATED_WEIGHT_AFTER);
        assertThat(testTraining.getDrunk()).isEqualTo(DEFAULT_DRUNK);
        assertThat(testTraining.getEaten()).isEqualTo(UPDATED_EATEN);
        assertThat(testTraining.getMoistureLossPercentage()).isEqualTo(UPDATED_MOISTURE_LOSS_PERCENTAGE);
        assertThat(testTraining.getMoistureLossPerHour()).isEqualTo(UPDATED_MOISTURE_LOSS_PER_HOUR);
        assertThat(testTraining.getDefaultMoisterLossPerHour()).isEqualTo(DEFAULT_DEFAULT_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getDeltaMoisterLossPerHour()).isEqualTo(DEFAULT_DELTA_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testTraining.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTraining.getCarboDrank()).isEqualTo(DEFAULT_CARBO_DRANK);
    }

    @Test
    @Transactional
    void fullUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .date(UPDATED_DATE)
            .trainingTypeCode(UPDATED_TRAINING_TYPE_CODE)
            .duration(UPDATED_DURATION)
            .trainingIntensityCode(UPDATED_TRAINING_INTENSITY_CODE)
            .temperature(UPDATED_TEMPERATURE)
            .weightBefore(UPDATED_WEIGHT_BEFORE)
            .weightAfter(UPDATED_WEIGHT_AFTER)
            .drunk(UPDATED_DRUNK)
            .eaten(UPDATED_EATEN)
            .moistureLossPercentage(UPDATED_MOISTURE_LOSS_PERCENTAGE)
            .moistureLossPerHour(UPDATED_MOISTURE_LOSS_PER_HOUR)
            .defaultMoisterLossPerHour(UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR)
            .deltaMoisterLossPerHour(UPDATED_DELTA_MOISTER_LOSS_PER_HOUR)
            .excluded(UPDATED_EXCLUDED)
            .comments(UPDATED_COMMENTS)
            .carboDrank(UPDATED_CARBO_DRANK);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTraining.getTrainingTypeCode()).isEqualTo(UPDATED_TRAINING_TYPE_CODE);
        assertThat(testTraining.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTraining.getTrainingIntensityCode()).isEqualTo(UPDATED_TRAINING_INTENSITY_CODE);
        assertThat(testTraining.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testTraining.getWeightBefore()).isEqualTo(UPDATED_WEIGHT_BEFORE);
        assertThat(testTraining.getWeightAfter()).isEqualTo(UPDATED_WEIGHT_AFTER);
        assertThat(testTraining.getDrunk()).isEqualTo(UPDATED_DRUNK);
        assertThat(testTraining.getEaten()).isEqualTo(UPDATED_EATEN);
        assertThat(testTraining.getMoistureLossPercentage()).isEqualTo(UPDATED_MOISTURE_LOSS_PERCENTAGE);
        assertThat(testTraining.getMoistureLossPerHour()).isEqualTo(UPDATED_MOISTURE_LOSS_PER_HOUR);
        assertThat(testTraining.getDefaultMoisterLossPerHour()).isEqualTo(UPDATED_DEFAULT_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getDeltaMoisterLossPerHour()).isEqualTo(UPDATED_DELTA_MOISTER_LOSS_PER_HOUR);
        assertThat(testTraining.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testTraining.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testTraining.getCarboDrank()).isEqualTo(UPDATED_CARBO_DRANK);
    }

    @Test
    @Transactional
    void patchNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, training.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(training))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(training))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc
            .perform(delete(ENTITY_API_URL_ID, training.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
