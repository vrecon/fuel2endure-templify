package nl.templify.fuel2endure.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelSettings.
 */
@Entity
@Table(name = "fuel_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FuelSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "carbohydrates_gel")
    private Integer carbohydratesGel;

    @Column(name = "carbohydrates_sport_drank")
    private Integer carbohydratesSportDrank;

    @Column(name = "selected_own_gel_item")
    private Integer selectedOwnGelItem;

    @Column(name = "selected_own_drink_item")
    private Integer selectedOwnDrinkItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuelSettings id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCarbohydratesGel() {
        return this.carbohydratesGel;
    }

    public FuelSettings carbohydratesGel(Integer carbohydratesGel) {
        this.carbohydratesGel = carbohydratesGel;
        return this;
    }

    public void setCarbohydratesGel(Integer carbohydratesGel) {
        this.carbohydratesGel = carbohydratesGel;
    }

    public Integer getCarbohydratesSportDrank() {
        return this.carbohydratesSportDrank;
    }

    public FuelSettings carbohydratesSportDrank(Integer carbohydratesSportDrank) {
        this.carbohydratesSportDrank = carbohydratesSportDrank;
        return this;
    }

    public void setCarbohydratesSportDrank(Integer carbohydratesSportDrank) {
        this.carbohydratesSportDrank = carbohydratesSportDrank;
    }

    public Integer getSelectedOwnGelItem() {
        return this.selectedOwnGelItem;
    }

    public FuelSettings selectedOwnGelItem(Integer selectedOwnGelItem) {
        this.selectedOwnGelItem = selectedOwnGelItem;
        return this;
    }

    public void setSelectedOwnGelItem(Integer selectedOwnGelItem) {
        this.selectedOwnGelItem = selectedOwnGelItem;
    }

    public Integer getSelectedOwnDrinkItem() {
        return this.selectedOwnDrinkItem;
    }

    public FuelSettings selectedOwnDrinkItem(Integer selectedOwnDrinkItem) {
        this.selectedOwnDrinkItem = selectedOwnDrinkItem;
        return this;
    }

    public void setSelectedOwnDrinkItem(Integer selectedOwnDrinkItem) {
        this.selectedOwnDrinkItem = selectedOwnDrinkItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelSettings)) {
            return false;
        }
        return id != null && id.equals(((FuelSettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelSettings{" +
            "id=" + getId() +
            ", carbohydratesGel=" + getCarbohydratesGel() +
            ", carbohydratesSportDrank=" + getCarbohydratesSportDrank() +
            ", selectedOwnGelItem=" + getSelectedOwnGelItem() +
            ", selectedOwnDrinkItem=" + getSelectedOwnDrinkItem() +
            "}";
    }
}
