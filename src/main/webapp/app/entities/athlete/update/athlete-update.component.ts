import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAthlete, Athlete } from '../athlete.model';
import { AthleteService } from '../service/athlete.service';
import { IFuelSettings } from 'app/entities/fuel-settings/fuel-settings.model';
import { FuelSettingsService } from 'app/entities/fuel-settings/service/fuel-settings.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

@Component({
  selector: 'jhi-athlete-update',
  templateUrl: './athlete-update.component.html',
})
export class AthleteUpdateComponent implements OnInit {
  isSaving = false;

  fuelSettingsCollection: IFuelSettings[] = [];
  usersSharedCollection: IUser[] = [];
  vouchersSharedCollection: IVoucher[] = [];

  editForm = this.fb.group({
    id: [],
    middleName: [],
    length: [],
    weight: [],
    status: [],
    fuelSettings: [],
    internalUser: [],
    voucher: [],
  });

  constructor(
    protected athleteService: AthleteService,
    protected fuelSettingsService: FuelSettingsService,
    protected userService: UserService,
    protected voucherService: VoucherService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athlete }) => {
      this.updateForm(athlete);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const athlete = this.createFromForm();
    if (athlete.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteService.update(athlete));
    } else {
      this.subscribeToSaveResponse(this.athleteService.create(athlete));
    }
  }

  trackFuelSettingsById(index: number, item: IFuelSettings): number {
    return item.id!;
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackVoucherById(index: number, item: IVoucher): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthlete>>): void {
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

  protected updateForm(athlete: IAthlete): void {
    this.editForm.patchValue({
      id: athlete.id,
      middleName: athlete.middleName,
      length: athlete.length,
      weight: athlete.weight,
      status: athlete.status,
      fuelSettings: athlete.fuelSettings,
      internalUser: athlete.internalUser,
      voucher: athlete.voucher,
    });

    this.fuelSettingsCollection = this.fuelSettingsService.addFuelSettingsToCollectionIfMissing(
      this.fuelSettingsCollection,
      athlete.fuelSettings
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, athlete.internalUser);
    this.vouchersSharedCollection = this.voucherService.addVoucherToCollectionIfMissing(this.vouchersSharedCollection, athlete.voucher);
  }

  protected loadRelationshipsOptions(): void {
    this.fuelSettingsService
      .query({ 'athleteId.specified': 'false' })
      .pipe(map((res: HttpResponse<IFuelSettings[]>) => res.body ?? []))
      .pipe(
        map((fuelSettings: IFuelSettings[]) =>
          this.fuelSettingsService.addFuelSettingsToCollectionIfMissing(fuelSettings, this.editForm.get('fuelSettings')!.value)
        )
      )
      .subscribe((fuelSettings: IFuelSettings[]) => (this.fuelSettingsCollection = fuelSettings));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('internalUser')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.voucherService
      .query()
      .pipe(map((res: HttpResponse<IVoucher[]>) => res.body ?? []))
      .pipe(
        map((vouchers: IVoucher[]) => this.voucherService.addVoucherToCollectionIfMissing(vouchers, this.editForm.get('voucher')!.value))
      )
      .subscribe((vouchers: IVoucher[]) => (this.vouchersSharedCollection = vouchers));
  }

  protected createFromForm(): IAthlete {
    return {
      ...new Athlete(),
      id: this.editForm.get(['id'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      length: this.editForm.get(['length'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      status: this.editForm.get(['status'])!.value,
      fuelSettings: this.editForm.get(['fuelSettings'])!.value,
      internalUser: this.editForm.get(['internalUser'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
    };
  }
}
