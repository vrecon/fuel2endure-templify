package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Athlete.
 */
@Entity
@Table(name = "athlete")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Athlete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "length")
    private Integer length;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(unique = true)
    private FuelSettings fuelSettings;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "athlete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "racePlanForms", "athlete" }, allowSetters = true)
    private Set<Race> races = new HashSet<>();

    @OneToMany(mappedBy = "athlete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "athlete" }, allowSetters = true)
    private Set<Training> trainings = new HashSet<>();

    @OneToMany(mappedBy = "athlete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "athlete" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "athletes" }, allowSetters = true)
    private Voucher voucher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Athlete id(Long id) {
        this.id = id;
        return this;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Athlete middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getLength() {
        return this.length;
    }

    public Athlete length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Float getWeight() {
        return this.weight;
    }

    public Athlete weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return this.status;
    }

    public Athlete status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FuelSettings getFuelSettings() {
        return this.fuelSettings;
    }

    public Athlete fuelSettings(FuelSettings fuelSettings) {
        this.setFuelSettings(fuelSettings);
        return this;
    }

    public void setFuelSettings(FuelSettings fuelSettings) {
        this.fuelSettings = fuelSettings;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public Athlete internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Set<Race> getRaces() {
        return this.races;
    }

    public Athlete races(Set<Race> races) {
        this.setRaces(races);
        return this;
    }

    public Athlete addRace(Race race) {
        this.races.add(race);
        race.setAthlete(this);
        return this;
    }

    public Athlete removeRace(Race race) {
        this.races.remove(race);
        race.setAthlete(null);
        return this;
    }

    public void setRaces(Set<Race> races) {
        if (this.races != null) {
            this.races.forEach(i -> i.setAthlete(null));
        }
        if (races != null) {
            races.forEach(i -> i.setAthlete(this));
        }
        this.races = races;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }

    public Athlete trainings(Set<Training> trainings) {
        this.setTrainings(trainings);
        return this;
    }

    public Athlete addTraining(Training training) {
        this.trainings.add(training);
        training.setAthlete(this);
        return this;
    }

    public Athlete removeTraining(Training training) {
        this.trainings.remove(training);
        training.setAthlete(null);
        return this;
    }

    public void setTrainings(Set<Training> trainings) {
        if (this.trainings != null) {
            this.trainings.forEach(i -> i.setAthlete(null));
        }
        if (trainings != null) {
            trainings.forEach(i -> i.setAthlete(this));
        }
        this.trainings = trainings;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public Athlete payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public Athlete addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setAthlete(this);
        return this;
    }

    public Athlete removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setAthlete(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setAthlete(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setAthlete(this));
        }
        this.payments = payments;
    }

    public Voucher getVoucher() {
        return this.voucher;
    }

    public Athlete voucher(Voucher voucher) {
        this.setVoucher(voucher);
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Athlete)) {
            return false;
        }
        return id != null && id.equals(((Athlete) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Athlete{" +
            "id=" + getId() +
            ", middleName='" + getMiddleName() + "'" +
            ", length=" + getLength() +
            ", weight=" + getWeight() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
