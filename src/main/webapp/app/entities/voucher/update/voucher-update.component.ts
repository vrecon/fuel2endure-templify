import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVoucher, Voucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';

@Component({
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html',
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    voucherType: [],
    redeemed: [],
    maxDate: [],
    amount: [],
    maxRedeemed: [],
    category: [],
  });

  constructor(protected voucherService: VoucherService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.updateForm(voucher);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucher = this.createFromForm();
    if (voucher.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherService.update(voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(voucher));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
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

  protected updateForm(voucher: IVoucher): void {
    this.editForm.patchValue({
      id: voucher.id,
      code: voucher.code,
      voucherType: voucher.voucherType,
      redeemed: voucher.redeemed,
      maxDate: voucher.maxDate,
      amount: voucher.amount,
      maxRedeemed: voucher.maxRedeemed,
      category: voucher.category,
    });
  }

  protected createFromForm(): IVoucher {
    return {
      ...new Voucher(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      voucherType: this.editForm.get(['voucherType'])!.value,
      redeemed: this.editForm.get(['redeemed'])!.value,
      maxDate: this.editForm.get(['maxDate'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      maxRedeemed: this.editForm.get(['maxRedeemed'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }
}
