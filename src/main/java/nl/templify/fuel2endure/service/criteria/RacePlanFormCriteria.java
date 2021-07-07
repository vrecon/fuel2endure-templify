package nl.templify.fuel2endure.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link nl.templify.fuel2endure.domain.RacePlanForm} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.RacePlanFormResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /race-plan-forms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RacePlanFormCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comp;

    private StringFilter name;

    private BooleanFilter selectedOrgGelQuery;

    private BooleanFilter selectedOrgDrankQuery;

    private IntegerFilter orsBeforeStart;

    private IntegerFilter drinkCarbo;

    private IntegerFilter gelCarbo;

    private IntegerFilter drinkOrgCarbo;

    private IntegerFilter gelOrgCarbo;

    private IntegerFilter sportDrinkOrgBike;

    private IntegerFilter waterOrgBike;

    private IntegerFilter gelsOrgBike;

    private IntegerFilter sportDrinkOrgRun;

    private IntegerFilter waterOrgRun;

    private IntegerFilter gelsOrgRun;

    private IntegerFilter sportDrinkToTakeBike;

    private IntegerFilter waterToTakeBike;

    private IntegerFilter extraWaterToTakeBike;

    private IntegerFilter orsToTakeBike;

    private IntegerFilter gelsToTakeBike;

    private IntegerFilter sportDrinkToTakeRun;

    private IntegerFilter waterToTakeRun;

    private IntegerFilter extraWaterToTakeRun;

    private IntegerFilter orsToTakeRun;

    private IntegerFilter gelsToTakeRun;

    private FloatFilter weightLossDuringBike;

    private FloatFilter carboNeedDuringRun;

    private DoubleFilter fluidNeedPerHourBike;

    private DoubleFilter fluidNeedPerHourSwim;

    private DoubleFilter carboNeedDuringBike;

    private LongFilter fluidNeedDuringBike;

    private DoubleFilter fluidNeedPerHourRun;

    private LongFilter fluidNeedDuringRun;

    private FloatFilter weightLossDuringRun;

    private IntegerFilter diffCarboRun;

    private IntegerFilter diffMoisterRun;

    private IntegerFilter difCarbo;

    private IntegerFilter difMoister;

    private IntegerFilter actFluidNeedBike;

    private IntegerFilter actFluidNeedRun;

    private IntegerFilter carboNeedDuringBikeMin;

    private IntegerFilter carboNeedDuringBikeMax;

    private IntegerFilter carboNeedDuringRunMin;

    private IntegerFilter carboNeedDuringRunMax;

    private IntegerFilter startGel;

    private IntegerFilter startDrank;

    private LongFilter raceId;

    public RacePlanFormCriteria() {}

    public RacePlanFormCriteria(RacePlanFormCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comp = other.comp == null ? null : other.comp.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.selectedOrgGelQuery = other.selectedOrgGelQuery == null ? null : other.selectedOrgGelQuery.copy();
        this.selectedOrgDrankQuery = other.selectedOrgDrankQuery == null ? null : other.selectedOrgDrankQuery.copy();
        this.orsBeforeStart = other.orsBeforeStart == null ? null : other.orsBeforeStart.copy();
        this.drinkCarbo = other.drinkCarbo == null ? null : other.drinkCarbo.copy();
        this.gelCarbo = other.gelCarbo == null ? null : other.gelCarbo.copy();
        this.drinkOrgCarbo = other.drinkOrgCarbo == null ? null : other.drinkOrgCarbo.copy();
        this.gelOrgCarbo = other.gelOrgCarbo == null ? null : other.gelOrgCarbo.copy();
        this.sportDrinkOrgBike = other.sportDrinkOrgBike == null ? null : other.sportDrinkOrgBike.copy();
        this.waterOrgBike = other.waterOrgBike == null ? null : other.waterOrgBike.copy();
        this.gelsOrgBike = other.gelsOrgBike == null ? null : other.gelsOrgBike.copy();
        this.sportDrinkOrgRun = other.sportDrinkOrgRun == null ? null : other.sportDrinkOrgRun.copy();
        this.waterOrgRun = other.waterOrgRun == null ? null : other.waterOrgRun.copy();
        this.gelsOrgRun = other.gelsOrgRun == null ? null : other.gelsOrgRun.copy();
        this.sportDrinkToTakeBike = other.sportDrinkToTakeBike == null ? null : other.sportDrinkToTakeBike.copy();
        this.waterToTakeBike = other.waterToTakeBike == null ? null : other.waterToTakeBike.copy();
        this.extraWaterToTakeBike = other.extraWaterToTakeBike == null ? null : other.extraWaterToTakeBike.copy();
        this.orsToTakeBike = other.orsToTakeBike == null ? null : other.orsToTakeBike.copy();
        this.gelsToTakeBike = other.gelsToTakeBike == null ? null : other.gelsToTakeBike.copy();
        this.sportDrinkToTakeRun = other.sportDrinkToTakeRun == null ? null : other.sportDrinkToTakeRun.copy();
        this.waterToTakeRun = other.waterToTakeRun == null ? null : other.waterToTakeRun.copy();
        this.extraWaterToTakeRun = other.extraWaterToTakeRun == null ? null : other.extraWaterToTakeRun.copy();
        this.orsToTakeRun = other.orsToTakeRun == null ? null : other.orsToTakeRun.copy();
        this.gelsToTakeRun = other.gelsToTakeRun == null ? null : other.gelsToTakeRun.copy();
        this.weightLossDuringBike = other.weightLossDuringBike == null ? null : other.weightLossDuringBike.copy();
        this.carboNeedDuringRun = other.carboNeedDuringRun == null ? null : other.carboNeedDuringRun.copy();
        this.fluidNeedPerHourBike = other.fluidNeedPerHourBike == null ? null : other.fluidNeedPerHourBike.copy();
        this.fluidNeedPerHourSwim = other.fluidNeedPerHourSwim == null ? null : other.fluidNeedPerHourSwim.copy();
        this.carboNeedDuringBike = other.carboNeedDuringBike == null ? null : other.carboNeedDuringBike.copy();
        this.fluidNeedDuringBike = other.fluidNeedDuringBike == null ? null : other.fluidNeedDuringBike.copy();
        this.fluidNeedPerHourRun = other.fluidNeedPerHourRun == null ? null : other.fluidNeedPerHourRun.copy();
        this.fluidNeedDuringRun = other.fluidNeedDuringRun == null ? null : other.fluidNeedDuringRun.copy();
        this.weightLossDuringRun = other.weightLossDuringRun == null ? null : other.weightLossDuringRun.copy();
        this.diffCarboRun = other.diffCarboRun == null ? null : other.diffCarboRun.copy();
        this.diffMoisterRun = other.diffMoisterRun == null ? null : other.diffMoisterRun.copy();
        this.difCarbo = other.difCarbo == null ? null : other.difCarbo.copy();
        this.difMoister = other.difMoister == null ? null : other.difMoister.copy();
        this.actFluidNeedBike = other.actFluidNeedBike == null ? null : other.actFluidNeedBike.copy();
        this.actFluidNeedRun = other.actFluidNeedRun == null ? null : other.actFluidNeedRun.copy();
        this.carboNeedDuringBikeMin = other.carboNeedDuringBikeMin == null ? null : other.carboNeedDuringBikeMin.copy();
        this.carboNeedDuringBikeMax = other.carboNeedDuringBikeMax == null ? null : other.carboNeedDuringBikeMax.copy();
        this.carboNeedDuringRunMin = other.carboNeedDuringRunMin == null ? null : other.carboNeedDuringRunMin.copy();
        this.carboNeedDuringRunMax = other.carboNeedDuringRunMax == null ? null : other.carboNeedDuringRunMax.copy();
        this.startGel = other.startGel == null ? null : other.startGel.copy();
        this.startDrank = other.startDrank == null ? null : other.startDrank.copy();
        this.raceId = other.raceId == null ? null : other.raceId.copy();
    }

    @Override
    public RacePlanFormCriteria copy() {
        return new RacePlanFormCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getComp() {
        return comp;
    }

    public StringFilter comp() {
        if (comp == null) {
            comp = new StringFilter();
        }
        return comp;
    }

    public void setComp(StringFilter comp) {
        this.comp = comp;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getSelectedOrgGelQuery() {
        return selectedOrgGelQuery;
    }

    public BooleanFilter selectedOrgGelQuery() {
        if (selectedOrgGelQuery == null) {
            selectedOrgGelQuery = new BooleanFilter();
        }
        return selectedOrgGelQuery;
    }

    public void setSelectedOrgGelQuery(BooleanFilter selectedOrgGelQuery) {
        this.selectedOrgGelQuery = selectedOrgGelQuery;
    }

    public BooleanFilter getSelectedOrgDrankQuery() {
        return selectedOrgDrankQuery;
    }

    public BooleanFilter selectedOrgDrankQuery() {
        if (selectedOrgDrankQuery == null) {
            selectedOrgDrankQuery = new BooleanFilter();
        }
        return selectedOrgDrankQuery;
    }

    public void setSelectedOrgDrankQuery(BooleanFilter selectedOrgDrankQuery) {
        this.selectedOrgDrankQuery = selectedOrgDrankQuery;
    }

    public IntegerFilter getOrsBeforeStart() {
        return orsBeforeStart;
    }

    public IntegerFilter orsBeforeStart() {
        if (orsBeforeStart == null) {
            orsBeforeStart = new IntegerFilter();
        }
        return orsBeforeStart;
    }

    public void setOrsBeforeStart(IntegerFilter orsBeforeStart) {
        this.orsBeforeStart = orsBeforeStart;
    }

    public IntegerFilter getDrinkCarbo() {
        return drinkCarbo;
    }

    public IntegerFilter drinkCarbo() {
        if (drinkCarbo == null) {
            drinkCarbo = new IntegerFilter();
        }
        return drinkCarbo;
    }

    public void setDrinkCarbo(IntegerFilter drinkCarbo) {
        this.drinkCarbo = drinkCarbo;
    }

    public IntegerFilter getGelCarbo() {
        return gelCarbo;
    }

    public IntegerFilter gelCarbo() {
        if (gelCarbo == null) {
            gelCarbo = new IntegerFilter();
        }
        return gelCarbo;
    }

    public void setGelCarbo(IntegerFilter gelCarbo) {
        this.gelCarbo = gelCarbo;
    }

    public IntegerFilter getDrinkOrgCarbo() {
        return drinkOrgCarbo;
    }

    public IntegerFilter drinkOrgCarbo() {
        if (drinkOrgCarbo == null) {
            drinkOrgCarbo = new IntegerFilter();
        }
        return drinkOrgCarbo;
    }

    public void setDrinkOrgCarbo(IntegerFilter drinkOrgCarbo) {
        this.drinkOrgCarbo = drinkOrgCarbo;
    }

    public IntegerFilter getGelOrgCarbo() {
        return gelOrgCarbo;
    }

    public IntegerFilter gelOrgCarbo() {
        if (gelOrgCarbo == null) {
            gelOrgCarbo = new IntegerFilter();
        }
        return gelOrgCarbo;
    }

    public void setGelOrgCarbo(IntegerFilter gelOrgCarbo) {
        this.gelOrgCarbo = gelOrgCarbo;
    }

    public IntegerFilter getSportDrinkOrgBike() {
        return sportDrinkOrgBike;
    }

    public IntegerFilter sportDrinkOrgBike() {
        if (sportDrinkOrgBike == null) {
            sportDrinkOrgBike = new IntegerFilter();
        }
        return sportDrinkOrgBike;
    }

    public void setSportDrinkOrgBike(IntegerFilter sportDrinkOrgBike) {
        this.sportDrinkOrgBike = sportDrinkOrgBike;
    }

    public IntegerFilter getWaterOrgBike() {
        return waterOrgBike;
    }

    public IntegerFilter waterOrgBike() {
        if (waterOrgBike == null) {
            waterOrgBike = new IntegerFilter();
        }
        return waterOrgBike;
    }

    public void setWaterOrgBike(IntegerFilter waterOrgBike) {
        this.waterOrgBike = waterOrgBike;
    }

    public IntegerFilter getGelsOrgBike() {
        return gelsOrgBike;
    }

    public IntegerFilter gelsOrgBike() {
        if (gelsOrgBike == null) {
            gelsOrgBike = new IntegerFilter();
        }
        return gelsOrgBike;
    }

    public void setGelsOrgBike(IntegerFilter gelsOrgBike) {
        this.gelsOrgBike = gelsOrgBike;
    }

    public IntegerFilter getSportDrinkOrgRun() {
        return sportDrinkOrgRun;
    }

    public IntegerFilter sportDrinkOrgRun() {
        if (sportDrinkOrgRun == null) {
            sportDrinkOrgRun = new IntegerFilter();
        }
        return sportDrinkOrgRun;
    }

    public void setSportDrinkOrgRun(IntegerFilter sportDrinkOrgRun) {
        this.sportDrinkOrgRun = sportDrinkOrgRun;
    }

    public IntegerFilter getWaterOrgRun() {
        return waterOrgRun;
    }

    public IntegerFilter waterOrgRun() {
        if (waterOrgRun == null) {
            waterOrgRun = new IntegerFilter();
        }
        return waterOrgRun;
    }

    public void setWaterOrgRun(IntegerFilter waterOrgRun) {
        this.waterOrgRun = waterOrgRun;
    }

    public IntegerFilter getGelsOrgRun() {
        return gelsOrgRun;
    }

    public IntegerFilter gelsOrgRun() {
        if (gelsOrgRun == null) {
            gelsOrgRun = new IntegerFilter();
        }
        return gelsOrgRun;
    }

    public void setGelsOrgRun(IntegerFilter gelsOrgRun) {
        this.gelsOrgRun = gelsOrgRun;
    }

    public IntegerFilter getSportDrinkToTakeBike() {
        return sportDrinkToTakeBike;
    }

    public IntegerFilter sportDrinkToTakeBike() {
        if (sportDrinkToTakeBike == null) {
            sportDrinkToTakeBike = new IntegerFilter();
        }
        return sportDrinkToTakeBike;
    }

    public void setSportDrinkToTakeBike(IntegerFilter sportDrinkToTakeBike) {
        this.sportDrinkToTakeBike = sportDrinkToTakeBike;
    }

    public IntegerFilter getWaterToTakeBike() {
        return waterToTakeBike;
    }

    public IntegerFilter waterToTakeBike() {
        if (waterToTakeBike == null) {
            waterToTakeBike = new IntegerFilter();
        }
        return waterToTakeBike;
    }

    public void setWaterToTakeBike(IntegerFilter waterToTakeBike) {
        this.waterToTakeBike = waterToTakeBike;
    }

    public IntegerFilter getExtraWaterToTakeBike() {
        return extraWaterToTakeBike;
    }

    public IntegerFilter extraWaterToTakeBike() {
        if (extraWaterToTakeBike == null) {
            extraWaterToTakeBike = new IntegerFilter();
        }
        return extraWaterToTakeBike;
    }

    public void setExtraWaterToTakeBike(IntegerFilter extraWaterToTakeBike) {
        this.extraWaterToTakeBike = extraWaterToTakeBike;
    }

    public IntegerFilter getOrsToTakeBike() {
        return orsToTakeBike;
    }

    public IntegerFilter orsToTakeBike() {
        if (orsToTakeBike == null) {
            orsToTakeBike = new IntegerFilter();
        }
        return orsToTakeBike;
    }

    public void setOrsToTakeBike(IntegerFilter orsToTakeBike) {
        this.orsToTakeBike = orsToTakeBike;
    }

    public IntegerFilter getGelsToTakeBike() {
        return gelsToTakeBike;
    }

    public IntegerFilter gelsToTakeBike() {
        if (gelsToTakeBike == null) {
            gelsToTakeBike = new IntegerFilter();
        }
        return gelsToTakeBike;
    }

    public void setGelsToTakeBike(IntegerFilter gelsToTakeBike) {
        this.gelsToTakeBike = gelsToTakeBike;
    }

    public IntegerFilter getSportDrinkToTakeRun() {
        return sportDrinkToTakeRun;
    }

    public IntegerFilter sportDrinkToTakeRun() {
        if (sportDrinkToTakeRun == null) {
            sportDrinkToTakeRun = new IntegerFilter();
        }
        return sportDrinkToTakeRun;
    }

    public void setSportDrinkToTakeRun(IntegerFilter sportDrinkToTakeRun) {
        this.sportDrinkToTakeRun = sportDrinkToTakeRun;
    }

    public IntegerFilter getWaterToTakeRun() {
        return waterToTakeRun;
    }

    public IntegerFilter waterToTakeRun() {
        if (waterToTakeRun == null) {
            waterToTakeRun = new IntegerFilter();
        }
        return waterToTakeRun;
    }

    public void setWaterToTakeRun(IntegerFilter waterToTakeRun) {
        this.waterToTakeRun = waterToTakeRun;
    }

    public IntegerFilter getExtraWaterToTakeRun() {
        return extraWaterToTakeRun;
    }

    public IntegerFilter extraWaterToTakeRun() {
        if (extraWaterToTakeRun == null) {
            extraWaterToTakeRun = new IntegerFilter();
        }
        return extraWaterToTakeRun;
    }

    public void setExtraWaterToTakeRun(IntegerFilter extraWaterToTakeRun) {
        this.extraWaterToTakeRun = extraWaterToTakeRun;
    }

    public IntegerFilter getOrsToTakeRun() {
        return orsToTakeRun;
    }

    public IntegerFilter orsToTakeRun() {
        if (orsToTakeRun == null) {
            orsToTakeRun = new IntegerFilter();
        }
        return orsToTakeRun;
    }

    public void setOrsToTakeRun(IntegerFilter orsToTakeRun) {
        this.orsToTakeRun = orsToTakeRun;
    }

    public IntegerFilter getGelsToTakeRun() {
        return gelsToTakeRun;
    }

    public IntegerFilter gelsToTakeRun() {
        if (gelsToTakeRun == null) {
            gelsToTakeRun = new IntegerFilter();
        }
        return gelsToTakeRun;
    }

    public void setGelsToTakeRun(IntegerFilter gelsToTakeRun) {
        this.gelsToTakeRun = gelsToTakeRun;
    }

    public FloatFilter getWeightLossDuringBike() {
        return weightLossDuringBike;
    }

    public FloatFilter weightLossDuringBike() {
        if (weightLossDuringBike == null) {
            weightLossDuringBike = new FloatFilter();
        }
        return weightLossDuringBike;
    }

    public void setWeightLossDuringBike(FloatFilter weightLossDuringBike) {
        this.weightLossDuringBike = weightLossDuringBike;
    }

    public FloatFilter getCarboNeedDuringRun() {
        return carboNeedDuringRun;
    }

    public FloatFilter carboNeedDuringRun() {
        if (carboNeedDuringRun == null) {
            carboNeedDuringRun = new FloatFilter();
        }
        return carboNeedDuringRun;
    }

    public void setCarboNeedDuringRun(FloatFilter carboNeedDuringRun) {
        this.carboNeedDuringRun = carboNeedDuringRun;
    }

    public DoubleFilter getFluidNeedPerHourBike() {
        return fluidNeedPerHourBike;
    }

    public DoubleFilter fluidNeedPerHourBike() {
        if (fluidNeedPerHourBike == null) {
            fluidNeedPerHourBike = new DoubleFilter();
        }
        return fluidNeedPerHourBike;
    }

    public void setFluidNeedPerHourBike(DoubleFilter fluidNeedPerHourBike) {
        this.fluidNeedPerHourBike = fluidNeedPerHourBike;
    }

    public DoubleFilter getFluidNeedPerHourSwim() {
        return fluidNeedPerHourSwim;
    }

    public DoubleFilter fluidNeedPerHourSwim() {
        if (fluidNeedPerHourSwim == null) {
            fluidNeedPerHourSwim = new DoubleFilter();
        }
        return fluidNeedPerHourSwim;
    }

    public void setFluidNeedPerHourSwim(DoubleFilter fluidNeedPerHourSwim) {
        this.fluidNeedPerHourSwim = fluidNeedPerHourSwim;
    }

    public DoubleFilter getCarboNeedDuringBike() {
        return carboNeedDuringBike;
    }

    public DoubleFilter carboNeedDuringBike() {
        if (carboNeedDuringBike == null) {
            carboNeedDuringBike = new DoubleFilter();
        }
        return carboNeedDuringBike;
    }

    public void setCarboNeedDuringBike(DoubleFilter carboNeedDuringBike) {
        this.carboNeedDuringBike = carboNeedDuringBike;
    }

    public LongFilter getFluidNeedDuringBike() {
        return fluidNeedDuringBike;
    }

    public LongFilter fluidNeedDuringBike() {
        if (fluidNeedDuringBike == null) {
            fluidNeedDuringBike = new LongFilter();
        }
        return fluidNeedDuringBike;
    }

    public void setFluidNeedDuringBike(LongFilter fluidNeedDuringBike) {
        this.fluidNeedDuringBike = fluidNeedDuringBike;
    }

    public DoubleFilter getFluidNeedPerHourRun() {
        return fluidNeedPerHourRun;
    }

    public DoubleFilter fluidNeedPerHourRun() {
        if (fluidNeedPerHourRun == null) {
            fluidNeedPerHourRun = new DoubleFilter();
        }
        return fluidNeedPerHourRun;
    }

    public void setFluidNeedPerHourRun(DoubleFilter fluidNeedPerHourRun) {
        this.fluidNeedPerHourRun = fluidNeedPerHourRun;
    }

    public LongFilter getFluidNeedDuringRun() {
        return fluidNeedDuringRun;
    }

    public LongFilter fluidNeedDuringRun() {
        if (fluidNeedDuringRun == null) {
            fluidNeedDuringRun = new LongFilter();
        }
        return fluidNeedDuringRun;
    }

    public void setFluidNeedDuringRun(LongFilter fluidNeedDuringRun) {
        this.fluidNeedDuringRun = fluidNeedDuringRun;
    }

    public FloatFilter getWeightLossDuringRun() {
        return weightLossDuringRun;
    }

    public FloatFilter weightLossDuringRun() {
        if (weightLossDuringRun == null) {
            weightLossDuringRun = new FloatFilter();
        }
        return weightLossDuringRun;
    }

    public void setWeightLossDuringRun(FloatFilter weightLossDuringRun) {
        this.weightLossDuringRun = weightLossDuringRun;
    }

    public IntegerFilter getDiffCarboRun() {
        return diffCarboRun;
    }

    public IntegerFilter diffCarboRun() {
        if (diffCarboRun == null) {
            diffCarboRun = new IntegerFilter();
        }
        return diffCarboRun;
    }

    public void setDiffCarboRun(IntegerFilter diffCarboRun) {
        this.diffCarboRun = diffCarboRun;
    }

    public IntegerFilter getDiffMoisterRun() {
        return diffMoisterRun;
    }

    public IntegerFilter diffMoisterRun() {
        if (diffMoisterRun == null) {
            diffMoisterRun = new IntegerFilter();
        }
        return diffMoisterRun;
    }

    public void setDiffMoisterRun(IntegerFilter diffMoisterRun) {
        this.diffMoisterRun = diffMoisterRun;
    }

    public IntegerFilter getDifCarbo() {
        return difCarbo;
    }

    public IntegerFilter difCarbo() {
        if (difCarbo == null) {
            difCarbo = new IntegerFilter();
        }
        return difCarbo;
    }

    public void setDifCarbo(IntegerFilter difCarbo) {
        this.difCarbo = difCarbo;
    }

    public IntegerFilter getDifMoister() {
        return difMoister;
    }

    public IntegerFilter difMoister() {
        if (difMoister == null) {
            difMoister = new IntegerFilter();
        }
        return difMoister;
    }

    public void setDifMoister(IntegerFilter difMoister) {
        this.difMoister = difMoister;
    }

    public IntegerFilter getActFluidNeedBike() {
        return actFluidNeedBike;
    }

    public IntegerFilter actFluidNeedBike() {
        if (actFluidNeedBike == null) {
            actFluidNeedBike = new IntegerFilter();
        }
        return actFluidNeedBike;
    }

    public void setActFluidNeedBike(IntegerFilter actFluidNeedBike) {
        this.actFluidNeedBike = actFluidNeedBike;
    }

    public IntegerFilter getActFluidNeedRun() {
        return actFluidNeedRun;
    }

    public IntegerFilter actFluidNeedRun() {
        if (actFluidNeedRun == null) {
            actFluidNeedRun = new IntegerFilter();
        }
        return actFluidNeedRun;
    }

    public void setActFluidNeedRun(IntegerFilter actFluidNeedRun) {
        this.actFluidNeedRun = actFluidNeedRun;
    }

    public IntegerFilter getCarboNeedDuringBikeMin() {
        return carboNeedDuringBikeMin;
    }

    public IntegerFilter carboNeedDuringBikeMin() {
        if (carboNeedDuringBikeMin == null) {
            carboNeedDuringBikeMin = new IntegerFilter();
        }
        return carboNeedDuringBikeMin;
    }

    public void setCarboNeedDuringBikeMin(IntegerFilter carboNeedDuringBikeMin) {
        this.carboNeedDuringBikeMin = carboNeedDuringBikeMin;
    }

    public IntegerFilter getCarboNeedDuringBikeMax() {
        return carboNeedDuringBikeMax;
    }

    public IntegerFilter carboNeedDuringBikeMax() {
        if (carboNeedDuringBikeMax == null) {
            carboNeedDuringBikeMax = new IntegerFilter();
        }
        return carboNeedDuringBikeMax;
    }

    public void setCarboNeedDuringBikeMax(IntegerFilter carboNeedDuringBikeMax) {
        this.carboNeedDuringBikeMax = carboNeedDuringBikeMax;
    }

    public IntegerFilter getCarboNeedDuringRunMin() {
        return carboNeedDuringRunMin;
    }

    public IntegerFilter carboNeedDuringRunMin() {
        if (carboNeedDuringRunMin == null) {
            carboNeedDuringRunMin = new IntegerFilter();
        }
        return carboNeedDuringRunMin;
    }

    public void setCarboNeedDuringRunMin(IntegerFilter carboNeedDuringRunMin) {
        this.carboNeedDuringRunMin = carboNeedDuringRunMin;
    }

    public IntegerFilter getCarboNeedDuringRunMax() {
        return carboNeedDuringRunMax;
    }

    public IntegerFilter carboNeedDuringRunMax() {
        if (carboNeedDuringRunMax == null) {
            carboNeedDuringRunMax = new IntegerFilter();
        }
        return carboNeedDuringRunMax;
    }

    public void setCarboNeedDuringRunMax(IntegerFilter carboNeedDuringRunMax) {
        this.carboNeedDuringRunMax = carboNeedDuringRunMax;
    }

    public IntegerFilter getStartGel() {
        return startGel;
    }

    public IntegerFilter startGel() {
        if (startGel == null) {
            startGel = new IntegerFilter();
        }
        return startGel;
    }

    public void setStartGel(IntegerFilter startGel) {
        this.startGel = startGel;
    }

    public IntegerFilter getStartDrank() {
        return startDrank;
    }

    public IntegerFilter startDrank() {
        if (startDrank == null) {
            startDrank = new IntegerFilter();
        }
        return startDrank;
    }

    public void setStartDrank(IntegerFilter startDrank) {
        this.startDrank = startDrank;
    }

    public LongFilter getRaceId() {
        return raceId;
    }

    public LongFilter raceId() {
        if (raceId == null) {
            raceId = new LongFilter();
        }
        return raceId;
    }

    public void setRaceId(LongFilter raceId) {
        this.raceId = raceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RacePlanFormCriteria that = (RacePlanFormCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comp, that.comp) &&
            Objects.equals(name, that.name) &&
            Objects.equals(selectedOrgGelQuery, that.selectedOrgGelQuery) &&
            Objects.equals(selectedOrgDrankQuery, that.selectedOrgDrankQuery) &&
            Objects.equals(orsBeforeStart, that.orsBeforeStart) &&
            Objects.equals(drinkCarbo, that.drinkCarbo) &&
            Objects.equals(gelCarbo, that.gelCarbo) &&
            Objects.equals(drinkOrgCarbo, that.drinkOrgCarbo) &&
            Objects.equals(gelOrgCarbo, that.gelOrgCarbo) &&
            Objects.equals(sportDrinkOrgBike, that.sportDrinkOrgBike) &&
            Objects.equals(waterOrgBike, that.waterOrgBike) &&
            Objects.equals(gelsOrgBike, that.gelsOrgBike) &&
            Objects.equals(sportDrinkOrgRun, that.sportDrinkOrgRun) &&
            Objects.equals(waterOrgRun, that.waterOrgRun) &&
            Objects.equals(gelsOrgRun, that.gelsOrgRun) &&
            Objects.equals(sportDrinkToTakeBike, that.sportDrinkToTakeBike) &&
            Objects.equals(waterToTakeBike, that.waterToTakeBike) &&
            Objects.equals(extraWaterToTakeBike, that.extraWaterToTakeBike) &&
            Objects.equals(orsToTakeBike, that.orsToTakeBike) &&
            Objects.equals(gelsToTakeBike, that.gelsToTakeBike) &&
            Objects.equals(sportDrinkToTakeRun, that.sportDrinkToTakeRun) &&
            Objects.equals(waterToTakeRun, that.waterToTakeRun) &&
            Objects.equals(extraWaterToTakeRun, that.extraWaterToTakeRun) &&
            Objects.equals(orsToTakeRun, that.orsToTakeRun) &&
            Objects.equals(gelsToTakeRun, that.gelsToTakeRun) &&
            Objects.equals(weightLossDuringBike, that.weightLossDuringBike) &&
            Objects.equals(carboNeedDuringRun, that.carboNeedDuringRun) &&
            Objects.equals(fluidNeedPerHourBike, that.fluidNeedPerHourBike) &&
            Objects.equals(fluidNeedPerHourSwim, that.fluidNeedPerHourSwim) &&
            Objects.equals(carboNeedDuringBike, that.carboNeedDuringBike) &&
            Objects.equals(fluidNeedDuringBike, that.fluidNeedDuringBike) &&
            Objects.equals(fluidNeedPerHourRun, that.fluidNeedPerHourRun) &&
            Objects.equals(fluidNeedDuringRun, that.fluidNeedDuringRun) &&
            Objects.equals(weightLossDuringRun, that.weightLossDuringRun) &&
            Objects.equals(diffCarboRun, that.diffCarboRun) &&
            Objects.equals(diffMoisterRun, that.diffMoisterRun) &&
            Objects.equals(difCarbo, that.difCarbo) &&
            Objects.equals(difMoister, that.difMoister) &&
            Objects.equals(actFluidNeedBike, that.actFluidNeedBike) &&
            Objects.equals(actFluidNeedRun, that.actFluidNeedRun) &&
            Objects.equals(carboNeedDuringBikeMin, that.carboNeedDuringBikeMin) &&
            Objects.equals(carboNeedDuringBikeMax, that.carboNeedDuringBikeMax) &&
            Objects.equals(carboNeedDuringRunMin, that.carboNeedDuringRunMin) &&
            Objects.equals(carboNeedDuringRunMax, that.carboNeedDuringRunMax) &&
            Objects.equals(startGel, that.startGel) &&
            Objects.equals(startDrank, that.startDrank) &&
            Objects.equals(raceId, that.raceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            comp,
            name,
            selectedOrgGelQuery,
            selectedOrgDrankQuery,
            orsBeforeStart,
            drinkCarbo,
            gelCarbo,
            drinkOrgCarbo,
            gelOrgCarbo,
            sportDrinkOrgBike,
            waterOrgBike,
            gelsOrgBike,
            sportDrinkOrgRun,
            waterOrgRun,
            gelsOrgRun,
            sportDrinkToTakeBike,
            waterToTakeBike,
            extraWaterToTakeBike,
            orsToTakeBike,
            gelsToTakeBike,
            sportDrinkToTakeRun,
            waterToTakeRun,
            extraWaterToTakeRun,
            orsToTakeRun,
            gelsToTakeRun,
            weightLossDuringBike,
            carboNeedDuringRun,
            fluidNeedPerHourBike,
            fluidNeedPerHourSwim,
            carboNeedDuringBike,
            fluidNeedDuringBike,
            fluidNeedPerHourRun,
            fluidNeedDuringRun,
            weightLossDuringRun,
            diffCarboRun,
            diffMoisterRun,
            difCarbo,
            difMoister,
            actFluidNeedBike,
            actFluidNeedRun,
            carboNeedDuringBikeMin,
            carboNeedDuringBikeMax,
            carboNeedDuringRunMin,
            carboNeedDuringRunMax,
            startGel,
            startDrank,
            raceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RacePlanFormCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comp != null ? "comp=" + comp + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (selectedOrgGelQuery != null ? "selectedOrgGelQuery=" + selectedOrgGelQuery + ", " : "") +
            (selectedOrgDrankQuery != null ? "selectedOrgDrankQuery=" + selectedOrgDrankQuery + ", " : "") +
            (orsBeforeStart != null ? "orsBeforeStart=" + orsBeforeStart + ", " : "") +
            (drinkCarbo != null ? "drinkCarbo=" + drinkCarbo + ", " : "") +
            (gelCarbo != null ? "gelCarbo=" + gelCarbo + ", " : "") +
            (drinkOrgCarbo != null ? "drinkOrgCarbo=" + drinkOrgCarbo + ", " : "") +
            (gelOrgCarbo != null ? "gelOrgCarbo=" + gelOrgCarbo + ", " : "") +
            (sportDrinkOrgBike != null ? "sportDrinkOrgBike=" + sportDrinkOrgBike + ", " : "") +
            (waterOrgBike != null ? "waterOrgBike=" + waterOrgBike + ", " : "") +
            (gelsOrgBike != null ? "gelsOrgBike=" + gelsOrgBike + ", " : "") +
            (sportDrinkOrgRun != null ? "sportDrinkOrgRun=" + sportDrinkOrgRun + ", " : "") +
            (waterOrgRun != null ? "waterOrgRun=" + waterOrgRun + ", " : "") +
            (gelsOrgRun != null ? "gelsOrgRun=" + gelsOrgRun + ", " : "") +
            (sportDrinkToTakeBike != null ? "sportDrinkToTakeBike=" + sportDrinkToTakeBike + ", " : "") +
            (waterToTakeBike != null ? "waterToTakeBike=" + waterToTakeBike + ", " : "") +
            (extraWaterToTakeBike != null ? "extraWaterToTakeBike=" + extraWaterToTakeBike + ", " : "") +
            (orsToTakeBike != null ? "orsToTakeBike=" + orsToTakeBike + ", " : "") +
            (gelsToTakeBike != null ? "gelsToTakeBike=" + gelsToTakeBike + ", " : "") +
            (sportDrinkToTakeRun != null ? "sportDrinkToTakeRun=" + sportDrinkToTakeRun + ", " : "") +
            (waterToTakeRun != null ? "waterToTakeRun=" + waterToTakeRun + ", " : "") +
            (extraWaterToTakeRun != null ? "extraWaterToTakeRun=" + extraWaterToTakeRun + ", " : "") +
            (orsToTakeRun != null ? "orsToTakeRun=" + orsToTakeRun + ", " : "") +
            (gelsToTakeRun != null ? "gelsToTakeRun=" + gelsToTakeRun + ", " : "") +
            (weightLossDuringBike != null ? "weightLossDuringBike=" + weightLossDuringBike + ", " : "") +
            (carboNeedDuringRun != null ? "carboNeedDuringRun=" + carboNeedDuringRun + ", " : "") +
            (fluidNeedPerHourBike != null ? "fluidNeedPerHourBike=" + fluidNeedPerHourBike + ", " : "") +
            (fluidNeedPerHourSwim != null ? "fluidNeedPerHourSwim=" + fluidNeedPerHourSwim + ", " : "") +
            (carboNeedDuringBike != null ? "carboNeedDuringBike=" + carboNeedDuringBike + ", " : "") +
            (fluidNeedDuringBike != null ? "fluidNeedDuringBike=" + fluidNeedDuringBike + ", " : "") +
            (fluidNeedPerHourRun != null ? "fluidNeedPerHourRun=" + fluidNeedPerHourRun + ", " : "") +
            (fluidNeedDuringRun != null ? "fluidNeedDuringRun=" + fluidNeedDuringRun + ", " : "") +
            (weightLossDuringRun != null ? "weightLossDuringRun=" + weightLossDuringRun + ", " : "") +
            (diffCarboRun != null ? "diffCarboRun=" + diffCarboRun + ", " : "") +
            (diffMoisterRun != null ? "diffMoisterRun=" + diffMoisterRun + ", " : "") +
            (difCarbo != null ? "difCarbo=" + difCarbo + ", " : "") +
            (difMoister != null ? "difMoister=" + difMoister + ", " : "") +
            (actFluidNeedBike != null ? "actFluidNeedBike=" + actFluidNeedBike + ", " : "") +
            (actFluidNeedRun != null ? "actFluidNeedRun=" + actFluidNeedRun + ", " : "") +
            (carboNeedDuringBikeMin != null ? "carboNeedDuringBikeMin=" + carboNeedDuringBikeMin + ", " : "") +
            (carboNeedDuringBikeMax != null ? "carboNeedDuringBikeMax=" + carboNeedDuringBikeMax + ", " : "") +
            (carboNeedDuringRunMin != null ? "carboNeedDuringRunMin=" + carboNeedDuringRunMin + ", " : "") +
            (carboNeedDuringRunMax != null ? "carboNeedDuringRunMax=" + carboNeedDuringRunMax + ", " : "") +
            (startGel != null ? "startGel=" + startGel + ", " : "") +
            (startDrank != null ? "startDrank=" + startDrank + ", " : "") +
            (raceId != null ? "raceId=" + raceId + ", " : "") +
            "}";
    }
}
