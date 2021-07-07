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
 * Criteria class for the {@link nl.templify.fuel2endure.domain.FuelSettings} entity. This class is used
 * in {@link nl.templify.fuel2endure.web.rest.FuelSettingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fuel-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FuelSettingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter carbohydratesGel;

    private IntegerFilter carbohydratesSportDrank;

    private IntegerFilter selectedOwnGelItem;

    private IntegerFilter selectedOwnDrinkItem;

    public FuelSettingsCriteria() {}

    public FuelSettingsCriteria(FuelSettingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.carbohydratesGel = other.carbohydratesGel == null ? null : other.carbohydratesGel.copy();
        this.carbohydratesSportDrank = other.carbohydratesSportDrank == null ? null : other.carbohydratesSportDrank.copy();
        this.selectedOwnGelItem = other.selectedOwnGelItem == null ? null : other.selectedOwnGelItem.copy();
        this.selectedOwnDrinkItem = other.selectedOwnDrinkItem == null ? null : other.selectedOwnDrinkItem.copy();
    }

    @Override
    public FuelSettingsCriteria copy() {
        return new FuelSettingsCriteria(this);
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

    public IntegerFilter getCarbohydratesGel() {
        return carbohydratesGel;
    }

    public IntegerFilter carbohydratesGel() {
        if (carbohydratesGel == null) {
            carbohydratesGel = new IntegerFilter();
        }
        return carbohydratesGel;
    }

    public void setCarbohydratesGel(IntegerFilter carbohydratesGel) {
        this.carbohydratesGel = carbohydratesGel;
    }

    public IntegerFilter getCarbohydratesSportDrank() {
        return carbohydratesSportDrank;
    }

    public IntegerFilter carbohydratesSportDrank() {
        if (carbohydratesSportDrank == null) {
            carbohydratesSportDrank = new IntegerFilter();
        }
        return carbohydratesSportDrank;
    }

    public void setCarbohydratesSportDrank(IntegerFilter carbohydratesSportDrank) {
        this.carbohydratesSportDrank = carbohydratesSportDrank;
    }

    public IntegerFilter getSelectedOwnGelItem() {
        return selectedOwnGelItem;
    }

    public IntegerFilter selectedOwnGelItem() {
        if (selectedOwnGelItem == null) {
            selectedOwnGelItem = new IntegerFilter();
        }
        return selectedOwnGelItem;
    }

    public void setSelectedOwnGelItem(IntegerFilter selectedOwnGelItem) {
        this.selectedOwnGelItem = selectedOwnGelItem;
    }

    public IntegerFilter getSelectedOwnDrinkItem() {
        return selectedOwnDrinkItem;
    }

    public IntegerFilter selectedOwnDrinkItem() {
        if (selectedOwnDrinkItem == null) {
            selectedOwnDrinkItem = new IntegerFilter();
        }
        return selectedOwnDrinkItem;
    }

    public void setSelectedOwnDrinkItem(IntegerFilter selectedOwnDrinkItem) {
        this.selectedOwnDrinkItem = selectedOwnDrinkItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FuelSettingsCriteria that = (FuelSettingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(carbohydratesGel, that.carbohydratesGel) &&
            Objects.equals(carbohydratesSportDrank, that.carbohydratesSportDrank) &&
            Objects.equals(selectedOwnGelItem, that.selectedOwnGelItem) &&
            Objects.equals(selectedOwnDrinkItem, that.selectedOwnDrinkItem)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carbohydratesGel, carbohydratesSportDrank, selectedOwnGelItem, selectedOwnDrinkItem);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelSettingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (carbohydratesGel != null ? "carbohydratesGel=" + carbohydratesGel + ", " : "") +
            (carbohydratesSportDrank != null ? "carbohydratesSportDrank=" + carbohydratesSportDrank + ", " : "") +
            (selectedOwnGelItem != null ? "selectedOwnGelItem=" + selectedOwnGelItem + ", " : "") +
            (selectedOwnDrinkItem != null ? "selectedOwnDrinkItem=" + selectedOwnDrinkItem + ", " : "") +
            "}";
    }
}
