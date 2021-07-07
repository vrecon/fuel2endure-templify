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
import nl.templify.fuel2endure.domain.FuelSettings;
import nl.templify.fuel2endure.repository.FuelSettingsRepository;
import nl.templify.fuel2endure.service.criteria.FuelSettingsCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FuelSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelSettingsResourceIT {

    private static final Integer DEFAULT_CARBOHYDRATES_GEL = 1;
    private static final Integer UPDATED_CARBOHYDRATES_GEL = 2;
    private static final Integer SMALLER_CARBOHYDRATES_GEL = 1 - 1;

    private static final Integer DEFAULT_CARBOHYDRATES_SPORT_DRANK = 1;
    private static final Integer UPDATED_CARBOHYDRATES_SPORT_DRANK = 2;
    private static final Integer SMALLER_CARBOHYDRATES_SPORT_DRANK = 1 - 1;

    private static final Integer DEFAULT_SELECTED_OWN_GEL_ITEM = 1;
    private static final Integer UPDATED_SELECTED_OWN_GEL_ITEM = 2;
    private static final Integer SMALLER_SELECTED_OWN_GEL_ITEM = 1 - 1;

    private static final Integer DEFAULT_SELECTED_OWN_DRINK_ITEM = 1;
    private static final Integer UPDATED_SELECTED_OWN_DRINK_ITEM = 2;
    private static final Integer SMALLER_SELECTED_OWN_DRINK_ITEM = 1 - 1;

    private static final String ENTITY_API_URL = "/api/fuel-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuelSettingsRepository fuelSettingsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelSettingsMockMvc;

    private FuelSettings fuelSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelSettings createEntity(EntityManager em) {
        FuelSettings fuelSettings = new FuelSettings()
            .carbohydratesGel(DEFAULT_CARBOHYDRATES_GEL)
            .carbohydratesSportDrank(DEFAULT_CARBOHYDRATES_SPORT_DRANK)
            .selectedOwnGelItem(DEFAULT_SELECTED_OWN_GEL_ITEM)
            .selectedOwnDrinkItem(DEFAULT_SELECTED_OWN_DRINK_ITEM);
        return fuelSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelSettings createUpdatedEntity(EntityManager em) {
        FuelSettings fuelSettings = new FuelSettings()
            .carbohydratesGel(UPDATED_CARBOHYDRATES_GEL)
            .carbohydratesSportDrank(UPDATED_CARBOHYDRATES_SPORT_DRANK)
            .selectedOwnGelItem(UPDATED_SELECTED_OWN_GEL_ITEM)
            .selectedOwnDrinkItem(UPDATED_SELECTED_OWN_DRINK_ITEM);
        return fuelSettings;
    }

    @BeforeEach
    public void initTest() {
        fuelSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createFuelSettings() throws Exception {
        int databaseSizeBeforeCreate = fuelSettingsRepository.findAll().size();
        // Create the FuelSettings
        restFuelSettingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelSettings)))
            .andExpect(status().isCreated());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        FuelSettings testFuelSettings = fuelSettingsList.get(fuelSettingsList.size() - 1);
        assertThat(testFuelSettings.getCarbohydratesGel()).isEqualTo(DEFAULT_CARBOHYDRATES_GEL);
        assertThat(testFuelSettings.getCarbohydratesSportDrank()).isEqualTo(DEFAULT_CARBOHYDRATES_SPORT_DRANK);
        assertThat(testFuelSettings.getSelectedOwnGelItem()).isEqualTo(DEFAULT_SELECTED_OWN_GEL_ITEM);
        assertThat(testFuelSettings.getSelectedOwnDrinkItem()).isEqualTo(DEFAULT_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void createFuelSettingsWithExistingId() throws Exception {
        // Create the FuelSettings with an existing ID
        fuelSettings.setId(1L);

        int databaseSizeBeforeCreate = fuelSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelSettingsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelSettings)))
            .andExpect(status().isBadRequest());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelSettings() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].carbohydratesGel").value(hasItem(DEFAULT_CARBOHYDRATES_GEL)))
            .andExpect(jsonPath("$.[*].carbohydratesSportDrank").value(hasItem(DEFAULT_CARBOHYDRATES_SPORT_DRANK)))
            .andExpect(jsonPath("$.[*].selectedOwnGelItem").value(hasItem(DEFAULT_SELECTED_OWN_GEL_ITEM)))
            .andExpect(jsonPath("$.[*].selectedOwnDrinkItem").value(hasItem(DEFAULT_SELECTED_OWN_DRINK_ITEM)));
    }

    @Test
    @Transactional
    void getFuelSettings() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get the fuelSettings
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelSettings.getId().intValue()))
            .andExpect(jsonPath("$.carbohydratesGel").value(DEFAULT_CARBOHYDRATES_GEL))
            .andExpect(jsonPath("$.carbohydratesSportDrank").value(DEFAULT_CARBOHYDRATES_SPORT_DRANK))
            .andExpect(jsonPath("$.selectedOwnGelItem").value(DEFAULT_SELECTED_OWN_GEL_ITEM))
            .andExpect(jsonPath("$.selectedOwnDrinkItem").value(DEFAULT_SELECTED_OWN_DRINK_ITEM));
    }

    @Test
    @Transactional
    void getFuelSettingsByIdFiltering() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        Long id = fuelSettings.getId();

        defaultFuelSettingsShouldBeFound("id.equals=" + id);
        defaultFuelSettingsShouldNotBeFound("id.notEquals=" + id);

        defaultFuelSettingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFuelSettingsShouldNotBeFound("id.greaterThan=" + id);

        defaultFuelSettingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFuelSettingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel equals to DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.equals=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel equals to UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.equals=" + UPDATED_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel not equals to DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.notEquals=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel not equals to UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.notEquals=" + UPDATED_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsInShouldWork() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel in DEFAULT_CARBOHYDRATES_GEL or UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.in=" + DEFAULT_CARBOHYDRATES_GEL + "," + UPDATED_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel equals to UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.in=" + UPDATED_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel is not null
        defaultFuelSettingsShouldBeFound("carbohydratesGel.specified=true");

        // Get all the fuelSettingsList where carbohydratesGel is null
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel is greater than or equal to DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.greaterThanOrEqual=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel is greater than or equal to UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.greaterThanOrEqual=" + UPDATED_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel is less than or equal to DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.lessThanOrEqual=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel is less than or equal to SMALLER_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.lessThanOrEqual=" + SMALLER_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsLessThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel is less than DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.lessThan=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel is less than UPDATED_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.lessThan=" + UPDATED_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesGelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesGel is greater than DEFAULT_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldNotBeFound("carbohydratesGel.greaterThan=" + DEFAULT_CARBOHYDRATES_GEL);

        // Get all the fuelSettingsList where carbohydratesGel is greater than SMALLER_CARBOHYDRATES_GEL
        defaultFuelSettingsShouldBeFound("carbohydratesGel.greaterThan=" + SMALLER_CARBOHYDRATES_GEL);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank equals to DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.equals=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank equals to UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.equals=" + UPDATED_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank not equals to DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.notEquals=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank not equals to UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.notEquals=" + UPDATED_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsInShouldWork() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank in DEFAULT_CARBOHYDRATES_SPORT_DRANK or UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound(
            "carbohydratesSportDrank.in=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK + "," + UPDATED_CARBOHYDRATES_SPORT_DRANK
        );

        // Get all the fuelSettingsList where carbohydratesSportDrank equals to UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.in=" + UPDATED_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank is not null
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.specified=true");

        // Get all the fuelSettingsList where carbohydratesSportDrank is null
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank is greater than or equal to DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.greaterThanOrEqual=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank is greater than or equal to UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.greaterThanOrEqual=" + UPDATED_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank is less than or equal to DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.lessThanOrEqual=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank is less than or equal to SMALLER_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.lessThanOrEqual=" + SMALLER_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsLessThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank is less than DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.lessThan=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank is less than UPDATED_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.lessThan=" + UPDATED_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsByCarbohydratesSportDrankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where carbohydratesSportDrank is greater than DEFAULT_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldNotBeFound("carbohydratesSportDrank.greaterThan=" + DEFAULT_CARBOHYDRATES_SPORT_DRANK);

        // Get all the fuelSettingsList where carbohydratesSportDrank is greater than SMALLER_CARBOHYDRATES_SPORT_DRANK
        defaultFuelSettingsShouldBeFound("carbohydratesSportDrank.greaterThan=" + SMALLER_CARBOHYDRATES_SPORT_DRANK);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem equals to DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.equals=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem equals to UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.equals=" + UPDATED_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem not equals to DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.notEquals=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem not equals to UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.notEquals=" + UPDATED_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsInShouldWork() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem in DEFAULT_SELECTED_OWN_GEL_ITEM or UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.in=" + DEFAULT_SELECTED_OWN_GEL_ITEM + "," + UPDATED_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem equals to UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.in=" + UPDATED_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem is not null
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.specified=true");

        // Get all the fuelSettingsList where selectedOwnGelItem is null
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem is greater than or equal to DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.greaterThanOrEqual=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem is greater than or equal to UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.greaterThanOrEqual=" + UPDATED_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem is less than or equal to DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.lessThanOrEqual=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem is less than or equal to SMALLER_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.lessThanOrEqual=" + SMALLER_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsLessThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem is less than DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.lessThan=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem is less than UPDATED_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.lessThan=" + UPDATED_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnGelItemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnGelItem is greater than DEFAULT_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnGelItem.greaterThan=" + DEFAULT_SELECTED_OWN_GEL_ITEM);

        // Get all the fuelSettingsList where selectedOwnGelItem is greater than SMALLER_SELECTED_OWN_GEL_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnGelItem.greaterThan=" + SMALLER_SELECTED_OWN_GEL_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem equals to DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.equals=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem equals to UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.equals=" + UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem not equals to DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.notEquals=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem not equals to UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.notEquals=" + UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsInShouldWork() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem in DEFAULT_SELECTED_OWN_DRINK_ITEM or UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound(
            "selectedOwnDrinkItem.in=" + DEFAULT_SELECTED_OWN_DRINK_ITEM + "," + UPDATED_SELECTED_OWN_DRINK_ITEM
        );

        // Get all the fuelSettingsList where selectedOwnDrinkItem equals to UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.in=" + UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is not null
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.specified=true");

        // Get all the fuelSettingsList where selectedOwnDrinkItem is null
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is greater than or equal to DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.greaterThanOrEqual=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is greater than or equal to UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.greaterThanOrEqual=" + UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is less than or equal to DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.lessThanOrEqual=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is less than or equal to SMALLER_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.lessThanOrEqual=" + SMALLER_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsLessThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is less than DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.lessThan=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is less than UPDATED_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.lessThan=" + UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void getAllFuelSettingsBySelectedOwnDrinkItemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is greater than DEFAULT_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldNotBeFound("selectedOwnDrinkItem.greaterThan=" + DEFAULT_SELECTED_OWN_DRINK_ITEM);

        // Get all the fuelSettingsList where selectedOwnDrinkItem is greater than SMALLER_SELECTED_OWN_DRINK_ITEM
        defaultFuelSettingsShouldBeFound("selectedOwnDrinkItem.greaterThan=" + SMALLER_SELECTED_OWN_DRINK_ITEM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuelSettingsShouldBeFound(String filter) throws Exception {
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].carbohydratesGel").value(hasItem(DEFAULT_CARBOHYDRATES_GEL)))
            .andExpect(jsonPath("$.[*].carbohydratesSportDrank").value(hasItem(DEFAULT_CARBOHYDRATES_SPORT_DRANK)))
            .andExpect(jsonPath("$.[*].selectedOwnGelItem").value(hasItem(DEFAULT_SELECTED_OWN_GEL_ITEM)))
            .andExpect(jsonPath("$.[*].selectedOwnDrinkItem").value(hasItem(DEFAULT_SELECTED_OWN_DRINK_ITEM)));

        // Check, that the count call also returns 1
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuelSettingsShouldNotBeFound(String filter) throws Exception {
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuelSettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuelSettings() throws Exception {
        // Get the fuelSettings
        restFuelSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFuelSettings() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();

        // Update the fuelSettings
        FuelSettings updatedFuelSettings = fuelSettingsRepository.findById(fuelSettings.getId()).get();
        // Disconnect from session so that the updates on updatedFuelSettings are not directly saved in db
        em.detach(updatedFuelSettings);
        updatedFuelSettings
            .carbohydratesGel(UPDATED_CARBOHYDRATES_GEL)
            .carbohydratesSportDrank(UPDATED_CARBOHYDRATES_SPORT_DRANK)
            .selectedOwnGelItem(UPDATED_SELECTED_OWN_GEL_ITEM)
            .selectedOwnDrinkItem(UPDATED_SELECTED_OWN_DRINK_ITEM);

        restFuelSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelSettings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuelSettings))
            )
            .andExpect(status().isOk());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
        FuelSettings testFuelSettings = fuelSettingsList.get(fuelSettingsList.size() - 1);
        assertThat(testFuelSettings.getCarbohydratesGel()).isEqualTo(UPDATED_CARBOHYDRATES_GEL);
        assertThat(testFuelSettings.getCarbohydratesSportDrank()).isEqualTo(UPDATED_CARBOHYDRATES_SPORT_DRANK);
        assertThat(testFuelSettings.getSelectedOwnGelItem()).isEqualTo(UPDATED_SELECTED_OWN_GEL_ITEM);
        assertThat(testFuelSettings.getSelectedOwnDrinkItem()).isEqualTo(UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void putNonExistingFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelSettings.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelSettings)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelSettingsWithPatch() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();

        // Update the fuelSettings using partial update
        FuelSettings partialUpdatedFuelSettings = new FuelSettings();
        partialUpdatedFuelSettings.setId(fuelSettings.getId());

        partialUpdatedFuelSettings
            .carbohydratesSportDrank(UPDATED_CARBOHYDRATES_SPORT_DRANK)
            .selectedOwnGelItem(UPDATED_SELECTED_OWN_GEL_ITEM);

        restFuelSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelSettings))
            )
            .andExpect(status().isOk());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
        FuelSettings testFuelSettings = fuelSettingsList.get(fuelSettingsList.size() - 1);
        assertThat(testFuelSettings.getCarbohydratesGel()).isEqualTo(DEFAULT_CARBOHYDRATES_GEL);
        assertThat(testFuelSettings.getCarbohydratesSportDrank()).isEqualTo(UPDATED_CARBOHYDRATES_SPORT_DRANK);
        assertThat(testFuelSettings.getSelectedOwnGelItem()).isEqualTo(UPDATED_SELECTED_OWN_GEL_ITEM);
        assertThat(testFuelSettings.getSelectedOwnDrinkItem()).isEqualTo(DEFAULT_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void fullUpdateFuelSettingsWithPatch() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();

        // Update the fuelSettings using partial update
        FuelSettings partialUpdatedFuelSettings = new FuelSettings();
        partialUpdatedFuelSettings.setId(fuelSettings.getId());

        partialUpdatedFuelSettings
            .carbohydratesGel(UPDATED_CARBOHYDRATES_GEL)
            .carbohydratesSportDrank(UPDATED_CARBOHYDRATES_SPORT_DRANK)
            .selectedOwnGelItem(UPDATED_SELECTED_OWN_GEL_ITEM)
            .selectedOwnDrinkItem(UPDATED_SELECTED_OWN_DRINK_ITEM);

        restFuelSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelSettings))
            )
            .andExpect(status().isOk());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
        FuelSettings testFuelSettings = fuelSettingsList.get(fuelSettingsList.size() - 1);
        assertThat(testFuelSettings.getCarbohydratesGel()).isEqualTo(UPDATED_CARBOHYDRATES_GEL);
        assertThat(testFuelSettings.getCarbohydratesSportDrank()).isEqualTo(UPDATED_CARBOHYDRATES_SPORT_DRANK);
        assertThat(testFuelSettings.getSelectedOwnGelItem()).isEqualTo(UPDATED_SELECTED_OWN_GEL_ITEM);
        assertThat(testFuelSettings.getSelectedOwnDrinkItem()).isEqualTo(UPDATED_SELECTED_OWN_DRINK_ITEM);
    }

    @Test
    @Transactional
    void patchNonExistingFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelSettings))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelSettings() throws Exception {
        int databaseSizeBeforeUpdate = fuelSettingsRepository.findAll().size();
        fuelSettings.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fuelSettings))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelSettings in the database
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelSettings() throws Exception {
        // Initialize the database
        fuelSettingsRepository.saveAndFlush(fuelSettings);

        int databaseSizeBeforeDelete = fuelSettingsRepository.findAll().size();

        // Delete the fuelSettings
        restFuelSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuelSettings> fuelSettingsList = fuelSettingsRepository.findAll();
        assertThat(fuelSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
