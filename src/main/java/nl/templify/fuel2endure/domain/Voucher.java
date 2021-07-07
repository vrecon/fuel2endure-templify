package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import nl.templify.fuel2endure.domain.enumeration.CategoryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "voucher_type")
    private String voucherType;

    @Column(name = "redeemed")
    private Integer redeemed;

    @Column(name = "max_date")
    private LocalDate maxDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "max_redeemed")
    private Integer maxRedeemed;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryType category;

    @OneToMany(mappedBy = "voucher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fuelSettings", "internalUser", "races", "trainings", "payments", "voucher" }, allowSetters = true)
    private Set<Athlete> athletes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voucher id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Voucher code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVoucherType() {
        return this.voucherType;
    }

    public Voucher voucherType(String voucherType) {
        this.voucherType = voucherType;
        return this;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Integer getRedeemed() {
        return this.redeemed;
    }

    public Voucher redeemed(Integer redeemed) {
        this.redeemed = redeemed;
        return this;
    }

    public void setRedeemed(Integer redeemed) {
        this.redeemed = redeemed;
    }

    public LocalDate getMaxDate() {
        return this.maxDate;
    }

    public Voucher maxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Voucher amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getMaxRedeemed() {
        return this.maxRedeemed;
    }

    public Voucher maxRedeemed(Integer maxRedeemed) {
        this.maxRedeemed = maxRedeemed;
        return this;
    }

    public void setMaxRedeemed(Integer maxRedeemed) {
        this.maxRedeemed = maxRedeemed;
    }

    public CategoryType getCategory() {
        return this.category;
    }

    public Voucher category(CategoryType category) {
        this.category = category;
        return this;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public Set<Athlete> getAthletes() {
        return this.athletes;
    }

    public Voucher athletes(Set<Athlete> athletes) {
        this.setAthletes(athletes);
        return this;
    }

    public Voucher addAthlete(Athlete athlete) {
        this.athletes.add(athlete);
        athlete.setVoucher(this);
        return this;
    }

    public Voucher removeAthlete(Athlete athlete) {
        this.athletes.remove(athlete);
        athlete.setVoucher(null);
        return this;
    }

    public void setAthletes(Set<Athlete> athletes) {
        if (this.athletes != null) {
            this.athletes.forEach(i -> i.setVoucher(null));
        }
        if (athletes != null) {
            athletes.forEach(i -> i.setVoucher(this));
        }
        this.athletes = athletes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher)) {
            return false;
        }
        return id != null && id.equals(((Voucher) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", voucherType='" + getVoucherType() + "'" +
            ", redeemed=" + getRedeemed() +
            ", maxDate='" + getMaxDate() + "'" +
            ", amount=" + getAmount() +
            ", maxRedeemed=" + getMaxRedeemed() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
