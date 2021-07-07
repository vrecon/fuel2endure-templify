package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Race.
 */
@Entity
@Table(name = "race")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "name")
    private String name;

    @Column(name = "logging")
    private String logging;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "length")
    private Integer length;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "comp")
    private Integer comp;

    @Column(name = "swim_duration")
    private Integer swimDuration;

    @Column(name = "bike_duration")
    private Integer bikeDuration;

    @Column(name = "run_duration")
    private Integer runDuration;

    @Column(name = "gel_carbo")
    private Integer gelCarbo;

    @Column(name = "drink_carbo")
    private Integer drinkCarbo;

    @Column(name = "drink_org_carbo")
    private Integer drinkOrgCarbo;

    @Column(name = "gel_org_carbo")
    private Integer gelOrgCarbo;

    @Column(name = "gelsbike")
    private Integer gelsbike;

    @Column(name = "gelsrun")
    private Integer gelsrun;

    @Column(name = "selected_org_gel_query")
    private Boolean selectedOrgGelQuery;

    @Column(name = "selected_org_drank_query")
    private Boolean selectedOrgDrankQuery;

    @Column(name = "sport_drink_org_bike")
    private Integer sportDrinkOrgBike;

    @Column(name = "water_org_bike")
    private Integer waterOrgBike;

    @Column(name = "gels_org_bike")
    private Integer gelsOrgBike;

    @Column(name = "sport_drink_org_run")
    private Integer sportDrinkOrgRun;

    @Column(name = "water_org_run")
    private Integer waterOrgRun;

    @Column(name = "gels_org_run")
    private Integer gelsOrgRun;

    @Column(name = "ors_before_start")
    private Integer orsBeforeStart;

    @Column(name = "sport_drink_to_take_bike")
    private Integer sportDrinkToTakeBike;

    @Column(name = "water_to_take_bike")
    private Integer waterToTakeBike;

    @Column(name = "extra_water_to_take_bike")
    private Integer extraWaterToTakeBike;

    @Column(name = "ors_to_take_bike")
    private Integer orsToTakeBike;

    @Column(name = "gels_to_take_bike")
    private Integer gelsToTakeBike;

    @Column(name = "sport_drink_to_take_run")
    private Integer sportDrinkToTakeRun;

    @Column(name = "water_to_take_run")
    private Integer waterToTakeRun;

    @Column(name = "extra_water_to_take_run")
    private Integer extraWaterToTakeRun;

    @Column(name = "ors_to_take_run")
    private Integer orsToTakeRun;

    @Column(name = "carbo_need_during_bike_min")
    private Integer carboNeedDuringBikeMin;

    @Column(name = "carbo_need_during_bike_max")
    private Integer carboNeedDuringBikeMax;

    @Column(name = "carbo_need_during_run_min")
    private Integer carboNeedDuringRunMin;

    @Column(name = "carbo_need_during_run_max")
    private Integer carboNeedDuringRunMax;

    @Column(name = "diff_carbo_run")
    private Integer diffCarboRun;

    @Column(name = "diff_moister_run")
    private Integer diffMoisterRun;

    @Column(name = "dif_carbo")
    private Integer difCarbo;

    @Column(name = "dif_moister")
    private Integer difMoister;

    @Column(name = "gels_to_take_run")
    private Integer gelsToTakeRun;

    @Column(name = "weight_loss_during_bike")
    private Float weightLossDuringBike;

    @Column(name = "carbo_need_during_run")
    private Float carboNeedDuringRun;

    @Column(name = "fluid_need_per_hour_bike")
    private Double fluidNeedPerHourBike;

    @Column(name = "fluid_need_per_hour_swim")
    private Double fluidNeedPerHourSwim;

    @Column(name = "carbo_need_during_bike")
    private Double carboNeedDuringBike;

    @Column(name = "fluid_need_during_bike")
    private Long fluidNeedDuringBike;

    @Column(name = "fluid_need_per_hour_run")
    private Double fluidNeedPerHourRun;

    @Column(name = "fluid_need_during_run")
    private Long fluidNeedDuringRun;

    @Column(name = "weight_loss_during_run")
    private Float weightLossDuringRun;

    @Column(name = "act_fluid_need_bike")
    private Integer actFluidNeedBike;

    @Column(name = "act_fluid_need_run")
    private Integer actFluidNeedRun;

    @OneToMany(mappedBy = "race")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "race" }, allowSetters = true)
    private Set<RacePlanForm> racePlanForms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "fuelSettings", "internalUser", "races", "trainings", "payments", "voucher" }, allowSetters = true)
    private Athlete athlete;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Race id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Race date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public Race name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogging() {
        return this.logging;
    }

    public Race logging(String logging) {
        this.logging = logging;
        return this;
    }

    public void setLogging(String logging) {
        this.logging = logging;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Race weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLength() {
        return this.length;
    }

    public Race length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public Race temperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getComp() {
        return this.comp;
    }

    public Race comp(Integer comp) {
        this.comp = comp;
        return this;
    }

    public void setComp(Integer comp) {
        this.comp = comp;
    }

    public Integer getSwimDuration() {
        return this.swimDuration;
    }

    public Race swimDuration(Integer swimDuration) {
        this.swimDuration = swimDuration;
        return this;
    }

    public void setSwimDuration(Integer swimDuration) {
        this.swimDuration = swimDuration;
    }

    public Integer getBikeDuration() {
        return this.bikeDuration;
    }

    public Race bikeDuration(Integer bikeDuration) {
        this.bikeDuration = bikeDuration;
        return this;
    }

    public void setBikeDuration(Integer bikeDuration) {
        this.bikeDuration = bikeDuration;
    }

    public Integer getRunDuration() {
        return this.runDuration;
    }

    public Race runDuration(Integer runDuration) {
        this.runDuration = runDuration;
        return this;
    }

    public void setRunDuration(Integer runDuration) {
        this.runDuration = runDuration;
    }

    public Integer getGelCarbo() {
        return this.gelCarbo;
    }

    public Race gelCarbo(Integer gelCarbo) {
        this.gelCarbo = gelCarbo;
        return this;
    }

    public void setGelCarbo(Integer gelCarbo) {
        this.gelCarbo = gelCarbo;
    }

    public Integer getDrinkCarbo() {
        return this.drinkCarbo;
    }

    public Race drinkCarbo(Integer drinkCarbo) {
        this.drinkCarbo = drinkCarbo;
        return this;
    }

    public void setDrinkCarbo(Integer drinkCarbo) {
        this.drinkCarbo = drinkCarbo;
    }

    public Integer getDrinkOrgCarbo() {
        return this.drinkOrgCarbo;
    }

    public Race drinkOrgCarbo(Integer drinkOrgCarbo) {
        this.drinkOrgCarbo = drinkOrgCarbo;
        return this;
    }

    public void setDrinkOrgCarbo(Integer drinkOrgCarbo) {
        this.drinkOrgCarbo = drinkOrgCarbo;
    }

    public Integer getGelOrgCarbo() {
        return this.gelOrgCarbo;
    }

    public Race gelOrgCarbo(Integer gelOrgCarbo) {
        this.gelOrgCarbo = gelOrgCarbo;
        return this;
    }

    public void setGelOrgCarbo(Integer gelOrgCarbo) {
        this.gelOrgCarbo = gelOrgCarbo;
    }

    public Integer getGelsbike() {
        return this.gelsbike;
    }

    public Race gelsbike(Integer gelsbike) {
        this.gelsbike = gelsbike;
        return this;
    }

    public void setGelsbike(Integer gelsbike) {
        this.gelsbike = gelsbike;
    }

    public Integer getGelsrun() {
        return this.gelsrun;
    }

    public Race gelsrun(Integer gelsrun) {
        this.gelsrun = gelsrun;
        return this;
    }

    public void setGelsrun(Integer gelsrun) {
        this.gelsrun = gelsrun;
    }

    public Boolean getSelectedOrgGelQuery() {
        return this.selectedOrgGelQuery;
    }

    public Race selectedOrgGelQuery(Boolean selectedOrgGelQuery) {
        this.selectedOrgGelQuery = selectedOrgGelQuery;
        return this;
    }

    public void setSelectedOrgGelQuery(Boolean selectedOrgGelQuery) {
        this.selectedOrgGelQuery = selectedOrgGelQuery;
    }

    public Boolean getSelectedOrgDrankQuery() {
        return this.selectedOrgDrankQuery;
    }

    public Race selectedOrgDrankQuery(Boolean selectedOrgDrankQuery) {
        this.selectedOrgDrankQuery = selectedOrgDrankQuery;
        return this;
    }

    public void setSelectedOrgDrankQuery(Boolean selectedOrgDrankQuery) {
        this.selectedOrgDrankQuery = selectedOrgDrankQuery;
    }

    public Integer getSportDrinkOrgBike() {
        return this.sportDrinkOrgBike;
    }

    public Race sportDrinkOrgBike(Integer sportDrinkOrgBike) {
        this.sportDrinkOrgBike = sportDrinkOrgBike;
        return this;
    }

    public void setSportDrinkOrgBike(Integer sportDrinkOrgBike) {
        this.sportDrinkOrgBike = sportDrinkOrgBike;
    }

    public Integer getWaterOrgBike() {
        return this.waterOrgBike;
    }

    public Race waterOrgBike(Integer waterOrgBike) {
        this.waterOrgBike = waterOrgBike;
        return this;
    }

    public void setWaterOrgBike(Integer waterOrgBike) {
        this.waterOrgBike = waterOrgBike;
    }

    public Integer getGelsOrgBike() {
        return this.gelsOrgBike;
    }

    public Race gelsOrgBike(Integer gelsOrgBike) {
        this.gelsOrgBike = gelsOrgBike;
        return this;
    }

    public void setGelsOrgBike(Integer gelsOrgBike) {
        this.gelsOrgBike = gelsOrgBike;
    }

    public Integer getSportDrinkOrgRun() {
        return this.sportDrinkOrgRun;
    }

    public Race sportDrinkOrgRun(Integer sportDrinkOrgRun) {
        this.sportDrinkOrgRun = sportDrinkOrgRun;
        return this;
    }

    public void setSportDrinkOrgRun(Integer sportDrinkOrgRun) {
        this.sportDrinkOrgRun = sportDrinkOrgRun;
    }

    public Integer getWaterOrgRun() {
        return this.waterOrgRun;
    }

    public Race waterOrgRun(Integer waterOrgRun) {
        this.waterOrgRun = waterOrgRun;
        return this;
    }

    public void setWaterOrgRun(Integer waterOrgRun) {
        this.waterOrgRun = waterOrgRun;
    }

    public Integer getGelsOrgRun() {
        return this.gelsOrgRun;
    }

    public Race gelsOrgRun(Integer gelsOrgRun) {
        this.gelsOrgRun = gelsOrgRun;
        return this;
    }

    public void setGelsOrgRun(Integer gelsOrgRun) {
        this.gelsOrgRun = gelsOrgRun;
    }

    public Integer getOrsBeforeStart() {
        return this.orsBeforeStart;
    }

    public Race orsBeforeStart(Integer orsBeforeStart) {
        this.orsBeforeStart = orsBeforeStart;
        return this;
    }

    public void setOrsBeforeStart(Integer orsBeforeStart) {
        this.orsBeforeStart = orsBeforeStart;
    }

    public Integer getSportDrinkToTakeBike() {
        return this.sportDrinkToTakeBike;
    }

    public Race sportDrinkToTakeBike(Integer sportDrinkToTakeBike) {
        this.sportDrinkToTakeBike = sportDrinkToTakeBike;
        return this;
    }

    public void setSportDrinkToTakeBike(Integer sportDrinkToTakeBike) {
        this.sportDrinkToTakeBike = sportDrinkToTakeBike;
    }

    public Integer getWaterToTakeBike() {
        return this.waterToTakeBike;
    }

    public Race waterToTakeBike(Integer waterToTakeBike) {
        this.waterToTakeBike = waterToTakeBike;
        return this;
    }

    public void setWaterToTakeBike(Integer waterToTakeBike) {
        this.waterToTakeBike = waterToTakeBike;
    }

    public Integer getExtraWaterToTakeBike() {
        return this.extraWaterToTakeBike;
    }

    public Race extraWaterToTakeBike(Integer extraWaterToTakeBike) {
        this.extraWaterToTakeBike = extraWaterToTakeBike;
        return this;
    }

    public void setExtraWaterToTakeBike(Integer extraWaterToTakeBike) {
        this.extraWaterToTakeBike = extraWaterToTakeBike;
    }

    public Integer getOrsToTakeBike() {
        return this.orsToTakeBike;
    }

    public Race orsToTakeBike(Integer orsToTakeBike) {
        this.orsToTakeBike = orsToTakeBike;
        return this;
    }

    public void setOrsToTakeBike(Integer orsToTakeBike) {
        this.orsToTakeBike = orsToTakeBike;
    }

    public Integer getGelsToTakeBike() {
        return this.gelsToTakeBike;
    }

    public Race gelsToTakeBike(Integer gelsToTakeBike) {
        this.gelsToTakeBike = gelsToTakeBike;
        return this;
    }

    public void setGelsToTakeBike(Integer gelsToTakeBike) {
        this.gelsToTakeBike = gelsToTakeBike;
    }

    public Integer getSportDrinkToTakeRun() {
        return this.sportDrinkToTakeRun;
    }

    public Race sportDrinkToTakeRun(Integer sportDrinkToTakeRun) {
        this.sportDrinkToTakeRun = sportDrinkToTakeRun;
        return this;
    }

    public void setSportDrinkToTakeRun(Integer sportDrinkToTakeRun) {
        this.sportDrinkToTakeRun = sportDrinkToTakeRun;
    }

    public Integer getWaterToTakeRun() {
        return this.waterToTakeRun;
    }

    public Race waterToTakeRun(Integer waterToTakeRun) {
        this.waterToTakeRun = waterToTakeRun;
        return this;
    }

    public void setWaterToTakeRun(Integer waterToTakeRun) {
        this.waterToTakeRun = waterToTakeRun;
    }

    public Integer getExtraWaterToTakeRun() {
        return this.extraWaterToTakeRun;
    }

    public Race extraWaterToTakeRun(Integer extraWaterToTakeRun) {
        this.extraWaterToTakeRun = extraWaterToTakeRun;
        return this;
    }

    public void setExtraWaterToTakeRun(Integer extraWaterToTakeRun) {
        this.extraWaterToTakeRun = extraWaterToTakeRun;
    }

    public Integer getOrsToTakeRun() {
        return this.orsToTakeRun;
    }

    public Race orsToTakeRun(Integer orsToTakeRun) {
        this.orsToTakeRun = orsToTakeRun;
        return this;
    }

    public void setOrsToTakeRun(Integer orsToTakeRun) {
        this.orsToTakeRun = orsToTakeRun;
    }

    public Integer getCarboNeedDuringBikeMin() {
        return this.carboNeedDuringBikeMin;
    }

    public Race carboNeedDuringBikeMin(Integer carboNeedDuringBikeMin) {
        this.carboNeedDuringBikeMin = carboNeedDuringBikeMin;
        return this;
    }

    public void setCarboNeedDuringBikeMin(Integer carboNeedDuringBikeMin) {
        this.carboNeedDuringBikeMin = carboNeedDuringBikeMin;
    }

    public Integer getCarboNeedDuringBikeMax() {
        return this.carboNeedDuringBikeMax;
    }

    public Race carboNeedDuringBikeMax(Integer carboNeedDuringBikeMax) {
        this.carboNeedDuringBikeMax = carboNeedDuringBikeMax;
        return this;
    }

    public void setCarboNeedDuringBikeMax(Integer carboNeedDuringBikeMax) {
        this.carboNeedDuringBikeMax = carboNeedDuringBikeMax;
    }

    public Integer getCarboNeedDuringRunMin() {
        return this.carboNeedDuringRunMin;
    }

    public Race carboNeedDuringRunMin(Integer carboNeedDuringRunMin) {
        this.carboNeedDuringRunMin = carboNeedDuringRunMin;
        return this;
    }

    public void setCarboNeedDuringRunMin(Integer carboNeedDuringRunMin) {
        this.carboNeedDuringRunMin = carboNeedDuringRunMin;
    }

    public Integer getCarboNeedDuringRunMax() {
        return this.carboNeedDuringRunMax;
    }

    public Race carboNeedDuringRunMax(Integer carboNeedDuringRunMax) {
        this.carboNeedDuringRunMax = carboNeedDuringRunMax;
        return this;
    }

    public void setCarboNeedDuringRunMax(Integer carboNeedDuringRunMax) {
        this.carboNeedDuringRunMax = carboNeedDuringRunMax;
    }

    public Integer getDiffCarboRun() {
        return this.diffCarboRun;
    }

    public Race diffCarboRun(Integer diffCarboRun) {
        this.diffCarboRun = diffCarboRun;
        return this;
    }

    public void setDiffCarboRun(Integer diffCarboRun) {
        this.diffCarboRun = diffCarboRun;
    }

    public Integer getDiffMoisterRun() {
        return this.diffMoisterRun;
    }

    public Race diffMoisterRun(Integer diffMoisterRun) {
        this.diffMoisterRun = diffMoisterRun;
        return this;
    }

    public void setDiffMoisterRun(Integer diffMoisterRun) {
        this.diffMoisterRun = diffMoisterRun;
    }

    public Integer getDifCarbo() {
        return this.difCarbo;
    }

    public Race difCarbo(Integer difCarbo) {
        this.difCarbo = difCarbo;
        return this;
    }

    public void setDifCarbo(Integer difCarbo) {
        this.difCarbo = difCarbo;
    }

    public Integer getDifMoister() {
        return this.difMoister;
    }

    public Race difMoister(Integer difMoister) {
        this.difMoister = difMoister;
        return this;
    }

    public void setDifMoister(Integer difMoister) {
        this.difMoister = difMoister;
    }

    public Integer getGelsToTakeRun() {
        return this.gelsToTakeRun;
    }

    public Race gelsToTakeRun(Integer gelsToTakeRun) {
        this.gelsToTakeRun = gelsToTakeRun;
        return this;
    }

    public void setGelsToTakeRun(Integer gelsToTakeRun) {
        this.gelsToTakeRun = gelsToTakeRun;
    }

    public Float getWeightLossDuringBike() {
        return this.weightLossDuringBike;
    }

    public Race weightLossDuringBike(Float weightLossDuringBike) {
        this.weightLossDuringBike = weightLossDuringBike;
        return this;
    }

    public void setWeightLossDuringBike(Float weightLossDuringBike) {
        this.weightLossDuringBike = weightLossDuringBike;
    }

    public Float getCarboNeedDuringRun() {
        return this.carboNeedDuringRun;
    }

    public Race carboNeedDuringRun(Float carboNeedDuringRun) {
        this.carboNeedDuringRun = carboNeedDuringRun;
        return this;
    }

    public void setCarboNeedDuringRun(Float carboNeedDuringRun) {
        this.carboNeedDuringRun = carboNeedDuringRun;
    }

    public Double getFluidNeedPerHourBike() {
        return this.fluidNeedPerHourBike;
    }

    public Race fluidNeedPerHourBike(Double fluidNeedPerHourBike) {
        this.fluidNeedPerHourBike = fluidNeedPerHourBike;
        return this;
    }

    public void setFluidNeedPerHourBike(Double fluidNeedPerHourBike) {
        this.fluidNeedPerHourBike = fluidNeedPerHourBike;
    }

    public Double getFluidNeedPerHourSwim() {
        return this.fluidNeedPerHourSwim;
    }

    public Race fluidNeedPerHourSwim(Double fluidNeedPerHourSwim) {
        this.fluidNeedPerHourSwim = fluidNeedPerHourSwim;
        return this;
    }

    public void setFluidNeedPerHourSwim(Double fluidNeedPerHourSwim) {
        this.fluidNeedPerHourSwim = fluidNeedPerHourSwim;
    }

    public Double getCarboNeedDuringBike() {
        return this.carboNeedDuringBike;
    }

    public Race carboNeedDuringBike(Double carboNeedDuringBike) {
        this.carboNeedDuringBike = carboNeedDuringBike;
        return this;
    }

    public void setCarboNeedDuringBike(Double carboNeedDuringBike) {
        this.carboNeedDuringBike = carboNeedDuringBike;
    }

    public Long getFluidNeedDuringBike() {
        return this.fluidNeedDuringBike;
    }

    public Race fluidNeedDuringBike(Long fluidNeedDuringBike) {
        this.fluidNeedDuringBike = fluidNeedDuringBike;
        return this;
    }

    public void setFluidNeedDuringBike(Long fluidNeedDuringBike) {
        this.fluidNeedDuringBike = fluidNeedDuringBike;
    }

    public Double getFluidNeedPerHourRun() {
        return this.fluidNeedPerHourRun;
    }

    public Race fluidNeedPerHourRun(Double fluidNeedPerHourRun) {
        this.fluidNeedPerHourRun = fluidNeedPerHourRun;
        return this;
    }

    public void setFluidNeedPerHourRun(Double fluidNeedPerHourRun) {
        this.fluidNeedPerHourRun = fluidNeedPerHourRun;
    }

    public Long getFluidNeedDuringRun() {
        return this.fluidNeedDuringRun;
    }

    public Race fluidNeedDuringRun(Long fluidNeedDuringRun) {
        this.fluidNeedDuringRun = fluidNeedDuringRun;
        return this;
    }

    public void setFluidNeedDuringRun(Long fluidNeedDuringRun) {
        this.fluidNeedDuringRun = fluidNeedDuringRun;
    }

    public Float getWeightLossDuringRun() {
        return this.weightLossDuringRun;
    }

    public Race weightLossDuringRun(Float weightLossDuringRun) {
        this.weightLossDuringRun = weightLossDuringRun;
        return this;
    }

    public void setWeightLossDuringRun(Float weightLossDuringRun) {
        this.weightLossDuringRun = weightLossDuringRun;
    }

    public Integer getActFluidNeedBike() {
        return this.actFluidNeedBike;
    }

    public Race actFluidNeedBike(Integer actFluidNeedBike) {
        this.actFluidNeedBike = actFluidNeedBike;
        return this;
    }

    public void setActFluidNeedBike(Integer actFluidNeedBike) {
        this.actFluidNeedBike = actFluidNeedBike;
    }

    public Integer getActFluidNeedRun() {
        return this.actFluidNeedRun;
    }

    public Race actFluidNeedRun(Integer actFluidNeedRun) {
        this.actFluidNeedRun = actFluidNeedRun;
        return this;
    }

    public void setActFluidNeedRun(Integer actFluidNeedRun) {
        this.actFluidNeedRun = actFluidNeedRun;
    }

    public Set<RacePlanForm> getRacePlanForms() {
        return this.racePlanForms;
    }

    public Race racePlanForms(Set<RacePlanForm> racePlanForms) {
        this.setRacePlanForms(racePlanForms);
        return this;
    }

    public Race addRacePlanForm(RacePlanForm racePlanForm) {
        this.racePlanForms.add(racePlanForm);
        racePlanForm.setRace(this);
        return this;
    }

    public Race removeRacePlanForm(RacePlanForm racePlanForm) {
        this.racePlanForms.remove(racePlanForm);
        racePlanForm.setRace(null);
        return this;
    }

    public void setRacePlanForms(Set<RacePlanForm> racePlanForms) {
        if (this.racePlanForms != null) {
            this.racePlanForms.forEach(i -> i.setRace(null));
        }
        if (racePlanForms != null) {
            racePlanForms.forEach(i -> i.setRace(this));
        }
        this.racePlanForms = racePlanForms;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Race athlete(Athlete athlete) {
        this.setAthlete(athlete);
        return this;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Race)) {
            return false;
        }
        return id != null && id.equals(((Race) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Race{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", name='" + getName() + "'" +
            ", logging='" + getLogging() + "'" +
            ", weight=" + getWeight() +
            ", length=" + getLength() +
            ", temperature=" + getTemperature() +
            ", comp=" + getComp() +
            ", swimDuration=" + getSwimDuration() +
            ", bikeDuration=" + getBikeDuration() +
            ", runDuration=" + getRunDuration() +
            ", gelCarbo=" + getGelCarbo() +
            ", drinkCarbo=" + getDrinkCarbo() +
            ", drinkOrgCarbo=" + getDrinkOrgCarbo() +
            ", gelOrgCarbo=" + getGelOrgCarbo() +
            ", gelsbike=" + getGelsbike() +
            ", gelsrun=" + getGelsrun() +
            ", selectedOrgGelQuery='" + getSelectedOrgGelQuery() + "'" +
            ", selectedOrgDrankQuery='" + getSelectedOrgDrankQuery() + "'" +
            ", sportDrinkOrgBike=" + getSportDrinkOrgBike() +
            ", waterOrgBike=" + getWaterOrgBike() +
            ", gelsOrgBike=" + getGelsOrgBike() +
            ", sportDrinkOrgRun=" + getSportDrinkOrgRun() +
            ", waterOrgRun=" + getWaterOrgRun() +
            ", gelsOrgRun=" + getGelsOrgRun() +
            ", orsBeforeStart=" + getOrsBeforeStart() +
            ", sportDrinkToTakeBike=" + getSportDrinkToTakeBike() +
            ", waterToTakeBike=" + getWaterToTakeBike() +
            ", extraWaterToTakeBike=" + getExtraWaterToTakeBike() +
            ", orsToTakeBike=" + getOrsToTakeBike() +
            ", gelsToTakeBike=" + getGelsToTakeBike() +
            ", sportDrinkToTakeRun=" + getSportDrinkToTakeRun() +
            ", waterToTakeRun=" + getWaterToTakeRun() +
            ", extraWaterToTakeRun=" + getExtraWaterToTakeRun() +
            ", orsToTakeRun=" + getOrsToTakeRun() +
            ", carboNeedDuringBikeMin=" + getCarboNeedDuringBikeMin() +
            ", carboNeedDuringBikeMax=" + getCarboNeedDuringBikeMax() +
            ", carboNeedDuringRunMin=" + getCarboNeedDuringRunMin() +
            ", carboNeedDuringRunMax=" + getCarboNeedDuringRunMax() +
            ", diffCarboRun=" + getDiffCarboRun() +
            ", diffMoisterRun=" + getDiffMoisterRun() +
            ", difCarbo=" + getDifCarbo() +
            ", difMoister=" + getDifMoister() +
            ", gelsToTakeRun=" + getGelsToTakeRun() +
            ", weightLossDuringBike=" + getWeightLossDuringBike() +
            ", carboNeedDuringRun=" + getCarboNeedDuringRun() +
            ", fluidNeedPerHourBike=" + getFluidNeedPerHourBike() +
            ", fluidNeedPerHourSwim=" + getFluidNeedPerHourSwim() +
            ", carboNeedDuringBike=" + getCarboNeedDuringBike() +
            ", fluidNeedDuringBike=" + getFluidNeedDuringBike() +
            ", fluidNeedPerHourRun=" + getFluidNeedPerHourRun() +
            ", fluidNeedDuringRun=" + getFluidNeedDuringRun() +
            ", weightLossDuringRun=" + getWeightLossDuringRun() +
            ", actFluidNeedBike=" + getActFluidNeedBike() +
            ", actFluidNeedRun=" + getActFluidNeedRun() +
            "}";
    }
}
