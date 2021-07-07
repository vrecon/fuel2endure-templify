import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFuelSettings, FuelSettings } from '../fuel-settings.model';
import { FuelSettingsService } from '../service/fuel-settings.service';

@Component({
  selector: 'jhi-fuel-settings-update',
  templateUrl: './fuel-settings-update.component.html',
})
export class FuelSettingsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    carbohydratesGel: [],
    carbohydratesSportDrank: [],
    selectedOwnGelItem: [],
    selectedOwnDrinkItem: [],
  });

  constructor(protected fuelSettingsService: FuelSettingsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelSettings }) => {
      this.updateForm(fuelSettings);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelSettings = this.createFromForm();
    if (fuelSettings.id !== undefined) {
      this.subscribeToSaveResponse(this.fuelSettingsService.update(fuelSettings));
    } else {
      this.subscribeToSaveResponse(this.fuelSettingsService.create(fuelSettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelSettings>>): void {
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

  protected updateForm(fuelSettings: IFuelSettings): void {
    this.editForm.patchValue({
      id: fuelSettings.id,
      carbohydratesGel: fuelSettings.carbohydratesGel,
      carbohydratesSportDrank: fuelSettings.carbohydratesSportDrank,
      selectedOwnGelItem: fuelSettings.selectedOwnGelItem,
      selectedOwnDrinkItem: fuelSettings.selectedOwnDrinkItem,
    });
  }

  protected createFromForm(): IFuelSettings {
    return {
      ...new FuelSettings(),
      id: this.editForm.get(['id'])!.value,
      carbohydratesGel: this.editForm.get(['carbohydratesGel'])!.value,
      carbohydratesSportDrank: this.editForm.get(['carbohydratesSportDrank'])!.value,
      selectedOwnGelItem: this.editForm.get(['selectedOwnGelItem'])!.value,
      selectedOwnDrinkItem: this.editForm.get(['selectedOwnDrinkItem'])!.value,
    };
  }
}
