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
 * Criteria class for the {@link nl.templify.fuel2endure.domain.Training} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private StringFilter trainingTypeCode;

    private IntegerFilter duration;

    private IntegerFilter trainingIntensityCode;

    private IntegerFilter temperature;

    private FloatFilter weightBefore;

    private FloatFilter weightAfter;

    private IntegerFilter drunk;

    private IntegerFilter eaten;

    private FloatFilter moistureLossPercentage;

    private FloatFilter moistureLossPerHour;

    private FloatFilter defaultMoisterLossPerHour;

    private FloatFilter deltaMoisterLossPerHour;

    private BooleanFilter excluded;

    private StringFilter comments;

    private IntegerFilter carboDrank;

    private LongFilter athleteId;

    public TrainingCriteria() {}

    public TrainingCriteria(TrainingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.trainingTypeCode = other.trainingTypeCode == null ? null : other.trainingTypeCode.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.trainingIntensityCode = other.trainingIntensityCode == null ? null : other.trainingIntensityCode.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.weightBefore = other.weightBefore == null ? null : other.weightBefore.copy();
        this.weightAfter = other.weightAfter == null ? null : other.weightAfter.copy();
        this.drunk = other.drunk == null ? null : other.drunk.copy();
        this.eaten = other.eaten == null ? null : other.eaten.copy();
        this.moistureLossPercentage = other.moistureLossPercentage == null ? null : other.moistureLossPercentage.copy();
        this.moistureLossPerHour = other.moistureLossPerHour == null ? null : other.moistureLossPerHour.copy();
        this.defaultMoisterLossPerHour = other.defaultMoisterLossPerHour == null ? null : other.defaultMoisterLossPerHour.copy();
        this.deltaMoisterLossPerHour = other.deltaMoisterLossPerHour == null ? null : other.deltaMoisterLossPerHour.copy();
        this.excluded = other.excluded == null ? null : other.excluded.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.carboDrank = other.carboDrank == null ? null : other.carboDrank.copy();
        this.athleteId = other.athleteId == null ? null : other.athleteId.copy();
    }

    @Override
    public TrainingCriteria copy() {
        return new TrainingCriteria(this);
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

    public StringFilter getTrainingTypeCode() {
        return trainingTypeCode;
    }

    public StringFilter trainingTypeCode() {
        if (trainingTypeCode == null) {
            trainingTypeCode = new StringFilter();
        }
        return trainingTypeCode;
    }

    public void setTrainingTypeCode(StringFilter trainingTypeCode) {
        this.trainingTypeCode = trainingTypeCode;
    }

    public IntegerFilter getDuration() {
        return duration;
    }

    public IntegerFilter duration() {
        if (duration == null) {
            duration = new IntegerFilter();
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public IntegerFilter getTrainingIntensityCode() {
        return trainingIntensityCode;
    }

    public IntegerFilter trainingIntensityCode() {
        if (trainingIntensityCode == null) {
            trainingIntensityCode = new IntegerFilter();
        }
        return trainingIntensityCode;
    }

    public void setTrainingIntensityCode(IntegerFilter trainingIntensityCode) {
        this.trainingIntensityCode = trainingIntensityCode;
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

    public FloatFilter getWeightBefore() {
        return weightBefore;
    }

    public FloatFilter weightBefore() {
        if (weightBefore == null) {
            weightBefore = new FloatFilter();
        }
        return weightBefore;
    }

    public void setWeightBefore(FloatFilter weightBefore) {
        this.weightBefore = weightBefore;
    }

    public FloatFilter getWeightAfter() {
        return weightAfter;
    }

    public FloatFilter weightAfter() {
        if (weightAfter == null) {
            weightAfter = new FloatFilter();
        }
        return weightAfter;
    }

    public void setWeightAfter(FloatFilter weightAfter) {
        this.weightAfter = weightAfter;
    }

    public IntegerFilter getDrunk() {
        return drunk;
    }

    public IntegerFilter drunk() {
        if (drunk == null) {
            drunk = new IntegerFilter();
        }
        return drunk;
    }

    public void setDrunk(IntegerFilter drunk) {
        this.drunk = drunk;
    }

    public IntegerFilter getEaten() {
        return eaten;
    }

    public IntegerFilter eaten() {
        if (eaten == null) {
            eaten = new IntegerFilter();
        }
        return eaten;
    }

    public void setEaten(IntegerFilter eaten) {
        this.eaten = eaten;
    }

    public FloatFilter getMoistureLossPercentage() {
        return moistureLossPercentage;
    }

    public FloatFilter moistureLossPercentage() {
        if (moistureLossPercentage == null) {
            moistureLossPercentage = new FloatFilter();
        }
        return moistureLossPercentage;
    }

    public void setMoistureLossPercentage(FloatFilter moistureLossPercentage) {
        this.moistureLossPercentage = moistureLossPercentage;
    }

    public FloatFilter getMoistureLossPerHour() {
        return moistureLossPerHour;
    }

    public FloatFilter moistureLossPerHour() {
        if (moistureLossPerHour == null) {
            moistureLossPerHour = new FloatFilter();
        }
        return moistureLossPerHour;
    }

    public void setMoistureLossPerHour(FloatFilter moistureLossPerHour) {
        this.moistureLossPerHour = moistureLossPerHour;
    }

    public FloatFilter getDefaultMoisterLossPerHour() {
        return defaultMoisterLossPerHour;
    }

    public FloatFilter defaultMoisterLossPerHour() {
        if (defaultMoisterLossPerHour == null) {
            defaultMoisterLossPerHour = new FloatFilter();
        }
        return defaultMoisterLossPerHour;
    }

    public void setDefaultMoisterLossPerHour(FloatFilter defaultMoisterLossPerHour) {
        this.defaultMoisterLossPerHour = defaultMoisterLossPerHour;
    }

    public FloatFilter getDeltaMoisterLossPerHour() {
        return deltaMoisterLossPerHour;
    }

    public FloatFilter deltaMoisterLossPerHour() {
        if (deltaMoisterLossPerHour == null) {
            deltaMoisterLossPerHour = new FloatFilter();
        }
        return deltaMoisterLossPerHour;
    }

    public void setDeltaMoisterLossPerHour(FloatFilter deltaMoisterLossPerHour) {
        this.deltaMoisterLossPerHour = deltaMoisterLossPerHour;
    }

    public BooleanFilter getExcluded() {
        return excluded;
    }

    public BooleanFilter excluded() {
        if (excluded == null) {
            excluded = new BooleanFilter();
        }
        return excluded;
    }

    public void setExcluded(BooleanFilter excluded) {
        this.excluded = excluded;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public IntegerFilter getCarboDrank() {
        return carboDrank;
    }

    public IntegerFilter carboDrank() {
        if (carboDrank == null) {
            carboDrank = new IntegerFilter();
        }
        return carboDrank;
    }

    public void setCarboDrank(IntegerFilter carboDrank) {
        this.carboDrank = carboDrank;
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
        final TrainingCriteria that = (TrainingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(trainingTypeCode, that.trainingTypeCode) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(trainingIntensityCode, that.trainingIntensityCode) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(weightBefore, that.weightBefore) &&
            Objects.equals(weightAfter, that.weightAfter) &&
            Objects.equals(drunk, that.drunk) &&
            Objects.equals(eaten, that.eaten) &&
            Objects.equals(moistureLossPercentage, that.moistureLossPercentage) &&
            Objects.equals(moistureLossPerHour, that.moistureLossPerHour) &&
            Objects.equals(defaultMoisterLossPerHour, that.defaultMoisterLossPerHour) &&
            Objects.equals(deltaMoisterLossPerHour, that.deltaMoisterLossPerHour) &&
            Objects.equals(excluded, that.excluded) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(carboDrank, that.carboDrank) &&
            Objects.equals(athleteId, that.athleteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            date,
            trainingTypeCode,
            duration,
            trainingIntensityCode,
            temperature,
            weightBefore,
            weightAfter,
            drunk,
            eaten,
            moistureLossPercentage,
            moistureLossPerHour,
            defaultMoisterLossPerHour,
            deltaMoisterLossPerHour,
            excluded,
            comments,
            carboDrank,
            athleteId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (trainingTypeCode != null ? "trainingTypeCode=" + trainingTypeCode + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (trainingIntensityCode != null ? "trainingIntensityCode=" + trainingIntensityCode + ", " : "") +
            (temperature != null ? "temperature=" + temperature + ", " : "") +
            (weightBefore != null ? "weightBefore=" + weightBefore + ", " : "") +
            (weightAfter != null ? "weightAfter=" + weightAfter + ", " : "") +
            (drunk != null ? "drunk=" + drunk + ", " : "") +
            (eaten != null ? "eaten=" + eaten + ", " : "") +
            (moistureLossPercentage != null ? "moistureLossPercentage=" + moistureLossPercentage + ", " : "") +
            (moistureLossPerHour != null ? "moistureLossPerHour=" + moistureLossPerHour + ", " : "") +
            (defaultMoisterLossPerHour != null ? "defaultMoisterLossPerHour=" + defaultMoisterLossPerHour + ", " : "") +
            (deltaMoisterLossPerHour != null ? "deltaMoisterLossPerHour=" + deltaMoisterLossPerHour + ", " : "") +
            (excluded != null ? "excluded=" + excluded + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (carboDrank != null ? "carboDrank=" + carboDrank + ", " : "") +
            (athleteId != null ? "athleteId=" + athleteId + ", " : "") +
            "}";
    }
}
