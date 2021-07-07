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
 * Criteria class for the {@link nl.templify.fuel2endure.domain.Athlete} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.AthleteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /athletes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AthleteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter middleName;

    private IntegerFilter length;

    private FloatFilter weight;

    private StringFilter status;

    private LongFilter fuelSettingsId;

    private LongFilter internalUserId;

    private LongFilter raceId;

    private LongFilter trainingId;

    private LongFilter paymentId;

    private LongFilter voucherId;

    public AthleteCriteria() {}

    public AthleteCriteria(AthleteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.weight = other.weight == null ? null : other.weight.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.fuelSettingsId = other.fuelSettingsId == null ? null : other.fuelSettingsId.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.raceId = other.raceId == null ? null : other.raceId.copy();
        this.trainingId = other.trainingId == null ? null : other.trainingId.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.voucherId = other.voucherId == null ? null : other.voucherId.copy();
    }

    @Override
    public AthleteCriteria copy() {
        return new AthleteCriteria(this);
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

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
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

    public FloatFilter getWeight() {
        return weight;
    }

    public FloatFilter weight() {
        if (weight == null) {
            weight = new FloatFilter();
        }
        return weight;
    }

    public void setWeight(FloatFilter weight) {
        this.weight = weight;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getFuelSettingsId() {
        return fuelSettingsId;
    }

    public LongFilter fuelSettingsId() {
        if (fuelSettingsId == null) {
            fuelSettingsId = new LongFilter();
        }
        return fuelSettingsId;
    }

    public void setFuelSettingsId(LongFilter fuelSettingsId) {
        this.fuelSettingsId = fuelSettingsId;
    }

    public LongFilter getInternalUserId() {
        return internalUserId;
    }

    public LongFilter internalUserId() {
        if (internalUserId == null) {
            internalUserId = new LongFilter();
        }
        return internalUserId;
    }

    public void setInternalUserId(LongFilter internalUserId) {
        this.internalUserId = internalUserId;
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

    public LongFilter getTrainingId() {
        return trainingId;
    }

    public LongFilter trainingId() {
        if (trainingId == null) {
            trainingId = new LongFilter();
        }
        return trainingId;
    }

    public void setTrainingId(LongFilter trainingId) {
        this.trainingId = trainingId;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public LongFilter paymentId() {
        if (paymentId == null) {
            paymentId = new LongFilter();
        }
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getVoucherId() {
        return voucherId;
    }

    public LongFilter voucherId() {
        if (voucherId == null) {
            voucherId = new LongFilter();
        }
        return voucherId;
    }

    public void setVoucherId(LongFilter voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AthleteCriteria that = (AthleteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(length, that.length) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(status, that.status) &&
            Objects.equals(fuelSettingsId, that.fuelSettingsId) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(raceId, that.raceId) &&
            Objects.equals(trainingId, that.trainingId) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(voucherId, that.voucherId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            middleName,
            length,
            weight,
            status,
            fuelSettingsId,
            internalUserId,
            raceId,
            trainingId,
            paymentId,
            voucherId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AthleteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (length != null ? "length=" + length + ", " : "") +
            (weight != null ? "weight=" + weight + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (fuelSettingsId != null ? "fuelSettingsId=" + fuelSettingsId + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (raceId != null ? "raceId=" + raceId + ", " : "") +
            (trainingId != null ? "trainingId=" + trainingId + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (voucherId != null ? "voucherId=" + voucherId + ", " : "") +
            "}";
    }
}
