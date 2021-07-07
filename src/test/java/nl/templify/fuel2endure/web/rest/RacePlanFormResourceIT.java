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
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.domain.RacePlanForm;
import nl.templify.fuel2endure.repository.RacePlanFormRepository;
import nl.templify.fuel2endure.service.criteria.RacePlanFormCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RacePlanFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RacePlanFormResourceIT {

    private static final String DEFAULT_COMP = "AAAAAAAAAA";
    private static final String UPDATED_COMP = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELECTED_ORG_GEL_QUERY = false;
    private static final Boolean UPDATED_SELECTED_ORG_GEL_QUERY = true;

    private static final Boolean DEFAULT_SELECTED_ORG_DRANK_QUERY = false;
    private static final Boolean UPDATED_SELECTED_ORG_DRANK_QUERY = true;

    private static final Integer DEFAULT_ORS_BEFORE_START = 1;
    private static final Integer UPDATED_ORS_BEFORE_START = 2;
    private static final Integer SMALLER_ORS_BEFORE_START = 1 - 1;

    private static final Integer DEFAULT_DRINK_CARBO = 1;
    private static final Integer UPDATED_DRINK_CARBO = 2;
    private static final Integer SMALLER_DRINK_CARBO = 1 - 1;

    private static final Integer DEFAULT_GEL_CARBO = 1;
    private static final Integer UPDATED_GEL_CARBO = 2;
    private static final Integer SMALLER_GEL_CARBO = 1 - 1;

    private static final Integer DEFAULT_DRINK_ORG_CARBO = 1;
    private static final Integer UPDATED_DRINK_ORG_CARBO = 2;
    private static final Integer SMALLER_DRINK_ORG_CARBO = 1 - 1;

    private static final Integer DEFAULT_GEL_ORG_CARBO = 1;
    private static final Integer UPDATED_GEL_ORG_CARBO = 2;
    private static final Integer SMALLER_GEL_ORG_CARBO = 1 - 1;

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

    private static final Integer DEFAULT_ACT_FLUID_NEED_BIKE = 1;
    private static final Integer UPDATED_ACT_FLUID_NEED_BIKE = 2;
    private static final Integer SMALLER_ACT_FLUID_NEED_BIKE = 1 - 1;

    private static final Integer DEFAULT_ACT_FLUID_NEED_RUN = 1;
    private static final Integer UPDATED_ACT_FLUID_NEED_RUN = 2;
    private static final Integer SMALLER_ACT_FLUID_NEED_RUN = 1 - 1;

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

    private static final Integer DEFAULT_START_GEL = 1;
    private static final Integer UPDATED_START_GEL = 2;
    private static final Integer SMALLER_START_GEL = 1 - 1;

    private static final Integer DEFAULT_START_DRANK = 1;
    private static final Integer UPDATED_START_DRANK = 2;
    private static final Integer SMALLER_START_DRANK = 1 - 1;

    private static final String ENTITY_API_URL = "/api/race-plan-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RacePlanFormRepository racePlanFormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRacePlanFormMockMvc;

    private RacePlanForm racePlanForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacePlanForm createEntity(EntityManager em) {
        RacePlanForm racePlanForm = new RacePlanForm()
            .comp(DEFAULT_COMP)
            .name(DEFAULT_NAME)
            .selectedOrgGelQuery(DEFAULT_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(DEFAULT_SELECTED_ORG_DRANK_QUERY)
            .orsBeforeStart(DEFAULT_ORS_BEFORE_START)
            .drinkCarbo(DEFAULT_DRINK_CARBO)
            .gelCarbo(DEFAULT_GEL_CARBO)
            .drinkOrgCarbo(DEFAULT_DRINK_ORG_CARBO)
            .gelOrgCarbo(DEFAULT_GEL_ORG_CARBO)
            .sportDrinkOrgBike(DEFAULT_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(DEFAULT_WATER_ORG_BIKE)
            .gelsOrgBike(DEFAULT_GELS_ORG_BIKE)
            .sportDrinkOrgRun(DEFAULT_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(DEFAULT_WATER_ORG_RUN)
            .gelsOrgRun(DEFAULT_GELS_ORG_RUN)
            .sportDrinkToTakeBike(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(DEFAULT_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(DEFAULT_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(DEFAULT_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(DEFAULT_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(DEFAULT_ORS_TO_TAKE_RUN)
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
            .diffCarboRun(DEFAULT_DIFF_CARBO_RUN)
            .diffMoisterRun(DEFAULT_DIFF_MOISTER_RUN)
            .difCarbo(DEFAULT_DIF_CARBO)
            .difMoister(DEFAULT_DIF_MOISTER)
            .actFluidNeedBike(DEFAULT_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(DEFAULT_ACT_FLUID_NEED_RUN)
            .carboNeedDuringBikeMin(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(DEFAULT_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(DEFAULT_CARBO_NEED_DURING_RUN_MAX)
            .startGel(DEFAULT_START_GEL)
            .startDrank(DEFAULT_START_DRANK);
        return racePlanForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacePlanForm createUpdatedEntity(EntityManager em) {
        RacePlanForm racePlanForm = new RacePlanForm()
            .comp(UPDATED_COMP)
            .name(UPDATED_NAME)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
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
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .startGel(UPDATED_START_GEL)
            .startDrank(UPDATED_START_DRANK);
        return racePlanForm;
    }

    @BeforeEach
    public void initTest() {
        racePlanForm = createEntity(em);
    }

    @Test
    @Transactional
    void createRacePlanForm() throws Exception {
        int databaseSizeBeforeCreate = racePlanFormRepository.findAll().size();
        // Create the RacePlanForm
        restRacePlanFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(racePlanForm)))
            .andExpect(status().isCreated());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeCreate + 1);
        RacePlanForm testRacePlanForm = racePlanFormList.get(racePlanFormList.size() - 1);
        assertThat(testRacePlanForm.getComp()).isEqualTo(DEFAULT_COMP);
        assertThat(testRacePlanForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRacePlanForm.getSelectedOrgGelQuery()).isEqualTo(DEFAULT_SELECTED_ORG_GEL_QUERY);
        assertThat(testRacePlanForm.getSelectedOrgDrankQuery()).isEqualTo(DEFAULT_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRacePlanForm.getOrsBeforeStart()).isEqualTo(DEFAULT_ORS_BEFORE_START);
        assertThat(testRacePlanForm.getDrinkCarbo()).isEqualTo(DEFAULT_DRINK_CARBO);
        assertThat(testRacePlanForm.getGelCarbo()).isEqualTo(DEFAULT_GEL_CARBO);
        assertThat(testRacePlanForm.getDrinkOrgCarbo()).isEqualTo(DEFAULT_DRINK_ORG_CARBO);
        assertThat(testRacePlanForm.getGelOrgCarbo()).isEqualTo(DEFAULT_GEL_ORG_CARBO);
        assertThat(testRacePlanForm.getSportDrinkOrgBike()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_BIKE);
        assertThat(testRacePlanForm.getWaterOrgBike()).isEqualTo(DEFAULT_WATER_ORG_BIKE);
        assertThat(testRacePlanForm.getGelsOrgBike()).isEqualTo(DEFAULT_GELS_ORG_BIKE);
        assertThat(testRacePlanForm.getSportDrinkOrgRun()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_RUN);
        assertThat(testRacePlanForm.getWaterOrgRun()).isEqualTo(DEFAULT_WATER_ORG_RUN);
        assertThat(testRacePlanForm.getGelsOrgRun()).isEqualTo(DEFAULT_GELS_ORG_RUN);
        assertThat(testRacePlanForm.getSportDrinkToTakeBike()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getWaterToTakeBike()).isEqualTo(DEFAULT_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getExtraWaterToTakeBike()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getOrsToTakeBike()).isEqualTo(DEFAULT_ORS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getGelsToTakeBike()).isEqualTo(DEFAULT_GELS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getSportDrinkToTakeRun()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWaterToTakeRun()).isEqualTo(DEFAULT_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getExtraWaterToTakeRun()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getOrsToTakeRun()).isEqualTo(DEFAULT_ORS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getGelsToTakeRun()).isEqualTo(DEFAULT_GELS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringBike()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRacePlanForm.getCarboNeedDuringRun()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getFluidNeedPerHourBike()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourSwim()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRacePlanForm.getCarboNeedDuringBike()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedDuringBike()).isEqualTo(DEFAULT_FLUID_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourRun()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRacePlanForm.getFluidNeedDuringRun()).isEqualTo(DEFAULT_FLUID_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringRun()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRacePlanForm.getDiffCarboRun()).isEqualTo(DEFAULT_DIFF_CARBO_RUN);
        assertThat(testRacePlanForm.getDiffMoisterRun()).isEqualTo(DEFAULT_DIFF_MOISTER_RUN);
        assertThat(testRacePlanForm.getDifCarbo()).isEqualTo(DEFAULT_DIF_CARBO);
        assertThat(testRacePlanForm.getDifMoister()).isEqualTo(DEFAULT_DIF_MOISTER);
        assertThat(testRacePlanForm.getActFluidNeedBike()).isEqualTo(DEFAULT_ACT_FLUID_NEED_BIKE);
        assertThat(testRacePlanForm.getActFluidNeedRun()).isEqualTo(DEFAULT_ACT_FLUID_NEED_RUN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMax()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRacePlanForm.getStartGel()).isEqualTo(DEFAULT_START_GEL);
        assertThat(testRacePlanForm.getStartDrank()).isEqualTo(DEFAULT_START_DRANK);
    }

    @Test
    @Transactional
    void createRacePlanFormWithExistingId() throws Exception {
        // Create the RacePlanForm with an existing ID
        racePlanForm.setId(1L);

        int databaseSizeBeforeCreate = racePlanFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacePlanFormMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(racePlanForm)))
            .andExpect(status().isBadRequest());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRacePlanForms() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racePlanForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].comp").value(hasItem(DEFAULT_COMP)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].selectedOrgGelQuery").value(hasItem(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedOrgDrankQuery").value(hasItem(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].orsBeforeStart").value(hasItem(DEFAULT_ORS_BEFORE_START)))
            .andExpect(jsonPath("$.[*].drinkCarbo").value(hasItem(DEFAULT_DRINK_CARBO)))
            .andExpect(jsonPath("$.[*].gelCarbo").value(hasItem(DEFAULT_GEL_CARBO)))
            .andExpect(jsonPath("$.[*].drinkOrgCarbo").value(hasItem(DEFAULT_DRINK_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelOrgCarbo").value(hasItem(DEFAULT_GEL_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgBike").value(hasItem(DEFAULT_SPORT_DRINK_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].waterOrgBike").value(hasItem(DEFAULT_WATER_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].gelsOrgBike").value(hasItem(DEFAULT_GELS_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgRun").value(hasItem(DEFAULT_SPORT_DRINK_ORG_RUN)))
            .andExpect(jsonPath("$.[*].waterOrgRun").value(hasItem(DEFAULT_WATER_ORG_RUN)))
            .andExpect(jsonPath("$.[*].gelsOrgRun").value(hasItem(DEFAULT_GELS_ORG_RUN)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeBike").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].waterToTakeBike").value(hasItem(DEFAULT_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeBike").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].orsToTakeBike").value(hasItem(DEFAULT_ORS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].gelsToTakeBike").value(hasItem(DEFAULT_GELS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeRun").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].waterToTakeRun").value(hasItem(DEFAULT_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeRun").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].orsToTakeRun").value(hasItem(DEFAULT_ORS_TO_TAKE_RUN)))
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
            .andExpect(jsonPath("$.[*].diffCarboRun").value(hasItem(DEFAULT_DIFF_CARBO_RUN)))
            .andExpect(jsonPath("$.[*].diffMoisterRun").value(hasItem(DEFAULT_DIFF_MOISTER_RUN)))
            .andExpect(jsonPath("$.[*].difCarbo").value(hasItem(DEFAULT_DIF_CARBO)))
            .andExpect(jsonPath("$.[*].difMoister").value(hasItem(DEFAULT_DIF_MOISTER)))
            .andExpect(jsonPath("$.[*].actFluidNeedBike").value(hasItem(DEFAULT_ACT_FLUID_NEED_BIKE)))
            .andExpect(jsonPath("$.[*].actFluidNeedRun").value(hasItem(DEFAULT_ACT_FLUID_NEED_RUN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MAX)))
            .andExpect(jsonPath("$.[*].startGel").value(hasItem(DEFAULT_START_GEL)))
            .andExpect(jsonPath("$.[*].startDrank").value(hasItem(DEFAULT_START_DRANK)));
    }

    @Test
    @Transactional
    void getRacePlanForm() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get the racePlanForm
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL_ID, racePlanForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(racePlanForm.getId().intValue()))
            .andExpect(jsonPath("$.comp").value(DEFAULT_COMP))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.selectedOrgGelQuery").value(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue()))
            .andExpect(jsonPath("$.selectedOrgDrankQuery").value(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue()))
            .andExpect(jsonPath("$.orsBeforeStart").value(DEFAULT_ORS_BEFORE_START))
            .andExpect(jsonPath("$.drinkCarbo").value(DEFAULT_DRINK_CARBO))
            .andExpect(jsonPath("$.gelCarbo").value(DEFAULT_GEL_CARBO))
            .andExpect(jsonPath("$.drinkOrgCarbo").value(DEFAULT_DRINK_ORG_CARBO))
            .andExpect(jsonPath("$.gelOrgCarbo").value(DEFAULT_GEL_ORG_CARBO))
            .andExpect(jsonPath("$.sportDrinkOrgBike").value(DEFAULT_SPORT_DRINK_ORG_BIKE))
            .andExpect(jsonPath("$.waterOrgBike").value(DEFAULT_WATER_ORG_BIKE))
            .andExpect(jsonPath("$.gelsOrgBike").value(DEFAULT_GELS_ORG_BIKE))
            .andExpect(jsonPath("$.sportDrinkOrgRun").value(DEFAULT_SPORT_DRINK_ORG_RUN))
            .andExpect(jsonPath("$.waterOrgRun").value(DEFAULT_WATER_ORG_RUN))
            .andExpect(jsonPath("$.gelsOrgRun").value(DEFAULT_GELS_ORG_RUN))
            .andExpect(jsonPath("$.sportDrinkToTakeBike").value(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.waterToTakeBike").value(DEFAULT_WATER_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.extraWaterToTakeBike").value(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.orsToTakeBike").value(DEFAULT_ORS_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.gelsToTakeBike").value(DEFAULT_GELS_TO_TAKE_BIKE))
            .andExpect(jsonPath("$.sportDrinkToTakeRun").value(DEFAULT_SPORT_DRINK_TO_TAKE_RUN))
            .andExpect(jsonPath("$.waterToTakeRun").value(DEFAULT_WATER_TO_TAKE_RUN))
            .andExpect(jsonPath("$.extraWaterToTakeRun").value(DEFAULT_EXTRA_WATER_TO_TAKE_RUN))
            .andExpect(jsonPath("$.orsToTakeRun").value(DEFAULT_ORS_TO_TAKE_RUN))
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
            .andExpect(jsonPath("$.diffCarboRun").value(DEFAULT_DIFF_CARBO_RUN))
            .andExpect(jsonPath("$.diffMoisterRun").value(DEFAULT_DIFF_MOISTER_RUN))
            .andExpect(jsonPath("$.difCarbo").value(DEFAULT_DIF_CARBO))
            .andExpect(jsonPath("$.difMoister").value(DEFAULT_DIF_MOISTER))
            .andExpect(jsonPath("$.actFluidNeedBike").value(DEFAULT_ACT_FLUID_NEED_BIKE))
            .andExpect(jsonPath("$.actFluidNeedRun").value(DEFAULT_ACT_FLUID_NEED_RUN))
            .andExpect(jsonPath("$.carboNeedDuringBikeMin").value(DEFAULT_CARBO_NEED_DURING_BIKE_MIN))
            .andExpect(jsonPath("$.carboNeedDuringBikeMax").value(DEFAULT_CARBO_NEED_DURING_BIKE_MAX))
            .andExpect(jsonPath("$.carboNeedDuringRunMin").value(DEFAULT_CARBO_NEED_DURING_RUN_MIN))
            .andExpect(jsonPath("$.carboNeedDuringRunMax").value(DEFAULT_CARBO_NEED_DURING_RUN_MAX))
            .andExpect(jsonPath("$.startGel").value(DEFAULT_START_GEL))
            .andExpect(jsonPath("$.startDrank").value(DEFAULT_START_DRANK));
    }

    @Test
    @Transactional
    void getRacePlanFormsByIdFiltering() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        Long id = racePlanForm.getId();

        defaultRacePlanFormShouldBeFound("id.equals=" + id);
        defaultRacePlanFormShouldNotBeFound("id.notEquals=" + id);

        defaultRacePlanFormShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRacePlanFormShouldNotBeFound("id.greaterThan=" + id);

        defaultRacePlanFormShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRacePlanFormShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp equals to DEFAULT_COMP
        defaultRacePlanFormShouldBeFound("comp.equals=" + DEFAULT_COMP);

        // Get all the racePlanFormList where comp equals to UPDATED_COMP
        defaultRacePlanFormShouldNotBeFound("comp.equals=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp not equals to DEFAULT_COMP
        defaultRacePlanFormShouldNotBeFound("comp.notEquals=" + DEFAULT_COMP);

        // Get all the racePlanFormList where comp not equals to UPDATED_COMP
        defaultRacePlanFormShouldBeFound("comp.notEquals=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp in DEFAULT_COMP or UPDATED_COMP
        defaultRacePlanFormShouldBeFound("comp.in=" + DEFAULT_COMP + "," + UPDATED_COMP);

        // Get all the racePlanFormList where comp equals to UPDATED_COMP
        defaultRacePlanFormShouldNotBeFound("comp.in=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp is not null
        defaultRacePlanFormShouldBeFound("comp.specified=true");

        // Get all the racePlanFormList where comp is null
        defaultRacePlanFormShouldNotBeFound("comp.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompContainsSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp contains DEFAULT_COMP
        defaultRacePlanFormShouldBeFound("comp.contains=" + DEFAULT_COMP);

        // Get all the racePlanFormList where comp contains UPDATED_COMP
        defaultRacePlanFormShouldNotBeFound("comp.contains=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCompNotContainsSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where comp does not contain DEFAULT_COMP
        defaultRacePlanFormShouldNotBeFound("comp.doesNotContain=" + DEFAULT_COMP);

        // Get all the racePlanFormList where comp does not contain UPDATED_COMP
        defaultRacePlanFormShouldBeFound("comp.doesNotContain=" + UPDATED_COMP);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name equals to DEFAULT_NAME
        defaultRacePlanFormShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the racePlanFormList where name equals to UPDATED_NAME
        defaultRacePlanFormShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name not equals to DEFAULT_NAME
        defaultRacePlanFormShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the racePlanFormList where name not equals to UPDATED_NAME
        defaultRacePlanFormShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRacePlanFormShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the racePlanFormList where name equals to UPDATED_NAME
        defaultRacePlanFormShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name is not null
        defaultRacePlanFormShouldBeFound("name.specified=true");

        // Get all the racePlanFormList where name is null
        defaultRacePlanFormShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameContainsSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name contains DEFAULT_NAME
        defaultRacePlanFormShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the racePlanFormList where name contains UPDATED_NAME
        defaultRacePlanFormShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where name does not contain DEFAULT_NAME
        defaultRacePlanFormShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the racePlanFormList where name does not contain UPDATED_NAME
        defaultRacePlanFormShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgGelQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgGelQuery equals to DEFAULT_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldBeFound("selectedOrgGelQuery.equals=" + DEFAULT_SELECTED_ORG_GEL_QUERY);

        // Get all the racePlanFormList where selectedOrgGelQuery equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgGelQuery.equals=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgGelQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgGelQuery not equals to DEFAULT_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgGelQuery.notEquals=" + DEFAULT_SELECTED_ORG_GEL_QUERY);

        // Get all the racePlanFormList where selectedOrgGelQuery not equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldBeFound("selectedOrgGelQuery.notEquals=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgGelQueryIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgGelQuery in DEFAULT_SELECTED_ORG_GEL_QUERY or UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldBeFound("selectedOrgGelQuery.in=" + DEFAULT_SELECTED_ORG_GEL_QUERY + "," + UPDATED_SELECTED_ORG_GEL_QUERY);

        // Get all the racePlanFormList where selectedOrgGelQuery equals to UPDATED_SELECTED_ORG_GEL_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgGelQuery.in=" + UPDATED_SELECTED_ORG_GEL_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgGelQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgGelQuery is not null
        defaultRacePlanFormShouldBeFound("selectedOrgGelQuery.specified=true");

        // Get all the racePlanFormList where selectedOrgGelQuery is null
        defaultRacePlanFormShouldNotBeFound("selectedOrgGelQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgDrankQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgDrankQuery equals to DEFAULT_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldBeFound("selectedOrgDrankQuery.equals=" + DEFAULT_SELECTED_ORG_DRANK_QUERY);

        // Get all the racePlanFormList where selectedOrgDrankQuery equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgDrankQuery.equals=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgDrankQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgDrankQuery not equals to DEFAULT_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgDrankQuery.notEquals=" + DEFAULT_SELECTED_ORG_DRANK_QUERY);

        // Get all the racePlanFormList where selectedOrgDrankQuery not equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldBeFound("selectedOrgDrankQuery.notEquals=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgDrankQueryIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgDrankQuery in DEFAULT_SELECTED_ORG_DRANK_QUERY or UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldBeFound(
            "selectedOrgDrankQuery.in=" + DEFAULT_SELECTED_ORG_DRANK_QUERY + "," + UPDATED_SELECTED_ORG_DRANK_QUERY
        );

        // Get all the racePlanFormList where selectedOrgDrankQuery equals to UPDATED_SELECTED_ORG_DRANK_QUERY
        defaultRacePlanFormShouldNotBeFound("selectedOrgDrankQuery.in=" + UPDATED_SELECTED_ORG_DRANK_QUERY);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySelectedOrgDrankQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where selectedOrgDrankQuery is not null
        defaultRacePlanFormShouldBeFound("selectedOrgDrankQuery.specified=true");

        // Get all the racePlanFormList where selectedOrgDrankQuery is null
        defaultRacePlanFormShouldNotBeFound("selectedOrgDrankQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart equals to DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.equals=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart equals to UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.equals=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart not equals to DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.notEquals=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart not equals to UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.notEquals=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart in DEFAULT_ORS_BEFORE_START or UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.in=" + DEFAULT_ORS_BEFORE_START + "," + UPDATED_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart equals to UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.in=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart is not null
        defaultRacePlanFormShouldBeFound("orsBeforeStart.specified=true");

        // Get all the racePlanFormList where orsBeforeStart is null
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart is greater than or equal to DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.greaterThanOrEqual=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart is greater than or equal to UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.greaterThanOrEqual=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart is less than or equal to DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.lessThanOrEqual=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart is less than or equal to SMALLER_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.lessThanOrEqual=" + SMALLER_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart is less than DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.lessThan=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart is less than UPDATED_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.lessThan=" + UPDATED_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsBeforeStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsBeforeStart is greater than DEFAULT_ORS_BEFORE_START
        defaultRacePlanFormShouldNotBeFound("orsBeforeStart.greaterThan=" + DEFAULT_ORS_BEFORE_START);

        // Get all the racePlanFormList where orsBeforeStart is greater than SMALLER_ORS_BEFORE_START
        defaultRacePlanFormShouldBeFound("orsBeforeStart.greaterThan=" + SMALLER_ORS_BEFORE_START);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo equals to DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.equals=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo equals to UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.equals=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo not equals to DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.notEquals=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo not equals to UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.notEquals=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo in DEFAULT_DRINK_CARBO or UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.in=" + DEFAULT_DRINK_CARBO + "," + UPDATED_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo equals to UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.in=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo is not null
        defaultRacePlanFormShouldBeFound("drinkCarbo.specified=true");

        // Get all the racePlanFormList where drinkCarbo is null
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo is greater than or equal to DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.greaterThanOrEqual=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo is greater than or equal to UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.greaterThanOrEqual=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo is less than or equal to DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.lessThanOrEqual=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo is less than or equal to SMALLER_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.lessThanOrEqual=" + SMALLER_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo is less than DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.lessThan=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo is less than UPDATED_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.lessThan=" + UPDATED_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkCarbo is greater than DEFAULT_DRINK_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkCarbo.greaterThan=" + DEFAULT_DRINK_CARBO);

        // Get all the racePlanFormList where drinkCarbo is greater than SMALLER_DRINK_CARBO
        defaultRacePlanFormShouldBeFound("drinkCarbo.greaterThan=" + SMALLER_DRINK_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo equals to DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.equals=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo equals to UPDATED_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.equals=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo not equals to DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.notEquals=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo not equals to UPDATED_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.notEquals=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo in DEFAULT_GEL_CARBO or UPDATED_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.in=" + DEFAULT_GEL_CARBO + "," + UPDATED_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo equals to UPDATED_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.in=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo is not null
        defaultRacePlanFormShouldBeFound("gelCarbo.specified=true");

        // Get all the racePlanFormList where gelCarbo is null
        defaultRacePlanFormShouldNotBeFound("gelCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo is greater than or equal to DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.greaterThanOrEqual=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo is greater than or equal to UPDATED_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.greaterThanOrEqual=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo is less than or equal to DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.lessThanOrEqual=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo is less than or equal to SMALLER_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.lessThanOrEqual=" + SMALLER_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo is less than DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.lessThan=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo is less than UPDATED_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.lessThan=" + UPDATED_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelCarbo is greater than DEFAULT_GEL_CARBO
        defaultRacePlanFormShouldNotBeFound("gelCarbo.greaterThan=" + DEFAULT_GEL_CARBO);

        // Get all the racePlanFormList where gelCarbo is greater than SMALLER_GEL_CARBO
        defaultRacePlanFormShouldBeFound("gelCarbo.greaterThan=" + SMALLER_GEL_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo equals to DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.equals=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo equals to UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.equals=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo not equals to DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.notEquals=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo not equals to UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.notEquals=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo in DEFAULT_DRINK_ORG_CARBO or UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.in=" + DEFAULT_DRINK_ORG_CARBO + "," + UPDATED_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo equals to UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.in=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo is not null
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.specified=true");

        // Get all the racePlanFormList where drinkOrgCarbo is null
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo is greater than or equal to DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.greaterThanOrEqual=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo is greater than or equal to UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.greaterThanOrEqual=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo is less than or equal to DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.lessThanOrEqual=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo is less than or equal to SMALLER_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.lessThanOrEqual=" + SMALLER_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo is less than DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.lessThan=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo is less than UPDATED_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.lessThan=" + UPDATED_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDrinkOrgCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where drinkOrgCarbo is greater than DEFAULT_DRINK_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("drinkOrgCarbo.greaterThan=" + DEFAULT_DRINK_ORG_CARBO);

        // Get all the racePlanFormList where drinkOrgCarbo is greater than SMALLER_DRINK_ORG_CARBO
        defaultRacePlanFormShouldBeFound("drinkOrgCarbo.greaterThan=" + SMALLER_DRINK_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo equals to DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.equals=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo equals to UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.equals=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo not equals to DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.notEquals=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo not equals to UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.notEquals=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo in DEFAULT_GEL_ORG_CARBO or UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.in=" + DEFAULT_GEL_ORG_CARBO + "," + UPDATED_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo equals to UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.in=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo is not null
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.specified=true");

        // Get all the racePlanFormList where gelOrgCarbo is null
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo is greater than or equal to DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.greaterThanOrEqual=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo is greater than or equal to UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.greaterThanOrEqual=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo is less than or equal to DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.lessThanOrEqual=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo is less than or equal to SMALLER_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.lessThanOrEqual=" + SMALLER_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo is less than DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.lessThan=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo is less than UPDATED_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.lessThan=" + UPDATED_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelOrgCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelOrgCarbo is greater than DEFAULT_GEL_ORG_CARBO
        defaultRacePlanFormShouldNotBeFound("gelOrgCarbo.greaterThan=" + DEFAULT_GEL_ORG_CARBO);

        // Get all the racePlanFormList where gelOrgCarbo is greater than SMALLER_GEL_ORG_CARBO
        defaultRacePlanFormShouldBeFound("gelOrgCarbo.greaterThan=" + SMALLER_GEL_ORG_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike equals to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.equals=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.equals=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike not equals to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.notEquals=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike not equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.notEquals=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike in DEFAULT_SPORT_DRINK_ORG_BIKE or UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.in=" + DEFAULT_SPORT_DRINK_ORG_BIKE + "," + UPDATED_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike equals to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.in=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike is not null
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.specified=true");

        // Get all the racePlanFormList where sportDrinkOrgBike is null
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike is greater than or equal to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike is greater than or equal to UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike is less than or equal to DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike is less than or equal to SMALLER_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.lessThanOrEqual=" + SMALLER_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike is less than DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.lessThan=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike is less than UPDATED_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.lessThan=" + UPDATED_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgBike is greater than DEFAULT_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgBike.greaterThan=" + DEFAULT_SPORT_DRINK_ORG_BIKE);

        // Get all the racePlanFormList where sportDrinkOrgBike is greater than SMALLER_SPORT_DRINK_ORG_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkOrgBike.greaterThan=" + SMALLER_SPORT_DRINK_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike equals to DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.equals=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike equals to UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.equals=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike not equals to DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.notEquals=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike not equals to UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.notEquals=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike in DEFAULT_WATER_ORG_BIKE or UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.in=" + DEFAULT_WATER_ORG_BIKE + "," + UPDATED_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike equals to UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.in=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike is not null
        defaultRacePlanFormShouldBeFound("waterOrgBike.specified=true");

        // Get all the racePlanFormList where waterOrgBike is null
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike is greater than or equal to DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.greaterThanOrEqual=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike is greater than or equal to UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.greaterThanOrEqual=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike is less than or equal to DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.lessThanOrEqual=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike is less than or equal to SMALLER_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.lessThanOrEqual=" + SMALLER_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike is less than DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.lessThan=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike is less than UPDATED_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.lessThan=" + UPDATED_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgBike is greater than DEFAULT_WATER_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("waterOrgBike.greaterThan=" + DEFAULT_WATER_ORG_BIKE);

        // Get all the racePlanFormList where waterOrgBike is greater than SMALLER_WATER_ORG_BIKE
        defaultRacePlanFormShouldBeFound("waterOrgBike.greaterThan=" + SMALLER_WATER_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike equals to DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.equals=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike equals to UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.equals=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike not equals to DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.notEquals=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike not equals to UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.notEquals=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike in DEFAULT_GELS_ORG_BIKE or UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.in=" + DEFAULT_GELS_ORG_BIKE + "," + UPDATED_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike equals to UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.in=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike is not null
        defaultRacePlanFormShouldBeFound("gelsOrgBike.specified=true");

        // Get all the racePlanFormList where gelsOrgBike is null
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike is greater than or equal to DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.greaterThanOrEqual=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike is greater than or equal to UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.greaterThanOrEqual=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike is less than or equal to DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.lessThanOrEqual=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike is less than or equal to SMALLER_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.lessThanOrEqual=" + SMALLER_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike is less than DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.lessThan=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike is less than UPDATED_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.lessThan=" + UPDATED_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgBike is greater than DEFAULT_GELS_ORG_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsOrgBike.greaterThan=" + DEFAULT_GELS_ORG_BIKE);

        // Get all the racePlanFormList where gelsOrgBike is greater than SMALLER_GELS_ORG_BIKE
        defaultRacePlanFormShouldBeFound("gelsOrgBike.greaterThan=" + SMALLER_GELS_ORG_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun equals to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.equals=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.equals=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun not equals to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.notEquals=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun not equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.notEquals=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun in DEFAULT_SPORT_DRINK_ORG_RUN or UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.in=" + DEFAULT_SPORT_DRINK_ORG_RUN + "," + UPDATED_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun equals to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.in=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun is not null
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.specified=true");

        // Get all the racePlanFormList where sportDrinkOrgRun is null
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun is greater than or equal to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun is greater than or equal to UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun is less than or equal to DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun is less than or equal to SMALLER_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.lessThanOrEqual=" + SMALLER_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun is less than DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.lessThan=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun is less than UPDATED_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.lessThan=" + UPDATED_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkOrgRun is greater than DEFAULT_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkOrgRun.greaterThan=" + DEFAULT_SPORT_DRINK_ORG_RUN);

        // Get all the racePlanFormList where sportDrinkOrgRun is greater than SMALLER_SPORT_DRINK_ORG_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkOrgRun.greaterThan=" + SMALLER_SPORT_DRINK_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun equals to DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.equals=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun equals to UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.equals=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun not equals to DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.notEquals=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun not equals to UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.notEquals=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun in DEFAULT_WATER_ORG_RUN or UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.in=" + DEFAULT_WATER_ORG_RUN + "," + UPDATED_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun equals to UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.in=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun is not null
        defaultRacePlanFormShouldBeFound("waterOrgRun.specified=true");

        // Get all the racePlanFormList where waterOrgRun is null
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun is greater than or equal to DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.greaterThanOrEqual=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun is greater than or equal to UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.greaterThanOrEqual=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun is less than or equal to DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.lessThanOrEqual=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun is less than or equal to SMALLER_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.lessThanOrEqual=" + SMALLER_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun is less than DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.lessThan=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun is less than UPDATED_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.lessThan=" + UPDATED_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterOrgRun is greater than DEFAULT_WATER_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("waterOrgRun.greaterThan=" + DEFAULT_WATER_ORG_RUN);

        // Get all the racePlanFormList where waterOrgRun is greater than SMALLER_WATER_ORG_RUN
        defaultRacePlanFormShouldBeFound("waterOrgRun.greaterThan=" + SMALLER_WATER_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun equals to DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.equals=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun equals to UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.equals=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun not equals to DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.notEquals=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun not equals to UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.notEquals=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun in DEFAULT_GELS_ORG_RUN or UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.in=" + DEFAULT_GELS_ORG_RUN + "," + UPDATED_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun equals to UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.in=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun is not null
        defaultRacePlanFormShouldBeFound("gelsOrgRun.specified=true");

        // Get all the racePlanFormList where gelsOrgRun is null
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun is greater than or equal to DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.greaterThanOrEqual=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun is greater than or equal to UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.greaterThanOrEqual=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun is less than or equal to DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.lessThanOrEqual=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun is less than or equal to SMALLER_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.lessThanOrEqual=" + SMALLER_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun is less than DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.lessThan=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun is less than UPDATED_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.lessThan=" + UPDATED_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsOrgRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsOrgRun is greater than DEFAULT_GELS_ORG_RUN
        defaultRacePlanFormShouldNotBeFound("gelsOrgRun.greaterThan=" + DEFAULT_GELS_ORG_RUN);

        // Get all the racePlanFormList where gelsOrgRun is greater than SMALLER_GELS_ORG_RUN
        defaultRacePlanFormShouldBeFound("gelsOrgRun.greaterThan=" + SMALLER_GELS_ORG_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike equals to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.equals=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.equals=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike not equals to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.notEquals=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike not equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.notEquals=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike in DEFAULT_SPORT_DRINK_TO_TAKE_BIKE or UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound(
            "sportDrinkToTakeBike.in=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE + "," + UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        );

        // Get all the racePlanFormList where sportDrinkToTakeBike equals to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.in=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike is not null
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.specified=true");

        // Get all the racePlanFormList where sportDrinkToTakeBike is null
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike is greater than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike is greater than or equal to UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike is less than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike is less than or equal to SMALLER_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.lessThanOrEqual=" + SMALLER_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike is less than DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.lessThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike is less than UPDATED_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.lessThan=" + UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeBike is greater than DEFAULT_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeBike.greaterThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);

        // Get all the racePlanFormList where sportDrinkToTakeBike is greater than SMALLER_SPORT_DRINK_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeBike.greaterThan=" + SMALLER_SPORT_DRINK_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike equals to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.equals=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.equals=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike not equals to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.notEquals=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike not equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.notEquals=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike in DEFAULT_WATER_TO_TAKE_BIKE or UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.in=" + DEFAULT_WATER_TO_TAKE_BIKE + "," + UPDATED_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike equals to UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.in=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike is not null
        defaultRacePlanFormShouldBeFound("waterToTakeBike.specified=true");

        // Get all the racePlanFormList where waterToTakeBike is null
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike is greater than or equal to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.greaterThanOrEqual=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike is greater than or equal to UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.greaterThanOrEqual=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike is less than or equal to DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.lessThanOrEqual=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike is less than or equal to SMALLER_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.lessThanOrEqual=" + SMALLER_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike is less than DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.lessThan=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike is less than UPDATED_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.lessThan=" + UPDATED_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeBike is greater than DEFAULT_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("waterToTakeBike.greaterThan=" + DEFAULT_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where waterToTakeBike is greater than SMALLER_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("waterToTakeBike.greaterThan=" + SMALLER_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike equals to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.equals=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.equals=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike not equals to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.notEquals=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike not equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.notEquals=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike in DEFAULT_EXTRA_WATER_TO_TAKE_BIKE or UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound(
            "extraWaterToTakeBike.in=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE + "," + UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        );

        // Get all the racePlanFormList where extraWaterToTakeBike equals to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.in=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike is not null
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.specified=true");

        // Get all the racePlanFormList where extraWaterToTakeBike is null
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike is greater than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.greaterThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike is greater than or equal to UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.greaterThanOrEqual=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike is less than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.lessThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike is less than or equal to SMALLER_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.lessThanOrEqual=" + SMALLER_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike is less than DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.lessThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike is less than UPDATED_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.lessThan=" + UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeBike is greater than DEFAULT_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeBike.greaterThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);

        // Get all the racePlanFormList where extraWaterToTakeBike is greater than SMALLER_EXTRA_WATER_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("extraWaterToTakeBike.greaterThan=" + SMALLER_EXTRA_WATER_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike equals to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.equals=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.equals=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike not equals to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.notEquals=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike not equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.notEquals=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike in DEFAULT_ORS_TO_TAKE_BIKE or UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.in=" + DEFAULT_ORS_TO_TAKE_BIKE + "," + UPDATED_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike equals to UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.in=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike is not null
        defaultRacePlanFormShouldBeFound("orsToTakeBike.specified=true");

        // Get all the racePlanFormList where orsToTakeBike is null
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike is greater than or equal to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.greaterThanOrEqual=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike is greater than or equal to UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.greaterThanOrEqual=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike is less than or equal to DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.lessThanOrEqual=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike is less than or equal to SMALLER_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.lessThanOrEqual=" + SMALLER_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike is less than DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.lessThan=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike is less than UPDATED_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.lessThan=" + UPDATED_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeBike is greater than DEFAULT_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("orsToTakeBike.greaterThan=" + DEFAULT_ORS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where orsToTakeBike is greater than SMALLER_ORS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("orsToTakeBike.greaterThan=" + SMALLER_ORS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike equals to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.equals=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.equals=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike not equals to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.notEquals=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike not equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.notEquals=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike in DEFAULT_GELS_TO_TAKE_BIKE or UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.in=" + DEFAULT_GELS_TO_TAKE_BIKE + "," + UPDATED_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike equals to UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.in=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike is not null
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.specified=true");

        // Get all the racePlanFormList where gelsToTakeBike is null
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike is greater than or equal to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.greaterThanOrEqual=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike is greater than or equal to UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.greaterThanOrEqual=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike is less than or equal to DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.lessThanOrEqual=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike is less than or equal to SMALLER_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.lessThanOrEqual=" + SMALLER_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike is less than DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.lessThan=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike is less than UPDATED_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.lessThan=" + UPDATED_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeBike is greater than DEFAULT_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldNotBeFound("gelsToTakeBike.greaterThan=" + DEFAULT_GELS_TO_TAKE_BIKE);

        // Get all the racePlanFormList where gelsToTakeBike is greater than SMALLER_GELS_TO_TAKE_BIKE
        defaultRacePlanFormShouldBeFound("gelsToTakeBike.greaterThan=" + SMALLER_GELS_TO_TAKE_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun equals to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.equals=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.equals=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun not equals to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.notEquals=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun not equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.notEquals=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun in DEFAULT_SPORT_DRINK_TO_TAKE_RUN or UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound(
            "sportDrinkToTakeRun.in=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN + "," + UPDATED_SPORT_DRINK_TO_TAKE_RUN
        );

        // Get all the racePlanFormList where sportDrinkToTakeRun equals to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.in=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun is not null
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.specified=true");

        // Get all the racePlanFormList where sportDrinkToTakeRun is null
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun is greater than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.greaterThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun is greater than or equal to UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.greaterThanOrEqual=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun is less than or equal to DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.lessThanOrEqual=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun is less than or equal to SMALLER_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.lessThanOrEqual=" + SMALLER_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun is less than DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.lessThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun is less than UPDATED_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.lessThan=" + UPDATED_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsBySportDrinkToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where sportDrinkToTakeRun is greater than DEFAULT_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("sportDrinkToTakeRun.greaterThan=" + DEFAULT_SPORT_DRINK_TO_TAKE_RUN);

        // Get all the racePlanFormList where sportDrinkToTakeRun is greater than SMALLER_SPORT_DRINK_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("sportDrinkToTakeRun.greaterThan=" + SMALLER_SPORT_DRINK_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun equals to DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.equals=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.equals=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun not equals to DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.notEquals=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun not equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.notEquals=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun in DEFAULT_WATER_TO_TAKE_RUN or UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.in=" + DEFAULT_WATER_TO_TAKE_RUN + "," + UPDATED_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun equals to UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.in=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun is not null
        defaultRacePlanFormShouldBeFound("waterToTakeRun.specified=true");

        // Get all the racePlanFormList where waterToTakeRun is null
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun is greater than or equal to DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.greaterThanOrEqual=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun is greater than or equal to UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.greaterThanOrEqual=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun is less than or equal to DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.lessThanOrEqual=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun is less than or equal to SMALLER_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.lessThanOrEqual=" + SMALLER_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun is less than DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.lessThan=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun is less than UPDATED_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.lessThan=" + UPDATED_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWaterToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where waterToTakeRun is greater than DEFAULT_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("waterToTakeRun.greaterThan=" + DEFAULT_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where waterToTakeRun is greater than SMALLER_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("waterToTakeRun.greaterThan=" + SMALLER_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun equals to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.equals=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.equals=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun not equals to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.notEquals=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun not equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.notEquals=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun in DEFAULT_EXTRA_WATER_TO_TAKE_RUN or UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound(
            "extraWaterToTakeRun.in=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN + "," + UPDATED_EXTRA_WATER_TO_TAKE_RUN
        );

        // Get all the racePlanFormList where extraWaterToTakeRun equals to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.in=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun is not null
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.specified=true");

        // Get all the racePlanFormList where extraWaterToTakeRun is null
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun is greater than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.greaterThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun is greater than or equal to UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.greaterThanOrEqual=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun is less than or equal to DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.lessThanOrEqual=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun is less than or equal to SMALLER_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.lessThanOrEqual=" + SMALLER_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun is less than DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.lessThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun is less than UPDATED_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.lessThan=" + UPDATED_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByExtraWaterToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where extraWaterToTakeRun is greater than DEFAULT_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("extraWaterToTakeRun.greaterThan=" + DEFAULT_EXTRA_WATER_TO_TAKE_RUN);

        // Get all the racePlanFormList where extraWaterToTakeRun is greater than SMALLER_EXTRA_WATER_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("extraWaterToTakeRun.greaterThan=" + SMALLER_EXTRA_WATER_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun equals to DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.equals=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.equals=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun not equals to DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.notEquals=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun not equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.notEquals=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun in DEFAULT_ORS_TO_TAKE_RUN or UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.in=" + DEFAULT_ORS_TO_TAKE_RUN + "," + UPDATED_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun equals to UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.in=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun is not null
        defaultRacePlanFormShouldBeFound("orsToTakeRun.specified=true");

        // Get all the racePlanFormList where orsToTakeRun is null
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun is greater than or equal to DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.greaterThanOrEqual=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun is greater than or equal to UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.greaterThanOrEqual=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun is less than or equal to DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.lessThanOrEqual=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun is less than or equal to SMALLER_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.lessThanOrEqual=" + SMALLER_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun is less than DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.lessThan=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun is less than UPDATED_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.lessThan=" + UPDATED_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByOrsToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where orsToTakeRun is greater than DEFAULT_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("orsToTakeRun.greaterThan=" + DEFAULT_ORS_TO_TAKE_RUN);

        // Get all the racePlanFormList where orsToTakeRun is greater than SMALLER_ORS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("orsToTakeRun.greaterThan=" + SMALLER_ORS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun equals to DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.equals=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.equals=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun not equals to DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.notEquals=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun not equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.notEquals=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun in DEFAULT_GELS_TO_TAKE_RUN or UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.in=" + DEFAULT_GELS_TO_TAKE_RUN + "," + UPDATED_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun equals to UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.in=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun is not null
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.specified=true");

        // Get all the racePlanFormList where gelsToTakeRun is null
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun is greater than or equal to DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.greaterThanOrEqual=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun is greater than or equal to UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.greaterThanOrEqual=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun is less than or equal to DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.lessThanOrEqual=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun is less than or equal to SMALLER_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.lessThanOrEqual=" + SMALLER_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun is less than DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.lessThan=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun is less than UPDATED_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.lessThan=" + UPDATED_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByGelsToTakeRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where gelsToTakeRun is greater than DEFAULT_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldNotBeFound("gelsToTakeRun.greaterThan=" + DEFAULT_GELS_TO_TAKE_RUN);

        // Get all the racePlanFormList where gelsToTakeRun is greater than SMALLER_GELS_TO_TAKE_RUN
        defaultRacePlanFormShouldBeFound("gelsToTakeRun.greaterThan=" + SMALLER_GELS_TO_TAKE_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike equals to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.equals=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.equals=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike not equals to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.notEquals=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike not equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.notEquals=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike in DEFAULT_WEIGHT_LOSS_DURING_BIKE or UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound(
            "weightLossDuringBike.in=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE + "," + UPDATED_WEIGHT_LOSS_DURING_BIKE
        );

        // Get all the racePlanFormList where weightLossDuringBike equals to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.in=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike is not null
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.specified=true");

        // Get all the racePlanFormList where weightLossDuringBike is null
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike is greater than or equal to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.greaterThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike is greater than or equal to UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.greaterThanOrEqual=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike is less than or equal to DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.lessThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike is less than or equal to SMALLER_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.lessThanOrEqual=" + SMALLER_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike is less than DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.lessThan=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike is less than UPDATED_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.lessThan=" + UPDATED_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringBike is greater than DEFAULT_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("weightLossDuringBike.greaterThan=" + DEFAULT_WEIGHT_LOSS_DURING_BIKE);

        // Get all the racePlanFormList where weightLossDuringBike is greater than SMALLER_WEIGHT_LOSS_DURING_BIKE
        defaultRacePlanFormShouldBeFound("weightLossDuringBike.greaterThan=" + SMALLER_WEIGHT_LOSS_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun equals to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.equals=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.equals=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun not equals to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun not equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun in DEFAULT_CARBO_NEED_DURING_RUN or UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.in=" + DEFAULT_CARBO_NEED_DURING_RUN + "," + UPDATED_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun equals to UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.in=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.specified=true");

        // Get all the racePlanFormList where carboNeedDuringRun is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun is less than or equal to SMALLER_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun is less than DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun is less than UPDATED_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRun is greater than DEFAULT_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRun.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN);

        // Get all the racePlanFormList where carboNeedDuringRun is greater than SMALLER_CARBO_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRun.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike equals to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.equals=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike not equals to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike not equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike in DEFAULT_FLUID_NEED_PER_HOUR_BIKE or UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound(
            "fluidNeedPerHourBike.in=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE + "," + UPDATED_FLUID_NEED_PER_HOUR_BIKE
        );

        // Get all the racePlanFormList where fluidNeedPerHourBike equals to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.in=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike is not null
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.specified=true");

        // Get all the racePlanFormList where fluidNeedPerHourBike is null
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike is less than DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike is less than UPDATED_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourBike is greater than DEFAULT_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourBike.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_BIKE);

        // Get all the racePlanFormList where fluidNeedPerHourBike is greater than SMALLER_FLUID_NEED_PER_HOUR_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourBike.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim equals to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.equals=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim not equals to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim not equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim in DEFAULT_FLUID_NEED_PER_HOUR_SWIM or UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound(
            "fluidNeedPerHourSwim.in=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM + "," + UPDATED_FLUID_NEED_PER_HOUR_SWIM
        );

        // Get all the racePlanFormList where fluidNeedPerHourSwim equals to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.in=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is not null
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.specified=true");

        // Get all the racePlanFormList where fluidNeedPerHourSwim is null
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is less than DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is less than UPDATED_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourSwimIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is greater than DEFAULT_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourSwim.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_SWIM);

        // Get all the racePlanFormList where fluidNeedPerHourSwim is greater than SMALLER_FLUID_NEED_PER_HOUR_SWIM
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourSwim.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_SWIM);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike equals to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.equals=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike not equals to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike not equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike in DEFAULT_CARBO_NEED_DURING_BIKE or UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.in=" + DEFAULT_CARBO_NEED_DURING_BIKE + "," + UPDATED_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike equals to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.in=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.specified=true");

        // Get all the racePlanFormList where carboNeedDuringBike is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike is less than DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike is less than UPDATED_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBike is greater than DEFAULT_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBike.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE);

        // Get all the racePlanFormList where carboNeedDuringBike is greater than SMALLER_CARBO_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("carboNeedDuringBike.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike equals to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.equals=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.equals=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike not equals to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.notEquals=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike not equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.notEquals=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike in DEFAULT_FLUID_NEED_DURING_BIKE or UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.in=" + DEFAULT_FLUID_NEED_DURING_BIKE + "," + UPDATED_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike equals to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.in=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike is not null
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.specified=true");

        // Get all the racePlanFormList where fluidNeedDuringBike is null
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike is greater than or equal to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike is greater than or equal to UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.greaterThanOrEqual=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike is less than or equal to DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.lessThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike is less than or equal to SMALLER_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.lessThanOrEqual=" + SMALLER_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike is less than DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.lessThan=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike is less than UPDATED_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.lessThan=" + UPDATED_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringBike is greater than DEFAULT_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringBike.greaterThan=" + DEFAULT_FLUID_NEED_DURING_BIKE);

        // Get all the racePlanFormList where fluidNeedDuringBike is greater than SMALLER_FLUID_NEED_DURING_BIKE
        defaultRacePlanFormShouldBeFound("fluidNeedDuringBike.greaterThan=" + SMALLER_FLUID_NEED_DURING_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun equals to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.equals=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.equals=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun not equals to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.notEquals=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun not equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.notEquals=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun in DEFAULT_FLUID_NEED_PER_HOUR_RUN or UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound(
            "fluidNeedPerHourRun.in=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN + "," + UPDATED_FLUID_NEED_PER_HOUR_RUN
        );

        // Get all the racePlanFormList where fluidNeedPerHourRun equals to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.in=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun is not null
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.specified=true");

        // Get all the racePlanFormList where fluidNeedPerHourRun is null
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun is greater than or equal to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun is greater than or equal to UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.greaterThanOrEqual=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun is less than or equal to DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.lessThanOrEqual=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun is less than or equal to SMALLER_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.lessThanOrEqual=" + SMALLER_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun is less than DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.lessThan=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun is less than UPDATED_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.lessThan=" + UPDATED_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedPerHourRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedPerHourRun is greater than DEFAULT_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedPerHourRun.greaterThan=" + DEFAULT_FLUID_NEED_PER_HOUR_RUN);

        // Get all the racePlanFormList where fluidNeedPerHourRun is greater than SMALLER_FLUID_NEED_PER_HOUR_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedPerHourRun.greaterThan=" + SMALLER_FLUID_NEED_PER_HOUR_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun equals to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.equals=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.equals=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun not equals to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.notEquals=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun not equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.notEquals=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun in DEFAULT_FLUID_NEED_DURING_RUN or UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.in=" + DEFAULT_FLUID_NEED_DURING_RUN + "," + UPDATED_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun equals to UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.in=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun is not null
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.specified=true");

        // Get all the racePlanFormList where fluidNeedDuringRun is null
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun is greater than or equal to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.greaterThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun is greater than or equal to UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.greaterThanOrEqual=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun is less than or equal to DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.lessThanOrEqual=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun is less than or equal to SMALLER_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.lessThanOrEqual=" + SMALLER_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun is less than DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.lessThan=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun is less than UPDATED_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.lessThan=" + UPDATED_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByFluidNeedDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where fluidNeedDuringRun is greater than DEFAULT_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("fluidNeedDuringRun.greaterThan=" + DEFAULT_FLUID_NEED_DURING_RUN);

        // Get all the racePlanFormList where fluidNeedDuringRun is greater than SMALLER_FLUID_NEED_DURING_RUN
        defaultRacePlanFormShouldBeFound("fluidNeedDuringRun.greaterThan=" + SMALLER_FLUID_NEED_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun equals to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.equals=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.equals=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun not equals to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.notEquals=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun not equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.notEquals=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun in DEFAULT_WEIGHT_LOSS_DURING_RUN or UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.in=" + DEFAULT_WEIGHT_LOSS_DURING_RUN + "," + UPDATED_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun equals to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.in=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun is not null
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.specified=true");

        // Get all the racePlanFormList where weightLossDuringRun is null
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun is greater than or equal to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.greaterThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun is greater than or equal to UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.greaterThanOrEqual=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun is less than or equal to DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.lessThanOrEqual=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun is less than or equal to SMALLER_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.lessThanOrEqual=" + SMALLER_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun is less than DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.lessThan=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun is less than UPDATED_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.lessThan=" + UPDATED_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByWeightLossDuringRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where weightLossDuringRun is greater than DEFAULT_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldNotBeFound("weightLossDuringRun.greaterThan=" + DEFAULT_WEIGHT_LOSS_DURING_RUN);

        // Get all the racePlanFormList where weightLossDuringRun is greater than SMALLER_WEIGHT_LOSS_DURING_RUN
        defaultRacePlanFormShouldBeFound("weightLossDuringRun.greaterThan=" + SMALLER_WEIGHT_LOSS_DURING_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun equals to DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.equals=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun equals to UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.equals=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun not equals to DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.notEquals=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun not equals to UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.notEquals=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun in DEFAULT_DIFF_CARBO_RUN or UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.in=" + DEFAULT_DIFF_CARBO_RUN + "," + UPDATED_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun equals to UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.in=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun is not null
        defaultRacePlanFormShouldBeFound("diffCarboRun.specified=true");

        // Get all the racePlanFormList where diffCarboRun is null
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun is greater than or equal to DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.greaterThanOrEqual=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun is greater than or equal to UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.greaterThanOrEqual=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun is less than or equal to DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.lessThanOrEqual=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun is less than or equal to SMALLER_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.lessThanOrEqual=" + SMALLER_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun is less than DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.lessThan=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun is less than UPDATED_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.lessThan=" + UPDATED_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffCarboRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffCarboRun is greater than DEFAULT_DIFF_CARBO_RUN
        defaultRacePlanFormShouldNotBeFound("diffCarboRun.greaterThan=" + DEFAULT_DIFF_CARBO_RUN);

        // Get all the racePlanFormList where diffCarboRun is greater than SMALLER_DIFF_CARBO_RUN
        defaultRacePlanFormShouldBeFound("diffCarboRun.greaterThan=" + SMALLER_DIFF_CARBO_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun equals to DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.equals=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun equals to UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.equals=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun not equals to DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.notEquals=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun not equals to UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.notEquals=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun in DEFAULT_DIFF_MOISTER_RUN or UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.in=" + DEFAULT_DIFF_MOISTER_RUN + "," + UPDATED_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun equals to UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.in=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun is not null
        defaultRacePlanFormShouldBeFound("diffMoisterRun.specified=true");

        // Get all the racePlanFormList where diffMoisterRun is null
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun is greater than or equal to DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.greaterThanOrEqual=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun is greater than or equal to UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.greaterThanOrEqual=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun is less than or equal to DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.lessThanOrEqual=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun is less than or equal to SMALLER_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.lessThanOrEqual=" + SMALLER_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun is less than DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.lessThan=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun is less than UPDATED_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.lessThan=" + UPDATED_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDiffMoisterRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where diffMoisterRun is greater than DEFAULT_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldNotBeFound("diffMoisterRun.greaterThan=" + DEFAULT_DIFF_MOISTER_RUN);

        // Get all the racePlanFormList where diffMoisterRun is greater than SMALLER_DIFF_MOISTER_RUN
        defaultRacePlanFormShouldBeFound("diffMoisterRun.greaterThan=" + SMALLER_DIFF_MOISTER_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo equals to DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.equals=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo equals to UPDATED_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.equals=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo not equals to DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.notEquals=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo not equals to UPDATED_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.notEquals=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo in DEFAULT_DIF_CARBO or UPDATED_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.in=" + DEFAULT_DIF_CARBO + "," + UPDATED_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo equals to UPDATED_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.in=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo is not null
        defaultRacePlanFormShouldBeFound("difCarbo.specified=true");

        // Get all the racePlanFormList where difCarbo is null
        defaultRacePlanFormShouldNotBeFound("difCarbo.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo is greater than or equal to DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.greaterThanOrEqual=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo is greater than or equal to UPDATED_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.greaterThanOrEqual=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo is less than or equal to DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.lessThanOrEqual=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo is less than or equal to SMALLER_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.lessThanOrEqual=" + SMALLER_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo is less than DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.lessThan=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo is less than UPDATED_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.lessThan=" + UPDATED_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifCarboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difCarbo is greater than DEFAULT_DIF_CARBO
        defaultRacePlanFormShouldNotBeFound("difCarbo.greaterThan=" + DEFAULT_DIF_CARBO);

        // Get all the racePlanFormList where difCarbo is greater than SMALLER_DIF_CARBO
        defaultRacePlanFormShouldBeFound("difCarbo.greaterThan=" + SMALLER_DIF_CARBO);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister equals to DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.equals=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister equals to UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.equals=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister not equals to DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.notEquals=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister not equals to UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.notEquals=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister in DEFAULT_DIF_MOISTER or UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.in=" + DEFAULT_DIF_MOISTER + "," + UPDATED_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister equals to UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.in=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister is not null
        defaultRacePlanFormShouldBeFound("difMoister.specified=true");

        // Get all the racePlanFormList where difMoister is null
        defaultRacePlanFormShouldNotBeFound("difMoister.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister is greater than or equal to DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.greaterThanOrEqual=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister is greater than or equal to UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.greaterThanOrEqual=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister is less than or equal to DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.lessThanOrEqual=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister is less than or equal to SMALLER_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.lessThanOrEqual=" + SMALLER_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister is less than DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.lessThan=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister is less than UPDATED_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.lessThan=" + UPDATED_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByDifMoisterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where difMoister is greater than DEFAULT_DIF_MOISTER
        defaultRacePlanFormShouldNotBeFound("difMoister.greaterThan=" + DEFAULT_DIF_MOISTER);

        // Get all the racePlanFormList where difMoister is greater than SMALLER_DIF_MOISTER
        defaultRacePlanFormShouldBeFound("difMoister.greaterThan=" + SMALLER_DIF_MOISTER);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike equals to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.equals=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.equals=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike not equals to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.notEquals=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike not equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.notEquals=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike in DEFAULT_ACT_FLUID_NEED_BIKE or UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.in=" + DEFAULT_ACT_FLUID_NEED_BIKE + "," + UPDATED_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike equals to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.in=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike is not null
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.specified=true");

        // Get all the racePlanFormList where actFluidNeedBike is null
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike is greater than or equal to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.greaterThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike is greater than or equal to UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.greaterThanOrEqual=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike is less than or equal to DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.lessThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike is less than or equal to SMALLER_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.lessThanOrEqual=" + SMALLER_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike is less than DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.lessThan=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike is less than UPDATED_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.lessThan=" + UPDATED_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedBikeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedBike is greater than DEFAULT_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldNotBeFound("actFluidNeedBike.greaterThan=" + DEFAULT_ACT_FLUID_NEED_BIKE);

        // Get all the racePlanFormList where actFluidNeedBike is greater than SMALLER_ACT_FLUID_NEED_BIKE
        defaultRacePlanFormShouldBeFound("actFluidNeedBike.greaterThan=" + SMALLER_ACT_FLUID_NEED_BIKE);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun equals to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.equals=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.equals=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun not equals to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.notEquals=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun not equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.notEquals=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun in DEFAULT_ACT_FLUID_NEED_RUN or UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.in=" + DEFAULT_ACT_FLUID_NEED_RUN + "," + UPDATED_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun equals to UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.in=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun is not null
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.specified=true");

        // Get all the racePlanFormList where actFluidNeedRun is null
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun is greater than or equal to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.greaterThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun is greater than or equal to UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.greaterThanOrEqual=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun is less than or equal to DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.lessThanOrEqual=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun is less than or equal to SMALLER_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.lessThanOrEqual=" + SMALLER_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun is less than DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.lessThan=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun is less than UPDATED_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.lessThan=" + UPDATED_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByActFluidNeedRunIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where actFluidNeedRun is greater than DEFAULT_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldNotBeFound("actFluidNeedRun.greaterThan=" + DEFAULT_ACT_FLUID_NEED_RUN);

        // Get all the racePlanFormList where actFluidNeedRun is greater than SMALLER_ACT_FLUID_NEED_RUN
        defaultRacePlanFormShouldBeFound("actFluidNeedRun.greaterThan=" + SMALLER_ACT_FLUID_NEED_RUN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin equals to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.equals=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin not equals to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin not equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin in DEFAULT_CARBO_NEED_DURING_BIKE_MIN or UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound(
            "carboNeedDuringBikeMin.in=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN + "," + UPDATED_CARBO_NEED_DURING_BIKE_MIN
        );

        // Get all the racePlanFormList where carboNeedDuringBikeMin equals to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.in=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.specified=true");

        // Get all the racePlanFormList where carboNeedDuringBikeMin is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is less than DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is less than UPDATED_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is greater than DEFAULT_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMin.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MIN);

        // Get all the racePlanFormList where carboNeedDuringBikeMin is greater than SMALLER_CARBO_NEED_DURING_BIKE_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMin.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax equals to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.equals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.equals=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax not equals to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.notEquals=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax not equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.notEquals=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax in DEFAULT_CARBO_NEED_DURING_BIKE_MAX or UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound(
            "carboNeedDuringBikeMax.in=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX + "," + UPDATED_CARBO_NEED_DURING_BIKE_MAX
        );

        // Get all the racePlanFormList where carboNeedDuringBikeMax equals to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.in=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.specified=true");

        // Get all the racePlanFormList where carboNeedDuringBikeMax is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is greater than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is greater than or equal to UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is less than or equal to DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is less than or equal to SMALLER_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is less than DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.lessThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is less than UPDATED_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.lessThan=" + UPDATED_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringBikeMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is greater than DEFAULT_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringBikeMax.greaterThan=" + DEFAULT_CARBO_NEED_DURING_BIKE_MAX);

        // Get all the racePlanFormList where carboNeedDuringBikeMax is greater than SMALLER_CARBO_NEED_DURING_BIKE_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringBikeMax.greaterThan=" + SMALLER_CARBO_NEED_DURING_BIKE_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin equals to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.equals=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.equals=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin not equals to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin not equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin in DEFAULT_CARBO_NEED_DURING_RUN_MIN or UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound(
            "carboNeedDuringRunMin.in=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN + "," + UPDATED_CARBO_NEED_DURING_RUN_MIN
        );

        // Get all the racePlanFormList where carboNeedDuringRunMin equals to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.in=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.specified=true");

        // Get all the racePlanFormList where carboNeedDuringRunMin is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin is less than or equal to SMALLER_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin is less than DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin is less than UPDATED_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMin is greater than DEFAULT_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMin.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MIN);

        // Get all the racePlanFormList where carboNeedDuringRunMin is greater than SMALLER_CARBO_NEED_DURING_RUN_MIN
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMin.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN_MIN);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax equals to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.equals=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.equals=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax not equals to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.notEquals=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax not equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.notEquals=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax in DEFAULT_CARBO_NEED_DURING_RUN_MAX or UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound(
            "carboNeedDuringRunMax.in=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX + "," + UPDATED_CARBO_NEED_DURING_RUN_MAX
        );

        // Get all the racePlanFormList where carboNeedDuringRunMax equals to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.in=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax is not null
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.specified=true");

        // Get all the racePlanFormList where carboNeedDuringRunMax is null
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax is greater than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.greaterThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax is greater than or equal to UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.greaterThanOrEqual=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax is less than or equal to DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.lessThanOrEqual=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax is less than or equal to SMALLER_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.lessThanOrEqual=" + SMALLER_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax is less than DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.lessThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax is less than UPDATED_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.lessThan=" + UPDATED_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByCarboNeedDuringRunMaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where carboNeedDuringRunMax is greater than DEFAULT_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldNotBeFound("carboNeedDuringRunMax.greaterThan=" + DEFAULT_CARBO_NEED_DURING_RUN_MAX);

        // Get all the racePlanFormList where carboNeedDuringRunMax is greater than SMALLER_CARBO_NEED_DURING_RUN_MAX
        defaultRacePlanFormShouldBeFound("carboNeedDuringRunMax.greaterThan=" + SMALLER_CARBO_NEED_DURING_RUN_MAX);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel equals to DEFAULT_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.equals=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel equals to UPDATED_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.equals=" + UPDATED_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel not equals to DEFAULT_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.notEquals=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel not equals to UPDATED_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.notEquals=" + UPDATED_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel in DEFAULT_START_GEL or UPDATED_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.in=" + DEFAULT_START_GEL + "," + UPDATED_START_GEL);

        // Get all the racePlanFormList where startGel equals to UPDATED_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.in=" + UPDATED_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel is not null
        defaultRacePlanFormShouldBeFound("startGel.specified=true");

        // Get all the racePlanFormList where startGel is null
        defaultRacePlanFormShouldNotBeFound("startGel.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel is greater than or equal to DEFAULT_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.greaterThanOrEqual=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel is greater than or equal to UPDATED_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.greaterThanOrEqual=" + UPDATED_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel is less than or equal to DEFAULT_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.lessThanOrEqual=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel is less than or equal to SMALLER_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.lessThanOrEqual=" + SMALLER_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel is less than DEFAULT_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.lessThan=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel is less than UPDATED_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.lessThan=" + UPDATED_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartGelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startGel is greater than DEFAULT_START_GEL
        defaultRacePlanFormShouldNotBeFound("startGel.greaterThan=" + DEFAULT_START_GEL);

        // Get all the racePlanFormList where startGel is greater than SMALLER_START_GEL
        defaultRacePlanFormShouldBeFound("startGel.greaterThan=" + SMALLER_START_GEL);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank equals to DEFAULT_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.equals=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank equals to UPDATED_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.equals=" + UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank not equals to DEFAULT_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.notEquals=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank not equals to UPDATED_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.notEquals=" + UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsInShouldWork() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank in DEFAULT_START_DRANK or UPDATED_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.in=" + DEFAULT_START_DRANK + "," + UPDATED_START_DRANK);

        // Get all the racePlanFormList where startDrank equals to UPDATED_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.in=" + UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsNullOrNotNull() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank is not null
        defaultRacePlanFormShouldBeFound("startDrank.specified=true");

        // Get all the racePlanFormList where startDrank is null
        defaultRacePlanFormShouldNotBeFound("startDrank.specified=false");
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank is greater than or equal to DEFAULT_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.greaterThanOrEqual=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank is greater than or equal to UPDATED_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.greaterThanOrEqual=" + UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank is less than or equal to DEFAULT_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.lessThanOrEqual=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank is less than or equal to SMALLER_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.lessThanOrEqual=" + SMALLER_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsLessThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank is less than DEFAULT_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.lessThan=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank is less than UPDATED_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.lessThan=" + UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByStartDrankIsGreaterThanSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        // Get all the racePlanFormList where startDrank is greater than DEFAULT_START_DRANK
        defaultRacePlanFormShouldNotBeFound("startDrank.greaterThan=" + DEFAULT_START_DRANK);

        // Get all the racePlanFormList where startDrank is greater than SMALLER_START_DRANK
        defaultRacePlanFormShouldBeFound("startDrank.greaterThan=" + SMALLER_START_DRANK);
    }

    @Test
    @Transactional
    void getAllRacePlanFormsByRaceIsEqualToSomething() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);
        Race race = RaceResourceIT.createEntity(em);
        em.persist(race);
        em.flush();
        racePlanForm.setRace(race);
        racePlanFormRepository.saveAndFlush(racePlanForm);
        Long raceId = race.getId();

        // Get all the racePlanFormList where race equals to raceId
        defaultRacePlanFormShouldBeFound("raceId.equals=" + raceId);

        // Get all the racePlanFormList where race equals to (raceId + 1)
        defaultRacePlanFormShouldNotBeFound("raceId.equals=" + (raceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRacePlanFormShouldBeFound(String filter) throws Exception {
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racePlanForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].comp").value(hasItem(DEFAULT_COMP)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].selectedOrgGelQuery").value(hasItem(DEFAULT_SELECTED_ORG_GEL_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].selectedOrgDrankQuery").value(hasItem(DEFAULT_SELECTED_ORG_DRANK_QUERY.booleanValue())))
            .andExpect(jsonPath("$.[*].orsBeforeStart").value(hasItem(DEFAULT_ORS_BEFORE_START)))
            .andExpect(jsonPath("$.[*].drinkCarbo").value(hasItem(DEFAULT_DRINK_CARBO)))
            .andExpect(jsonPath("$.[*].gelCarbo").value(hasItem(DEFAULT_GEL_CARBO)))
            .andExpect(jsonPath("$.[*].drinkOrgCarbo").value(hasItem(DEFAULT_DRINK_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].gelOrgCarbo").value(hasItem(DEFAULT_GEL_ORG_CARBO)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgBike").value(hasItem(DEFAULT_SPORT_DRINK_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].waterOrgBike").value(hasItem(DEFAULT_WATER_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].gelsOrgBike").value(hasItem(DEFAULT_GELS_ORG_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkOrgRun").value(hasItem(DEFAULT_SPORT_DRINK_ORG_RUN)))
            .andExpect(jsonPath("$.[*].waterOrgRun").value(hasItem(DEFAULT_WATER_ORG_RUN)))
            .andExpect(jsonPath("$.[*].gelsOrgRun").value(hasItem(DEFAULT_GELS_ORG_RUN)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeBike").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].waterToTakeBike").value(hasItem(DEFAULT_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeBike").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].orsToTakeBike").value(hasItem(DEFAULT_ORS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].gelsToTakeBike").value(hasItem(DEFAULT_GELS_TO_TAKE_BIKE)))
            .andExpect(jsonPath("$.[*].sportDrinkToTakeRun").value(hasItem(DEFAULT_SPORT_DRINK_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].waterToTakeRun").value(hasItem(DEFAULT_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].extraWaterToTakeRun").value(hasItem(DEFAULT_EXTRA_WATER_TO_TAKE_RUN)))
            .andExpect(jsonPath("$.[*].orsToTakeRun").value(hasItem(DEFAULT_ORS_TO_TAKE_RUN)))
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
            .andExpect(jsonPath("$.[*].diffCarboRun").value(hasItem(DEFAULT_DIFF_CARBO_RUN)))
            .andExpect(jsonPath("$.[*].diffMoisterRun").value(hasItem(DEFAULT_DIFF_MOISTER_RUN)))
            .andExpect(jsonPath("$.[*].difCarbo").value(hasItem(DEFAULT_DIF_CARBO)))
            .andExpect(jsonPath("$.[*].difMoister").value(hasItem(DEFAULT_DIF_MOISTER)))
            .andExpect(jsonPath("$.[*].actFluidNeedBike").value(hasItem(DEFAULT_ACT_FLUID_NEED_BIKE)))
            .andExpect(jsonPath("$.[*].actFluidNeedRun").value(hasItem(DEFAULT_ACT_FLUID_NEED_RUN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringBikeMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_BIKE_MAX)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMin").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MIN)))
            .andExpect(jsonPath("$.[*].carboNeedDuringRunMax").value(hasItem(DEFAULT_CARBO_NEED_DURING_RUN_MAX)))
            .andExpect(jsonPath("$.[*].startGel").value(hasItem(DEFAULT_START_GEL)))
            .andExpect(jsonPath("$.[*].startDrank").value(hasItem(DEFAULT_START_DRANK)));

        // Check, that the count call also returns 1
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRacePlanFormShouldNotBeFound(String filter) throws Exception {
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRacePlanFormMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRacePlanForm() throws Exception {
        // Get the racePlanForm
        restRacePlanFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRacePlanForm() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();

        // Update the racePlanForm
        RacePlanForm updatedRacePlanForm = racePlanFormRepository.findById(racePlanForm.getId()).get();
        // Disconnect from session so that the updates on updatedRacePlanForm are not directly saved in db
        em.detach(updatedRacePlanForm);
        updatedRacePlanForm
            .comp(UPDATED_COMP)
            .name(UPDATED_NAME)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
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
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .startGel(UPDATED_START_GEL)
            .startDrank(UPDATED_START_DRANK);

        restRacePlanFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRacePlanForm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRacePlanForm))
            )
            .andExpect(status().isOk());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
        RacePlanForm testRacePlanForm = racePlanFormList.get(racePlanFormList.size() - 1);
        assertThat(testRacePlanForm.getComp()).isEqualTo(UPDATED_COMP);
        assertThat(testRacePlanForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRacePlanForm.getSelectedOrgGelQuery()).isEqualTo(UPDATED_SELECTED_ORG_GEL_QUERY);
        assertThat(testRacePlanForm.getSelectedOrgDrankQuery()).isEqualTo(UPDATED_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRacePlanForm.getOrsBeforeStart()).isEqualTo(UPDATED_ORS_BEFORE_START);
        assertThat(testRacePlanForm.getDrinkCarbo()).isEqualTo(UPDATED_DRINK_CARBO);
        assertThat(testRacePlanForm.getGelCarbo()).isEqualTo(UPDATED_GEL_CARBO);
        assertThat(testRacePlanForm.getDrinkOrgCarbo()).isEqualTo(UPDATED_DRINK_ORG_CARBO);
        assertThat(testRacePlanForm.getGelOrgCarbo()).isEqualTo(UPDATED_GEL_ORG_CARBO);
        assertThat(testRacePlanForm.getSportDrinkOrgBike()).isEqualTo(UPDATED_SPORT_DRINK_ORG_BIKE);
        assertThat(testRacePlanForm.getWaterOrgBike()).isEqualTo(UPDATED_WATER_ORG_BIKE);
        assertThat(testRacePlanForm.getGelsOrgBike()).isEqualTo(UPDATED_GELS_ORG_BIKE);
        assertThat(testRacePlanForm.getSportDrinkOrgRun()).isEqualTo(UPDATED_SPORT_DRINK_ORG_RUN);
        assertThat(testRacePlanForm.getWaterOrgRun()).isEqualTo(UPDATED_WATER_ORG_RUN);
        assertThat(testRacePlanForm.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRacePlanForm.getSportDrinkToTakeBike()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getWaterToTakeBike()).isEqualTo(UPDATED_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getExtraWaterToTakeBike()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getOrsToTakeBike()).isEqualTo(UPDATED_ORS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getGelsToTakeBike()).isEqualTo(UPDATED_GELS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getSportDrinkToTakeRun()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWaterToTakeRun()).isEqualTo(UPDATED_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getOrsToTakeRun()).isEqualTo(UPDATED_ORS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getGelsToTakeRun()).isEqualTo(UPDATED_GELS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringBike()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRacePlanForm.getCarboNeedDuringRun()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getFluidNeedPerHourBike()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRacePlanForm.getCarboNeedDuringBike()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedDuringBike()).isEqualTo(UPDATED_FLUID_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourRun()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRacePlanForm.getFluidNeedDuringRun()).isEqualTo(UPDATED_FLUID_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringRun()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRacePlanForm.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRacePlanForm.getDiffMoisterRun()).isEqualTo(UPDATED_DIFF_MOISTER_RUN);
        assertThat(testRacePlanForm.getDifCarbo()).isEqualTo(UPDATED_DIF_CARBO);
        assertThat(testRacePlanForm.getDifMoister()).isEqualTo(UPDATED_DIF_MOISTER);
        assertThat(testRacePlanForm.getActFluidNeedBike()).isEqualTo(UPDATED_ACT_FLUID_NEED_BIKE);
        assertThat(testRacePlanForm.getActFluidNeedRun()).isEqualTo(UPDATED_ACT_FLUID_NEED_RUN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRacePlanForm.getStartGel()).isEqualTo(UPDATED_START_GEL);
        assertThat(testRacePlanForm.getStartDrank()).isEqualTo(UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void putNonExistingRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, racePlanForm.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(racePlanForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(racePlanForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(racePlanForm)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRacePlanFormWithPatch() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();

        // Update the racePlanForm using partial update
        RacePlanForm partialUpdatedRacePlanForm = new RacePlanForm();
        partialUpdatedRacePlanForm.setId(racePlanForm.getId());

        partialUpdatedRacePlanForm
            .name(UPDATED_NAME)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .gelCarbo(UPDATED_GEL_CARBO)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .gelsToTakeRun(UPDATED_GELS_TO_TAKE_RUN)
            .fluidNeedPerHourSwim(UPDATED_FLUID_NEED_PER_HOUR_SWIM)
            .carboNeedDuringBike(UPDATED_CARBO_NEED_DURING_BIKE)
            .fluidNeedDuringBike(UPDATED_FLUID_NEED_DURING_BIKE)
            .weightLossDuringRun(UPDATED_WEIGHT_LOSS_DURING_RUN)
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .startDrank(UPDATED_START_DRANK);

        restRacePlanFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRacePlanForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRacePlanForm))
            )
            .andExpect(status().isOk());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
        RacePlanForm testRacePlanForm = racePlanFormList.get(racePlanFormList.size() - 1);
        assertThat(testRacePlanForm.getComp()).isEqualTo(DEFAULT_COMP);
        assertThat(testRacePlanForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRacePlanForm.getSelectedOrgGelQuery()).isEqualTo(DEFAULT_SELECTED_ORG_GEL_QUERY);
        assertThat(testRacePlanForm.getSelectedOrgDrankQuery()).isEqualTo(DEFAULT_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRacePlanForm.getOrsBeforeStart()).isEqualTo(DEFAULT_ORS_BEFORE_START);
        assertThat(testRacePlanForm.getDrinkCarbo()).isEqualTo(UPDATED_DRINK_CARBO);
        assertThat(testRacePlanForm.getGelCarbo()).isEqualTo(UPDATED_GEL_CARBO);
        assertThat(testRacePlanForm.getDrinkOrgCarbo()).isEqualTo(DEFAULT_DRINK_ORG_CARBO);
        assertThat(testRacePlanForm.getGelOrgCarbo()).isEqualTo(DEFAULT_GEL_ORG_CARBO);
        assertThat(testRacePlanForm.getSportDrinkOrgBike()).isEqualTo(DEFAULT_SPORT_DRINK_ORG_BIKE);
        assertThat(testRacePlanForm.getWaterOrgBike()).isEqualTo(UPDATED_WATER_ORG_BIKE);
        assertThat(testRacePlanForm.getGelsOrgBike()).isEqualTo(DEFAULT_GELS_ORG_BIKE);
        assertThat(testRacePlanForm.getSportDrinkOrgRun()).isEqualTo(UPDATED_SPORT_DRINK_ORG_RUN);
        assertThat(testRacePlanForm.getWaterOrgRun()).isEqualTo(UPDATED_WATER_ORG_RUN);
        assertThat(testRacePlanForm.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRacePlanForm.getSportDrinkToTakeBike()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getWaterToTakeBike()).isEqualTo(DEFAULT_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getExtraWaterToTakeBike()).isEqualTo(DEFAULT_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getOrsToTakeBike()).isEqualTo(DEFAULT_ORS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getGelsToTakeBike()).isEqualTo(DEFAULT_GELS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getSportDrinkToTakeRun()).isEqualTo(DEFAULT_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWaterToTakeRun()).isEqualTo(UPDATED_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getOrsToTakeRun()).isEqualTo(DEFAULT_ORS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getGelsToTakeRun()).isEqualTo(UPDATED_GELS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringBike()).isEqualTo(DEFAULT_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRacePlanForm.getCarboNeedDuringRun()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getFluidNeedPerHourBike()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRacePlanForm.getCarboNeedDuringBike()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedDuringBike()).isEqualTo(UPDATED_FLUID_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourRun()).isEqualTo(DEFAULT_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRacePlanForm.getFluidNeedDuringRun()).isEqualTo(DEFAULT_FLUID_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringRun()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRacePlanForm.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRacePlanForm.getDiffMoisterRun()).isEqualTo(UPDATED_DIFF_MOISTER_RUN);
        assertThat(testRacePlanForm.getDifCarbo()).isEqualTo(DEFAULT_DIF_CARBO);
        assertThat(testRacePlanForm.getDifMoister()).isEqualTo(DEFAULT_DIF_MOISTER);
        assertThat(testRacePlanForm.getActFluidNeedBike()).isEqualTo(UPDATED_ACT_FLUID_NEED_BIKE);
        assertThat(testRacePlanForm.getActFluidNeedRun()).isEqualTo(DEFAULT_ACT_FLUID_NEED_RUN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMin()).isEqualTo(DEFAULT_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRacePlanForm.getStartGel()).isEqualTo(DEFAULT_START_GEL);
        assertThat(testRacePlanForm.getStartDrank()).isEqualTo(UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void fullUpdateRacePlanFormWithPatch() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();

        // Update the racePlanForm using partial update
        RacePlanForm partialUpdatedRacePlanForm = new RacePlanForm();
        partialUpdatedRacePlanForm.setId(racePlanForm.getId());

        partialUpdatedRacePlanForm
            .comp(UPDATED_COMP)
            .name(UPDATED_NAME)
            .selectedOrgGelQuery(UPDATED_SELECTED_ORG_GEL_QUERY)
            .selectedOrgDrankQuery(UPDATED_SELECTED_ORG_DRANK_QUERY)
            .orsBeforeStart(UPDATED_ORS_BEFORE_START)
            .drinkCarbo(UPDATED_DRINK_CARBO)
            .gelCarbo(UPDATED_GEL_CARBO)
            .drinkOrgCarbo(UPDATED_DRINK_ORG_CARBO)
            .gelOrgCarbo(UPDATED_GEL_ORG_CARBO)
            .sportDrinkOrgBike(UPDATED_SPORT_DRINK_ORG_BIKE)
            .waterOrgBike(UPDATED_WATER_ORG_BIKE)
            .gelsOrgBike(UPDATED_GELS_ORG_BIKE)
            .sportDrinkOrgRun(UPDATED_SPORT_DRINK_ORG_RUN)
            .waterOrgRun(UPDATED_WATER_ORG_RUN)
            .gelsOrgRun(UPDATED_GELS_ORG_RUN)
            .sportDrinkToTakeBike(UPDATED_SPORT_DRINK_TO_TAKE_BIKE)
            .waterToTakeBike(UPDATED_WATER_TO_TAKE_BIKE)
            .extraWaterToTakeBike(UPDATED_EXTRA_WATER_TO_TAKE_BIKE)
            .orsToTakeBike(UPDATED_ORS_TO_TAKE_BIKE)
            .gelsToTakeBike(UPDATED_GELS_TO_TAKE_BIKE)
            .sportDrinkToTakeRun(UPDATED_SPORT_DRINK_TO_TAKE_RUN)
            .waterToTakeRun(UPDATED_WATER_TO_TAKE_RUN)
            .extraWaterToTakeRun(UPDATED_EXTRA_WATER_TO_TAKE_RUN)
            .orsToTakeRun(UPDATED_ORS_TO_TAKE_RUN)
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
            .diffCarboRun(UPDATED_DIFF_CARBO_RUN)
            .diffMoisterRun(UPDATED_DIFF_MOISTER_RUN)
            .difCarbo(UPDATED_DIF_CARBO)
            .difMoister(UPDATED_DIF_MOISTER)
            .actFluidNeedBike(UPDATED_ACT_FLUID_NEED_BIKE)
            .actFluidNeedRun(UPDATED_ACT_FLUID_NEED_RUN)
            .carboNeedDuringBikeMin(UPDATED_CARBO_NEED_DURING_BIKE_MIN)
            .carboNeedDuringBikeMax(UPDATED_CARBO_NEED_DURING_BIKE_MAX)
            .carboNeedDuringRunMin(UPDATED_CARBO_NEED_DURING_RUN_MIN)
            .carboNeedDuringRunMax(UPDATED_CARBO_NEED_DURING_RUN_MAX)
            .startGel(UPDATED_START_GEL)
            .startDrank(UPDATED_START_DRANK);

        restRacePlanFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRacePlanForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRacePlanForm))
            )
            .andExpect(status().isOk());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
        RacePlanForm testRacePlanForm = racePlanFormList.get(racePlanFormList.size() - 1);
        assertThat(testRacePlanForm.getComp()).isEqualTo(UPDATED_COMP);
        assertThat(testRacePlanForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRacePlanForm.getSelectedOrgGelQuery()).isEqualTo(UPDATED_SELECTED_ORG_GEL_QUERY);
        assertThat(testRacePlanForm.getSelectedOrgDrankQuery()).isEqualTo(UPDATED_SELECTED_ORG_DRANK_QUERY);
        assertThat(testRacePlanForm.getOrsBeforeStart()).isEqualTo(UPDATED_ORS_BEFORE_START);
        assertThat(testRacePlanForm.getDrinkCarbo()).isEqualTo(UPDATED_DRINK_CARBO);
        assertThat(testRacePlanForm.getGelCarbo()).isEqualTo(UPDATED_GEL_CARBO);
        assertThat(testRacePlanForm.getDrinkOrgCarbo()).isEqualTo(UPDATED_DRINK_ORG_CARBO);
        assertThat(testRacePlanForm.getGelOrgCarbo()).isEqualTo(UPDATED_GEL_ORG_CARBO);
        assertThat(testRacePlanForm.getSportDrinkOrgBike()).isEqualTo(UPDATED_SPORT_DRINK_ORG_BIKE);
        assertThat(testRacePlanForm.getWaterOrgBike()).isEqualTo(UPDATED_WATER_ORG_BIKE);
        assertThat(testRacePlanForm.getGelsOrgBike()).isEqualTo(UPDATED_GELS_ORG_BIKE);
        assertThat(testRacePlanForm.getSportDrinkOrgRun()).isEqualTo(UPDATED_SPORT_DRINK_ORG_RUN);
        assertThat(testRacePlanForm.getWaterOrgRun()).isEqualTo(UPDATED_WATER_ORG_RUN);
        assertThat(testRacePlanForm.getGelsOrgRun()).isEqualTo(UPDATED_GELS_ORG_RUN);
        assertThat(testRacePlanForm.getSportDrinkToTakeBike()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getWaterToTakeBike()).isEqualTo(UPDATED_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getExtraWaterToTakeBike()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getOrsToTakeBike()).isEqualTo(UPDATED_ORS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getGelsToTakeBike()).isEqualTo(UPDATED_GELS_TO_TAKE_BIKE);
        assertThat(testRacePlanForm.getSportDrinkToTakeRun()).isEqualTo(UPDATED_SPORT_DRINK_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWaterToTakeRun()).isEqualTo(UPDATED_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getExtraWaterToTakeRun()).isEqualTo(UPDATED_EXTRA_WATER_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getOrsToTakeRun()).isEqualTo(UPDATED_ORS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getGelsToTakeRun()).isEqualTo(UPDATED_GELS_TO_TAKE_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringBike()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_BIKE);
        assertThat(testRacePlanForm.getCarboNeedDuringRun()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getFluidNeedPerHourBike()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourSwim()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_SWIM);
        assertThat(testRacePlanForm.getCarboNeedDuringBike()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedDuringBike()).isEqualTo(UPDATED_FLUID_NEED_DURING_BIKE);
        assertThat(testRacePlanForm.getFluidNeedPerHourRun()).isEqualTo(UPDATED_FLUID_NEED_PER_HOUR_RUN);
        assertThat(testRacePlanForm.getFluidNeedDuringRun()).isEqualTo(UPDATED_FLUID_NEED_DURING_RUN);
        assertThat(testRacePlanForm.getWeightLossDuringRun()).isEqualTo(UPDATED_WEIGHT_LOSS_DURING_RUN);
        assertThat(testRacePlanForm.getDiffCarboRun()).isEqualTo(UPDATED_DIFF_CARBO_RUN);
        assertThat(testRacePlanForm.getDiffMoisterRun()).isEqualTo(UPDATED_DIFF_MOISTER_RUN);
        assertThat(testRacePlanForm.getDifCarbo()).isEqualTo(UPDATED_DIF_CARBO);
        assertThat(testRacePlanForm.getDifMoister()).isEqualTo(UPDATED_DIF_MOISTER);
        assertThat(testRacePlanForm.getActFluidNeedBike()).isEqualTo(UPDATED_ACT_FLUID_NEED_BIKE);
        assertThat(testRacePlanForm.getActFluidNeedRun()).isEqualTo(UPDATED_ACT_FLUID_NEED_RUN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringBikeMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_BIKE_MAX);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMin()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MIN);
        assertThat(testRacePlanForm.getCarboNeedDuringRunMax()).isEqualTo(UPDATED_CARBO_NEED_DURING_RUN_MAX);
        assertThat(testRacePlanForm.getStartGel()).isEqualTo(UPDATED_START_GEL);
        assertThat(testRacePlanForm.getStartDrank()).isEqualTo(UPDATED_START_DRANK);
    }

    @Test
    @Transactional
    void patchNonExistingRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, racePlanForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(racePlanForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(racePlanForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRacePlanForm() throws Exception {
        int databaseSizeBeforeUpdate = racePlanFormRepository.findAll().size();
        racePlanForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRacePlanFormMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(racePlanForm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RacePlanForm in the database
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRacePlanForm() throws Exception {
        // Initialize the database
        racePlanFormRepository.saveAndFlush(racePlanForm);

        int databaseSizeBeforeDelete = racePlanFormRepository.findAll().size();

        // Delete the racePlanForm
        restRacePlanFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, racePlanForm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RacePlanForm> racePlanFormList = racePlanFormRepository.findAll();
        assertThat(racePlanFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
