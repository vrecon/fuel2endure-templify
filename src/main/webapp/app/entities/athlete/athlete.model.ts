import { IFuelSettings } from 'app/entities/fuel-settings/fuel-settings.model';
import { IUser } from 'app/entities/user/user.model';
import { IRace } from 'app/entities/race/race.model';
import { ITraining } from 'app/entities/training/training.model';
import { IPayment } from 'app/entities/payment/payment.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IAthlete {
  id?: number;
  middleName?: string | null;
  length?: number | null;
  weight?: number | null;
  status?: string | null;
  fuelSettings?: IFuelSettings | null;
  internalUser?: IUser | null;
  races?: IRace[] | null;
  trainings?: ITraining[] | null;
  payments?: IPayment[] | null;
  voucher?: IVoucher | null;
}

export class Athlete implements IAthlete {
  constructor(
    public id?: number,
    public middleName?: string | null,
    public length?: number | null,
    public weight?: number | null,
    public status?: string | null,
    public fuelSettings?: IFuelSettings | null,
    public internalUser?: IUser | null,
    public races?: IRace[] | null,
    public trainings?: ITraining[] | null,
    public payments?: IPayment[] | null,
    public voucher?: IVoucher | null
  ) {}
}

export function getAthleteIdentifier(athlete: IAthlete): number | undefined {
  return athlete.id;
}
