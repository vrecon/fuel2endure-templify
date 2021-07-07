package nl.templify.fuel2endure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "training_type_code")
    private String trainingTypeCode;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "training_intensity_code")
    private Integer trainingIntensityCode;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "weight_before")
    private Float weightBefore;

    @Column(name = "weight_after")
    private Float weightAfter;

    @Column(name = "drunk")
    private Integer drunk;

    @Column(name = "eaten")
    private Integer eaten;

    @Column(name = "moisture_loss_percentage")
    private Float moistureLossPercentage;

    @Column(name = "moisture_loss_per_hour")
    private Float moistureLossPerHour;

    @Column(name = "default_moister_loss_per_hour")
    private Float defaultMoisterLossPerHour;

    @Column(name = "delta_moister_loss_per_hour")
    private Float deltaMoisterLossPerHour;

    @Column(name = "excluded")
    private Boolean excluded;

    @Column(name = "comments")
    private String comments;

    @Column(name = "carbo_drank")
    private Integer carboDrank;

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

    public Training id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Training date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTrainingTypeCode() {
        return this.trainingTypeCode;
    }

    public Training trainingTypeCode(String trainingTypeCode) {
        this.trainingTypeCode = trainingTypeCode;
        return this;
    }

    public void setTrainingTypeCode(String trainingTypeCode) {
        this.trainingTypeCode = trainingTypeCode;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Training duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTrainingIntensityCode() {
        return this.trainingIntensityCode;
    }

    public Training trainingIntensityCode(Integer trainingIntensityCode) {
        this.trainingIntensityCode = trainingIntensityCode;
        return this;
    }

    public void setTrainingIntensityCode(Integer trainingIntensityCode) {
        this.trainingIntensityCode = trainingIntensityCode;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public Training temperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Float getWeightBefore() {
        return this.weightBefore;
    }

    public Training weightBefore(Float weightBefore) {
        this.weightBefore = weightBefore;
        return this;
    }

    public void setWeightBefore(Float weightBefore) {
        this.weightBefore = weightBefore;
    }

    public Float getWeightAfter() {
        return this.weightAfter;
    }

    public Training weightAfter(Float weightAfter) {
        this.weightAfter = weightAfter;
        return this;
    }

    public void setWeightAfter(Float weightAfter) {
        this.weightAfter = weightAfter;
    }

    public Integer getDrunk() {
        return this.drunk;
    }

    public Training drunk(Integer drunk) {
        this.drunk = drunk;
        return this;
    }

    public void setDrunk(Integer drunk) {
        this.drunk = drunk;
    }

    public Integer getEaten() {
        return this.eaten;
    }

    public Training eaten(Integer eaten) {
        this.eaten = eaten;
        return this;
    }

    public void setEaten(Integer eaten) {
        this.eaten = eaten;
    }

    public Float getMoistureLossPercentage() {
        return this.moistureLossPercentage;
    }

    public Training moistureLossPercentage(Float moistureLossPercentage) {
        this.moistureLossPercentage = moistureLossPercentage;
        return this;
    }

    public void setMoistureLossPercentage(Float moistureLossPercentage) {
        this.moistureLossPercentage = moistureLossPercentage;
    }

    public Float getMoistureLossPerHour() {
        return this.moistureLossPerHour;
    }

    public Training moistureLossPerHour(Float moistureLossPerHour) {
        this.moistureLossPerHour = moistureLossPerHour;
        return this;
    }

    public void setMoistureLossPerHour(Float moistureLossPerHour) {
        this.moistureLossPerHour = moistureLossPerHour;
    }

    public Float getDefaultMoisterLossPerHour() {
        return this.defaultMoisterLossPerHour;
    }

    public Training defaultMoisterLossPerHour(Float defaultMoisterLossPerHour) {
        this.defaultMoisterLossPerHour = defaultMoisterLossPerHour;
        return this;
    }

    public void setDefaultMoisterLossPerHour(Float defaultMoisterLossPerHour) {
        this.defaultMoisterLossPerHour = defaultMoisterLossPerHour;
    }

    public Float getDeltaMoisterLossPerHour() {
        return this.deltaMoisterLossPerHour;
    }

    public Training deltaMoisterLossPerHour(Float deltaMoisterLossPerHour) {
        this.deltaMoisterLossPerHour = deltaMoisterLossPerHour;
        return this;
    }

    public void setDeltaMoisterLossPerHour(Float deltaMoisterLossPerHour) {
        this.deltaMoisterLossPerHour = deltaMoisterLossPerHour;
    }

    public Boolean getExcluded() {
        return this.excluded;
    }

    public Training excluded(Boolean excluded) {
        this.excluded = excluded;
        return this;
    }

    public void setExcluded(Boolean excluded) {
        this.excluded = excluded;
    }

    public String getComments() {
        return this.comments;
    }

    public Training comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCarboDrank() {
        return this.carboDrank;
    }

    public Training carboDrank(Integer carboDrank) {
        this.carboDrank = carboDrank;
        return this;
    }

    public void setCarboDrank(Integer carboDrank) {
        this.carboDrank = carboDrank;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Training athlete(Athlete athlete) {
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
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", trainingTypeCode='" + getTrainingTypeCode() + "'" +
            ", duration=" + getDuration() +
            ", trainingIntensityCode=" + getTrainingIntensityCode() +
            ", temperature=" + getTemperature() +
            ", weightBefore=" + getWeightBefore() +
            ", weightAfter=" + getWeightAfter() +
            ", drunk=" + getDrunk() +
            ", eaten=" + getEaten() +
            ", moistureLossPercentage=" + getMoistureLossPercentage() +
            ", moistureLossPerHour=" + getMoistureLossPerHour() +
            ", defaultMoisterLossPerHour=" + getDefaultMoisterLossPerHour() +
            ", deltaMoisterLossPerHour=" + getDeltaMoisterLossPerHour() +
            ", excluded='" + getExcluded() + "'" +
            ", comments='" + getComments() + "'" +
            ", carboDrank=" + getCarboDrank() +
            "}";
    }
}
