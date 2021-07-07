import * as dayjs from 'dayjs';
import { IAthlete } from 'app/entities/athlete/athlete.model';

export interface ITraining {
  id?: number;
  date?: dayjs.Dayjs | null;
  trainingTypeCode?: string | null;
  duration?: number | null;
  trainingIntensityCode?: number | null;
  temperature?: number | null;
  weightBefore?: number | null;
  weightAfter?: number | null;
  drunk?: number | null;
  eaten?: number | null;
  moistureLossPercentage?: number | null;
  moistureLossPerHour?: number | null;
  defaultMoisterLossPerHour?: number | null;
  deltaMoisterLossPerHour?: number | null;
  excluded?: boolean | null;
  comments?: string | null;
  carboDrank?: number | null;
  athlete?: IAthlete | null;
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs | null,
    public trainingTypeCode?: string | null,
    public duration?: number | null,
    public trainingIntensityCode?: number | null,
    public temperature?: number | null,
    public weightBefore?: number | null,
    public weightAfter?: number | null,
    public drunk?: number | null,
    public eaten?: number | null,
    public moistureLossPercentage?: number | null,
    public moistureLossPerHour?: number | null,
    public defaultMoisterLossPerHour?: number | null,
    public deltaMoisterLossPerHour?: number | null,
    public excluded?: boolean | null,
    public comments?: string | null,
    public carboDrank?: number | null,
    public athlete?: IAthlete | null
  ) {
    this.excluded = this.excluded ?? false;
  }
}

export function getTrainingIdentifier(training: ITraining): number | undefined {
  return training.id;
}
