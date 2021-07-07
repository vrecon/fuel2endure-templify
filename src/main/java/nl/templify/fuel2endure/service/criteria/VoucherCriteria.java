package nl.templify.fuel2endure.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import nl.templify.fuel2endure.domain.enumeration.CategoryType;
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
 * Criteria class for the {@link nl.templify.fuel2endure.domain.Voucher} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.VoucherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vouchers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VoucherCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CategoryType
     */
    public static class CategoryTypeFilter extends Filter<CategoryType> {

        public CategoryTypeFilter() {}

        public CategoryTypeFilter(CategoryTypeFilter filter) {
            super(filter);
        }

        @Override
        public CategoryTypeFilter copy() {
            return new CategoryTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter voucherType;

    private IntegerFilter redeemed;

    private LocalDateFilter maxDate;

    private DoubleFilter amount;

    private IntegerFilter maxRedeemed;

    private CategoryTypeFilter category;

    private LongFilter athleteId;

    public VoucherCriteria() {}

    public VoucherCriteria(VoucherCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.voucherType = other.voucherType == null ? null : other.voucherType.copy();
        this.redeemed = other.redeemed == null ? null : other.redeemed.copy();
        this.maxDate = other.maxDate == null ? null : other.maxDate.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.maxRedeemed = other.maxRedeemed == null ? null : other.maxRedeemed.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.athleteId = other.athleteId == null ? null : other.athleteId.copy();
    }

    @Override
    public VoucherCriteria copy() {
        return new VoucherCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getVoucherType() {
        return voucherType;
    }

    public StringFilter voucherType() {
        if (voucherType == null) {
            voucherType = new StringFilter();
        }
        return voucherType;
    }

    public void setVoucherType(StringFilter voucherType) {
        this.voucherType = voucherType;
    }

    public IntegerFilter getRedeemed() {
        return redeemed;
    }

    public IntegerFilter redeemed() {
        if (redeemed == null) {
            redeemed = new IntegerFilter();
        }
        return redeemed;
    }

    public void setRedeemed(IntegerFilter redeemed) {
        this.redeemed = redeemed;
    }

    public LocalDateFilter getMaxDate() {
        return maxDate;
    }

    public LocalDateFilter maxDate() {
        if (maxDate == null) {
            maxDate = new LocalDateFilter();
        }
        return maxDate;
    }

    public void setMaxDate(LocalDateFilter maxDate) {
        this.maxDate = maxDate;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public IntegerFilter getMaxRedeemed() {
        return maxRedeemed;
    }

    public IntegerFilter maxRedeemed() {
        if (maxRedeemed == null) {
            maxRedeemed = new IntegerFilter();
        }
        return maxRedeemed;
    }

    public void setMaxRedeemed(IntegerFilter maxRedeemed) {
        this.maxRedeemed = maxRedeemed;
    }

    public CategoryTypeFilter getCategory() {
        return category;
    }

    public CategoryTypeFilter category() {
        if (category == null) {
            category = new CategoryTypeFilter();
        }
        return category;
    }

    public void setCategory(CategoryTypeFilter category) {
        this.category = category;
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
        final VoucherCriteria that = (VoucherCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(voucherType, that.voucherType) &&
            Objects.equals(redeemed, that.redeemed) &&
            Objects.equals(maxDate, that.maxDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(maxRedeemed, that.maxRedeemed) &&
            Objects.equals(category, that.category) &&
            Objects.equals(athleteId, that.athleteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, voucherType, redeemed, maxDate, amount, maxRedeemed, category, athleteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (voucherType != null ? "voucherType=" + voucherType + ", " : "") +
            (redeemed != null ? "redeemed=" + redeemed + ", " : "") +
            (maxDate != null ? "maxDate=" + maxDate + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (maxRedeemed != null ? "maxRedeemed=" + maxRedeemed + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (athleteId != null ? "athleteId=" + athleteId + ", " : "") +
            "}";
    }
}
