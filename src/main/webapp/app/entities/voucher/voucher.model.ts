import * as dayjs from 'dayjs';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { CategoryType } from 'app/entities/enumerations/category-type.model';

export interface IVoucher {
  id?: number;
  code?: string | null;
  voucherType?: string | null;
  redeemed?: number | null;
  maxDate?: dayjs.Dayjs | null;
  amount?: number | null;
  maxRedeemed?: number | null;
  category?: CategoryType | null;
  athletes?: IAthlete[] | null;
}

export class Voucher implements IVoucher {
  constructor(
    public id?: number,
    public code?: string | null,
    public voucherType?: string | null,
    public redeemed?: number | null,
    public maxDate?: dayjs.Dayjs | null,
    public amount?: number | null,
    public maxRedeemed?: number | null,
    public category?: CategoryType | null,
    public athletes?: IAthlete[] | null
  ) {}
}

export function getVoucherIdentifier(voucher: IVoucher): number | undefined {
  return voucher.id;
}
