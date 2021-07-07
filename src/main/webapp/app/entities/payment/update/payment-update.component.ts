import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPayment, Payment } from '../payment.model';
import { PaymentService } from '../service/payment.service';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;

  athletesSharedCollection: IAthlete[] = [];

  editForm = this.fb.group({
    id: [],
    paymentDate: [],
    paymentStatus: [],
    mollieKey: [],
    athlete: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      this.updateForm(payment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  trackAthleteById(index: number, item: IAthlete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      paymentDate: payment.paymentDate,
      paymentStatus: payment.paymentStatus,
      mollieKey: payment.mollieKey,
      athlete: payment.athlete,
    });

    this.athletesSharedCollection = this.athleteService.addAthleteToCollectionIfMissing(this.athletesSharedCollection, payment.athlete);
  }

  protected loadRelationshipsOptions(): void {
    this.athleteService
      .query()
      .pipe(map((res: HttpResponse<IAthlete[]>) => res.body ?? []))
      .pipe(
        map((athletes: IAthlete[]) => this.athleteService.addAthleteToCollectionIfMissing(athletes, this.editForm.get('athlete')!.value))
      )
      .subscribe((athletes: IAthlete[]) => (this.athletesSharedCollection = athletes));
  }

  protected createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      mollieKey: this.editForm.get(['mollieKey'])!.value,
      athlete: this.editForm.get(['athlete'])!.value,
    };
  }
}
