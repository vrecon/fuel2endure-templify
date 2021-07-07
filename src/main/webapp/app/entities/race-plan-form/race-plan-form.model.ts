import { IRace } from 'app/entities/race/race.model';

export interface IRacePlanForm {
  id?: number;
  comp?: string | null;
  name?: string | null;
  selectedOrgGelQuery?: boolean | null;
  selectedOrgDrankQuery?: boolean | null;
  orsBeforeStart?: number | null;
  drinkCarbo?: number | null;
  gelCarbo?: number | null;
  drinkOrgCarbo?: number | null;
  gelOrgCarbo?: number | null;
  sportDrinkOrgBike?: number | null;
  waterOrgBike?: number | null;
  gelsOrgBike?: number | null;
  sportDrinkOrgRun?: number | null;
  waterOrgRun?: number | null;
  gelsOrgRun?: number | null;
  sportDrinkToTakeBike?: number | null;
  waterToTakeBike?: number | null;
  extraWaterToTakeBike?: number | null;
  orsToTakeBike?: number | null;
  gelsToTakeBike?: number | null;
  sportDrinkToTakeRun?: number | null;
  waterToTakeRun?: number | null;
  extraWaterToTakeRun?: number | null;
  orsToTakeRun?: number | null;
  gelsToTakeRun?: number | null;
  weightLossDuringBike?: number | null;
  carboNeedDuringRun?: number | null;
  fluidNeedPerHourBike?: number | null;
  fluidNeedPerHourSwim?: number | null;
  carboNeedDuringBike?: number | null;
  fluidNeedDuringBike?: number | null;
  fluidNeedPerHourRun?: number | null;
  fluidNeedDuringRun?: number | null;
  weightLossDuringRun?: number | null;
  diffCarboRun?: number | null;
  diffMoisterRun?: number | null;
  difCarbo?: number | null;
  difMoister?: number | null;
  actFluidNeedBike?: number | null;
  actFluidNeedRun?: number | null;
  carboNeedDuringBikeMin?: number | null;
  carboNeedDuringBikeMax?: number | null;
  carboNeedDuringRunMin?: number | null;
  carboNeedDuringRunMax?: number | null;
  startGel?: number | null;
  startDrank?: number | null;
  race?: IRace | null;
}

export class RacePlanForm implements IRacePlanForm {
  constructor(
    public id?: number,
    public comp?: string | null,
    public name?: string | null,
    public selectedOrgGelQuery?: boolean | null,
    public selectedOrgDrankQuery?: boolean | null,
    public orsBeforeStart?: number | null,
    public drinkCarbo?: number | null,
    public gelCarbo?: number | null,
    public drinkOrgCarbo?: number | null,
    public gelOrgCarbo?: number | null,
    public sportDrinkOrgBike?: number | null,
    public waterOrgBike?: number | null,
    public gelsOrgBike?: number | null,
    public sportDrinkOrgRun?: number | null,
    public waterOrgRun?: number | null,
    public gelsOrgRun?: number | null,
    public sportDrinkToTakeBike?: number | null,
    public waterToTakeBike?: number | null,
    public extraWaterToTakeBike?: number | null,
    public orsToTakeBike?: number | null,
    public gelsToTakeBike?: number | null,
    public sportDrinkToTakeRun?: number | null,
    public waterToTakeRun?: number | null,
    public extraWaterToTakeRun?: number | null,
    public orsToTakeRun?: number | null,
    public gelsToTakeRun?: number | null,
    public weightLossDuringBike?: number | null,
    public carboNeedDuringRun?: number | null,
    public fluidNeedPerHourBike?: number | null,
    public fluidNeedPerHourSwim?: number | null,
    public carboNeedDuringBike?: number | null,
    public fluidNeedDuringBike?: number | null,
    public fluidNeedPerHourRun?: number | null,
    public fluidNeedDuringRun?: number | null,
    public weightLossDuringRun?: number | null,
    public diffCarboRun?: number | null,
    public diffMoisterRun?: number | null,
    public difCarbo?: number | null,
    public difMoister?: number | null,
    public actFluidNeedBike?: number | null,
    public actFluidNeedRun?: number | null,
    public carboNeedDuringBikeMin?: number | null,
    public carboNeedDuringBikeMax?: number | null,
    public carboNeedDuringRunMin?: number | null,
    public carboNeedDuringRunMax?: number | null,
    public startGel?: number | null,
    public startDrank?: number | null,
    public race?: IRace | null
  ) {
    this.selectedOrgGelQuery = this.selectedOrgGelQuery ?? false;
    this.selectedOrgDrankQuery = this.selectedOrgDrankQuery ?? false;
  }
}

export function getRacePlanFormIdentifier(racePlanForm: IRacePlanForm): number | undefined {
  return racePlanForm.id;
}
