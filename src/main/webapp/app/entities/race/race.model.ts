import * as dayjs from 'dayjs';
import { IRacePlanForm } from 'app/entities/race-plan-form/race-plan-form.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';

export interface IRace {
  id?: number;
  date?: dayjs.Dayjs | null;
  name?: string | null;
  logging?: string | null;
  weight?: number | null;
  length?: number | null;
  temperature?: number | null;
  comp?: number | null;
  swimDuration?: number | null;
  bikeDuration?: number | null;
  runDuration?: number | null;
  gelCarbo?: number | null;
  drinkCarbo?: number | null;
  drinkOrgCarbo?: number | null;
  gelOrgCarbo?: number | null;
  gelsbike?: number | null;
  gelsrun?: number | null;
  selectedOrgGelQuery?: boolean | null;
  selectedOrgDrankQuery?: boolean | null;
  sportDrinkOrgBike?: number | null;
  waterOrgBike?: number | null;
  gelsOrgBike?: number | null;
  sportDrinkOrgRun?: number | null;
  waterOrgRun?: number | null;
  gelsOrgRun?: number | null;
  orsBeforeStart?: number | null;
  sportDrinkToTakeBike?: number | null;
  waterToTakeBike?: number | null;
  extraWaterToTakeBike?: number | null;
  orsToTakeBike?: number | null;
  gelsToTakeBike?: number | null;
  sportDrinkToTakeRun?: number | null;
  waterToTakeRun?: number | null;
  extraWaterToTakeRun?: number | null;
  orsToTakeRun?: number | null;
  carboNeedDuringBikeMin?: number | null;
  carboNeedDuringBikeMax?: number | null;
  carboNeedDuringRunMin?: number | null;
  carboNeedDuringRunMax?: number | null;
  diffCarboRun?: number | null;
  diffMoisterRun?: number | null;
  difCarbo?: number | null;
  difMoister?: number | null;
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
  actFluidNeedBike?: number | null;
  actFluidNeedRun?: number | null;
  racePlanForms?: IRacePlanForm[] | null;
  athlete?: IAthlete | null;
}

export class Race implements IRace {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs | null,
    public name?: string | null,
    public logging?: string | null,
    public weight?: number | null,
    public length?: number | null,
    public temperature?: number | null,
    public comp?: number | null,
    public swimDuration?: number | null,
    public bikeDuration?: number | null,
    public runDuration?: number | null,
    public gelCarbo?: number | null,
    public drinkCarbo?: number | null,
    public drinkOrgCarbo?: number | null,
    public gelOrgCarbo?: number | null,
    public gelsbike?: number | null,
    public gelsrun?: number | null,
    public selectedOrgGelQuery?: boolean | null,
    public selectedOrgDrankQuery?: boolean | null,
    public sportDrinkOrgBike?: number | null,
    public waterOrgBike?: number | null,
    public gelsOrgBike?: number | null,
    public sportDrinkOrgRun?: number | null,
    public waterOrgRun?: number | null,
    public gelsOrgRun?: number | null,
    public orsBeforeStart?: number | null,
    public sportDrinkToTakeBike?: number | null,
    public waterToTakeBike?: number | null,
    public extraWaterToTakeBike?: number | null,
    public orsToTakeBike?: number | null,
    public gelsToTakeBike?: number | null,
    public sportDrinkToTakeRun?: number | null,
    public waterToTakeRun?: number | null,
    public extraWaterToTakeRun?: number | null,
    public orsToTakeRun?: number | null,
    public carboNeedDuringBikeMin?: number | null,
    public carboNeedDuringBikeMax?: number | null,
    public carboNeedDuringRunMin?: number | null,
    public carboNeedDuringRunMax?: number | null,
    public diffCarboRun?: number | null,
    public diffMoisterRun?: number | null,
    public difCarbo?: number | null,
    public difMoister?: number | null,
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
    public actFluidNeedBike?: number | null,
    public actFluidNeedRun?: number | null,
    public racePlanForms?: IRacePlanForm[] | null,
    public athlete?: IAthlete | null
  ) {
    this.selectedOrgGelQuery = this.selectedOrgGelQuery ?? false;
    this.selectedOrgDrankQuery = this.selectedOrgDrankQuery ?? false;
  }
}

export function getRaceIdentifier(race: IRace): number | undefined {
  return race.id;
}
