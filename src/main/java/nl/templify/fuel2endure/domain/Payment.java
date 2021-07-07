package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "mollie_key")
    private String mollieKey;

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

    public Payment id(Long id) {
        this.id = id;
        return this;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public Payment paymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Payment paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMollieKey() {
        return this.mollieKey;
    }

    public Payment mollieKey(String mollieKey) {
        this.mollieKey = mollieKey;
        return this;
    }

    public void setMollieKey(String mollieKey) {
        this.mollieKey = mollieKey;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Payment athlete(Athlete athlete) {
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
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", mollieKey='" + getMollieKey() + "'" +
            "}";
    }
}
