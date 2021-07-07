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
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.domain.RacePlanForm;
import nl.templify.fuel2endure.repository.RaceRepository;
import nl.templify.fuel2endure.service.criteria.RaceCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaceResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGGING = "AAAAAAAAAA";
    private static final String UPDATED_LOGGING = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;
    private static final Integer SMALLER_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;
    private static final Integer SMALLER_LENGTH = 1 - 1;

    private static final Integer DEFAULT_TEMPERATURE = 1;
    private static final Integer UPDATED_TEMPERATURE = 2;
    private static final Integer SMALLER_TEMPERATURE = 1 - 1;

    private static final Integer DEFAULT_COMP = 1;
    private static final Integer UPDATED_COMP = 2;
    private static final Integer SMALLER_COMP = 1 - 1;

    private static final Integer DEFAULT_SWIM_DURATION = 1;
    private static final Integer UPDATED_SWIM_DURATION = 2;
    private static final Integer SMALLER_SWIM_DURATION = 1 - 1;

    private static final Integer DEFAULT_BIKE_DURATION = 1;
    private static final Integer UPDATED_BIKE_DURATION = 2;
    private static final Integer SMALLER_BIKE_DURATION = 1 - 1;

    private static final Integer DEFAULT_RUN_DURATION = 1;
    private static final Integer UPDATED_RUN_DURATION = 2;
    private static final Integer SMALLER_RUN_DURATION = 1 - 1;

    private static final Integer DEFAULT_GEL_CARBO = 1;
    private static final Integer UPDATED_GEL_CARBO = 2;
    private static final Integer SMALLER_GEL_CARBO = 1 - 1;

    private static final Integer DEFAULT_DRINK_CARBO = 1;
    private static final Integer UPDATED_DRINK_CARBO = 2;
    private static final Integer SMALLER_DRINK_CARBO = 1 - 1;

    private static final Integer DEFAULT_DRINK_ORG_CARBO = 1;
    private static final Integer UPDATED_DRINK_ORG_CARBO = 2;
    private static final Integer SMALLER_DRINK_ORG_CARBO = 1 - 1;

    private static final Integer DEFAULT_GEL_ORG_CARBO = 1;
    private static final Integer UPDATED_GEL_ORG_CARBO = 2;
    private static final Integer SMALLER_GEL_ORG_CARBO = 1 - 1;

    private static final Integer DEFAULT_GELSBIKE = 1;
    private static final Integer UPDATED_GELSBIKE = 2;
    private static final Integer SMALLER_GELSBIKE = 1 - 1;

    private static final Integer DEFAULT_GELSRUN = 1;
    private static final Integer UPDATED_GELSRUN = 2;
    private static final Integer SMALLER_GELSRUN = 1 - 1;

    private static final Boolean DEFAULT_SELECTED_ORG_GEL_QUERY = false;
    private static final Boolean UPDATED_SELECTED_ORG_GEL_QUERY = true;

    private static final Boolean DEFAULT_SELECTED_ORG_DRANK_QUERY = false;
    private static final Boolean UPDATED_SELECTED_ORG_DRANK_QUERY = true;

    private static final Integer DEFAULT_SPORT_DRINK_ORG_BIKE = 1;
    private static final Integer UPDATED_SPORT_DRINK_ORG_BIKE = 2;
    private static final Integer SMALLER_SPORT_DRINK_ORG_BIKE = 1 - 1;

    private static final Integer DEFAULT_WATER_ORG_BIKE = 1;
    private static final Integer UPDATED_WATER_ORG_BIKE = 2;
    private static final Integer SMALLER_WATER_ORG_BIKE = 1 - 1;

    private static final Integer DEFAULT_GELS_ORG_BIKE = 1;
    private static final Integer UPDATED_GELS_ORG_BIKE = 2;
    private static final Integer SMALLER_GELS_ORG_BIKE = 1 - 1;

    private static final Integer DEFAULT_SPORT_DRINK_ORG_RUN = 1;
    private static final Integer UPDATED_SPORT_DRINK_ORG_RUN = 2;
    private static final Integer SMALLER_SPORT_DRINK_ORG_RUN = 1 - 1;

    private static final Integer DEFAULT_WATER_ORG_RUN = 1;
    private static final Integer UPDATED_WATER_ORG_RUN = 2;
    private static final Integer SMALLER_WATER_ORG_RUN = 1 - 1;

    private static final Integer DEFAULT_GELS_ORG_RUN = 1;
    private static final Integer UPDATED_GELS_ORG_RUN = 2;
    private static final Integer SMALLER_GELS_ORG_RUN = 1 - 1;

    private static final Integer DEFAULT_ORS_BEFORE_START = 1;
    private static final Integer UPDATED_ORS_BEFORE_START = 2;
    private static final Integer SMALLER_ORS_BEFORE_START = 1 - 1;

    private static final Integer DEFAULT_SPORT_DRINK_TO_TAKE_BIKE = 1;
    private static final Integer UPDATED_SPORT_DRINK_TO_TAKE_BIKE = 2;
    private static final Integer SMALLER_SPORT_DRINK_TO_TAKE_BIKE = 1 - 1;

    private static final Integer DEFAULT_WATER_TO_TAKE_BIKE = 1;
    private static final Integer UPDATED_WATER_TO_TAKE_BIKE = 2;
    private static final Integer SMALLER_WATER_TO_TAKE_BIKE = 1 - 1;

    private static final Integer DEFAULT_EXTRA_WATER_TO_TAKE_BIKE = 1;
    private static final Integer UPDATED_EXTRA_WATER_TO_TAKE_BIKE = 2;
    private static final Integer SMALLER_EXTRA_WATER_TO_TAKE_BIKE = 1 - 1;

    private static final Integer DEFAULT_ORS_TO_TAKE_BIKE = 1;
    private static final Integer UPDATED_ORS_TO_TAKE_BIKE = 2;
    private static final Integer SMALLER_ORS_TO_TAKE_BIKE = 1 - 1;

    private static final Integer DEFAULT_GELS_TO_TAKE_BIKE = 1;
    private static final Integer UPDATED_GELS_TO_TAKE_BIKE = 2;
    private static final Integer SMALLER_GELS_TO_TAKE_BIKE = 1 - 1;

    private static final Integer DEFAULT_SPORT_DRINK_TO_TAKE_RUN = 1;
    private static final Integer UPDATED_SPORT_DRINK_TO_TAKE_RUN = 2;
    private static final Integer SMALLER_SPORT_DRINK_TO_TAKE_RUN = 1 - 1;

    private static final Integer DEFAULT_WATER_TO_TAKE_RUN = 1;
    private static final Integer UPDATED_WATER_TO_TAKE_RUN = 2;
    private static final Integer SMALLER_WATER_TO_TAKE_RUN = 1 - 1;

    private static final Integer DEFAULT_EXTRA_WATER_TO_TAKE_RUN = 1;
    private static final Integer UPDATED_EXTRA_WATER_TO_TAKE_RUN = 2;
    private static final Integer SMALLER_EXTRA_WATER_TO_TAKE_RUN = 1 - 1;

    private static final Integer DEFAULT_ORS_TO_TAKE_RUN = 1;
    private static final Integer UPDATED_ORS_TO_TAKE_RUN = 2;
    private static final Integer SMALLER_ORS_TO_TAKE_RUN = 1 - 1;

    private static final Integer DEFAULT_CARBO_NEED_DURING_BIKE_MIN = 1;
    private static final Integer UPDATED_CARBO_NEED_DURING_BIKE_MIN = 2;
    private static final Integer SMALLER_CARBO_NEED_DURING_BIKE_MIN = 1 - 1;

    private static final Integer DEFAULT_CARBO_NEED_DURING_BIKE_MAX = 1;
    private static final Integer UPDATED_CARBO_NEED_DURING_BIKE_MAX = 2;
    private static final Integer SMALLER_CARBO_NEED_DURING_BIKE_MAX = 1 - 1;

    private static final Integer DEFAULT_CARBO_NEED_DURING_RUN_MIN = 1;
    private static final Integer UPDATED_CARBO_NEED_DURING_RUN_MIN = 2;
    private static final Integer SMALLER_CARBO_NEED_DURING_RUN_MIN = 1 - 1;

    private static final Integer DEFAULT_CARBO_NEED_DURING_RUN_MAX = 1;
    private static final Integer UPDATED_CARBO_NEED_DURING_RUN_MAX = 2;
    private static final Integer SMALLER_CARBO_NEED_DURING_RUN_MAX = 1 - 1;

    private static final Integer DEFAULT_DIFF_CARBO_RUN = 1;
    private static final Integer UPDATED_DIFF_CARBO_RUN = 2;
    private static final Integer SMALLER_DIFF_CARBO_RUN = 1 - 1;

    private static final Integer DEFAULT_DIFF_MOISTER_RUN = 1;
    private static final Integer UPDATED_DIFF_MOISTER_RUN = 2;
    private static final Integer SMALLER_DIFF_MOISTER_RUN = 1 - 1;

    private static final Integer DEFAULT_DIF_CARBO = 1;
    private static final Integer UPDATED_DIF_CARBO = 2;
    private static final Integer SMALLER_DIF_CARBO = 1 - 1;

    private static final Integer DEFAULT_DIF_MOISTER = 1;
    private static final Integer UPDATED_DIF_MOISTER = 2;
    private static final Integer SMALLER_DIF_MOISTER = 1 - 1;

    private static final Integer DEFAULT_GELS_TO_TAKE_RUN = 1;
    private static final Integer UPDATED_GELS_TO_TAKE_RUN = 2;
    private static final Integer SMALLER_GELS_TO_TAKE_RUN = 1 - 1;

    private static final Float DEFAULT_WEIGHT_LOSS_DURING_BIKE = 1F;
    private static final Float UPDATED_WEIGHT_LOSS_DURING_BIKE = 2F;
    private static final Float SMALLER_WEIGHT_LOSS_DURING_BIKE = 1F - 1F;

    private static final Float DEFAULT_CARBO_NEED_DURING_RUN = 1F;
    private static final Float UPDATED_CARBO_NEED_DURING_RUN = 2F;
    private static final Float SMALLER_CARBO_NEED_DURING_RUN = 1F - 1F;

    private static final Double DEFAULT_FLUID_NEED_PER_HOUR_BIKE = 1D;
    private static final Double UPDATED_FLUID_NEED_PER_HOUR_BIKE = 2D;
    private static final Double SMALLER_FLUID_NEED_PER_HOUR_BIKE = 1D - 1D;

    private static final Double DEFAULT_FLUID_NEED_PER_HOUR_SWIM = 1D;
    private static final Double UPDATED_FLUID_NEED_PER_HOUR_SWIM = 2D;
    private static final Double SMALLER_FLUID_NEED_PER_HOUR_SWIM = 1D - 1D;

    private static final Double DEFAULT_CARBO_NEED_DURING_BIKE = 1D;
    private static final Double UPDATED_CARBO_NEED_DURING_BIKE = 2D;
    private static final Double SMALLER_CARBO_NEED_DURING_BIKE = 1D - 1D;

    private static final Long DEFAULT_FLUID_NEED_DURING_BIKE = 1L;
    private static final Long UPDATED_FLUID_NEED_DURING_BIKE = 2L;
    private static final Long SMALLER_FLUID_NEED_DURING_BIKE = 1L - 1L;

    private static final Double DEFAULT_FLUID_NEED_PER_HOUR_RUN = 1D;
    private static final Double UPDATED_FLUID_NEED_PER_HOUR_RUN = 2D;
    private static final Double SMALLER_FLUID_NEED_PER_HOUR_RUN = 1D - 1D;

    private static final Long DEFAULT_FLUID_NEED_DURING_RUN = 1L;
    private static final Long UPDATED_FLUID_NEED_DURING_RUN = 2L;
    private static final Long SMALLER_FLUID_NEED_DURING_RUN = 1L - 1L;

    private static final Float DEFAULT_WEIGHT_LOSS_DURING_RUN = 1F;
    private static final Float UPDATED_WEIGHT_LOSS_DURING_RUN = 2F;
    private static final Float SMALLER_WEIGHT_LOSS_DURING_RUN = 1F - 1F;

    private static final Integer DEFAULT_ACT_FLUID_NEED_BIKE = 1;
    private static final Integer UPDATED_ACT_FLUID_NEED_BIKE = 2;
    private static final Integer SMALLER_ACT_FLUID_NEED_BIKE = 1 - 1;

    private static final Integer DEFAULT_ACT_FLUID_NEED_RUN = 1;
    private static final Integer UPDATED_ACT_FLUID_NEED_RUN = 2;
    private static final Integer SMALLER_ACT_FLUID_NEED_RUN = 1 - 1;

    private static final String ENTITY_API_URL = "/api/races";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaceMockMvc;

    private Race race;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Race createEntity(EntityManager em) {
        Race race = new Race()
            .date(DEFAULT_DATE)
            .name(DEFAULT_NAME)
            .logging(DEFAULT_LOGGING)
            .weight(DEFAULT_WEIGHT)
            .length(DEFAULT_LENGTH)
            .temperature(DEFAULT_TEMPERATURE)
            .comp(DEFAULT_COMP)
            .swimDuration(DEFAULT_SWIM_DURATION)
            .bikeDuration(DEFAULT_BIKE_DURATION)
            .runDuration(DEFAULT_RUN_DURATION)
            .gelCarbo(DEFAULT_GEL_CARBO)
            .drinkCarbo(DEFAULT_DRINK_CARBO)
            .drinkOrgCarbo(DEFAULT_DRINK_ORG_CARBO)
            .gelOrgCarbo(DEFAULT_GEL_ORG_CARBO)
            .gelsbike(DEFAULT_GELSBIKE)
            .gelsrun(DEFAULT_GELSRUN)
            .selectedOrgGelQuery(DEFAULT_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(DEFAULT_SELECTED_ORG_DRANK_QUERY)
            .sportDrinkOrgBike(DEFAULT_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(DEFAULT_WATER_ORG_BIKE)
            .gelsOrgBike(DEFAULT_GELS_ORG_BIKE)
            .sportDrinkOrgRun(DEFAULT_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(DEFAULT_WATER_ORG_RUN)
            .gelsOrgRun(DEFAULT_GELS_ORG_RUN)
            .orsBeforeStart(DEFAULT_ORS_BEFORE_START)
            .sportDrinkToTakeBike(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(DEFAULT_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(DEFAULT_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(DEFAULT_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(DEFAULT_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(DEFAULT_ORS_TO_TAKE_RUN)
            .carboNeedDuringBikeMin(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(DEFAULT_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(DEFAULT_CARBO_NEED_DURING_RUN_MAX)
            .diffCarboRun(DEFAULT_DIFF_CARBO_RUN)
            .diffMoisterRun(DEFAULT_DIFF_MOISTER_RUN)
            .difCarbo(DEFAULT_DIF_CARBO)
            .difMoister(DEFAULT_DIF_MOISTER)
            .gelsToTakeRun(DEFAULT_GELS_TO_TAKE_RUN)
            .weightLossDuringBike(DEFAULT_WEIGHT_LOSS_DURING_BIKE)
            .carboNeedDuringRun(DEFAULT_CARBO_NEED_DURING_RUN)
            .fluidNeedPerHourBike(DEFAULT_FLUID_NEED_PER_HOUR_BIKE)
            .fluidNeedPerHourSwim(DEFAULT_FLUID_NEED_PER_HOUR_SWIM)
            .carboNeedDuringBike(DEFAULT_CARBO_NEED_DURING_BIKE)
            .fluidNeedDuringBike(DEFAULT_FLUID_NEED_DURING_BIKE)
            .fluidNeedPerHourRun(DEFAULT_FLUID_NEED_PER_HOUR_RUN)
            .fluidNeedDuringRun(DEFAULT_FLUID_NEED_DURING_RUN)
            .weightLossDuringRun(DEFAULT_WEIGHT_LOSS_DURING_RUN)
            .actFluidNeedBike(DEFAULT_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(DEFAULT_ACT_FLUID_NEED_RUN);
        return race;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Race createUpdatedEntity(EntityManager em) {
        Race race = new Race()
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .logging(UPDATED_LOGGING)
            .weight(UPDATED_WEIGHT)
            .length(UPDATED_LENGTH)
            .temperature(UPDATED_TEMPERATURE)
            .comp(UPDATED_COMP)
            .swimDuration(UPDATED_SWIM_DURATION)
            .bikeDuration(UPDATED_BIKE_DURATION)
            .runDuration(UPDATED_RUN_DURATION)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .gelsbike(UPDATED_GELSBIKE)
            .gelsrun(UPDATED_GELSRUN)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .gelsToTakeRun(UPDATED_GELS_TO_TAKE_RUN)
            .weightLossDuringBike(UPDATED_WEIGHT_LOSS_DURING_BIKE)
            .carboNeedDuringRun(UPDATED_CARBO_NEED_DURING_RUN)
            .fluidNeedPerHourBike(UPDATED_FLUID_NEED_PER_HOUR_BIKE)
            .fluidNeedPerHourSwim(UPDATED_FLUID_NEED_PER_HOUR_SWIM)
            .carboNeedDuringBike(UPDATED_CARBO_NEED_DURING_BIKE)
            .fluidNeedDuringBike(UPDATED_FLUID_NEED_DURING_BIKE)
            .fluidNeedPerHourRun(UPDATED_FLUID_NEED_PER_HOUR_RUN)
            .fluidNeedDuringRun(UPDATED_FLUID_NEED_DURING_RUN)
            .weightLossDuringRun(UPDATED_WEIGHT_LOSS_DURING_RUN)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN);
        return race;
    }

    @BeforeEach
    public void initTest() {
        race = createEntity(em);
    }

    @Test
    @Transactional
    void createRace() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();
        // Create the Race
        restRaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate + 1);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRace.getLogging()).isEqualTo(DEFAULT_LOGGING);
        assertThat(testRace.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testRace.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testRace.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testRace.getComp()).isEqualTo(DEFAULT_COMP);
        assertThat(testRace.getSwimDuration()).isEqualTo(DEFAULT_SWIM_DURATION);
        assertThat(testRace.getBikeDuration()).isEqualTo(DEFAULT_BIKE_DURATION);
        assertThat(testRace.getRunDuration()).isEqualTo(DEFAULT_RUN_DURATION);
        assertThat(testRace.getGelCarbo()).isEqualTo(DEFAULT_GEL_CARBO);
        assertThat(testRace.getDrinkCarbo()).isEqualTo(DEFAULT_DRINK_CARBO);
        assertThat(testRace.getDrinkOrgCarbo()).isEqualTo(DEFAULT_DRINK_ORG_CARBO);
        assertThat(testRace.getGelOrgCarbo()).isEqualTo(DEFAULT_GEL_ORG_CARBO);
        assertThat(testRace.getGelsbike()).isEqualTo(DEFAULT_GELSBIKE);
        assertThat(testRace.getGelsrun()).isEqualTo(DEFAULT_GELSRUN);
        assertThat(testRace.getSelectedOrgGelQuery()).isEqualTo(DEFAULT_SELECTED_ORG_GEL_QUERY);
        assertThat(testRace.getSelectedOrgDrankQuery()).isEqualTo(DEFAULT_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRace.getSportDrinkOrgBike()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_BIKE);
        assertThat(testRace.getWaterOrgBike()).isEqualTo(DEFAULT_WATER_ORG_BIKE);
        assertThat(testRace.getGelsOrgBike()).isEqualTo(DEFAULT_GELS_ORG_BIKE);
        assertThat(testRace.getSportDrinkOrgRun()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_RUN);
        assertThat(testRace.getWaterOrgRun()).isEqualTo(DEFAULT_WATER_ORG_RUN);
        assertThat(testRace.getGelsOrgRun()).isEqualTo(DEFAULT_GELS_ORG_RUN);
        assertThat(testRace.getOrsBeforeStart()).isEqualTo(DEFAULT_ORS_BEFORE_START);
        assertThat(testRace.getSportDrinkToTakeBike()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRace.getWaterToTakeBike()).isEqualTo(DEFAULT_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getExtraWaterToTakeBike()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getOrsToTakeBike()).isEqualTo(DEFAULT_ORS_TO_TAKE_BIKE);
        assertThat(testRace.getGelsToTakeBike()).isEqualTo(DEFAULT_GELS_TO_TAKE_BIKE);
        assertThat(testRace.getSportDrinkToTakeRun()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRace.getWaterToTakeRun()).isEqualTo(DEFAULT_WATER_TO_TAKE_RUN);
        assertThat(testRace.getExtraWaterToTakeRun()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRace.getOrsToTakeRun()).isEqualTo(DEFAULT_ORS_TO_TAKE_RUN);
        assertThat(testRace.getCarboNeedDuringBikeMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRace.getCarboNeedDuringBikeMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRace.getCarboNeedDuringRunMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRace.getCarboNeedDuringRunMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRace.getDiffCarboRun()).isEqualTo(DEFAULT_DIFF_CARBO_RUN);
        assertThat(testRace.getDiffMoisterRun()).isEqualTo(DEFAULT_DIFF_MOISTER_RUN);
        assertThat(testRace.getDifCarbo()).isEqualTo(DEFAULT_DIF_CARBO);
        assertThat(testRace.getDifMoister()).isEqualTo(DEFAULT_DIF_MOISTER);
        assertThat(testRace.getGelsToTakeRun()).isEqualTo(DEFAULT_GELS_TO_TAKE_RUN);
        assertThat(testRace.getWeightLossDuringBike()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRace.getCarboNeedDuringRun()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN);
        assertThat(testRace.getFluidNeedPerHourBike()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRace.getFluidNeedPerHourSwim()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRace.getCarboNeedDuringBike()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedDuringBike()).isEqualTo(DEFAULT_FLUID_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedPerHourRun()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRace.getFluidNeedDuringRun()).isEqualTo(DEFAULT_FLUID_NEED_DURING_RUN);
        assertThat(testRace.getWeightLossDuringRun()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRace.getActFluidNeedBike()).isEqualTo(DEFAULT_ACT_FLUID_NEED_BIKE);
        assertThat(testRace.getActFluidNeedRun()).isEqualTo(DEFAULT_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void createRaceWithExistingId() throws Exception {
        // Create the Race with an existing ID
        race.setId(1L);

        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRaces() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(race.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logging").value(hasItem(DEFAULT_LOGGING)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].comp").value(hasItem(DEFAULT_COMP)))
            .andExpect(jsonPath("$.[*].swimDuration").value(hasItem(DEFAULT_SWIM_DURATION)))
            .andExpect(jsonPath("$.[*].bikeDuration").value(hasItem(DEFAULT_BIKE_DURATION)))
            .andExpect(jsonPath("$.[*].runDuration").value(hasItem(DEFAULT_RUN_DURATION)))
            .andExpect(jsonPath("$.[*].gelCarbo").value(hasItem(DEFAULT_GEL_CARBO)))
            .andExpect(jsonPath("$.[*].drinkCarbo").value(hasItem(DEFAULT_DRINK_CARBO)))
            .andExpect(jsonPath("$.[*].drinkOrgCarbo").value(hasItem(DEFAULT_DRINK_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelOrgCarbo").value(hasItem(DEFAULT_GEL_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelsbike").value(hasItem(DEFAULT_GELSBIKE)))
            .andExpect(jsonPath("$.[*].gelsrun").value(hasItem(DEFAULT_GELSRUN)))
            .andExpect(jsonPath("$.[*].selectedOrgGelQuery").value(hasItem(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedOrgDrankQuery").value(hasItem(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].sportDrinkOrgBike").value(hasItem(DEFAULT_SPORT_DRINK_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].waterOrgBike").value(hasItem(DEFAULT_WATER_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].gelsOrgBike").value(hasItem(DEFAULT_GELS_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgRun").value(hasItem(DEFAULT_SPORT_DRINK_ORG_RUN)))
            .andExpect(jsonPath("$.[*].waterOrgRun").value(hasItem(DEFAULT_WATER_ORG_RUN)))
            .andExpect(jsonPath("$.[*].gelsOrgRun").value(hasItem(DEFAULT_GELS_ORG_RUN)))
            .andExpect(jsonPath("$.[*].orsBeforeStart").value(hasItem(DEFAULT_ORS_BEFORE_START)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeBike").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].waterToTakeBike").value(hasItem(DEFAULT_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeBike").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].orsToTakeBike").value(hasItem(DEFAULT_ORS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].gelsToTakeBike").value(hasItem(DEFAULT_GELS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeRun").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].waterToTakeRun").value(hasItem(DEFAULT_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeRun").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].orsToTakeRun").value(hasItem(DEFAULT_ORS_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MAX)))
            .andExpect(jsonPath("$.[*].diffCarboRun").value(hasItem(DEFAULT_DIFF_CARBO_RUN)))
            .andExpect(jsonPath("$.[*].diffMoisterRun").value(hasItem(DEFAULT_DIFF_MOISTER_RUN)))
            .andExpect(jsonPath("$.[*].difCarbo").value(hasItem(DEFAULT_DIF_CARBO)))
            .andExpect(jsonPath("$.[*].difMoister").value(hasItem(DEFAULT_DIF_MOISTER)))
            .andExpect(jsonPath("$.[*].gelsToTakeRun").value(hasItem(DEFAULT_GELS_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].weightLossDuringBike").value(hasItem(DEFAULT_WEIGHT_LOSS_DURING_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].carboNeedDuringRun").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourBike").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourSwim").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_SWIM.doubleValue())))
            .andExpect(jsonPath("$.[*].carboNeedDuringBike").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedDuringBike").value(hasItem(DEFAULT_FLUID_NEED_DURING_BIKE.intValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourRun").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedDuringRun").value(hasItem(DEFAULT_FLUID_NEED_DURING_RUN.intValue())))
            .andExpect(jsonPath("$.[*].weightLossDuringRun").value(hasItem(DEFAULT_WEIGHT_LOSS_DURING_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].actFluidNeedBike").value(hasItem(DEFAULT_ACT_FLUID_NEED_BIKE)))
            .andExpect(jsonPath("$.[*].actFluidNeedRun").value(hasItem(DEFAULT_ACT_FLUID_NEED_RUN)));
    }

    @Test
    @Transactional
    void getRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get the race
        restRaceMockMvc
            .perform(get(ENTITY_API_URL_ID, race.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(race.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logging").value(DEFAULT_LOGGING))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE))
            .andExpect(jsonPath("$.comp").value(DEFAULT_COMP))
            .andExpect(jsonPath("$.swimDuration").value(DEFAULT_SWIM_DURATION))
            .andExpect(jsonPath("$.bikeDuration").value(DEFAULT_BIKE_DURATION))
            .andExpect(jsonPath("$.runDuration").value(DEFAULT_RUN_DURATION))
            .andExpect(jsonPath("$.gelCarbo").value(DEFAULT_GEL_CARBO))
            .andExpect(jsonPath("$.drinkCarbo").value(DEFAULT_DRINK_CARBO))
            .andExpect(jsonPath("$.drinkOrgCarbo").value(DEFAULT_DRINK_ORG_CARBO))
            .andExpect(jsonPath("$.gelOrgCarbo").value(DEFAULT_GEL_ORG_CARBO))
            .andExpect(jsonPath("$.gelsbike").value(DEFAULT_GELSBIKE))
            .andExpect(jsonPath("$.gelsrun").value(DEFAULT_GELSRUN))
            .andExpect(jsonPath("$.selectedOrgGelQuery").value(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue()))
            .andExpect(jsonPath("$.selectedOrgDrankQuery").value(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue()))
            .andExpect(jsonPath("$.sportDrinkOrgBike").value(DEFAULT_SPORT_DRINK_ORG_BIKE))
            .andExpect(jsonPath("$.waterOrgBike").value(DEFAULT_WATER_ORG_BIKE))
            .andExpect(jsonPath("$.gelsOrgBike").value(DEFAULT_GELS_ORG_BIKE))
            .andExpect(jsonPath("$.sportDrinkOrgRun").value(DEFAULT_SPORT_DRINK_ORG_RUN))
            .andExpect(jsonPath("$.waterOrgRun").value(DEFAULT_WATER_ORG_RUN))
            .andExpect(jsonPath("$.gelsOrgRun").value(DEFAULT_GELS_ORG_RUN))
            .andExpect(jsonPath("$.orsBeforeStart").value(DEFAULT_ORS_BEFORE_START))
            .andExpect(jsonPath("$.sportDrinkToTakeBike").value(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.waterToTakeBike").value(DEFAULT_WATER_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.extraWaterToTakeBike").value(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.orsToTakeBike").value(DEFAULT_ORS_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.gelsToTakeBike").value(DEFAULT_GELS_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.sportDrinkToTakeRun").value(DEFAULT_SPORT_DRINK_TO_TAKE_RUN))
            .andExpect(jsonPath("$.waterToTakeRun").value(DEFAULT_WATER_TO_TAKE_RUN))
            .andExpect(jsonPath("$.extraWaterToTakeRun").value(DEFAULT_EXTRA_WATER_TO_TAKE_RUN))
            .andExpect(jsonPath("$.orsToTakeRun").value(DEFAULT_ORS_TO_TAKE_RUN))
            .andExpect(jsonPath("$.carboNeedDuringBikeMin").value(DEFAULT_CARBO_NEED_DURING_BIKE_MIN))
            .andExpect(jsonPath("$.carboNeedDuringBikeMax").value(DEFAULT_CARBO_NEED_DURING_BIKE_MAX))
            .andExpect(jsonPath("$.carboNeedDuringRunMin").value(DEFAULT_CARBO_NEED_DURING_RUN_MIN))
            .andExpect(jsonPath("$.carboNeedDuringRunMax").value(DEFAULT_CARBO_NEED_DURING_RUN_MAX))
            .andExpect(jsonPath("$.diffCarboRun").value(DEFAULT_DIFF_CARBO_RUN))
            .andExpect(jsonPath("$.diffMoisterRun").value(DEFAULT_DIFF_MOISTER_RUN))
            .andExpect(jsonPath("$.difCarbo").value(DEFAULT_DIF_CARBO))
            .andExpect(jsonPath("$.difMoister").value(DEFAULT_DIF_MOISTER))
            .andExpect(jsonPath("$.gelsToTakeRun").value(DEFAULT_GELS_TO_TAKE_RUN))
            .andExpect(jsonPath("$.weightLossDuringBike").value(DEFAULT_WEIGHT_LOSS_DURING_BIKE.doubleValue()))
            .andExpect(jsonPath("$.carboNeedDuringRun").value(DEFAULT_CARBO_NEED_DURING_RUN.doubleValue()))
            .andExpect(jsonPath("$.fluidNeedPerHourBike").value(DEFAULT_FLUID_NEED_PER_HOUR_BIKE.doubleValue()))
            .andExpect(jsonPath("$.fluidNeedPerHourSwim").value(DEFAULT_FLUID_NEED_PER_HOUR_SWIM.doubleValue()))
            .andExpect(jsonPath("$.carboNeedDuringBike").value(DEFAULT_CARBO_NEED_DURING_BIKE.doubleValue()))
            .andExpect(jsonPath("$.fluidNeedDuringBike").value(DEFAULT_FLUID_NEED_DURING_BIKE.intValue()))
            .andExpect(jsonPath("$.fluidNeedPerHourRun").value(DEFAULT_FLUID_NEED_PER_HOUR_RUN.doubleValue()))
            .andExpect(jsonPath("$.fluidNeedDuringRun").value(DEFAULT_FLUID_NEED_DURING_RUN.intValue()))
            .andExpect(jsonPath("$.weightLossDuringRun").value(DEFAULT_WEIGHT_LOSS_DURING_RUN.doubleValue()))
            .andExpect(jsonPath("$.actFluidNeedBike").value(DEFAULT_ACT_FLUID_NEED_BIKE))
            .andExpect(jsonPath("$.actFluidNeedRun").value(DEFAULT_ACT_FLUID_NEED_RUN));
    }

    @Test
    @Transactional
    void getRacesByIdFiltering() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        Long id = race.getId();

        defaultRaceShouldBeFound("id.equals=" + id);
        defaultRaceShouldNotBeFound("id.notEquals=" + id);

        defaultRaceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRaceShouldNotBeFound("id.greaterThan=" + id);

        defaultRaceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRaceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date equals to DEFAULT_DATE
        defaultRaceShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the raceList where date equals to UPDATED_DATE
        defaultRaceShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date not equals to DEFAULT_DATE
        defaultRaceShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the raceList where date not equals to UPDATED_DATE
        defaultRaceShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date in DEFAULT_DATE or UPDATED_DATE
        defaultRaceShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the raceList where date equals to UPDATED_DATE
        defaultRaceShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date is not null
        defaultRaceShouldBeFound("date.specified=true");

        // Get all the raceList where date is null
        defaultRaceShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date is greater than or equal to DEFAULT_DATE
        defaultRaceShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the raceList where date is greater than or equal to UPDATED_DATE
        defaultRaceShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date is less than or equal to DEFAULT_DATE
        defaultRaceShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the raceList where date is less than or equal to SMALLER_DATE
        defaultRaceShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date is less than DEFAULT_DATE
        defaultRaceShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the raceList where date is less than UPDATED_DATE
        defaultRaceShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where date is greater than DEFAULT_DATE
        defaultRaceShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the raceList where date is greater than SMALLER_DATE
        defaultRaceShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRacesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name equals to DEFAULT_NAME
        defaultRaceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the raceList where name equals to UPDATED_NAME
        defaultRaceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name not equals to DEFAULT_NAME
        defaultRaceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the raceList where name not equals to UPDATED_NAME
        defaultRaceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRaceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the raceList where name equals to UPDATED_NAME
        defaultRaceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name is not null
        defaultRaceShouldBeFound("name.specified=true");

        // Get all the raceList where name is null
        defaultRaceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByNameContainsSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name contains DEFAULT_NAME
        defaultRaceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the raceList where name contains UPDATED_NAME
        defaultRaceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where name does not contain DEFAULT_NAME
        defaultRaceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the raceList where name does not contain UPDATED_NAME
        defaultRaceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacesByLoggingIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging equals to DEFAULT_LOGGING
        defaultRaceShouldBeFound("logging.equals=" + DEFAULT_LOGGING);

        // Get all the raceList where logging equals to UPDATED_LOGGING
        defaultRaceShouldNotBeFound("logging.equals=" + UPDATED_LOGGING);
    }

    @Test
    @Transactional
    void getAllRacesByLoggingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging not equals to DEFAULT_LOGGING
        defaultRaceShouldNotBeFound("logging.notEquals=" + DEFAULT_LOGGING);

        // Get all the raceList where logging not equals to UPDATED_LOGGING
        defaultRaceShouldBeFound("logging.notEquals=" + UPDATED_LOGGING);
    }

    @Test
    @Transactional
    void getAllRacesByLoggingIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging in DEFAULT_LOGGING or UPDATED_LOGGING
        defaultRaceShouldBeFound("logging.in=" + DEFAULT_LOGGING + "," + UPDATED_LOGGING);

        // Get all the raceList where logging equals to UPDATED_LOGGING
        defaultRaceShouldNotBeFound("logging.in=" + UPDATED_LOGGING);
    }

    @Test
    @Transactional
    void getAllRacesByLoggingIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging is not null
        defaultRaceShouldBeFound("logging.specified=true");

        // Get all the raceList where logging is null
        defaultRaceShouldNotBeFound("logging.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByLoggingContainsSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging contains DEFAULT_LOGGING
        defaultRaceShouldBeFound("logging.contains=" + DEFAULT_LOGGING);

        // Get all the raceList where logging contains UPDATED_LOGGING
        defaultRaceShouldNotBeFound("logging.contains=" + UPDATED_LOGGING);
    }

    @Test
    @Transactional
    void getAllRacesByLoggingNotContainsSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where logging does not contain DEFAULT_LOGGING
        defaultRaceShouldNotBeFound("logging.doesNotContain=" + DEFAULT_LOGGING);

        // Get all the raceList where logging does not contain UPDATED_LOGGING
        defaultRaceShouldBeFound("logging.doesNotContain=" + UPDATED_LOGGING);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight equals to DEFAULT_WEIGHT
        defaultRaceShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight equals to UPDATED_WEIGHT
        defaultRaceShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight not equals to DEFAULT_WEIGHT
        defaultRaceShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight not equals to UPDATED_WEIGHT
        defaultRaceShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultRaceShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the raceList where weight equals to UPDATED_WEIGHT
        defaultRaceShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight is not null
        defaultRaceShouldBeFound("weight.specified=true");

        // Get all the raceList where weight is null
        defaultRaceShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultRaceShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight is greater than or equal to UPDATED_WEIGHT
        defaultRaceShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight is less than or equal to DEFAULT_WEIGHT
        defaultRaceShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight is less than or equal to SMALLER_WEIGHT
        defaultRaceShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight is less than DEFAULT_WEIGHT
        defaultRaceShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight is less than UPDATED_WEIGHT
        defaultRaceShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weight is greater than DEFAULT_WEIGHT
        defaultRaceShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the raceList where weight is greater than SMALLER_WEIGHT
        defaultRaceShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length equals to DEFAULT_LENGTH
        defaultRaceShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the raceList where length equals to UPDATED_LENGTH
        defaultRaceShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length not equals to DEFAULT_LENGTH
        defaultRaceShouldNotBeFound("length.notEquals=" + DEFAULT_LENGTH);

        // Get all the raceList where length not equals to UPDATED_LENGTH
        defaultRaceShouldBeFound("length.notEquals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultRaceShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the raceList where length equals to UPDATED_LENGTH
        defaultRaceShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length is not null
        defaultRaceShouldBeFound("length.specified=true");

        // Get all the raceList where length is null
        defaultRaceShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length is greater than or equal to DEFAULT_LENGTH
        defaultRaceShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the raceList where length is greater than or equal to UPDATED_LENGTH
        defaultRaceShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length is less than or equal to DEFAULT_LENGTH
        defaultRaceShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the raceList where length is less than or equal to SMALLER_LENGTH
        defaultRaceShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length is less than DEFAULT_LENGTH
        defaultRaceShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the raceList where length is less than UPDATED_LENGTH
        defaultRaceShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where length is greater than DEFAULT_LENGTH
        defaultRaceShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the raceList where length is greater than SMALLER_LENGTH
        defaultRaceShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature equals to DEFAULT_TEMPERATURE
        defaultRaceShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature equals to UPDATED_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature not equals to DEFAULT_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature not equals to UPDATED_TEMPERATURE
        defaultRaceShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultRaceShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the raceList where temperature equals to UPDATED_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature is not null
        defaultRaceShouldBeFound("temperature.specified=true");

        // Get all the raceList where temperature is null
        defaultRaceShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultRaceShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultRaceShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature is less than DEFAULT_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature is less than UPDATED_TEMPERATURE
        defaultRaceShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where temperature is greater than DEFAULT_TEMPERATURE
        defaultRaceShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the raceList where temperature is greater than SMALLER_TEMPERATURE
        defaultRaceShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp equals to DEFAULT_COMP
        defaultRaceShouldBeFound("comp.equals=" + DEFAULT_COMP);

        // Get all the raceList where comp equals to UPDATED_COMP
        defaultRaceShouldNotBeFound("comp.equals=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp not equals to DEFAULT_COMP
        defaultRaceShouldNotBeFound("comp.notEquals=" + DEFAULT_COMP);

        // Get all the raceList where comp not equals to UPDATED_COMP
        defaultRaceShouldBeFound("comp.notEquals=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp in DEFAULT_COMP or UPDATED_COMP
        defaultRaceShouldBeFound("comp.in=" + DEFAULT_COMP + "," + UPDATED_COMP);

        // Get all the raceList where comp equals to UPDATED_COMP
        defaultRaceShouldNotBeFound("comp.in=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp is not null
        defaultRaceShouldBeFound("comp.specified=true");

        // Get all the raceList where comp is null
        defaultRaceShouldNotBeFound("comp.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCompIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp is greater than or equal to DEFAULT_COMP
        defaultRaceShouldBeFound("comp.greaterThanOrEqual=" + DEFAULT_COMP);

        // Get all the raceList where comp is greater than or equal to UPDATED_COMP
        defaultRaceShouldNotBeFound("comp.greaterThanOrEqual=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp is less than or equal to DEFAULT_COMP
        defaultRaceShouldBeFound("comp.lessThanOrEqual=" + DEFAULT_COMP);

        // Get all the raceList where comp is less than or equal to SMALLER_COMP
        defaultRaceShouldNotBeFound("comp.lessThanOrEqual=" + SMALLER_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp is less than DEFAULT_COMP
        defaultRaceShouldNotBeFound("comp.lessThan=" + DEFAULT_COMP);

        // Get all the raceList where comp is less than UPDATED_COMP
        defaultRaceShouldBeFound("comp.lessThan=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacesByCompIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where comp is greater than DEFAULT_COMP
        defaultRaceShouldNotBeFound("comp.greaterThan=" + DEFAULT_COMP);

        // Get all the raceList where comp is greater than SMALLER_COMP
        defaultRaceShouldBeFound("comp.greaterThan=" + SMALLER_COMP);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration equals to DEFAULT_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.equals=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration equals to UPDATED_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.equals=" + UPDATED_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration not equals to DEFAULT_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.notEquals=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration not equals to UPDATED_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.notEquals=" + UPDATED_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration in DEFAULT_SWIM_DURATION or UPDATED_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.in=" + DEFAULT_SWIM_DURATION + "," + UPDATED_SWIM_DURATION);

        // Get all the raceList where swimDuration equals to UPDATED_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.in=" + UPDATED_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration is not null
        defaultRaceShouldBeFound("swimDuration.specified=true");

        // Get all the raceList where swimDuration is null
        defaultRaceShouldNotBeFound("swimDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration is greater than or equal to DEFAULT_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.greaterThanOrEqual=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration is greater than or equal to UPDATED_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.greaterThanOrEqual=" + UPDATED_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration is less than or equal to DEFAULT_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.lessThanOrEqual=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration is less than or equal to SMALLER_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.lessThanOrEqual=" + SMALLER_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration is less than DEFAULT_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.lessThan=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration is less than UPDATED_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.lessThan=" + UPDATED_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesBySwimDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where swimDuration is greater than DEFAULT_SWIM_DURATION
        defaultRaceShouldNotBeFound("swimDuration.greaterThan=" + DEFAULT_SWIM_DURATION);

        // Get all the raceList where swimDuration is greater than SMALLER_SWIM_DURATION
        defaultRaceShouldBeFound("swimDuration.greaterThan=" + SMALLER_SWIM_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration equals to DEFAULT_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.equals=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration equals to UPDATED_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.equals=" + UPDATED_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration not equals to DEFAULT_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.notEquals=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration not equals to UPDATED_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.notEquals=" + UPDATED_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration in DEFAULT_BIKE_DURATION or UPDATED_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.in=" + DEFAULT_BIKE_DURATION + "," + UPDATED_BIKE_DURATION);

        // Get all the raceList where bikeDuration equals to UPDATED_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.in=" + UPDATED_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration is not null
        defaultRaceShouldBeFound("bikeDuration.specified=true");

        // Get all the raceList where bikeDuration is null
        defaultRaceShouldNotBeFound("bikeDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration is greater than or equal to DEFAULT_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.greaterThanOrEqual=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration is greater than or equal to UPDATED_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.greaterThanOrEqual=" + UPDATED_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration is less than or equal to DEFAULT_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.lessThanOrEqual=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration is less than or equal to SMALLER_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.lessThanOrEqual=" + SMALLER_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration is less than DEFAULT_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.lessThan=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration is less than UPDATED_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.lessThan=" + UPDATED_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByBikeDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where bikeDuration is greater than DEFAULT_BIKE_DURATION
        defaultRaceShouldNotBeFound("bikeDuration.greaterThan=" + DEFAULT_BIKE_DURATION);

        // Get all the raceList where bikeDuration is greater than SMALLER_BIKE_DURATION
        defaultRaceShouldBeFound("bikeDuration.greaterThan=" + SMALLER_BIKE_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration equals to DEFAULT_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.equals=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration equals to UPDATED_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.equals=" + UPDATED_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration not equals to DEFAULT_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.notEquals=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration not equals to UPDATED_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.notEquals=" + UPDATED_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration in DEFAULT_RUN_DURATION or UPDATED_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.in=" + DEFAULT_RUN_DURATION + "," + UPDATED_RUN_DURATION);

        // Get all the raceList where runDuration equals to UPDATED_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.in=" + UPDATED_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration is not null
        defaultRaceShouldBeFound("runDuration.specified=true");

        // Get all the raceList where runDuration is null
        defaultRaceShouldNotBeFound("runDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration is greater than or equal to DEFAULT_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.greaterThanOrEqual=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration is greater than or equal to UPDATED_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.greaterThanOrEqual=" + UPDATED_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration is less than or equal to DEFAULT_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.lessThanOrEqual=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration is less than or equal to SMALLER_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.lessThanOrEqual=" + SMALLER_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration is less than DEFAULT_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.lessThan=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration is less than UPDATED_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.lessThan=" + UPDATED_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByRunDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where runDuration is greater than DEFAULT_RUN_DURATION
        defaultRaceShouldNotBeFound("runDuration.greaterThan=" + DEFAULT_RUN_DURATION);

        // Get all the raceList where runDuration is greater than SMALLER_RUN_DURATION
        defaultRaceShouldBeFound("runDuration.greaterThan=" + SMALLER_RUN_DURATION);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo equals to DEFAULT_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.equals=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo equals to UPDATED_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.equals=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo not equals to DEFAULT_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.notEquals=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo not equals to UPDATED_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.notEquals=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo in DEFAULT_GEL_CARBO or UPDATED_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.in=" + DEFAULT_GEL_CARBO + "," + UPDATED_GEL_CARBO);

        // Get all the raceList where gelCarbo equals to UPDATED_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.in=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo is not null
        defaultRaceShouldBeFound("gelCarbo.specified=true");

        // Get all the raceList where gelCarbo is null
        defaultRaceShouldNotBeFound("gelCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo is greater than or equal to DEFAULT_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.greaterThanOrEqual=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo is greater than or equal to UPDATED_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.greaterThanOrEqual=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo is less than or equal to DEFAULT_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.lessThanOrEqual=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo is less than or equal to SMALLER_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.lessThanOrEqual=" + SMALLER_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo is less than DEFAULT_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.lessThan=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo is less than UPDATED_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.lessThan=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelCarbo is greater than DEFAULT_GEL_CARBO
        defaultRaceShouldNotBeFound("gelCarbo.greaterThan=" + DEFAULT_GEL_CARBO);

        // Get all the raceList where gelCarbo is greater than SMALLER_GEL_CARBO
        defaultRaceShouldBeFound("gelCarbo.greaterThan=" + SMALLER_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo equals to DEFAULT_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.equals=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo equals to UPDATED_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.equals=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo not equals to DEFAULT_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.notEquals=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo not equals to UPDATED_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.notEquals=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo in DEFAULT_DRINK_CARBO or UPDATED_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.in=" + DEFAULT_DRINK_CARBO + "," + UPDATED_DRINK_CARBO);

        // Get all the raceList where drinkCarbo equals to UPDATED_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.in=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo is not null
        defaultRaceShouldBeFound("drinkCarbo.specified=true");

        // Get all the raceList where drinkCarbo is null
        defaultRaceShouldNotBeFound("drinkCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo is greater than or equal to DEFAULT_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.greaterThanOrEqual=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo is greater than or equal to UPDATED_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.greaterThanOrEqual=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo is less than or equal to DEFAULT_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.lessThanOrEqual=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo is less than or equal to SMALLER_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.lessThanOrEqual=" + SMALLER_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo is less than DEFAULT_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.lessThan=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo is less than UPDATED_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.lessThan=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkCarbo is greater than DEFAULT_DRINK_CARBO
        defaultRaceShouldNotBeFound("drinkCarbo.greaterThan=" + DEFAULT_DRINK_CARBO);

        // Get all the raceList where drinkCarbo is greater than SMALLER_DRINK_CARBO
        defaultRaceShouldBeFound("drinkCarbo.greaterThan=" + SMALLER_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo equals to DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.equals=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo equals to UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.equals=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo not equals to DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.notEquals=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo not equals to UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.notEquals=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo in DEFAULT_DRINK_ORG_CARBO or UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.in=" + DEFAULT_DRINK_ORG_CARBO + "," + UPDATED_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo equals to UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.in=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo is not null
        defaultRaceShouldBeFound("drinkOrgCarbo.specified=true");

        // Get all the raceList where drinkOrgCarbo is null
        defaultRaceShouldNotBeFound("drinkOrgCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo is greater than or equal to DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.greaterThanOrEqual=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo is greater than or equal to UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.greaterThanOrEqual=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo is less than or equal to DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.lessThanOrEqual=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo is less than or equal to SMALLER_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.lessThanOrEqual=" + SMALLER_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo is less than DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.lessThan=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo is less than UPDATED_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.lessThan=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDrinkOrgCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where drinkOrgCarbo is greater than DEFAULT_DRINK_ORG_CARBO
        defaultRaceShouldNotBeFound("drinkOrgCarbo.greaterThan=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the raceList where drinkOrgCarbo is greater than SMALLER_DRINK_ORG_CARBO
        defaultRaceShouldBeFound("drinkOrgCarbo.greaterThan=" + SMALLER_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo equals to DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.equals=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo equals to UPDATED_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.equals=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo not equals to DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.notEquals=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo not equals to UPDATED_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.notEquals=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo in DEFAULT_GEL_ORG_CARBO or UPDATED_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.in=" + DEFAULT_GEL_ORG_CARBO + "," + UPDATED_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo equals to UPDATED_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.in=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo is not null
        defaultRaceShouldBeFound("gelOrgCarbo.specified=true");

        // Get all the raceList where gelOrgCarbo is null
        defaultRaceShouldNotBeFound("gelOrgCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo is greater than or equal to DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.greaterThanOrEqual=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo is greater than or equal to UPDATED_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.greaterThanOrEqual=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo is less than or equal to DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.lessThanOrEqual=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo is less than or equal to SMALLER_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.lessThanOrEqual=" + SMALLER_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo is less than DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.lessThan=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo is less than UPDATED_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.lessThan=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelOrgCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelOrgCarbo is greater than DEFAULT_GEL_ORG_CARBO
        defaultRaceShouldNotBeFound("gelOrgCarbo.greaterThan=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the raceList where gelOrgCarbo is greater than SMALLER_GEL_ORG_CARBO
        defaultRaceShouldBeFound("gelOrgCarbo.greaterThan=" + SMALLER_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike equals to DEFAULT_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.equals=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike equals to UPDATED_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.equals=" + UPDATED_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike not equals to DEFAULT_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.notEquals=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike not equals to UPDATED_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.notEquals=" + UPDATED_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike in DEFAULT_GELSBIKE or UPDATED_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.in=" + DEFAULT_GELSBIKE + "," + UPDATED_GELSBIKE);

        // Get all the raceList where gelsbike equals to UPDATED_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.in=" + UPDATED_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike is not null
        defaultRaceShouldBeFound("gelsbike.specified=true");

        // Get all the raceList where gelsbike is null
        defaultRaceShouldNotBeFound("gelsbike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike is greater than or equal to DEFAULT_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.greaterThanOrEqual=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike is greater than or equal to UPDATED_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.greaterThanOrEqual=" + UPDATED_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike is less than or equal to DEFAULT_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.lessThanOrEqual=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike is less than or equal to SMALLER_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.lessThanOrEqual=" + SMALLER_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike is less than DEFAULT_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.lessThan=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike is less than UPDATED_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.lessThan=" + UPDATED_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsbikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsbike is greater than DEFAULT_GELSBIKE
        defaultRaceShouldNotBeFound("gelsbike.greaterThan=" + DEFAULT_GELSBIKE);

        // Get all the raceList where gelsbike is greater than SMALLER_GELSBIKE
        defaultRaceShouldBeFound("gelsbike.greaterThan=" + SMALLER_GELSBIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun equals to DEFAULT_GELSRUN
        defaultRaceShouldBeFound("gelsrun.equals=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun equals to UPDATED_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.equals=" + UPDATED_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun not equals to DEFAULT_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.notEquals=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun not equals to UPDATED_GELSRUN
        defaultRaceShouldBeFound("gelsrun.notEquals=" + UPDATED_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun in DEFAULT_GELSRUN or UPDATED_GELSRUN
        defaultRaceShouldBeFound("gelsrun.in=" + DEFAULT_GELSRUN + "," + UPDATED_GELSRUN);

        // Get all the raceList where gelsrun equals to UPDATED_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.in=" + UPDATED_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun is not null
        defaultRaceShouldBeFound("gelsrun.specified=true");

        // Get all the raceList where gelsrun is null
        defaultRaceShouldNotBeFound("gelsrun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun is greater than or equal to DEFAULT_GELSRUN
        defaultRaceShouldBeFound("gelsrun.greaterThanOrEqual=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun is greater than or equal to UPDATED_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.greaterThanOrEqual=" + UPDATED_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun is less than or equal to DEFAULT_GELSRUN
        defaultRaceShouldBeFound("gelsrun.lessThanOrEqual=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun is less than or equal to SMALLER_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.lessThanOrEqual=" + SMALLER_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun is less than DEFAULT_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.lessThan=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun is less than UPDATED_GELSRUN
        defaultRaceShouldBeFound("gelsrun.lessThan=" + UPDATED_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsrunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsrun is greater than DEFAULT_GELSRUN
        defaultRaceShouldNotBeFound("gelsrun.greaterThan=" + DEFAULT_GELSRUN);

        // Get all the raceList where gelsrun is greater than SMALLER_GELSRUN
        defaultRaceShouldBeFound("gelsrun.greaterThan=" + SMALLER_GELSRUN);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgGelQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgGelQuery equals to DEFAULT_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldBeFound("selectedOrgGelQuery.equals=" + DEFAULT_SELECTED_ORG_GEL_QUERY);

        // Get all the raceList where selectedOrgGelQuery equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldNotBeFound("selectedOrgGelQuery.equals=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgGelQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgGelQuery not equals to DEFAULT_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldNotBeFound("selectedOrgGelQuery.notEquals=" + DEFAULT_SELECTED_ORG_GEL_QUERY);

        // Get all the raceList where selectedOrgGelQuery not equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldBeFound("selectedOrgGelQuery.notEquals=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgGelQueryIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgGelQuery in DEFAULT_SELECTED_ORG_GEL_QUERY or UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldBeFound("selectedOrgGelQuery.in=" + DEFAULT_SELECTED_ORG_GEL_QUERY + "," + UPDATED_SELECTED_ORG_GEL_QUERY);

        // Get all the raceList where selectedOrgGelQuery equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRaceShouldNotBeFound("selectedOrgGelQuery.in=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgGelQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgGelQuery is not null
        defaultRaceShouldBeFound("selectedOrgGelQuery.specified=true");

        // Get all the raceList where selectedOrgGelQuery is null
        defaultRaceShouldNotBeFound("selectedOrgGelQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgDrankQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgDrankQuery equals to DEFAULT_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldBeFound("selectedOrgDrankQuery.equals=" + DEFAULT_SELECTED_ORG_DRANK_QUERY);

        // Get all the raceList where selectedOrgDrankQuery equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldNotBeFound("selectedOrgDrankQuery.equals=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgDrankQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgDrankQuery not equals to DEFAULT_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldNotBeFound("selectedOrgDrankQuery.notEquals=" + DEFAULT_SELECTED_ORG_DRANK_QUERY);

        // Get all the raceList where selectedOrgDrankQuery not equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldBeFound("selectedOrgDrankQuery.notEquals=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgDrankQueryIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgDrankQuery in DEFAULT_SELECTED_ORG_DRANK_QUERY or UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldBeFound("selectedOrgDrankQuery.in=" + DEFAULT_SELECTED_ORG_DRANK_QUERY + "," + UPDATED_SELECTED_ORG_DRANK_QUERY);

        // Get all the raceList where selectedOrgDrankQuery equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRaceShouldNotBeFound("selectedOrgDrankQuery.in=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacesBySelectedOrgDrankQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where selectedOrgDrankQuery is not null
        defaultRaceShouldBeFound("selectedOrgDrankQuery.specified=true");

        // Get all the raceList where selectedOrgDrankQuery is null
        defaultRaceShouldNotBeFound("selectedOrgDrankQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike equals to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.equals=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.equals=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike not equals to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.notEquals=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike not equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.notEquals=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike in DEFAULT_SPORT_DRINK_ORG_BIKE or UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.in=" + DEFAULT_SPORT_DRINK_ORG_BIKE + "," + UPDATED_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.in=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike is not null
        defaultRaceShouldBeFound("sportDrinkOrgBike.specified=true");

        // Get all the raceList where sportDrinkOrgBike is null
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike is greater than or equal to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike is greater than or equal to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike is less than or equal to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike is less than or equal to SMALLER_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.lessThanOrEqual=" + SMALLER_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike is less than DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.lessThan=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike is less than UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.lessThan=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgBike is greater than DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldNotBeFound("sportDrinkOrgBike.greaterThan=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the raceList where sportDrinkOrgBike is greater than SMALLER_SPORT_DRINK_ORG_BIKE
        defaultRaceShouldBeFound("sportDrinkOrgBike.greaterThan=" + SMALLER_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike equals to DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.equals=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike equals to UPDATED_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.equals=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike not equals to DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.notEquals=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike not equals to UPDATED_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.notEquals=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike in DEFAULT_WATER_ORG_BIKE or UPDATED_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.in=" + DEFAULT_WATER_ORG_BIKE + "," + UPDATED_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike equals to UPDATED_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.in=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike is not null
        defaultRaceShouldBeFound("waterOrgBike.specified=true");

        // Get all the raceList where waterOrgBike is null
        defaultRaceShouldNotBeFound("waterOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike is greater than or equal to DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.greaterThanOrEqual=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike is greater than or equal to UPDATED_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.greaterThanOrEqual=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike is less than or equal to DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.lessThanOrEqual=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike is less than or equal to SMALLER_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.lessThanOrEqual=" + SMALLER_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike is less than DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.lessThan=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike is less than UPDATED_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.lessThan=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgBike is greater than DEFAULT_WATER_ORG_BIKE
        defaultRaceShouldNotBeFound("waterOrgBike.greaterThan=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the raceList where waterOrgBike is greater than SMALLER_WATER_ORG_BIKE
        defaultRaceShouldBeFound("waterOrgBike.greaterThan=" + SMALLER_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike equals to DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.equals=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike equals to UPDATED_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.equals=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike not equals to DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.notEquals=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike not equals to UPDATED_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.notEquals=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike in DEFAULT_GELS_ORG_BIKE or UPDATED_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.in=" + DEFAULT_GELS_ORG_BIKE + "," + UPDATED_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike equals to UPDATED_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.in=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike is not null
        defaultRaceShouldBeFound("gelsOrgBike.specified=true");

        // Get all the raceList where gelsOrgBike is null
        defaultRaceShouldNotBeFound("gelsOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike is greater than or equal to DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.greaterThanOrEqual=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike is greater than or equal to UPDATED_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.greaterThanOrEqual=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike is less than or equal to DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.lessThanOrEqual=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike is less than or equal to SMALLER_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.lessThanOrEqual=" + SMALLER_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike is less than DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.lessThan=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike is less than UPDATED_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.lessThan=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgBike is greater than DEFAULT_GELS_ORG_BIKE
        defaultRaceShouldNotBeFound("gelsOrgBike.greaterThan=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the raceList where gelsOrgBike is greater than SMALLER_GELS_ORG_BIKE
        defaultRaceShouldBeFound("gelsOrgBike.greaterThan=" + SMALLER_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun equals to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.equals=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.equals=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun not equals to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.notEquals=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun not equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.notEquals=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun in DEFAULT_SPORT_DRINK_ORG_RUN or UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.in=" + DEFAULT_SPORT_DRINK_ORG_RUN + "," + UPDATED_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.in=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun is not null
        defaultRaceShouldBeFound("sportDrinkOrgRun.specified=true");

        // Get all the raceList where sportDrinkOrgRun is null
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun is greater than or equal to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun is greater than or equal to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun is less than or equal to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun is less than or equal to SMALLER_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.lessThanOrEqual=" + SMALLER_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun is less than DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.lessThan=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun is less than UPDATED_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.lessThan=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkOrgRun is greater than DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRaceShouldNotBeFound("sportDrinkOrgRun.greaterThan=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the raceList where sportDrinkOrgRun is greater than SMALLER_SPORT_DRINK_ORG_RUN
        defaultRaceShouldBeFound("sportDrinkOrgRun.greaterThan=" + SMALLER_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun equals to DEFAULT_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.equals=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun equals to UPDATED_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.equals=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun not equals to DEFAULT_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.notEquals=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun not equals to UPDATED_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.notEquals=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun in DEFAULT_WATER_ORG_RUN or UPDATED_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.in=" + DEFAULT_WATER_ORG_RUN + "," + UPDATED_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun equals to UPDATED_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.in=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun is not null
        defaultRaceShouldBeFound("waterOrgRun.specified=true");

        // Get all the raceList where waterOrgRun is null
        defaultRaceShouldNotBeFound("waterOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun is greater than or equal to DEFAULT_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.greaterThanOrEqual=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun is greater than or equal to UPDATED_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.greaterThanOrEqual=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun is less than or equal to DEFAULT_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.lessThanOrEqual=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun is less than or equal to SMALLER_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.lessThanOrEqual=" + SMALLER_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun is less than DEFAULT_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.lessThan=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun is less than UPDATED_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.lessThan=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterOrgRun is greater than DEFAULT_WATER_ORG_RUN
        defaultRaceShouldNotBeFound("waterOrgRun.greaterThan=" + DEFAULT_WATER_ORG_RUN);

        // Get all the raceList where waterOrgRun is greater than SMALLER_WATER_ORG_RUN
        defaultRaceShouldBeFound("waterOrgRun.greaterThan=" + SMALLER_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun equals to DEFAULT_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.equals=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun equals to UPDATED_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.equals=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun not equals to DEFAULT_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.notEquals=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun not equals to UPDATED_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.notEquals=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun in DEFAULT_GELS_ORG_RUN or UPDATED_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.in=" + DEFAULT_GELS_ORG_RUN + "," + UPDATED_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun equals to UPDATED_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.in=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun is not null
        defaultRaceShouldBeFound("gelsOrgRun.specified=true");

        // Get all the raceList where gelsOrgRun is null
        defaultRaceShouldNotBeFound("gelsOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun is greater than or equal to DEFAULT_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.greaterThanOrEqual=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun is greater than or equal to UPDATED_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.greaterThanOrEqual=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun is less than or equal to DEFAULT_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.lessThanOrEqual=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun is less than or equal to SMALLER_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.lessThanOrEqual=" + SMALLER_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun is less than DEFAULT_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.lessThan=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun is less than UPDATED_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.lessThan=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsOrgRun is greater than DEFAULT_GELS_ORG_RUN
        defaultRaceShouldNotBeFound("gelsOrgRun.greaterThan=" + DEFAULT_GELS_ORG_RUN);

        // Get all the raceList where gelsOrgRun is greater than SMALLER_GELS_ORG_RUN
        defaultRaceShouldBeFound("gelsOrgRun.greaterThan=" + SMALLER_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart equals to DEFAULT_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.equals=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart equals to UPDATED_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.equals=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart not equals to DEFAULT_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.notEquals=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart not equals to UPDATED_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.notEquals=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart in DEFAULT_ORS_BEFORE_START or UPDATED_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.in=" + DEFAULT_ORS_BEFORE_START + "," + UPDATED_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart equals to UPDATED_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.in=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart is not null
        defaultRaceShouldBeFound("orsBeforeStart.specified=true");

        // Get all the raceList where orsBeforeStart is null
        defaultRaceShouldNotBeFound("orsBeforeStart.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart is greater than or equal to DEFAULT_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.greaterThanOrEqual=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart is greater than or equal to UPDATED_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.greaterThanOrEqual=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart is less than or equal to DEFAULT_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.lessThanOrEqual=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart is less than or equal to SMALLER_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.lessThanOrEqual=" + SMALLER_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart is less than DEFAULT_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.lessThan=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart is less than UPDATED_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.lessThan=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesByOrsBeforeStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsBeforeStart is greater than DEFAULT_ORS_BEFORE_START
        defaultRaceShouldNotBeFound("orsBeforeStart.greaterThan=" + DEFAULT_ORS_BEFORE_START);

        // Get all the raceList where orsBeforeStart is greater than SMALLER_ORS_BEFORE_START
        defaultRaceShouldBeFound("orsBeforeStart.greaterThan=" + SMALLER_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike equals to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.equals=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.equals=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike not equals to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.notEquals=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike not equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.notEquals=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike in DEFAULT_SPORT_DRINK_TO_TAKE_BIKE or UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.in=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE + "," + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.in=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike is not null
        defaultRaceShouldBeFound("sportDrinkToTakeBike.specified=true");

        // Get all the raceList where sportDrinkToTakeBike is null
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike is greater than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike is greater than or equal to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike is less than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike is less than or equal to SMALLER_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.lessThanOrEqual=" + SMALLER_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike is less than DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.lessThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike is less than UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.lessThan=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeBike is greater than DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("sportDrinkToTakeBike.greaterThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the raceList where sportDrinkToTakeBike is greater than SMALLER_SPORT_DRINK_TO_TAKE_BIKE
        defaultRaceShouldBeFound("sportDrinkToTakeBike.greaterThan=" + SMALLER_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike equals to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.equals=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.equals=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike not equals to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.notEquals=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike not equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.notEquals=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike in DEFAULT_WATER_TO_TAKE_BIKE or UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.in=" + DEFAULT_WATER_TO_TAKE_BIKE + "," + UPDATED_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.in=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike is not null
        defaultRaceShouldBeFound("waterToTakeBike.specified=true");

        // Get all the raceList where waterToTakeBike is null
        defaultRaceShouldNotBeFound("waterToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike is greater than or equal to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.greaterThanOrEqual=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike is greater than or equal to UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.greaterThanOrEqual=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike is less than or equal to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.lessThanOrEqual=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike is less than or equal to SMALLER_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.lessThanOrEqual=" + SMALLER_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike is less than DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.lessThan=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike is less than UPDATED_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.lessThan=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeBike is greater than DEFAULT_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("waterToTakeBike.greaterThan=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the raceList where waterToTakeBike is greater than SMALLER_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("waterToTakeBike.greaterThan=" + SMALLER_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike equals to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.equals=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.equals=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike not equals to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.notEquals=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike not equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.notEquals=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike in DEFAULT_EXTRA_WATER_TO_TAKE_BIKE or UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.in=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE + "," + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.in=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike is not null
        defaultRaceShouldBeFound("extraWaterToTakeBike.specified=true");

        // Get all the raceList where extraWaterToTakeBike is null
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike is greater than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.greaterThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike is greater than or equal to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.greaterThanOrEqual=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike is less than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.lessThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike is less than or equal to SMALLER_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.lessThanOrEqual=" + SMALLER_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike is less than DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.lessThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike is less than UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.lessThan=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeBike is greater than DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("extraWaterToTakeBike.greaterThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the raceList where extraWaterToTakeBike is greater than SMALLER_EXTRA_WATER_TO_TAKE_BIKE
        defaultRaceShouldBeFound("extraWaterToTakeBike.greaterThan=" + SMALLER_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike equals to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.equals=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.equals=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike not equals to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.notEquals=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike not equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.notEquals=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike in DEFAULT_ORS_TO_TAKE_BIKE or UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.in=" + DEFAULT_ORS_TO_TAKE_BIKE + "," + UPDATED_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.in=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike is not null
        defaultRaceShouldBeFound("orsToTakeBike.specified=true");

        // Get all the raceList where orsToTakeBike is null
        defaultRaceShouldNotBeFound("orsToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike is greater than or equal to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.greaterThanOrEqual=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike is greater than or equal to UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.greaterThanOrEqual=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike is less than or equal to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.lessThanOrEqual=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike is less than or equal to SMALLER_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.lessThanOrEqual=" + SMALLER_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike is less than DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.lessThan=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike is less than UPDATED_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.lessThan=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeBike is greater than DEFAULT_ORS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("orsToTakeBike.greaterThan=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the raceList where orsToTakeBike is greater than SMALLER_ORS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("orsToTakeBike.greaterThan=" + SMALLER_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike equals to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.equals=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.equals=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike not equals to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.notEquals=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike not equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.notEquals=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike in DEFAULT_GELS_TO_TAKE_BIKE or UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.in=" + DEFAULT_GELS_TO_TAKE_BIKE + "," + UPDATED_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.in=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike is not null
        defaultRaceShouldBeFound("gelsToTakeBike.specified=true");

        // Get all the raceList where gelsToTakeBike is null
        defaultRaceShouldNotBeFound("gelsToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike is greater than or equal to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.greaterThanOrEqual=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike is greater than or equal to UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.greaterThanOrEqual=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike is less than or equal to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.lessThanOrEqual=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike is less than or equal to SMALLER_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.lessThanOrEqual=" + SMALLER_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike is less than DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.lessThan=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike is less than UPDATED_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.lessThan=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeBike is greater than DEFAULT_GELS_TO_TAKE_BIKE
        defaultRaceShouldNotBeFound("gelsToTakeBike.greaterThan=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the raceList where gelsToTakeBike is greater than SMALLER_GELS_TO_TAKE_BIKE
        defaultRaceShouldBeFound("gelsToTakeBike.greaterThan=" + SMALLER_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun equals to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.equals=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.equals=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun not equals to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.notEquals=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun not equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.notEquals=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun in DEFAULT_SPORT_DRINK_TO_TAKE_RUN or UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.in=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN + "," + UPDATED_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.in=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun is not null
        defaultRaceShouldBeFound("sportDrinkToTakeRun.specified=true");

        // Get all the raceList where sportDrinkToTakeRun is null
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun is greater than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun is greater than or equal to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun is less than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun is less than or equal to SMALLER_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.lessThanOrEqual=" + SMALLER_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun is less than DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.lessThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun is less than UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.lessThan=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesBySportDrinkToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where sportDrinkToTakeRun is greater than DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("sportDrinkToTakeRun.greaterThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the raceList where sportDrinkToTakeRun is greater than SMALLER_SPORT_DRINK_TO_TAKE_RUN
        defaultRaceShouldBeFound("sportDrinkToTakeRun.greaterThan=" + SMALLER_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun equals to DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.equals=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.equals=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun not equals to DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.notEquals=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun not equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.notEquals=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun in DEFAULT_WATER_TO_TAKE_RUN or UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.in=" + DEFAULT_WATER_TO_TAKE_RUN + "," + UPDATED_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.in=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun is not null
        defaultRaceShouldBeFound("waterToTakeRun.specified=true");

        // Get all the raceList where waterToTakeRun is null
        defaultRaceShouldNotBeFound("waterToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun is greater than or equal to DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.greaterThanOrEqual=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun is greater than or equal to UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.greaterThanOrEqual=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun is less than or equal to DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.lessThanOrEqual=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun is less than or equal to SMALLER_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.lessThanOrEqual=" + SMALLER_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun is less than DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.lessThan=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun is less than UPDATED_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.lessThan=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWaterToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where waterToTakeRun is greater than DEFAULT_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("waterToTakeRun.greaterThan=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the raceList where waterToTakeRun is greater than SMALLER_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("waterToTakeRun.greaterThan=" + SMALLER_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun equals to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.equals=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.equals=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun not equals to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.notEquals=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun not equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.notEquals=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun in DEFAULT_EXTRA_WATER_TO_TAKE_RUN or UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.in=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN + "," + UPDATED_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.in=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun is not null
        defaultRaceShouldBeFound("extraWaterToTakeRun.specified=true");

        // Get all the raceList where extraWaterToTakeRun is null
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun is greater than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.greaterThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun is greater than or equal to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.greaterThanOrEqual=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun is less than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.lessThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun is less than or equal to SMALLER_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.lessThanOrEqual=" + SMALLER_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun is less than DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.lessThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun is less than UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.lessThan=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByExtraWaterToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where extraWaterToTakeRun is greater than DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("extraWaterToTakeRun.greaterThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the raceList where extraWaterToTakeRun is greater than SMALLER_EXTRA_WATER_TO_TAKE_RUN
        defaultRaceShouldBeFound("extraWaterToTakeRun.greaterThan=" + SMALLER_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun equals to DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.equals=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.equals=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun not equals to DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.notEquals=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun not equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.notEquals=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun in DEFAULT_ORS_TO_TAKE_RUN or UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.in=" + DEFAULT_ORS_TO_TAKE_RUN + "," + UPDATED_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.in=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun is not null
        defaultRaceShouldBeFound("orsToTakeRun.specified=true");

        // Get all the raceList where orsToTakeRun is null
        defaultRaceShouldNotBeFound("orsToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun is greater than or equal to DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.greaterThanOrEqual=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun is greater than or equal to UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.greaterThanOrEqual=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun is less than or equal to DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.lessThanOrEqual=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun is less than or equal to SMALLER_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.lessThanOrEqual=" + SMALLER_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun is less than DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.lessThan=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun is less than UPDATED_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.lessThan=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByOrsToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where orsToTakeRun is greater than DEFAULT_ORS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("orsToTakeRun.greaterThan=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the raceList where orsToTakeRun is greater than SMALLER_ORS_TO_TAKE_RUN
        defaultRaceShouldBeFound("orsToTakeRun.greaterThan=" + SMALLER_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin equals to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.equals=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin not equals to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin not equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin in DEFAULT_CARBO_NEED_DURING_BIKE_MIN or UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound(
            "carboNeedDuringBikeMin.in=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN + "," + UPDATED_CARBO_NEED_DURING_BIKE_MIN
        );

        // Get all the raceList where carboNeedDuringBikeMin equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.in=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin is not null
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.specified=true");

        // Get all the raceList where carboNeedDuringBikeMin is null
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin is less than DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin is less than UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMin is greater than DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMin.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the raceList where carboNeedDuringBikeMin is greater than SMALLER_CARBO_NEED_DURING_BIKE_MIN
        defaultRaceShouldBeFound("carboNeedDuringBikeMin.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax equals to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.equals=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax not equals to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax not equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax in DEFAULT_CARBO_NEED_DURING_BIKE_MAX or UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound(
            "carboNeedDuringBikeMax.in=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX + "," + UPDATED_CARBO_NEED_DURING_BIKE_MAX
        );

        // Get all the raceList where carboNeedDuringBikeMax equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.in=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax is not null
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.specified=true");

        // Get all the raceList where carboNeedDuringBikeMax is null
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax is less than DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax is less than UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBikeMax is greater than DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringBikeMax.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the raceList where carboNeedDuringBikeMax is greater than SMALLER_CARBO_NEED_DURING_BIKE_MAX
        defaultRaceShouldBeFound("carboNeedDuringBikeMax.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin equals to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.equals=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.equals=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin not equals to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin not equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin in DEFAULT_CARBO_NEED_DURING_RUN_MIN or UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.in=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN + "," + UPDATED_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.in=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin is not null
        defaultRaceShouldBeFound("carboNeedDuringRunMin.specified=true");

        // Get all the raceList where carboNeedDuringRunMin is null
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin is less than or equal to SMALLER_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin is less than DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin is less than UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMin is greater than DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldNotBeFound("carboNeedDuringRunMin.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the raceList where carboNeedDuringRunMin is greater than SMALLER_CARBO_NEED_DURING_RUN_MIN
        defaultRaceShouldBeFound("carboNeedDuringRunMin.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax equals to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.equals=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.equals=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax not equals to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax not equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax in DEFAULT_CARBO_NEED_DURING_RUN_MAX or UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.in=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX + "," + UPDATED_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.in=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax is not null
        defaultRaceShouldBeFound("carboNeedDuringRunMax.specified=true");

        // Get all the raceList where carboNeedDuringRunMax is null
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax is less than or equal to SMALLER_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax is less than DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax is less than UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRunMax is greater than DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldNotBeFound("carboNeedDuringRunMax.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the raceList where carboNeedDuringRunMax is greater than SMALLER_CARBO_NEED_DURING_RUN_MAX
        defaultRaceShouldBeFound("carboNeedDuringRunMax.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun equals to DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.equals=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun equals to UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.equals=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun not equals to DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.notEquals=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun not equals to UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.notEquals=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun in DEFAULT_DIFF_CARBO_RUN or UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.in=" + DEFAULT_DIFF_CARBO_RUN + "," + UPDATED_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun equals to UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.in=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun is not null
        defaultRaceShouldBeFound("diffCarboRun.specified=true");

        // Get all the raceList where diffCarboRun is null
        defaultRaceShouldNotBeFound("diffCarboRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun is greater than or equal to DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.greaterThanOrEqual=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun is greater than or equal to UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.greaterThanOrEqual=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun is less than or equal to DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.lessThanOrEqual=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun is less than or equal to SMALLER_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.lessThanOrEqual=" + SMALLER_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun is less than DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.lessThan=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun is less than UPDATED_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.lessThan=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffCarboRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffCarboRun is greater than DEFAULT_DIFF_CARBO_RUN
        defaultRaceShouldNotBeFound("diffCarboRun.greaterThan=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the raceList where diffCarboRun is greater than SMALLER_DIFF_CARBO_RUN
        defaultRaceShouldBeFound("diffCarboRun.greaterThan=" + SMALLER_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun equals to DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.equals=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun equals to UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.equals=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun not equals to DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.notEquals=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun not equals to UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.notEquals=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun in DEFAULT_DIFF_MOISTER_RUN or UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.in=" + DEFAULT_DIFF_MOISTER_RUN + "," + UPDATED_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun equals to UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.in=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun is not null
        defaultRaceShouldBeFound("diffMoisterRun.specified=true");

        // Get all the raceList where diffMoisterRun is null
        defaultRaceShouldNotBeFound("diffMoisterRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun is greater than or equal to DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.greaterThanOrEqual=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun is greater than or equal to UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.greaterThanOrEqual=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun is less than or equal to DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.lessThanOrEqual=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun is less than or equal to SMALLER_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.lessThanOrEqual=" + SMALLER_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun is less than DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.lessThan=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun is less than UPDATED_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.lessThan=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDiffMoisterRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where diffMoisterRun is greater than DEFAULT_DIFF_MOISTER_RUN
        defaultRaceShouldNotBeFound("diffMoisterRun.greaterThan=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the raceList where diffMoisterRun is greater than SMALLER_DIFF_MOISTER_RUN
        defaultRaceShouldBeFound("diffMoisterRun.greaterThan=" + SMALLER_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo equals to DEFAULT_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.equals=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo equals to UPDATED_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.equals=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo not equals to DEFAULT_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.notEquals=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo not equals to UPDATED_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.notEquals=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo in DEFAULT_DIF_CARBO or UPDATED_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.in=" + DEFAULT_DIF_CARBO + "," + UPDATED_DIF_CARBO);

        // Get all the raceList where difCarbo equals to UPDATED_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.in=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo is not null
        defaultRaceShouldBeFound("difCarbo.specified=true");

        // Get all the raceList where difCarbo is null
        defaultRaceShouldNotBeFound("difCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo is greater than or equal to DEFAULT_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.greaterThanOrEqual=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo is greater than or equal to UPDATED_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.greaterThanOrEqual=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo is less than or equal to DEFAULT_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.lessThanOrEqual=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo is less than or equal to SMALLER_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.lessThanOrEqual=" + SMALLER_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo is less than DEFAULT_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.lessThan=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo is less than UPDATED_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.lessThan=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difCarbo is greater than DEFAULT_DIF_CARBO
        defaultRaceShouldNotBeFound("difCarbo.greaterThan=" + DEFAULT_DIF_CARBO);

        // Get all the raceList where difCarbo is greater than SMALLER_DIF_CARBO
        defaultRaceShouldBeFound("difCarbo.greaterThan=" + SMALLER_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister equals to DEFAULT_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.equals=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister equals to UPDATED_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.equals=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister not equals to DEFAULT_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.notEquals=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister not equals to UPDATED_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.notEquals=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister in DEFAULT_DIF_MOISTER or UPDATED_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.in=" + DEFAULT_DIF_MOISTER + "," + UPDATED_DIF_MOISTER);

        // Get all the raceList where difMoister equals to UPDATED_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.in=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister is not null
        defaultRaceShouldBeFound("difMoister.specified=true");

        // Get all the raceList where difMoister is null
        defaultRaceShouldNotBeFound("difMoister.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister is greater than or equal to DEFAULT_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.greaterThanOrEqual=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister is greater than or equal to UPDATED_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.greaterThanOrEqual=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister is less than or equal to DEFAULT_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.lessThanOrEqual=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister is less than or equal to SMALLER_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.lessThanOrEqual=" + SMALLER_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister is less than DEFAULT_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.lessThan=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister is less than UPDATED_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.lessThan=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByDifMoisterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where difMoister is greater than DEFAULT_DIF_MOISTER
        defaultRaceShouldNotBeFound("difMoister.greaterThan=" + DEFAULT_DIF_MOISTER);

        // Get all the raceList where difMoister is greater than SMALLER_DIF_MOISTER
        defaultRaceShouldBeFound("difMoister.greaterThan=" + SMALLER_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun equals to DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.equals=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.equals=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun not equals to DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.notEquals=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun not equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.notEquals=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun in DEFAULT_GELS_TO_TAKE_RUN or UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.in=" + DEFAULT_GELS_TO_TAKE_RUN + "," + UPDATED_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.in=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun is not null
        defaultRaceShouldBeFound("gelsToTakeRun.specified=true");

        // Get all the raceList where gelsToTakeRun is null
        defaultRaceShouldNotBeFound("gelsToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun is greater than or equal to DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.greaterThanOrEqual=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun is greater than or equal to UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.greaterThanOrEqual=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun is less than or equal to DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.lessThanOrEqual=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun is less than or equal to SMALLER_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.lessThanOrEqual=" + SMALLER_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun is less than DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.lessThan=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun is less than UPDATED_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.lessThan=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByGelsToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where gelsToTakeRun is greater than DEFAULT_GELS_TO_TAKE_RUN
        defaultRaceShouldNotBeFound("gelsToTakeRun.greaterThan=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the raceList where gelsToTakeRun is greater than SMALLER_GELS_TO_TAKE_RUN
        defaultRaceShouldBeFound("gelsToTakeRun.greaterThan=" + SMALLER_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike equals to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.equals=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.equals=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike not equals to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.notEquals=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike not equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.notEquals=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike in DEFAULT_WEIGHT_LOSS_DURING_BIKE or UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.in=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE + "," + UPDATED_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.in=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike is not null
        defaultRaceShouldBeFound("weightLossDuringBike.specified=true");

        // Get all the raceList where weightLossDuringBike is null
        defaultRaceShouldNotBeFound("weightLossDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike is greater than or equal to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.greaterThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike is greater than or equal to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.greaterThanOrEqual=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike is less than or equal to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.lessThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike is less than or equal to SMALLER_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.lessThanOrEqual=" + SMALLER_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike is less than DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.lessThan=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike is less than UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.lessThan=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringBike is greater than DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldNotBeFound("weightLossDuringBike.greaterThan=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the raceList where weightLossDuringBike is greater than SMALLER_WEIGHT_LOSS_DURING_BIKE
        defaultRaceShouldBeFound("weightLossDuringBike.greaterThan=" + SMALLER_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun equals to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.equals=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.equals=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun not equals to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun not equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun in DEFAULT_CARBO_NEED_DURING_RUN or UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.in=" + DEFAULT_CARBO_NEED_DURING_RUN + "," + UPDATED_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.in=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun is not null
        defaultRaceShouldBeFound("carboNeedDuringRun.specified=true");

        // Get all the raceList where carboNeedDuringRun is null
        defaultRaceShouldNotBeFound("carboNeedDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun is less than or equal to SMALLER_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun is less than DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun is less than UPDATED_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringRun is greater than DEFAULT_CARBO_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("carboNeedDuringRun.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the raceList where carboNeedDuringRun is greater than SMALLER_CARBO_NEED_DURING_RUN
        defaultRaceShouldBeFound("carboNeedDuringRun.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike equals to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.equals=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike not equals to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike not equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike in DEFAULT_FLUID_NEED_PER_HOUR_BIKE or UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.in=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE + "," + UPDATED_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.in=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike is not null
        defaultRaceShouldBeFound("fluidNeedPerHourBike.specified=true");

        // Get all the raceList where fluidNeedPerHourBike is null
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike is less than DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike is less than UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourBike is greater than DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldNotBeFound("fluidNeedPerHourBike.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the raceList where fluidNeedPerHourBike is greater than SMALLER_FLUID_NEED_PER_HOUR_BIKE
        defaultRaceShouldBeFound("fluidNeedPerHourBike.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim equals to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.equals=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim not equals to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim not equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim in DEFAULT_FLUID_NEED_PER_HOUR_SWIM or UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.in=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM + "," + UPDATED_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.in=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim is not null
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.specified=true");

        // Get all the raceList where fluidNeedPerHourSwim is null
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim is less than DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim is less than UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourSwimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourSwim is greater than DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldNotBeFound("fluidNeedPerHourSwim.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the raceList where fluidNeedPerHourSwim is greater than SMALLER_FLUID_NEED_PER_HOUR_SWIM
        defaultRaceShouldBeFound("fluidNeedPerHourSwim.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike equals to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.equals=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike not equals to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike not equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike in DEFAULT_CARBO_NEED_DURING_BIKE or UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.in=" + DEFAULT_CARBO_NEED_DURING_BIKE + "," + UPDATED_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.in=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike is not null
        defaultRaceShouldBeFound("carboNeedDuringBike.specified=true");

        // Get all the raceList where carboNeedDuringBike is null
        defaultRaceShouldNotBeFound("carboNeedDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike is less than DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike is less than UPDATED_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByCarboNeedDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where carboNeedDuringBike is greater than DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("carboNeedDuringBike.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the raceList where carboNeedDuringBike is greater than SMALLER_CARBO_NEED_DURING_BIKE
        defaultRaceShouldBeFound("carboNeedDuringBike.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike equals to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.equals=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.equals=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike not equals to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.notEquals=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike not equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.notEquals=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike in DEFAULT_FLUID_NEED_DURING_BIKE or UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.in=" + DEFAULT_FLUID_NEED_DURING_BIKE + "," + UPDATED_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.in=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike is not null
        defaultRaceShouldBeFound("fluidNeedDuringBike.specified=true");

        // Get all the raceList where fluidNeedDuringBike is null
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike is greater than or equal to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike is greater than or equal to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.greaterThanOrEqual=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike is less than or equal to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.lessThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike is less than or equal to SMALLER_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.lessThanOrEqual=" + SMALLER_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike is less than DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.lessThan=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike is less than UPDATED_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.lessThan=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringBike is greater than DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRaceShouldNotBeFound("fluidNeedDuringBike.greaterThan=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the raceList where fluidNeedDuringBike is greater than SMALLER_FLUID_NEED_DURING_BIKE
        defaultRaceShouldBeFound("fluidNeedDuringBike.greaterThan=" + SMALLER_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun equals to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.equals=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun not equals to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun not equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun in DEFAULT_FLUID_NEED_PER_HOUR_RUN or UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.in=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN + "," + UPDATED_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.in=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun is not null
        defaultRaceShouldBeFound("fluidNeedPerHourRun.specified=true");

        // Get all the raceList where fluidNeedPerHourRun is null
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun is less than DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun is less than UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedPerHourRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedPerHourRun is greater than DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldNotBeFound("fluidNeedPerHourRun.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the raceList where fluidNeedPerHourRun is greater than SMALLER_FLUID_NEED_PER_HOUR_RUN
        defaultRaceShouldBeFound("fluidNeedPerHourRun.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun equals to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.equals=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.equals=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun not equals to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.notEquals=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun not equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.notEquals=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun in DEFAULT_FLUID_NEED_DURING_RUN or UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.in=" + DEFAULT_FLUID_NEED_DURING_RUN + "," + UPDATED_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.in=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun is not null
        defaultRaceShouldBeFound("fluidNeedDuringRun.specified=true");

        // Get all the raceList where fluidNeedDuringRun is null
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun is greater than or equal to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun is greater than or equal to UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.greaterThanOrEqual=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun is less than or equal to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.lessThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun is less than or equal to SMALLER_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.lessThanOrEqual=" + SMALLER_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun is less than DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.lessThan=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun is less than UPDATED_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.lessThan=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByFluidNeedDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where fluidNeedDuringRun is greater than DEFAULT_FLUID_NEED_DURING_RUN
        defaultRaceShouldNotBeFound("fluidNeedDuringRun.greaterThan=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the raceList where fluidNeedDuringRun is greater than SMALLER_FLUID_NEED_DURING_RUN
        defaultRaceShouldBeFound("fluidNeedDuringRun.greaterThan=" + SMALLER_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun equals to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.equals=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.equals=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun not equals to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.notEquals=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun not equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.notEquals=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun in DEFAULT_WEIGHT_LOSS_DURING_RUN or UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.in=" + DEFAULT_WEIGHT_LOSS_DURING_RUN + "," + UPDATED_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.in=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun is not null
        defaultRaceShouldBeFound("weightLossDuringRun.specified=true");

        // Get all the raceList where weightLossDuringRun is null
        defaultRaceShouldNotBeFound("weightLossDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun is greater than or equal to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.greaterThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun is greater than or equal to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.greaterThanOrEqual=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun is less than or equal to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.lessThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun is less than or equal to SMALLER_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.lessThanOrEqual=" + SMALLER_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun is less than DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.lessThan=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun is less than UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.lessThan=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByWeightLossDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where weightLossDuringRun is greater than DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldNotBeFound("weightLossDuringRun.greaterThan=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the raceList where weightLossDuringRun is greater than SMALLER_WEIGHT_LOSS_DURING_RUN
        defaultRaceShouldBeFound("weightLossDuringRun.greaterThan=" + SMALLER_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike equals to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.equals=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.equals=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike not equals to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.notEquals=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike not equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.notEquals=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike in DEFAULT_ACT_FLUID_NEED_BIKE or UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.in=" + DEFAULT_ACT_FLUID_NEED_BIKE + "," + UPDATED_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.in=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike is not null
        defaultRaceShouldBeFound("actFluidNeedBike.specified=true");

        // Get all the raceList where actFluidNeedBike is null
        defaultRaceShouldNotBeFound("actFluidNeedBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike is greater than or equal to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.greaterThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike is greater than or equal to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.greaterThanOrEqual=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike is less than or equal to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.lessThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike is less than or equal to SMALLER_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.lessThanOrEqual=" + SMALLER_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike is less than DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.lessThan=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike is less than UPDATED_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.lessThan=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedBike is greater than DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRaceShouldNotBeFound("actFluidNeedBike.greaterThan=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the raceList where actFluidNeedBike is greater than SMALLER_ACT_FLUID_NEED_BIKE
        defaultRaceShouldBeFound("actFluidNeedBike.greaterThan=" + SMALLER_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun equals to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.equals=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.equals=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun not equals to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.notEquals=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun not equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.notEquals=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsInShouldWork() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun in DEFAULT_ACT_FLUID_NEED_RUN or UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.in=" + DEFAULT_ACT_FLUID_NEED_RUN + "," + UPDATED_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.in=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun is not null
        defaultRaceShouldBeFound("actFluidNeedRun.specified=true");

        // Get all the raceList where actFluidNeedRun is null
        defaultRaceShouldNotBeFound("actFluidNeedRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun is greater than or equal to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.greaterThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun is greater than or equal to UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.greaterThanOrEqual=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun is less than or equal to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.lessThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun is less than or equal to SMALLER_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.lessThanOrEqual=" + SMALLER_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsLessThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun is less than DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.lessThan=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun is less than UPDATED_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.lessThan=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByActFluidNeedRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList where actFluidNeedRun is greater than DEFAULT_ACT_FLUID_NEED_RUN
        defaultRaceShouldNotBeFound("actFluidNeedRun.greaterThan=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the raceList where actFluidNeedRun is greater than SMALLER_ACT_FLUID_NEED_RUN
        defaultRaceShouldBeFound("actFluidNeedRun.greaterThan=" + SMALLER_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacesByRacePlanFormIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);
        RacePlanForm racePlanForm = RacePlanFormResourceIT.createEntity(em);
        em.persist(racePlanForm);
        em.flush();
        race.addRacePlanForm(racePlanForm);
        raceRepository.saveAndFlush(race);
        Long racePlanFormId = racePlanForm.getId();

        // Get all the raceList where racePlanForm equals to racePlanFormId
        defaultRaceShouldBeFound("racePlanFormId.equals=" + racePlanFormId);

        // Get all the raceList where racePlanForm equals to (racePlanFormId + 1)
        defaultRaceShouldNotBeFound("racePlanFormId.equals=" + (racePlanFormId + 1));
    }

    @Test
    @Transactional
    void getAllRacesByAthleteIsEqualToSomething() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);
        Athlete athlete = AthleteResourceIT.createEntity(em);
        em.persist(athlete);
        em.flush();
        race.setAthlete(athlete);
        raceRepository.saveAndFlush(race);
        Long athleteId = athlete.getId();

        // Get all the raceList where athlete equals to athleteId
        defaultRaceShouldBeFound("athleteId.equals=" + athleteId);

        // Get all the raceList where athlete equals to (athleteId + 1)
        defaultRaceShouldNotBeFound("athleteId.equals=" + (athleteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRaceShouldBeFound(String filter) throws Exception {
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(race.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logging").value(hasItem(DEFAULT_LOGGING)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].comp").value(hasItem(DEFAULT_COMP)))
            .andExpect(jsonPath("$.[*].swimDuration").value(hasItem(DEFAULT_SWIM_DURATION)))
            .andExpect(jsonPath("$.[*].bikeDuration").value(hasItem(DEFAULT_BIKE_DURATION)))
            .andExpect(jsonPath("$.[*].runDuration").value(hasItem(DEFAULT_RUN_DURATION)))
            .andExpect(jsonPath("$.[*].gelCarbo").value(hasItem(DEFAULT_GEL_CARBO)))
            .andExpect(jsonPath("$.[*].drinkCarbo").value(hasItem(DEFAULT_DRINK_CARBO)))
            .andExpect(jsonPath("$.[*].drinkOrgCarbo").value(hasItem(DEFAULT_DRINK_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelOrgCarbo").value(hasItem(DEFAULT_GEL_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelsbike").value(hasItem(DEFAULT_GELSBIKE)))
            .andExpect(jsonPath("$.[*].gelsrun").value(hasItem(DEFAULT_GELSRUN)))
            .andExpect(jsonPath("$.[*].selectedOrgGelQuery").value(hasItem(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedOrgDrankQuery").value(hasItem(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].sportDrinkOrgBike").value(hasItem(DEFAULT_SPORT_DRINK_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].waterOrgBike").value(hasItem(DEFAULT_WATER_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].gelsOrgBike").value(hasItem(DEFAULT_GELS_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgRun").value(hasItem(DEFAULT_SPORT_DRINK_ORG_RUN)))
            .andExpect(jsonPath("$.[*].waterOrgRun").value(hasItem(DEFAULT_WATER_ORG_RUN)))
            .andExpect(jsonPath("$.[*].gelsOrgRun").value(hasItem(DEFAULT_GELS_ORG_RUN)))
            .andExpect(jsonPath("$.[*].orsBeforeStart").value(hasItem(DEFAULT_ORS_BEFORE_START)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeBike").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].waterToTakeBike").value(hasItem(DEFAULT_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeBike").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].orsToTakeBike").value(hasItem(DEFAULT_ORS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].gelsToTakeBike").value(hasItem(DEFAULT_GELS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeRun").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].waterToTakeRun").value(hasItem(DEFAULT_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeRun").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].orsToTakeRun").value(hasItem(DEFAULT_ORS_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MAX)))
            .andExpect(jsonPath("$.[*].diffCarboRun").value(hasItem(DEFAULT_DIFF_CARBO_RUN)))
            .andExpect(jsonPath("$.[*].diffMoisterRun").value(hasItem(DEFAULT_DIFF_MOISTER_RUN)))
            .andExpect(jsonPath("$.[*].difCarbo").value(hasItem(DEFAULT_DIF_CARBO)))
            .andExpect(jsonPath("$.[*].difMoister").value(hasItem(DEFAULT_DIF_MOISTER)))
            .andExpect(jsonPath("$.[*].gelsToTakeRun").value(hasItem(DEFAULT_GELS_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].weightLossDuringBike").value(hasItem(DEFAULT_WEIGHT_LOSS_DURING_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].carboNeedDuringRun").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourBike").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourSwim").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_SWIM.doubleValue())))
            .andExpect(jsonPath("$.[*].carboNeedDuringBike").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedDuringBike").value(hasItem(DEFAULT_FLUID_NEED_DURING_BIKE.intValue())))
            .andExpect(jsonPath("$.[*].fluidNeedPerHourRun").value(hasItem(DEFAULT_FLUID_NEED_PER_HOUR_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fluidNeedDuringRun").value(hasItem(DEFAULT_FLUID_NEED_DURING_RUN.intValue())))
            .andExpect(jsonPath("$.[*].weightLossDuringRun").value(hasItem(DEFAULT_WEIGHT_LOSS_DURING_RUN.doubleValue())))
            .andExpect(jsonPath("$.[*].actFluidNeedBike").value(hasItem(DEFAULT_ACT_FLUID_NEED_BIKE)))
            .andExpect(jsonPath("$.[*].actFluidNeedRun").value(hasItem(DEFAULT_ACT_FLUID_NEED_RUN)));

        // Check, that the count call also returns 1
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRaceShouldNotBeFound(String filter) throws Exception {
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRace() throws Exception {
        // Get the race
        restRaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race
        Race updatedRace = raceRepository.findById(race.getId()).get();
        // Disconnect from session so that the updates on updatedRace are not directly saved in db
        em.detach(updatedRace);
        updatedRace
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .logging(UPDATED_LOGGING)
            .weight(UPDATED_WEIGHT)
            .length(UPDATED_LENGTH)
            .temperature(UPDATED_TEMPERATURE)
            .comp(UPDATED_COMP)
            .swimDuration(UPDATED_SWIM_DURATION)
            .bikeDuration(UPDATED_BIKE_DURATION)
            .runDuration(UPDATED_RUN_DURATION)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .gelsbike(UPDATED_GELSBIKE)
            .gelsrun(UPDATED_GELSRUN)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .gelsToTakeRun(UPDATED_GELS_TO_TAKE_RUN)
            .weightLossDuringBike(UPDATED_WEIGHT_LOSS_DURING_BIKE)
            .carboNeedDuringRun(UPDATED_CARBO_NEED_DURING_RUN)
            .fluidNeedPerHourBike(UPDATED_FLUID_NEED_PER_HOUR_BIKE)
            .fluidNeedPerHourSwim(UPDATED_FLUID_NEED_PER_HOUR_SWIM)
            .carboNeedDuringBike(UPDATED_CARBO_NEED_DURING_BIKE)
            .fluidNeedDuringBike(UPDATED_FLUID_NEED_DURING_BIKE)
            .fluidNeedPerHourRun(UPDATED_FLUID_NEED_PER_HOUR_RUN)
            .fluidNeedDuringRun(UPDATED_FLUID_NEED_DURING_RUN)
            .weightLossDuringRun(UPDATED_WEIGHT_LOSS_DURING_RUN)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN);

        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRace.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRace))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRace.getLogging()).isEqualTo(UPDATED_LOGGING);
        assertThat(testRace.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRace.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testRace.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testRace.getComp()).isEqualTo(UPDATED_COMP);
        assertThat(testRace.getSwimDuration()).isEqualTo(UPDATED_SWIM_DURATION);
        assertThat(testRace.getBikeDuration()).isEqualTo(UPDATED_BIKE_DURATION);
        assertThat(testRace.getRunDuration()).isEqualTo(UPDATED_RUN_DURATION);
        assertThat(testRace.getGelCarbo()).isEqualTo(UPDATED_GEL_CARBO);
        assertThat(testRace.getDrinkCarbo()).isEqualTo(UPDATED_DRINK_CARBO);
        assertThat(testRace.getDrinkOrgCarbo()).isEqualTo(UPDATED_DRINK_ORG_CARBO);
        assertThat(testRace.getGelOrgCarbo()).isEqualTo(UPDATED_GEL_ORG_CARBO);
        assertThat(testRace.getGelsbike()).isEqualTo(UPDATED_GELSBIKE);
        assertThat(testRace.getGelsrun()).isEqualTo(UPDATED_GELSRUN);
        assertThat(testRace.getSelectedOrgGelQuery()).isEqualTo(UPDATED_SELECTED_ORG_GEL_QUERY);
        assertThat(testRace.getSelectedOrgDrankQuery()).isEqualTo(UPDATED_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRace.getSportDrinkOrgBike()).isEqualTo(UPDATED_SPORT_DRINK_ORG_BIKE);
        assertThat(testRace.getWaterOrgBike()).isEqualTo(UPDATED_WATER_ORG_BIKE);
        assertThat(testRace.getGelsOrgBike()).isEqualTo(UPDATED_GELS_ORG_BIKE);
        assertThat(testRace.getSportDrinkOrgRun()).isEqualTo(UPDATED_SPORT_DRINK_ORG_RUN);
        assertThat(testRace.getWaterOrgRun()).isEqualTo(UPDATED_WATER_ORG_RUN);
        assertThat(testRace.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRace.getOrsBeforeStart()).isEqualTo(UPDATED_ORS_BEFORE_START);
        assertThat(testRace.getSportDrinkToTakeBike()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRace.getWaterToTakeBike()).isEqualTo(UPDATED_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getExtraWaterToTakeBike()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getOrsToTakeBike()).isEqualTo(UPDATED_ORS_TO_TAKE_BIKE);
        assertThat(testRace.getGelsToTakeBike()).isEqualTo(UPDATED_GELS_TO_TAKE_BIKE);
        assertThat(testRace.getSportDrinkToTakeRun()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRace.getWaterToTakeRun()).isEqualTo(UPDATED_WATER_TO_TAKE_RUN);
        assertThat(testRace.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRace.getOrsToTakeRun()).isEqualTo(UPDATED_ORS_TO_TAKE_RUN);
        assertThat(testRace.getCarboNeedDuringBikeMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRace.getCarboNeedDuringBikeMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRace.getCarboNeedDuringRunMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRace.getCarboNeedDuringRunMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRace.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRace.getDiffMoisterRun()).isEqualTo(UPDATED_DIFF_MOISTER_RUN);
        assertThat(testRace.getDifCarbo()).isEqualTo(UPDATED_DIF_CARBO);
        assertThat(testRace.getDifMoister()).isEqualTo(UPDATED_DIF_MOISTER);
        assertThat(testRace.getGelsToTakeRun()).isEqualTo(UPDATED_GELS_TO_TAKE_RUN);
        assertThat(testRace.getWeightLossDuringBike()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRace.getCarboNeedDuringRun()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN);
        assertThat(testRace.getFluidNeedPerHourBike()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRace.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRace.getCarboNeedDuringBike()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedDuringBike()).isEqualTo(UPDATED_FLUID_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedPerHourRun()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRace.getFluidNeedDuringRun()).isEqualTo(UPDATED_FLUID_NEED_DURING_RUN);
        assertThat(testRace.getWeightLossDuringRun()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRace.getActFluidNeedBike()).isEqualTo(UPDATED_ACT_FLUID_NEED_BIKE);
        assertThat(testRace.getActFluidNeedRun()).isEqualTo(UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void putNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, race.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(race))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(race))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaceWithPatch() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race using partial update
        Race partialUpdatedRace = new Race();
        partialUpdatedRace.setId(race.getId());

        partialUpdatedRace
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .weight(UPDATED_WEIGHT)
            .temperature(UPDATED_TEMPERATURE)
            .swimDuration(UPDATED_SWIM_DURATION)
            .bikeDuration(UPDATED_BIKE_DURATION)
            .runDuration(UPDATED_RUN_DURATION)
            .gelsrun(UPDATED_GELSRUN)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .difMoister(UPDATED_DIF_MOISTER)
            .carboNeedDuringRun(UPDATED_CARBO_NEED_DURING_RUN)
            .fluidNeedPerHourSwim(UPDATED_FLUID_NEED_PER_HOUR_SWIM)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN);

        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRace))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRace.getLogging()).isEqualTo(DEFAULT_LOGGING);
        assertThat(testRace.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRace.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testRace.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testRace.getComp()).isEqualTo(DEFAULT_COMP);
        assertThat(testRace.getSwimDuration()).isEqualTo(UPDATED_SWIM_DURATION);
        assertThat(testRace.getBikeDuration()).isEqualTo(UPDATED_BIKE_DURATION);
        assertThat(testRace.getRunDuration()).isEqualTo(UPDATED_RUN_DURATION);
        assertThat(testRace.getGelCarbo()).isEqualTo(DEFAULT_GEL_CARBO);
        assertThat(testRace.getDrinkCarbo()).isEqualTo(DEFAULT_DRINK_CARBO);
        assertThat(testRace.getDrinkOrgCarbo()).isEqualTo(DEFAULT_DRINK_ORG_CARBO);
        assertThat(testRace.getGelOrgCarbo()).isEqualTo(DEFAULT_GEL_ORG_CARBO);
        assertThat(testRace.getGelsbike()).isEqualTo(DEFAULT_GELSBIKE);
        assertThat(testRace.getGelsrun()).isEqualTo(UPDATED_GELSRUN);
        assertThat(testRace.getSelectedOrgGelQuery()).isEqualTo(UPDATED_SELECTED_ORG_GEL_QUERY);
        assertThat(testRace.getSelectedOrgDrankQuery()).isEqualTo(UPDATED_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRace.getSportDrinkOrgBike()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_BIKE);
        assertThat(testRace.getWaterOrgBike()).isEqualTo(DEFAULT_WATER_ORG_BIKE);
        assertThat(testRace.getGelsOrgBike()).isEqualTo(UPDATED_GELS_ORG_BIKE);
        assertThat(testRace.getSportDrinkOrgRun()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_RUN);
        assertThat(testRace.getWaterOrgRun()).isEqualTo(DEFAULT_WATER_ORG_RUN);
        assertThat(testRace.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRace.getOrsBeforeStart()).isEqualTo(UPDATED_ORS_BEFORE_START);
        assertThat(testRace.getSportDrinkToTakeBike()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRace.getWaterToTakeBike()).isEqualTo(DEFAULT_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getExtraWaterToTakeBike()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getOrsToTakeBike()).isEqualTo(UPDATED_ORS_TO_TAKE_BIKE);
        assertThat(testRace.getGelsToTakeBike()).isEqualTo(DEFAULT_GELS_TO_TAKE_BIKE);
        assertThat(testRace.getSportDrinkToTakeRun()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRace.getWaterToTakeRun()).isEqualTo(DEFAULT_WATER_TO_TAKE_RUN);
        assertThat(testRace.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRace.getOrsToTakeRun()).isEqualTo(DEFAULT_ORS_TO_TAKE_RUN);
        assertThat(testRace.getCarboNeedDuringBikeMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRace.getCarboNeedDuringBikeMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRace.getCarboNeedDuringRunMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRace.getCarboNeedDuringRunMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRace.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRace.getDiffMoisterRun()).isEqualTo(DEFAULT_DIFF_MOISTER_RUN);
        assertThat(testRace.getDifCarbo()).isEqualTo(DEFAULT_DIF_CARBO);
        assertThat(testRace.getDifMoister()).isEqualTo(UPDATED_DIF_MOISTER);
        assertThat(testRace.getGelsToTakeRun()).isEqualTo(DEFAULT_GELS_TO_TAKE_RUN);
        assertThat(testRace.getWeightLossDuringBike()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRace.getCarboNeedDuringRun()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN);
        assertThat(testRace.getFluidNeedPerHourBike()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRace.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRace.getCarboNeedDuringBike()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedDuringBike()).isEqualTo(DEFAULT_FLUID_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedPerHourRun()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRace.getFluidNeedDuringRun()).isEqualTo(DEFAULT_FLUID_NEED_DURING_RUN);
        assertThat(testRace.getWeightLossDuringRun()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRace.getActFluidNeedBike()).isEqualTo(DEFAULT_ACT_FLUID_NEED_BIKE);
        assertThat(testRace.getActFluidNeedRun()).isEqualTo(UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void fullUpdateRaceWithPatch() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race using partial update
        Race partialUpdatedRace = new Race();
        partialUpdatedRace.setId(race.getId());

        partialUpdatedRace
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .logging(UPDATED_LOGGING)
            .weight(UPDATED_WEIGHT)
            .length(UPDATED_LENGTH)
            .temperature(UPDATED_TEMPERATURE)
            .comp(UPDATED_COMP)
            .swimDuration(UPDATED_SWIM_DURATION)
            .bikeDuration(UPDATED_BIKE_DURATION)
            .runDuration(UPDATED_RUN_DURATION)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .gelsbike(UPDATED_GELSBIKE)
            .gelsrun(UPDATED_GELSRUN)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .gelsToTakeRun(UPDATED_GELS_TO_TAKE_RUN)
            .weightLossDuringBike(UPDATED_WEIGHT_LOSS_DURING_BIKE)
            .carboNeedDuringRun(UPDATED_CARBO_NEED_DURING_RUN)
            .fluidNeedPerHourBike(UPDATED_FLUID_NEED_PER_HOUR_BIKE)
            .fluidNeedPerHourSwim(UPDATED_FLUID_NEED_PER_HOUR_SWIM)
            .carboNeedDuringBike(UPDATED_CARBO_NEED_DURING_BIKE)
            .fluidNeedDuringBike(UPDATED_FLUID_NEED_DURING_BIKE)
            .fluidNeedPerHourRun(UPDATED_FLUID_NEED_PER_HOUR_RUN)
            .fluidNeedDuringRun(UPDATED_FLUID_NEED_DURING_RUN)
            .weightLossDuringRun(UPDATED_WEIGHT_LOSS_DURING_RUN)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN);

        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRace))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRace.getLogging()).isEqualTo(UPDATED_LOGGING);
        assertThat(testRace.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRace.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testRace.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testRace.getComp()).isEqualTo(UPDATED_COMP);
        assertThat(testRace.getSwimDuration()).isEqualTo(UPDATED_SWIM_DURATION);
        assertThat(testRace.getBikeDuration()).isEqualTo(UPDATED_BIKE_DURATION);
        assertThat(testRace.getRunDuration()).isEqualTo(UPDATED_RUN_DURATION);
        assertThat(testRace.getGelCarbo()).isEqualTo(UPDATED_GEL_CARBO);
        assertThat(testRace.getDrinkCarbo()).isEqualTo(UPDATED_DRINK_CARBO);
        assertThat(testRace.getDrinkOrgCarbo()).isEqualTo(UPDATED_DRINK_ORG_CARBO);
        assertThat(testRace.getGelOrgCarbo()).isEqualTo(UPDATED_GEL_ORG_CARBO);
        assertThat(testRace.getGelsbike()).isEqualTo(UPDATED_GELSBIKE);
        assertThat(testRace.getGelsrun()).isEqualTo(UPDATED_GELSRUN);
        assertThat(testRace.getSelectedOrgGelQuery()).isEqualTo(UPDATED_SELECTED_ORG_GEL_QUERY);
        assertThat(testRace.getSelectedOrgDrankQuery()).isEqualTo(UPDATED_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRace.getSportDrinkOrgBike()).isEqualTo(UPDATED_SPORT_DRINK_ORG_BIKE);
        assertThat(testRace.getWaterOrgBike()).isEqualTo(UPDATED_WATER_ORG_BIKE);
        assertThat(testRace.getGelsOrgBike()).isEqualTo(UPDATED_GELS_ORG_BIKE);
        assertThat(testRace.getSportDrinkOrgRun()).isEqualTo(UPDATED_SPORT_DRINK_ORG_RUN);
        assertThat(testRace.getWaterOrgRun()).isEqualTo(UPDATED_WATER_ORG_RUN);
        assertThat(testRace.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRace.getOrsBeforeStart()).isEqualTo(UPDATED_ORS_BEFORE_START);
        assertThat(testRace.getSportDrinkToTakeBike()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRace.getWaterToTakeBike()).isEqualTo(UPDATED_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getExtraWaterToTakeBike()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRace.getOrsToTakeBike()).isEqualTo(UPDATED_ORS_TO_TAKE_BIKE);
        assertThat(testRace.getGelsToTakeBike()).isEqualTo(UPDATED_GELS_TO_TAKE_BIKE);
        assertThat(testRace.getSportDrinkToTakeRun()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRace.getWaterToTakeRun()).isEqualTo(UPDATED_WATER_TO_TAKE_RUN);
        assertThat(testRace.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRace.getOrsToTakeRun()).isEqualTo(UPDATED_ORS_TO_TAKE_RUN);
        assertThat(testRace.getCarboNeedDuringBikeMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRace.getCarboNeedDuringBikeMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRace.getCarboNeedDuringRunMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRace.getCarboNeedDuringRunMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRace.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRace.getDiffMoisterRun()).isEqualTo(UPDATED_DIFF_MOISTER_RUN);
        assertThat(testRace.getDifCarbo()).isEqualTo(UPDATED_DIF_CARBO);
        assertThat(testRace.getDifMoister()).isEqualTo(UPDATED_DIF_MOISTER);
        assertThat(testRace.getGelsToTakeRun()).isEqualTo(UPDATED_GELS_TO_TAKE_RUN);
        assertThat(testRace.getWeightLossDuringBike()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRace.getCarboNeedDuringRun()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN);
        assertThat(testRace.getFluidNeedPerHourBike()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRace.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRace.getCarboNeedDuringBike()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedDuringBike()).isEqualTo(UPDATED_FLUID_NEED_DURING_BIKE);
        assertThat(testRace.getFluidNeedPerHourRun()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRace.getFluidNeedDuringRun()).isEqualTo(UPDATED_FLUID_NEED_DURING_RUN);
        assertThat(testRace.getWeightLossDuringRun()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRace.getActFluidNeedBike()).isEqualTo(UPDATED_ACT_FLUID_NEED_BIKE);
        assertThat(testRace.getActFluidNeedRun()).isEqualTo(UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void patchNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, race.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(race))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(race))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(race)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeDelete = raceRepository.findAll().size();

        // Delete the race
        restRaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, race.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
