import { IAthlete } from 'app/entities/athlete/athlete.model';

export interface IPayment {
  id?: number;
  paymentDate?: string | null;
  paymentStatus?: string | null;
  mollieKey?: string | null;
  athlete?: IAthlete | null;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public paymentDate?: string | null,
    public paymentStatus?: string | null,
    public mollieKey?: string | null,
    public athlete?: IAthlete | null
  ) {}
}

export function getPaymentIdentifier(payment: IPayment): number | undefined {
  return payment.id;
}
