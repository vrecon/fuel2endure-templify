package nl.templify.fuel2endure.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link nl.templify.fuel2endure.domain.Race} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.RaceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /races?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RaceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private StringFilter name;

    private StringFilter logging;

    private IntegerFilter weight;

    private IntegerFilter length;

    private IntegerFilter temperature;

    private IntegerFilter comp;

    private IntegerFilter swimDuration;

    private IntegerFilter bikeDuration;

    private IntegerFilter runDuration;

    private IntegerFilter gelCarbo;

    private IntegerFilter drinkCarbo;

    private IntegerFilter drinkOrgCarbo;

    private IntegerFilter gelOrgCarbo;

    private IntegerFilter gelsbike;

    private IntegerFilter gelsrun;

    private BooleanFilter selectedOrgGelQuery;

    private BooleanFilter selectedOrgDrankQuery;

    private IntegerFilter sportDrinkOrgBike;

    private IntegerFilter waterOrgBike;

    private IntegerFilter gelsOrgBike;

    private IntegerFilter sportDrinkOrgRun;

    private IntegerFilter waterOrgRun;

    private IntegerFilter gelsOrgRun;

    private IntegerFilter orsBeforeStart;

    private IntegerFilter sportDrinkToTakeBike;

    private IntegerFilter waterToTakeBike;

    private IntegerFilter extraWaterToTakeBike;

    private IntegerFilter orsToTakeBike;

    private IntegerFilter gelsToTakeBike;

    private IntegerFilter sportDrinkToTakeRun;

    private IntegerFilter waterToTakeRun;

    private IntegerFilter extraWaterToTakeRun;

    private IntegerFilter orsToTakeRun;

    private IntegerFilter carboNeedDuringBikeMin;

    private IntegerFilter carboNeedDuringBikeMax;

    private IntegerFilter carboNeedDuringRunMin;

    private IntegerFilter carboNeedDuringRunMax;

    private IntegerFilter diffCarboRun;

    private IntegerFilter diffMoisterRun;

    private IntegerFilter difCarbo;

    private IntegerFilter difMoister;

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

    private IntegerFilter actFluidNeedBike;

    private IntegerFilter actFluidNeedRun;

    private LongFilter racePlanFormId;

    private LongFilter athleteId;

    public RaceCriteria() {}

    public RaceCriteria(RaceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.logging = other.logging == null ? null : other.logging.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.comp = other.comp == null ? null : other.comp.copy();
        this.swimDuration = other.swimDuration == null ? null : other.swimDuration.copy();
        this.bikeDuration = other.bikeDuration == null ? null : other.bikeDuration.copy();
        this.runDuration = other.runDuration == null ? null : other.runDuration.copy();
        this.gelCarbo = other.gelCarbo == null ? null : other.gelCarbo.copy();
        this.drinkCarbo = other.drinkCarbo == null ? null : other.drinkCarbo.copy();
        this.drinkOrgCarbo = other.drinkOrgCarbo == null ? null : other.drinkOrgCarbo.copy();
        this.gelOrgCarbo = other.gelOrgCarbo == null ? null : other.gelOrgCarbo.copy();
        this.gelsbike = other.gelsbike == null ? null : other.gelsbike.copy();
        this.gelsrun = other.gelsrun == null ? null : other.gelsrun.copy();
        this.selectedOrgGelQuery = other.selectedOrgGelQuery == null ? null : other.selectedOrgGelQuery.copy();
        this.selectedOrgDrankQuery = other.selectedOrgDrankQuery == null ? null : other.selectedOrgDrankQuery.copy();
        this.sportDrinkOrgBike = other.sportDrinkOrgBike == null ? null : other.sportDrinkOrgBike.copy();
        this.waterOrgBike = other.waterOrgBike == null ? null : other.waterOrgBike.copy();
        this.gelsOrgBike = other.gelsOrgBike == null ? null : other.gelsOrgBike.copy();
        this.sportDrinkOrgRun = other.sportDrinkOrgRun == null ? null : other.sportDrinkOrgRun.copy();
        this.waterOrgRun = other.waterOrgRun == null ? null : other.waterOrgRun.copy();
        this.gelsOrgRun = other.gelsOrgRun == null ? null : other.gelsOrgRun.copy();
        this.orsBeforeStart = other.orsBeforeStart == null ? null : other.orsBeforeStart.copy();
        this.sportDrinkToTakeBike = other.sportDrinkToTakeBike == null ? null : other.sportDrinkToTakeBike.copy();
        this.waterToTakeBike = other.waterToTakeBike == null ? null : other.waterToTakeBike.copy();
        this.extraWaterToTakeBike = other.extraWaterToTakeBike == null ? null : other.extraWaterToTakeBike.copy();
        this.orsToTakeBike = other.orsToTakeBike == null ? null : other.orsToTakeBike.copy();
        this.gelsToTakeBike = other.gelsToTakeBike == null ? null : other.gelsToTakeBike.copy();
        this.sportDrinkToTakeRun = other.sportDrinkToTakeRun == null ? null : other.sportDrinkToTakeRun.copy();
        this.waterToTakeRun = other.waterToTakeRun == null ? null : other.waterToTakeRun.copy();
        this.extraWaterToTakeRun = other.extraWaterToTakeRun == null ? null : other.extraWaterToTakeRun.copy();
        this.orsToTakeRun = other.orsToTakeRun == null ? null : other.orsToTakeRun.copy();
        this.carboNeedDuringBikeMin = other.carboNeedDuringBikeMin == null ? null : other.carboNeedDuringBikeMin.copy();
        this.carboNeedDuringBikeMax = other.carboNeedDuringBikeMax == null ? null : other.carboNeedDuringBikeMax.copy();
        this.carboNeedDuringRunMin = other.carboNeedDuringRunMin == null ? null : other.carboNeedDuringRunMin.copy();
        this.carboNeedDuringRunMax = other.carboNeedDuringRunMax == null ? null : other.carboNeedDuringRunMax.copy();
        this.diffCarboRun = other.diffCarboRun == null ? null : other.diffCarboRun.copy();
        this.diffMoisterRun = other.diffMoisterRun == null ? null : other.diffMoisterRun.copy();
        this.difCarbo = other.difCarbo == null ? null : other.difCarbo.copy();
        this.difMoister = other.difMoister == null ? null : other.difMoister.copy();
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
        this.actFluidNeedBike = other.actFluidNeedBike == null ? null : other.actFluidNeedBike.copy();
        this.actFluidNeedRun = other.actFluidNeedRun == null ? null : other.actFluidNeedRun.copy();
        this.racePlanFormId = other.racePlanFormId == null ? null : other.racePlanFormId.copy();
        this.athleteId = other.athleteId == null ? null : other.athleteId.copy();
    }

    @Override
    public RaceCriteria copy() {
        return new RaceCriteria(this);
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

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
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

    public StringFilter getLogging() {
        return logging;
    }

    public StringFilter logging() {
        if (logging == null) {
            logging = new StringFilter();
        }
        return logging;
    }

    public void setLogging(StringFilter logging) {
        this.logging = logging;
    }

    public IntegerFilter getWeight() {
        return weight;
    }

    public IntegerFilter weight() {
        if (weight == null) {
            weight = new IntegerFilter();
        }
        return weight;
    }

    public void setWeight(IntegerFilter weight) {
        this.weight = weight;
    }

    public IntegerFilter getLength() {
        return length;
    }

    public IntegerFilter length() {
        if (length == null) {
            length = new IntegerFilter();
        }
        return length;
    }

    public void setLength(IntegerFilter length) {
        this.length = length;
    }

    public IntegerFilter getTemperature() {
        return temperature;
    }

    public IntegerFilter temperature() {
        if (temperature == null) {
            temperature = new IntegerFilter();
        }
        return temperature;
    }

    public void setTemperature(IntegerFilter temperature) {
        this.temperature = temperature;
    }

    public IntegerFilter getComp() {
        return comp;
    }

    public IntegerFilter comp() {
        if (comp == null) {
            comp = new IntegerFilter();
        }
        return comp;
    }

    public void setComp(IntegerFilter comp) {
        this.comp = comp;
    }

    public IntegerFilter getSwimDuration() {
        return swimDuration;
    }

    public IntegerFilter swimDuration() {
        if (swimDuration == null) {
            swimDuration = new IntegerFilter();
        }
        return swimDuration;
    }

    public void setSwimDuration(IntegerFilter swimDuration) {
        this.swimDuration = swimDuration;
    }

    public IntegerFilter getBikeDuration() {
        return bikeDuration;
    }

    public IntegerFilter bikeDuration() {
        if (bikeDuration == null) {
            bikeDuration = new IntegerFilter();
        }
        return bikeDuration;
    }

    public void setBikeDuration(IntegerFilter bikeDuration) {
        this.bikeDuration = bikeDuration;
    }

    public IntegerFilter getRunDuration() {
        return runDuration;
    }

    public IntegerFilter runDuration() {
        if (runDuration == null) {
            runDuration = new IntegerFilter();
        }
        return runDuration;
    }

    public void setRunDuration(IntegerFilter runDuration) {
        this.runDuration = runDuration;
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

    public IntegerFilter getGelsbike() {
        return gelsbike;
    }

    public IntegerFilter gelsbike() {
        if (gelsbike == null) {
            gelsbike = new IntegerFilter();
        }
        return gelsbike;
    }

    public void setGelsbike(IntegerFilter gelsbike) {
        this.gelsbike = gelsbike;
    }

    public IntegerFilter getGelsrun() {
        return gelsrun;
    }

    public IntegerFilter gelsrun() {
        if (gelsrun == null) {
            gelsrun = new IntegerFilter();
        }
        return gelsrun;
    }

    public void setGelsrun(IntegerFilter gelsrun) {
        this.gelsrun = gelsrun;
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

    public LongFilter getRacePlanFormId() {
        return racePlanFormId;
    }

    public LongFilter racePlanFormId() {
        if (racePlanFormId == null) {
            racePlanFormId = new LongFilter();
        }
        return racePlanFormId;
    }

    public void setRacePlanFormId(LongFilter racePlanFormId) {
        this.racePlanFormId = racePlanFormId;
    }

    public LongFilter getAthleteId() {
        return athleteId;
    }

    public LongFilter athleteId() {
        if (athleteId == null) {
            athleteId = new LongFilter();
        }
        return athleteId;
    }

    public void setAthleteId(LongFilter athleteId) {
        this.athleteId = athleteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RaceCriteria that = (RaceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(name, that.name) &&
            Objects.equals(logging, that.logging) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(length, that.length) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(comp, that.comp) &&
            Objects.equals(swimDuration, that.swimDuration) &&
            Objects.equals(bikeDuration, that.bikeDuration) &&
            Objects.equals(runDuration, that.runDuration) &&
            Objects.equals(gelCarbo, that.gelCarbo) &&
            Objects.equals(drinkCarbo, that.drinkCarbo) &&
            Objects.equals(drinkOrgCarbo, that.drinkOrgCarbo) &&
            Objects.equals(gelOrgCarbo, that.gelOrgCarbo) &&
            Objects.equals(gelsbike, that.gelsbike) &&
            Objects.equals(gelsrun, that.gelsrun) &&
            Objects.equals(selectedOrgGelQuery, that.selectedOrgGelQuery) &&
            Objects.equals(selectedOrgDrankQuery, that.selectedOrgDrankQuery) &&
            Objects.equals(sportDrinkOrgBike, that.sportDrinkOrgBike) &&
            Objects.equals(waterOrgBike, that.waterOrgBike) &&
            Objects.equals(gelsOrgBike, that.gelsOrgBike) &&
            Objects.equals(sportDrinkOrgRun, that.sportDrinkOrgRun) &&
            Objects.equals(waterOrgRun, that.waterOrgRun) &&
            Objects.equals(gelsOrgRun, that.gelsOrgRun) &&
            Objects.equals(orsBeforeStart, that.orsBeforeStart) &&
            Objects.equals(sportDrinkToTakeBike, that.sportDrinkToTakeBike) &&
            Objects.equals(waterToTakeBike, that.waterToTakeBike) &&
            Objects.equals(extraWaterToTakeBike, that.extraWaterToTakeBike) &&
            Objects.equals(orsToTakeBike, that.orsToTakeBike) &&
            Objects.equals(gelsToTakeBike, that.gelsToTakeBike) &&
            Objects.equals(sportDrinkToTakeRun, that.sportDrinkToTakeRun) &&
            Objects.equals(waterToTakeRun, that.waterToTakeRun) &&
            Objects.equals(extraWaterToTakeRun, that.extraWaterToTakeRun) &&
            Objects.equals(orsToTakeRun, that.orsToTakeRun) &&
            Objects.equals(carboNeedDuringBikeMin, that.carboNeedDuringBikeMin) &&
            Objects.equals(carboNeedDuringBikeMax, that.carboNeedDuringBikeMax) &&
            Objects.equals(carboNeedDuringRunMin, that.carboNeedDuringRunMin) &&
            Objects.equals(carboNeedDuringRunMax, that.carboNeedDuringRunMax) &&
            Objects.equals(diffCarboRun, that.diffCarboRun) &&
            Objects.equals(diffMoisterRun, that.diffMoisterRun) &&
            Objects.equals(difCarbo, that.difCarbo) &&
            Objects.equals(difMoister, that.difMoister) &&
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
            Objects.equals(actFluidNeedBike, that.actFluidNeedBike) &&
            Objects.equals(actFluidNeedRun, that.actFluidNeedRun) &&
            Objects.equals(racePlanFormId, that.racePlanFormId) &&
            Objects.equals(athleteId, that.athleteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            name,
            logging,
            weight,
            length,
            temperature,
            comp,
            swimDuration,
            bikeDuration,
            runDuration,
            gelCarbo,
            drinkCarbo,
            drinkOrgCarbo,
            gelOrgCarbo,
            gelsbike,
            gelsrun,
            selectedOrgGelQuery,
            selectedOrgDrankQuery,
            sportDrinkOrgBike,
            waterOrgBike,
            gelsOrgBike,
            sportDrinkOrgRun,
            waterOrgRun,
            gelsOrgRun,
            orsBeforeStart,
            sportDrinkToTakeBike,
            waterToTakeBike,
            extraWaterToTakeBike,
            orsToTakeBike,
            gelsToTakeBike,
            sportDrinkToTakeRun,
            waterToTakeRun,
            extraWaterToTakeRun,
            orsToTakeRun,
            carboNeedDuringBikeMin,
            carboNeedDuringBikeMax,
            carboNeedDuringRunMin,
            carboNeedDuringRunMax,
            diffCarboRun,
            diffMoisterRun,
            difCarbo,
            difMoister,
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
            actFluidNeedBike,
            actFluidNeedRun,
            racePlanFormId,
            athleteId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (logging != null ? "logging=" + logging + ", " : "") +
            (weight != null ? "weight=" + weight + ", " : "") +
            (length != null ? "length=" + length + ", " : "") +
            (temperature != null ? "temperature=" + temperature + ", " : "") +
            (comp != null ? "comp=" + comp + ", " : "") +
            (swimDuration != null ? "swimDuration=" + swimDuration + ", " : "") +
            (bikeDuration != null ? "bikeDuration=" + bikeDuration + ", " : "") +
            (runDuration != null ? "runDuration=" + runDuration + ", " : "") +
            (gelCarbo != null ? "gelCarbo=" + gelCarbo + ", " : "") +
            (drinkCarbo != null ? "drinkCarbo=" + drinkCarbo + ", " : "") +
            (drinkOrgCarbo != null ? "drinkOrgCarbo=" + drinkOrgCarbo + ", " : "") +
            (gelOrgCarbo != null ? "gelOrgCarbo=" + gelOrgCarbo + ", " : "") +
            (gelsbike != null ? "gelsbike=" + gelsbike + ", " : "") +
            (gelsrun != null ? "gelsrun=" + gelsrun + ", " : "") +
            (selectedOrgGelQuery != null ? "selectedOrgGelQuery=" + selectedOrgGelQuery + ", " : "") +
            (selectedOrgDrankQuery != null ? "selectedOrgDrankQuery=" + selectedOrgDrankQuery + ", " : "") +
            (sportDrinkOrgBike != null ? "sportDrinkOrgBike=" + sportDrinkOrgBike + ", " : "") +
            (waterOrgBike != null ? "waterOrgBike=" + waterOrgBike + ", " : "") +
            (gelsOrgBike != null ? "gelsOrgBike=" + gelsOrgBike + ", " : "") +
            (sportDrinkOrgRun != null ? "sportDrinkOrgRun=" + sportDrinkOrgRun + ", " : "") +
            (waterOrgRun != null ? "waterOrgRun=" + waterOrgRun + ", " : "") +
            (gelsOrgRun != null ? "gelsOrgRun=" + gelsOrgRun + ", " : "") +
            (orsBeforeStart != null ? "orsBeforeStart=" + orsBeforeStart + ", " : "") +
            (sportDrinkToTakeBike != null ? "sportDrinkToTakeBike=" + sportDrinkToTakeBike + ", " : "") +
            (waterToTakeBike != null ? "waterToTakeBike=" + waterToTakeBike + ", " : "") +
            (extraWaterToTakeBike != null ? "extraWaterToTakeBike=" + extraWaterToTakeBike + ", " : "") +
            (orsToTakeBike != null ? "orsToTakeBike=" + orsToTakeBike + ", " : "") +
            (gelsToTakeBike != null ? "gelsToTakeBike=" + gelsToTakeBike + ", " : "") +
            (sportDrinkToTakeRun != null ? "sportDrinkToTakeRun=" + sportDrinkToTakeRun + ", " : "") +
            (waterToTakeRun != null ? "waterToTakeRun=" + waterToTakeRun + ", " : "") +
            (extraWaterToTakeRun != null ? "extraWaterToTakeRun=" + extraWaterToTakeRun + ", " : "") +
            (orsToTakeRun != null ? "orsToTakeRun=" + orsToTakeRun + ", " : "") +
            (carboNeedDuringBikeMin != null ? "carboNeedDuringBikeMin=" + carboNeedDuringBikeMin + ", " : "") +
            (carboNeedDuringBikeMax != null ? "carboNeedDuringBikeMax=" + carboNeedDuringBikeMax + ", " : "") +
            (carboNeedDuringRunMin != null ? "carboNeedDuringRunMin=" + carboNeedDuringRunMin + ", " : "") +
            (carboNeedDuringRunMax != null ? "carboNeedDuringRunMax=" + carboNeedDuringRunMax + ", " : "") +
            (diffCarboRun != null ? "diffCarboRun=" + diffCarboRun + ", " : "") +
            (diffMoisterRun != null ? "diffMoisterRun=" + diffMoisterRun + ", " : "") +
            (difCarbo != null ? "difCarbo=" + difCarbo + ", " : "") +
            (difMoister != null ? "difMoister=" + difMoister + ", " : "") +
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
            (actFluidNeedBike != null ? "actFluidNeedBike=" + actFluidNeedBike + ", " : "") +
            (actFluidNeedRun != null ? "actFluidNeedRun=" + actFluidNeedRun + ", " : "") +
            (racePlanFormId != null ? "racePlanFormId=" + racePlanFormId + ", " : "") +
            (athleteId != null ? "athleteId=" + athleteId + ", " : "") +
            "}";
    }
}
