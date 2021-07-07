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
 * Criteria class for the {@link nl.templify.fuel2endure.domain.Payment} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.PaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentDate;

    private StringFilter paymentStatus;

    private StringFilter mollieKey;

    private LongFilter athleteId;

    public PaymentCriteria() {}

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.mollieKey = other.mollieKey == null ? null : other.mollieKey.copy();
        this.athleteId = other.athleteId == null ? null : other.athleteId.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
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

    public StringFilter getPaymentDate() {
        return paymentDate;
    }

    public StringFilter paymentDate() {
        if (paymentDate == null) {
            paymentDate = new StringFilter();
        }
        return paymentDate;
    }

    public void setPaymentDate(StringFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StringFilter getPaymentStatus() {
        return paymentStatus;
    }

    public StringFilter paymentStatus() {
        if (paymentStatus == null) {
            paymentStatus = new StringFilter();
        }
        return paymentStatus;
    }

    public void setPaymentStatus(StringFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StringFilter getMollieKey() {
        return mollieKey;
    }

    public StringFilter mollieKey() {
        if (mollieKey == null) {
            mollieKey = new StringFilter();
        }
        return mollieKey;
    }

    public void setMollieKey(StringFilter mollieKey) {
        this.mollieKey = mollieKey;
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
        final PaymentCriteria that = (PaymentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(mollieKey, that.mollieKey) &&
            Objects.equals(athleteId, that.athleteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentDate, paymentStatus, mollieKey, athleteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
            (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
            (mollieKey != null ? "mollieKey=" + mollieKey + ", " : "") +
            (athleteId != null ? "athleteId=" + athleteId + ", " : "") +
            "}";
    }
}
