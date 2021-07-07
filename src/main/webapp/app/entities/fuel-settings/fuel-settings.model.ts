export interface IFuelSettings {
  id?: number;
  carbohydratesGel?: number | null;
  carbohydratesSportDrank?: number | null;
  selectedOwnGelItem?: number | null;
  selectedOwnDrinkItem?: number | null;
}

export class FuelSettings implements IFuelSettings {
  constructor(
    public id?: number,
    public carbohydratesGel?: number | null,
    public carbohydratesSportDrank?: number | null,
    public selectedOwnGelItem?: number | null,
    public selectedOwnDrinkItem?: number | null
  ) {}
}

export function getFuelSettingsIdentifier(fuelSettings: IFuelSettings): number | undefined {
  return fuelSettings.id;
}
