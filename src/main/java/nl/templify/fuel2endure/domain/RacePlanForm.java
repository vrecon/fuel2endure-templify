package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RacePlanForm.
 */
@Entity
@Table(name = "race_plan_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RacePlanForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comp")
    private String comp;

    @Column(name = "name")
    private String name;

    @Column(name = "selected_org_gel_query")
    private Boolean selectedOrgGelQuery;

    @Column(name = "selected_org_drank_query")
    private Boolean selectedOrgDrankQuery;

    @Column(name = "ors_before_start")
    private Integer orsBeforeStart;

    @Column(name = "drink_carbo")
    private Integer drinkCarbo;

    @Column(name = "gel_carbo")
    private Integer gelCarbo;

    @Column(name = "drink_org_carbo")
    private Integer drinkOrgCarbo;

    @Column(name = "gel_org_carbo")
    private Integer gelOrgCarbo;

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

    @Column(name = "diff_carbo_run")
    private Integer diffCarboRun;

    @Column(name = "diff_moister_run")
    private Integer diffMoisterRun;

    @Column(name = "dif_carbo")
    private Integer difCarbo;

    @Column(name = "dif_moister")
    private Integer difMoister;

    @Column(name = "act_fluid_need_bike")
    private Integer actFluidNeedBike;

    @Column(name = "act_fluid_need_run")
    private Integer actFluidNeedRun;

    @Column(name = "carbo_need_during_bike_min")
    private Integer carboNeedDuringBikeMin;

    @Column(name = "carbo_need_during_bike_max")
    private Integer carboNeedDuringBikeMax;

    @Column(name = "carbo_need_during_run_min")
    private Integer carboNeedDuringRunMin;

    @Column(name = "carbo_need_during_run_max")
    private Integer carboNeedDuringRunMax;

    @Column(name = "start_gel")
    private Integer startGel;

    @Column(name = "start_drank")
    private Integer startDrank;

    @ManyToOne
    @JsonIgnoreProperties(value = { "racePlanForms", "athlete" }, allowSetters = true)
    private Race race;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RacePlanForm id(Long id) {
        this.id = id;
        return this;
    }

    public String getComp() {
        return this.comp;
    }

    public RacePlanForm comp(String comp) {
        this.comp = comp;
        return this;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getName() {
        return this.name;
    }

    public RacePlanForm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelectedOrgGelQuery() {
        return this.selectedOrgGelQuery;
    }

    public RacePlanForm selectedOrgGelQuery(Boolean selectedOrgGelQuery) {
        this.selectedOrgGelQuery = selectedOrgGelQuery;
        return this;
    }

    public void setSelectedOrgGelQuery(Boolean selectedOrgGelQuery) {
        this.selectedOrgGelQuery = selectedOrgGelQuery;
    }

    public Boolean getSelectedOrgDrankQuery() {
        return this.selectedOrgDrankQuery;
    }

    public RacePlanForm selectedOrgDrankQuery(Boolean selectedOrgDrankQuery) {
        this.selectedOrgDrankQuery = selectedOrgDrankQuery;
        return this;
    }

    public void setSelectedOrgDrankQuery(Boolean selectedOrgDrankQuery) {
        this.selectedOrgDrankQuery = selectedOrgDrankQuery;
    }

    public Integer getOrsBeforeStart() {
        return this.orsBeforeStart;
    }

    public RacePlanForm orsBeforeStart(Integer orsBeforeStart) {
        this.orsBeforeStart = orsBeforeStart;
        return this;
    }

    public void setOrsBeforeStart(Integer orsBeforeStart) {
        this.orsBeforeStart = orsBeforeStart;
    }

    public Integer getDrinkCarbo() {
        return this.drinkCarbo;
    }

    public RacePlanForm drinkCarbo(Integer drinkCarbo) {
        this.drinkCarbo = drinkCarbo;
        return this;
    }

    public void setDrinkCarbo(Integer drinkCarbo) {
        this.drinkCarbo = drinkCarbo;
    }

    public Integer getGelCarbo() {
        return this.gelCarbo;
    }

    public RacePlanForm gelCarbo(Integer gelCarbo) {
        this.gelCarbo = gelCarbo;
        return this;
    }

    public void setGelCarbo(Integer gelCarbo) {
        this.gelCarbo = gelCarbo;
    }

    public Integer getDrinkOrgCarbo() {
        return this.drinkOrgCarbo;
    }

    public RacePlanForm drinkOrgCarbo(Integer drinkOrgCarbo) {
        this.drinkOrgCarbo = drinkOrgCarbo;
        return this;
    }

    public void setDrinkOrgCarbo(Integer drinkOrgCarbo) {
        this.drinkOrgCarbo = drinkOrgCarbo;
    }

    public Integer getGelOrgCarbo() {
        return this.gelOrgCarbo;
    }

    public RacePlanForm gelOrgCarbo(Integer gelOrgCarbo) {
        this.gelOrgCarbo = gelOrgCarbo;
        return this;
    }

    public void setGelOrgCarbo(Integer gelOrgCarbo) {
        this.gelOrgCarbo = gelOrgCarbo;
    }

    public Integer getSportDrinkOrgBike() {
        return this.sportDrinkOrgBike;
    }

    public RacePlanForm sportDrinkOrgBike(Integer sportDrinkOrgBike) {
        this.sportDrinkOrgBike = sportDrinkOrgBike;
        return this;
    }

    public void setSportDrinkOrgBike(Integer sportDrinkOrgBike) {
        this.sportDrinkOrgBike = sportDrinkOrgBike;
    }

    public Integer getWaterOrgBike() {
        return this.waterOrgBike;
    }

    public RacePlanForm waterOrgBike(Integer waterOrgBike) {
        this.waterOrgBike = waterOrgBike;
        return this;
    }

    public void setWaterOrgBike(Integer waterOrgBike) {
        this.waterOrgBike = waterOrgBike;
    }

    public Integer getGelsOrgBike() {
        return this.gelsOrgBike;
    }

    public RacePlanForm gelsOrgBike(Integer gelsOrgBike) {
        this.gelsOrgBike = gelsOrgBike;
        return this;
    }

    public void setGelsOrgBike(Integer gelsOrgBike) {
        this.gelsOrgBike = gelsOrgBike;
    }

    public Integer getSportDrinkOrgRun() {
        return this.sportDrinkOrgRun;
    }

    public RacePlanForm sportDrinkOrgRun(Integer sportDrinkOrgRun) {
        this.sportDrinkOrgRun = sportDrinkOrgRun;
        return this;
    }

    public void setSportDrinkOrgRun(Integer sportDrinkOrgRun) {
        this.sportDrinkOrgRun = sportDrinkOrgRun;
    }

    public Integer getWaterOrgRun() {
        return this.waterOrgRun;
    }

    public RacePlanForm waterOrgRun(Integer waterOrgRun) {
        this.waterOrgRun = waterOrgRun;
        return this;
    }

    public void setWaterOrgRun(Integer waterOrgRun) {
        this.waterOrgRun = waterOrgRun;
    }

    public Integer getGelsOrgRun() {
        return this.gelsOrgRun;
    }

    public RacePlanForm gelsOrgRun(Integer gelsOrgRun) {
        this.gelsOrgRun = gelsOrgRun;
        return this;
    }

    public void setGelsOrgRun(Integer gelsOrgRun) {
        this.gelsOrgRun = gelsOrgRun;
    }

    public Integer getSportDrinkToTakeBike() {
        return this.sportDrinkToTakeBike;
    }

    public RacePlanForm sportDrinkToTakeBike(Integer sportDrinkToTakeBike) {
        this.sportDrinkToTakeBike = sportDrinkToTakeBike;
        return this;
    }

    public void setSportDrinkToTakeBike(Integer sportDrinkToTakeBike) {
        this.sportDrinkToTakeBike = sportDrinkToTakeBike;
    }

    public Integer getWaterToTakeBike() {
        return this.waterToTakeBike;
    }

    public RacePlanForm waterToTakeBike(Integer waterToTakeBike) {
        this.waterToTakeBike = waterToTakeBike;
        return this;
    }

    public void setWaterToTakeBike(Integer waterToTakeBike) {
        this.waterToTakeBike = waterToTakeBike;
    }

    public Integer getExtraWaterToTakeBike() {
        return this.extraWaterToTakeBike;
    }

    public RacePlanForm extraWaterToTakeBike(Integer extraWaterToTakeBike) {
        this.extraWaterToTakeBike = extraWaterToTakeBike;
        return this;
    }

    public void setExtraWaterToTakeBike(Integer extraWaterToTakeBike) {
        this.extraWaterToTakeBike = extraWaterToTakeBike;
    }

    public Integer getOrsToTakeBike() {
        return this.orsToTakeBike;
    }

    public RacePlanForm orsToTakeBike(Integer orsToTakeBike) {
        this.orsToTakeBike = orsToTakeBike;
        return this;
    }

    public void setOrsToTakeBike(Integer orsToTakeBike) {
        this.orsToTakeBike = orsToTakeBike;
    }

    public Integer getGelsToTakeBike() {
        return this.gelsToTakeBike;
    }

    public RacePlanForm gelsToTakeBike(Integer gelsToTakeBike) {
        this.gelsToTakeBike = gelsToTakeBike;
        return this;
    }

    public void setGelsToTakeBike(Integer gelsToTakeBike) {
        this.gelsToTakeBike = gelsToTakeBike;
    }

    public Integer getSportDrinkToTakeRun() {
        return this.sportDrinkToTakeRun;
    }

    public RacePlanForm sportDrinkToTakeRun(Integer sportDrinkToTakeRun) {
        this.sportDrinkToTakeRun = sportDrinkToTakeRun;
        return this;
    }

    public void setSportDrinkToTakeRun(Integer sportDrinkToTakeRun) {
        this.sportDrinkToTakeRun = sportDrinkToTakeRun;
    }

    public Integer getWaterToTakeRun() {
        return this.waterToTakeRun;
    }

    public RacePlanForm waterToTakeRun(Integer waterToTakeRun) {
        this.waterToTakeRun = waterToTakeRun;
        return this;
    }

    public void setWaterToTakeRun(Integer waterToTakeRun) {
        this.waterToTakeRun = waterToTakeRun;
    }

    public Integer getExtraWaterToTakeRun() {
        return this.extraWaterToTakeRun;
    }

    public RacePlanForm extraWaterToTakeRun(Integer extraWaterToTakeRun) {
        this.extraWaterToTakeRun = extraWaterToTakeRun;
        return this;
    }

    public void setExtraWaterToTakeRun(Integer extraWaterToTakeRun) {
        this.extraWaterToTakeRun = extraWaterToTakeRun;
    }

    public Integer getOrsToTakeRun() {
        return this.orsToTakeRun;
    }

    public RacePlanForm orsToTakeRun(Integer orsToTakeRun) {
        this.orsToTakeRun = orsToTakeRun;
        return this;
    }

    public void setOrsToTakeRun(Integer orsToTakeRun) {
        this.orsToTakeRun = orsToTakeRun;
    }

    public Integer getGelsToTakeRun() {
        return this.gelsToTakeRun;
    }

    public RacePlanForm gelsToTakeRun(Integer gelsToTakeRun) {
        this.gelsToTakeRun = gelsToTakeRun;
        return this;
    }

    public void setGelsToTakeRun(Integer gelsToTakeRun) {
        this.gelsToTakeRun = gelsToTakeRun;
    }

    public Float getWeightLossDuringBike() {
        return this.weightLossDuringBike;
    }

    public RacePlanForm weightLossDuringBike(Float weightLossDuringBike) {
        this.weightLossDuringBike = weightLossDuringBike;
        return this;
    }

    public void setWeightLossDuringBike(Float weightLossDuringBike) {
        this.weightLossDuringBike = weightLossDuringBike;
    }

    public Float getCarboNeedDuringRun() {
        return this.carboNeedDuringRun;
    }

    public RacePlanForm carboNeedDuringRun(Float carboNeedDuringRun) {
        this.carboNeedDuringRun = carboNeedDuringRun;
        return this;
    }

    public void setCarboNeedDuringRun(Float carboNeedDuringRun) {
        this.carboNeedDuringRun = carboNeedDuringRun;
    }

    public Double getFluidNeedPerHourBike() {
        return this.fluidNeedPerHourBike;
    }

    public RacePlanForm fluidNeedPerHourBike(Double fluidNeedPerHourBike) {
        this.fluidNeedPerHourBike = fluidNeedPerHourBike;
        return this;
    }

    public void setFluidNeedPerHourBike(Double fluidNeedPerHourBike) {
        this.fluidNeedPerHourBike = fluidNeedPerHourBike;
    }

    public Double getFluidNeedPerHourSwim() {
        return this.fluidNeedPerHourSwim;
    }

    public RacePlanForm fluidNeedPerHourSwim(Double fluidNeedPerHourSwim) {
        this.fluidNeedPerHourSwim = fluidNeedPerHourSwim;
        return this;
    }

    public void setFluidNeedPerHourSwim(Double fluidNeedPerHourSwim) {
        this.fluidNeedPerHourSwim = fluidNeedPerHourSwim;
    }

    public Double getCarboNeedDuringBike() {
        return this.carboNeedDuringBike;
    }

    public RacePlanForm carboNeedDuringBike(Double carboNeedDuringBike) {
        this.carboNeedDuringBike = carboNeedDuringBike;
        return this;
    }

    public void setCarboNeedDuringBike(Double carboNeedDuringBike) {
        this.carboNeedDuringBike = carboNeedDuringBike;
    }

    public Long getFluidNeedDuringBike() {
        return this.fluidNeedDuringBike;
    }

    public RacePlanForm fluidNeedDuringBike(Long fluidNeedDuringBike) {
        this.fluidNeedDuringBike = fluidNeedDuringBike;
        return this;
    }

    public void setFluidNeedDuringBike(Long fluidNeedDuringBike) {
        this.fluidNeedDuringBike = fluidNeedDuringBike;
    }

    public Double getFluidNeedPerHourRun() {
        return this.fluidNeedPerHourRun;
    }

    public RacePlanForm fluidNeedPerHourRun(Double fluidNeedPerHourRun) {
        this.fluidNeedPerHourRun = fluidNeedPerHourRun;
        return this;
    }

    public void setFluidNeedPerHourRun(Double fluidNeedPerHourRun) {
        this.fluidNeedPerHourRun = fluidNeedPerHourRun;
    }

    public Long getFluidNeedDuringRun() {
        return this.fluidNeedDuringRun;
    }

    public RacePlanForm fluidNeedDuringRun(Long fluidNeedDuringRun) {
        this.fluidNeedDuringRun = fluidNeedDuringRun;
        return this;
    }

    public void setFluidNeedDuringRun(Long fluidNeedDuringRun) {
        this.fluidNeedDuringRun = fluidNeedDuringRun;
    }

    public Float getWeightLossDuringRun() {
        return this.weightLossDuringRun;
    }

    public RacePlanForm weightLossDuringRun(Float weightLossDuringRun) {
        this.weightLossDuringRun = weightLossDuringRun;
        return this;
    }

    public void setWeightLossDuringRun(Float weightLossDuringRun) {
        this.weightLossDuringRun = weightLossDuringRun;
    }

    public Integer getDiffCarboRun() {
        return this.diffCarboRun;
    }

    public RacePlanForm diffCarboRun(Integer diffCarboRun) {
        this.diffCarboRun = diffCarboRun;
        return this;
    }

    public void setDiffCarboRun(Integer diffCarboRun) {
        this.diffCarboRun = diffCarboRun;
    }

    public Integer getDiffMoisterRun() {
        return this.diffMoisterRun;
    }

    public RacePlanForm diffMoisterRun(Integer diffMoisterRun) {
        this.diffMoisterRun = diffMoisterRun;
        return this;
    }

    public void setDiffMoisterRun(Integer diffMoisterRun) {
        this.diffMoisterRun = diffMoisterRun;
    }

    public Integer getDifCarbo() {
        return this.difCarbo;
    }

    public RacePlanForm difCarbo(Integer difCarbo) {
        this.difCarbo = difCarbo;
        return this;
    }

    public void setDifCarbo(Integer difCarbo) {
        this.difCarbo = difCarbo;
    }

    public Integer getDifMoister() {
        return this.difMoister;
    }

    public RacePlanForm difMoister(Integer difMoister) {
        this.difMoister = difMoister;
        return this;
    }

    public void setDifMoister(Integer difMoister) {
        this.difMoister = difMoister;
    }

    public Integer getActFluidNeedBike() {
        return this.actFluidNeedBike;
    }

    public RacePlanForm actFluidNeedBike(Integer actFluidNeedBike) {
        this.actFluidNeedBike = actFluidNeedBike;
        return this;
    }

    public void setActFluidNeedBike(Integer actFluidNeedBike) {
        this.actFluidNeedBike = actFluidNeedBike;
    }

    public Integer getActFluidNeedRun() {
        return this.actFluidNeedRun;
    }

    public RacePlanForm actFluidNeedRun(Integer actFluidNeedRun) {
        this.actFluidNeedRun = actFluidNeedRun;
        return this;
    }

    public void setActFluidNeedRun(Integer actFluidNeedRun) {
        this.actFluidNeedRun = actFluidNeedRun;
    }

    public Integer getCarboNeedDuringBikeMin() {
        return this.carboNeedDuringBikeMin;
    }

    public RacePlanForm carboNeedDuringBikeMin(Integer carboNeedDuringBikeMin) {
        this.carboNeedDuringBikeMin = carboNeedDuringBikeMin;
        return this;
    }

    public void setCarboNeedDuringBikeMin(Integer carboNeedDuringBikeMin) {
        this.carboNeedDuringBikeMin = carboNeedDuringBikeMin;
    }

    public Integer getCarboNeedDuringBikeMax() {
        return this.carboNeedDuringBikeMax;
    }

    public RacePlanForm carboNeedDuringBikeMax(Integer carboNeedDuringBikeMax) {
        this.carboNeedDuringBikeMax = carboNeedDuringBikeMax;
        return this;
    }

    public void setCarboNeedDuringBikeMax(Integer carboNeedDuringBikeMax) {
        this.carboNeedDuringBikeMax = carboNeedDuringBikeMax;
    }

    public Integer getCarboNeedDuringRunMin() {
        return this.carboNeedDuringRunMin;
    }

    public RacePlanForm carboNeedDuringRunMin(Integer carboNeedDuringRunMin) {
        this.carboNeedDuringRunMin = carboNeedDuringRunMin;
        return this;
    }

    public void setCarboNeedDuringRunMin(Integer carboNeedDuringRunMin) {
        this.carboNeedDuringRunMin = carboNeedDuringRunMin;
    }

    public Integer getCarboNeedDuringRunMax() {
        return this.carboNeedDuringRunMax;
    }

    public RacePlanForm carboNeedDuringRunMax(Integer carboNeedDuringRunMax) {
        this.carboNeedDuringRunMax = carboNeedDuringRunMax;
        return this;
    }

    public void setCarboNeedDuringRunMax(Integer carboNeedDuringRunMax) {
        this.carboNeedDuringRunMax = carboNeedDuringRunMax;
    }

    public Integer getStartGel() {
        return this.startGel;
    }

    public RacePlanForm startGel(Integer startGel) {
        this.startGel = startGel;
        return this;
    }

    public void setStartGel(Integer startGel) {
        this.startGel = startGel;
    }

    public Integer getStartDrank() {
        return this.startDrank;
    }

    public RacePlanForm startDrank(Integer startDrank) {
        this.startDrank = startDrank;
        return this;
    }

    public void setStartDrank(Integer startDrank) {
        this.startDrank = startDrank;
    }

    public Race getRace() {
        return this.race;
    }

    public RacePlanForm race(Race race) {
        this.setRace(race);
        return this;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RacePlanForm)) {
            return false;
        }
        return id != null && id.equals(((RacePlanForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RacePlanForm{" +
            "id=" + getId() +
            ", comp='" + getComp() + "'" +
            ", name='" + getName() + "'" +
            ", selectedOrgGelQuery='" + getSelectedOrgGelQuery() + "'" +
            ", selectedOrgDrankQuery='" + getSelectedOrgDrankQuery() + "'" +
            ", orsBeforeStart=" + getOrsBeforeStart() +
            ", drinkCarbo=" + getDrinkCarbo() +
            ", gelCarbo=" + getGelCarbo() +
            ", drinkOrgCarbo=" + getDrinkOrgCarbo() +
            ", gelOrgCarbo=" + getGelOrgCarbo() +
            ", sportDrinkOrgBike=" + getSportDrinkOrgBike() +
            ", waterOrgBike=" + getWaterOrgBike() +
            ", gelsOrgBike=" + getGelsOrgBike() +
            ", sportDrinkOrgRun=" + getSportDrinkOrgRun() +
            ", waterOrgRun=" + getWaterOrgRun() +
            ", gelsOrgRun=" + getGelsOrgRun() +
            ", sportDrinkToTakeBike=" + getSportDrinkToTakeBike() +
            ", waterToTakeBike=" + getWaterToTakeBike() +
            ", extraWaterToTakeBike=" + getExtraWaterToTakeBike() +
            ", orsToTakeBike=" + getOrsToTakeBike() +
            ", gelsToTakeBike=" + getGelsToTakeBike() +
            ", sportDrinkToTakeRun=" + getSportDrinkToTakeRun() +
            ", waterToTakeRun=" + getWaterToTakeRun() +
            ", extraWaterToTakeRun=" + getExtraWaterToTakeRun() +
            ", orsToTakeRun=" + getOrsToTakeRun() +
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
            ", diffCarboRun=" + getDiffCarboRun() +
            ", diffMoisterRun=" + getDiffMoisterRun() +
            ", difCarbo=" + getDifCarbo() +
            ", difMoister=" + getDifMoister() +
            ", actFluidNeedBike=" + getActFluidNeedBike() +
            ", actFluidNeedRun=" + getActFluidNeedRun() +
            ", carboNeedDuringBikeMin=" + getCarboNeedDuringBikeMin() +
            ", carboNeedDuringBikeMax=" + getCarboNeedDuringBikeMax() +
            ", carboNeedDuringRunMin=" + getCarboNeedDuringRunMin() +
            ", carboNeedDuringRunMax=" + getCarboNeedDuringRunMax() +
            ", startGel=" + getStartGel() +
            ", startDrank=" + getStartDrank() +
            "}";
    }
}
