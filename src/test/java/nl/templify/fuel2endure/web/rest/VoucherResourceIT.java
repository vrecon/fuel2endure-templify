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
import nl.templify.fuel2endure.domain.Voucher;
import nl.templify.fuel2endure.domain.enumeration.CategoryType;
import nl.templify.fuel2endure.repository.VoucherRepository;
import nl.templify.fuel2endure.service.criteria.VoucherCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VoucherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VOUCHER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_REDEEMED = 1;
    private static final Integer UPDATED_REDEEMED = 2;
    private static final Integer SMALLER_REDEEMED = 1 - 1;

    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MAX_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_MAX_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Integer DEFAULT_MAX_REDEEMED = 1;
    private static final Integer UPDATED_MAX_REDEEMED = 2;
    private static final Integer SMALLER_MAX_REDEEMED = 1 - 1;

    private static final CategoryType DEFAULT_CATEGORY = CategoryType.MUTIPLE;
    private static final CategoryType UPDATED_CATEGORY = CategoryType.SINGLE;

    private static final String ENTITY_API_URL = "/api/vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .code(DEFAULT_CODE)
            .voucherType(DEFAULT_VOUCHER_TYPE)
            .redeemed(DEFAULT_REDEEMED)
            .maxDate(DEFAULT_MAX_DATE)
            .amount(DEFAULT_AMOUNT)
            .maxRedeemed(DEFAULT_MAX_REDEEMED)
            .category(DEFAULT_CATEGORY);
        return voucher;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createUpdatedEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .code(UPDATED_CODE)
            .voucherType(UPDATED_VOUCHER_TYPE)
            .redeemed(UPDATED_REDEEMED)
            .maxDate(UPDATED_MAX_DATE)
            .amount(UPDATED_AMOUNT)
            .maxRedeemed(UPDATED_MAX_REDEEMED)
            .category(UPDATED_CATEGORY);
        return voucher;
    }

    @BeforeEach
    public void initTest() {
        voucher = createEntity(em);
    }

    @Test
    @Transactional
    void createVoucher() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();
        // Create the Voucher
        restVoucherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isCreated());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate + 1);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVoucher.getVoucherType()).isEqualTo(DEFAULT_VOUCHER_TYPE);
        assertThat(testVoucher.getRedeemed()).isEqualTo(DEFAULT_REDEEMED);
        assertThat(testVoucher.getMaxDate()).isEqualTo(DEFAULT_MAX_DATE);
        assertThat(testVoucher.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testVoucher.getMaxRedeemed()).isEqualTo(DEFAULT_MAX_REDEEMED);
        assertThat(testVoucher.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void createVoucherWithExistingId() throws Exception {
        // Create the Voucher with an existing ID
        voucher.setId(1L);

        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVouchers() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].voucherType").value(hasItem(DEFAULT_VOUCHER_TYPE)))
            .andExpect(jsonPath("$.[*].redeemed").value(hasItem(DEFAULT_REDEEMED)))
            .andExpect(jsonPath("$.[*].maxDate").value(hasItem(DEFAULT_MAX_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxRedeemed").value(hasItem(DEFAULT_MAX_REDEEMED)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    void getVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL_ID, voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.voucherType").value(DEFAULT_VOUCHER_TYPE))
            .andExpect(jsonPath("$.redeemed").value(DEFAULT_REDEEMED))
            .andExpect(jsonPath("$.maxDate").value(DEFAULT_MAX_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maxRedeemed").value(DEFAULT_MAX_REDEEMED))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    void getVouchersByIdFiltering() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        Long id = voucher.getId();

        defaultVoucherShouldBeFound("id.equals=" + id);
        defaultVoucherShouldNotBeFound("id.notEquals=" + id);

        defaultVoucherShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVoucherShouldNotBeFound("id.greaterThan=" + id);

        defaultVoucherShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVoucherShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code equals to DEFAULT_CODE
        defaultVoucherShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the voucherList where code equals to UPDATED_CODE
        defaultVoucherShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code not equals to DEFAULT_CODE
        defaultVoucherShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the voucherList where code not equals to UPDATED_CODE
        defaultVoucherShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVoucherShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the voucherList where code equals to UPDATED_CODE
        defaultVoucherShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code is not null
        defaultVoucherShouldBeFound("code.specified=true");

        // Get all the voucherList where code is null
        defaultVoucherShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByCodeContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code contains DEFAULT_CODE
        defaultVoucherShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the voucherList where code contains UPDATED_CODE
        defaultVoucherShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code does not contain DEFAULT_CODE
        defaultVoucherShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the voucherList where code does not contain UPDATED_CODE
        defaultVoucherShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType equals to DEFAULT_VOUCHER_TYPE
        defaultVoucherShouldBeFound("voucherType.equals=" + DEFAULT_VOUCHER_TYPE);

        // Get all the voucherList where voucherType equals to UPDATED_VOUCHER_TYPE
        defaultVoucherShouldNotBeFound("voucherType.equals=" + UPDATED_VOUCHER_TYPE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType not equals to DEFAULT_VOUCHER_TYPE
        defaultVoucherShouldNotBeFound("voucherType.notEquals=" + DEFAULT_VOUCHER_TYPE);

        // Get all the voucherList where voucherType not equals to UPDATED_VOUCHER_TYPE
        defaultVoucherShouldBeFound("voucherType.notEquals=" + UPDATED_VOUCHER_TYPE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType in DEFAULT_VOUCHER_TYPE or UPDATED_VOUCHER_TYPE
        defaultVoucherShouldBeFound("voucherType.in=" + DEFAULT_VOUCHER_TYPE + "," + UPDATED_VOUCHER_TYPE);

        // Get all the voucherList where voucherType equals to UPDATED_VOUCHER_TYPE
        defaultVoucherShouldNotBeFound("voucherType.in=" + UPDATED_VOUCHER_TYPE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType is not null
        defaultVoucherShouldBeFound("voucherType.specified=true");

        // Get all the voucherList where voucherType is null
        defaultVoucherShouldNotBeFound("voucherType.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType contains DEFAULT_VOUCHER_TYPE
        defaultVoucherShouldBeFound("voucherType.contains=" + DEFAULT_VOUCHER_TYPE);

        // Get all the voucherList where voucherType contains UPDATED_VOUCHER_TYPE
        defaultVoucherShouldNotBeFound("voucherType.contains=" + UPDATED_VOUCHER_TYPE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherTypeNotContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherType does not contain DEFAULT_VOUCHER_TYPE
        defaultVoucherShouldNotBeFound("voucherType.doesNotContain=" + DEFAULT_VOUCHER_TYPE);

        // Get all the voucherList where voucherType does not contain UPDATED_VOUCHER_TYPE
        defaultVoucherShouldBeFound("voucherType.doesNotContain=" + UPDATED_VOUCHER_TYPE);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed equals to DEFAULT_REDEEMED
        defaultVoucherShouldBeFound("redeemed.equals=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed equals to UPDATED_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.equals=" + UPDATED_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed not equals to DEFAULT_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.notEquals=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed not equals to UPDATED_REDEEMED
        defaultVoucherShouldBeFound("redeemed.notEquals=" + UPDATED_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed in DEFAULT_REDEEMED or UPDATED_REDEEMED
        defaultVoucherShouldBeFound("redeemed.in=" + DEFAULT_REDEEMED + "," + UPDATED_REDEEMED);

        // Get all the voucherList where redeemed equals to UPDATED_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.in=" + UPDATED_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed is not null
        defaultVoucherShouldBeFound("redeemed.specified=true");

        // Get all the voucherList where redeemed is null
        defaultVoucherShouldNotBeFound("redeemed.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed is greater than or equal to DEFAULT_REDEEMED
        defaultVoucherShouldBeFound("redeemed.greaterThanOrEqual=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed is greater than or equal to UPDATED_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.greaterThanOrEqual=" + UPDATED_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed is less than or equal to DEFAULT_REDEEMED
        defaultVoucherShouldBeFound("redeemed.lessThanOrEqual=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed is less than or equal to SMALLER_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.lessThanOrEqual=" + SMALLER_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed is less than DEFAULT_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.lessThan=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed is less than UPDATED_REDEEMED
        defaultVoucherShouldBeFound("redeemed.lessThan=" + UPDATED_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByRedeemedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where redeemed is greater than DEFAULT_REDEEMED
        defaultVoucherShouldNotBeFound("redeemed.greaterThan=" + DEFAULT_REDEEMED);

        // Get all the voucherList where redeemed is greater than SMALLER_REDEEMED
        defaultVoucherShouldBeFound("redeemed.greaterThan=" + SMALLER_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate equals to DEFAULT_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.equals=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate equals to UPDATED_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.equals=" + UPDATED_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate not equals to DEFAULT_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.notEquals=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate not equals to UPDATED_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.notEquals=" + UPDATED_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate in DEFAULT_MAX_DATE or UPDATED_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.in=" + DEFAULT_MAX_DATE + "," + UPDATED_MAX_DATE);

        // Get all the voucherList where maxDate equals to UPDATED_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.in=" + UPDATED_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate is not null
        defaultVoucherShouldBeFound("maxDate.specified=true");

        // Get all the voucherList where maxDate is null
        defaultVoucherShouldNotBeFound("maxDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate is greater than or equal to DEFAULT_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.greaterThanOrEqual=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate is greater than or equal to UPDATED_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.greaterThanOrEqual=" + UPDATED_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate is less than or equal to DEFAULT_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.lessThanOrEqual=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate is less than or equal to SMALLER_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.lessThanOrEqual=" + SMALLER_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate is less than DEFAULT_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.lessThan=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate is less than UPDATED_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.lessThan=" + UPDATED_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxDate is greater than DEFAULT_MAX_DATE
        defaultVoucherShouldNotBeFound("maxDate.greaterThan=" + DEFAULT_MAX_DATE);

        // Get all the voucherList where maxDate is greater than SMALLER_MAX_DATE
        defaultVoucherShouldBeFound("maxDate.greaterThan=" + SMALLER_MAX_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount equals to DEFAULT_AMOUNT
        defaultVoucherShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount equals to UPDATED_AMOUNT
        defaultVoucherShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount not equals to DEFAULT_AMOUNT
        defaultVoucherShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount not equals to UPDATED_AMOUNT
        defaultVoucherShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultVoucherShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the voucherList where amount equals to UPDATED_AMOUNT
        defaultVoucherShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount is not null
        defaultVoucherShouldBeFound("amount.specified=true");

        // Get all the voucherList where amount is null
        defaultVoucherShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultVoucherShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount is greater than or equal to UPDATED_AMOUNT
        defaultVoucherShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount is less than or equal to DEFAULT_AMOUNT
        defaultVoucherShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount is less than or equal to SMALLER_AMOUNT
        defaultVoucherShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount is less than DEFAULT_AMOUNT
        defaultVoucherShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount is less than UPDATED_AMOUNT
        defaultVoucherShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amount is greater than DEFAULT_AMOUNT
        defaultVoucherShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the voucherList where amount is greater than SMALLER_AMOUNT
        defaultVoucherShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed equals to DEFAULT_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.equals=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed equals to UPDATED_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.equals=" + UPDATED_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed not equals to DEFAULT_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.notEquals=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed not equals to UPDATED_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.notEquals=" + UPDATED_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed in DEFAULT_MAX_REDEEMED or UPDATED_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.in=" + DEFAULT_MAX_REDEEMED + "," + UPDATED_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed equals to UPDATED_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.in=" + UPDATED_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed is not null
        defaultVoucherShouldBeFound("maxRedeemed.specified=true");

        // Get all the voucherList where maxRedeemed is null
        defaultVoucherShouldNotBeFound("maxRedeemed.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed is greater than or equal to DEFAULT_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.greaterThanOrEqual=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed is greater than or equal to UPDATED_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.greaterThanOrEqual=" + UPDATED_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed is less than or equal to DEFAULT_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.lessThanOrEqual=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed is less than or equal to SMALLER_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.lessThanOrEqual=" + SMALLER_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed is less than DEFAULT_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.lessThan=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed is less than UPDATED_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.lessThan=" + UPDATED_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByMaxRedeemedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where maxRedeemed is greater than DEFAULT_MAX_REDEEMED
        defaultVoucherShouldNotBeFound("maxRedeemed.greaterThan=" + DEFAULT_MAX_REDEEMED);

        // Get all the voucherList where maxRedeemed is greater than SMALLER_MAX_REDEEMED
        defaultVoucherShouldBeFound("maxRedeemed.greaterThan=" + SMALLER_MAX_REDEEMED);
    }

    @Test
    @Transactional
    void getAllVouchersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where category equals to DEFAULT_CATEGORY
        defaultVoucherShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the voucherList where category equals to UPDATED_CATEGORY
        defaultVoucherShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllVouchersByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where category not equals to DEFAULT_CATEGORY
        defaultVoucherShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the voucherList where category not equals to UPDATED_CATEGORY
        defaultVoucherShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllVouchersByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultVoucherShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the voucherList where category equals to UPDATED_CATEGORY
        defaultVoucherShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllVouchersByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where category is not null
        defaultVoucherShouldBeFound("category.specified=true");

        // Get all the voucherList where category is null
        defaultVoucherShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByAthleteIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);
        Athlete athlete = AthleteResourceIT.createEntity(em);
        em.persist(athlete);
        em.flush();
        voucher.addAthlete(athlete);
        voucherRepository.saveAndFlush(voucher);
        Long athleteId = athlete.getId();

        // Get all the voucherList where athlete equals to athleteId
        defaultVoucherShouldBeFound("athleteId.equals=" + athleteId);

        // Get all the voucherList where athlete equals to (athleteId + 1)
        defaultVoucherShouldNotBeFound("athleteId.equals=" + (athleteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherShouldBeFound(String filter) throws Exception {
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].voucherType").value(hasItem(DEFAULT_VOUCHER_TYPE)))
            .andExpect(jsonPath("$.[*].redeemed").value(hasItem(DEFAULT_REDEEMED)))
            .andExpect(jsonPath("$.[*].maxDate").value(hasItem(DEFAULT_MAX_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxRedeemed").value(hasItem(DEFAULT_MAX_REDEEMED)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));

        // Check, that the count call also returns 1
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherShouldNotBeFound(String filter) throws Exception {
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .code(UPDATED_CODE)
            .voucherType(UPDATED_VOUCHER_TYPE)
            .redeemed(UPDATED_REDEEMED)
            .maxDate(UPDATED_MAX_DATE)
            .amount(UPDATED_AMOUNT)
            .maxRedeemed(UPDATED_MAX_REDEEMED)
            .category(UPDATED_CATEGORY);

        restVoucherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoucher.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVoucher.getVoucherType()).isEqualTo(UPDATED_VOUCHER_TYPE);
        assertThat(testVoucher.getRedeemed()).isEqualTo(UPDATED_REDEEMED);
        assertThat(testVoucher.getMaxDate()).isEqualTo(UPDATED_MAX_DATE);
        assertThat(testVoucher.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testVoucher.getMaxRedeemed()).isEqualTo(UPDATED_MAX_REDEEMED);
        assertThat(testVoucher.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucher.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherWithPatch() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher using partial update
        Voucher partialUpdatedVoucher = new Voucher();
        partialUpdatedVoucher.setId(voucher.getId());

        partialUpdatedVoucher
            .voucherType(UPDATED_VOUCHER_TYPE)
            .maxDate(UPDATED_MAX_DATE)
            .amount(UPDATED_AMOUNT)
            .maxRedeemed(UPDATED_MAX_REDEEMED)
            .category(UPDATED_CATEGORY);

        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVoucher.getVoucherType()).isEqualTo(UPDATED_VOUCHER_TYPE);
        assertThat(testVoucher.getRedeemed()).isEqualTo(DEFAULT_REDEEMED);
        assertThat(testVoucher.getMaxDate()).isEqualTo(UPDATED_MAX_DATE);
        assertThat(testVoucher.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testVoucher.getMaxRedeemed()).isEqualTo(UPDATED_MAX_REDEEMED);
        assertThat(testVoucher.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateVoucherWithPatch() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher using partial update
        Voucher partialUpdatedVoucher = new Voucher();
        partialUpdatedVoucher.setId(voucher.getId());

        partialUpdatedVoucher
            .code(UPDATED_CODE)
            .voucherType(UPDATED_VOUCHER_TYPE)
            .redeemed(UPDATED_REDEEMED)
            .maxDate(UPDATED_MAX_DATE)
            .amount(UPDATED_AMOUNT)
            .maxRedeemed(UPDATED_MAX_REDEEMED)
            .category(UPDATED_CATEGORY);

        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVoucher.getVoucherType()).isEqualTo(UPDATED_VOUCHER_TYPE);
        assertThat(testVoucher.getRedeemed()).isEqualTo(UPDATED_REDEEMED);
        assertThat(testVoucher.getMaxDate()).isEqualTo(UPDATED_MAX_DATE);
        assertThat(testVoucher.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testVoucher.getMaxRedeemed()).isEqualTo(UPDATED_MAX_REDEEMED);
        assertThat(testVoucher.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucher.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();
        voucher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeDelete = voucherRepository.findAll().size();

        // Delete the voucher
        restVoucherMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucher.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
