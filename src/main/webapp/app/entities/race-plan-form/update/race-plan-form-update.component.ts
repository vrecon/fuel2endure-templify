import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRacePlanForm, RacePlanForm } from '../race-plan-form.model';
import { RacePlanFormService } from '../service/race-plan-form.service';
import { IRace } from 'app/entities/race/race.model';
import { RaceService } from 'app/entities/race/service/race.service';

@Component({
  selector: 'jhi-race-plan-form-update',
  templateUrl: './race-plan-form-update.component.html',
})
export class RacePlanFormUpdateComponent implements OnInit {
  isSaving = false;

  racesSharedCollection: IRace[] = [];

  editForm = this.fb.group({
    id: [],
    comp: [],
    name: [],
    selectedOrgGelQuery: [],
    selectedOrgDrankQuery: [],
    orsBeforeStart: [],
    drinkCarbo: [],
    gelCarbo: [],
    drinkOrgCarbo: [],
    gelOrgCarbo: [],
    sportDrinkOrgBike: [],
    waterOrgBike: [],
    gelsOrgBike: [],
    sportDrinkOrgRun: [],
    waterOrgRun: [],
    gelsOrgRun: [],
    sportDrinkToTakeBike: [],
    waterToTakeBike: [],
    extraWaterToTakeBike: [],
    orsToTakeBike: [],
    gelsToTakeBike: [],
    sportDrinkToTakeRun: [],
    waterToTakeRun: [],
    extraWaterToTakeRun: [],
    orsToTakeRun: [],
    gelsToTakeRun: [],
    weightLossDuringBike: [],
    carboNeedDuringRun: [],
    fluidNeedPerHourBike: [],
    fluidNeedPerHourSwim: [],
    carboNeedDuringBike: [],
    fluidNeedDuringBike: [],
    fluidNeedPerHourRun: [],
    fluidNeedDuringRun: [],
    weightLossDuringRun: [],
    diffCarboRun: [],
    diffMoisterRun: [],
    difCarbo: [],
    difMoister: [],
    actFluidNeedBike: [],
    actFluidNeedRun: [],
    carboNeedDuringBikeMin: [],
    carboNeedDuringBikeMax: [],
    carboNeedDuringRunMin: [],
    carboNeedDuringRunMax: [],
    startGel: [],
    startDrank: [],
    race: [],
  });

  constructor(
    protected racePlanFormService: RacePlanFormService,
    protected raceService: RaceService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ racePlanForm }) => {
      this.updateForm(racePlanForm);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const racePlanForm = this.createFromForm();
    if (racePlanForm.id !== undefined) {
      this.subscribeToSaveResponse(this.racePlanFormService.update(racePlanForm));
    } else {
      this.subscribeToSaveResponse(this.racePlanFormService.create(racePlanForm));
    }
  }

  trackRaceById(index: number, item: IRace): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRacePlanForm>>): void {
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

  protected updateForm(racePlanForm: IRacePlanForm): void {
    this.editForm.patchValue({
      id: racePlanForm.id,
      comp: racePlanForm.comp,
      name: racePlanForm.name,
      selectedOrgGelQuery: racePlanForm.selectedOrgGelQuery,
      selectedOrgDrankQuery: racePlanForm.selectedOrgDrankQuery,
      orsBeforeStart: racePlanForm.orsBeforeStart,
      drinkCarbo: racePlanForm.drinkCarbo,
      gelCarbo: racePlanForm.gelCarbo,
      drinkOrgCarbo: racePlanForm.drinkOrgCarbo,
      gelOrgCarbo: racePlanForm.gelOrgCarbo,
      sportDrinkOrgBike: racePlanForm.sportDrinkOrgBike,
      waterOrgBike: racePlanForm.waterOrgBike,
      gelsOrgBike: racePlanForm.gelsOrgBike,
      sportDrinkOrgRun: racePlanForm.sportDrinkOrgRun,
      waterOrgRun: racePlanForm.waterOrgRun,
      gelsOrgRun: racePlanForm.gelsOrgRun,
      sportDrinkToTakeBike: racePlanForm.sportDrinkToTakeBike,
      waterToTakeBike: racePlanForm.waterToTakeBike,
      extraWaterToTakeBike: racePlanForm.extraWaterToTakeBike,
      orsToTakeBike: racePlanForm.orsToTakeBike,
      gelsToTakeBike: racePlanForm.gelsToTakeBike,
      sportDrinkToTakeRun: racePlanForm.sportDrinkToTakeRun,
      waterToTakeRun: racePlanForm.waterToTakeRun,
      extraWaterToTakeRun: racePlanForm.extraWaterToTakeRun,
      orsToTakeRun: racePlanForm.orsToTakeRun,
      gelsToTakeRun: racePlanForm.gelsToTakeRun,
      weightLossDuringBike: racePlanForm.weightLossDuringBike,
      carboNeedDuringRun: racePlanForm.carboNeedDuringRun,
      fluidNeedPerHourBike: racePlanForm.fluidNeedPerHourBike,
      fluidNeedPerHourSwim: racePlanForm.fluidNeedPerHourSwim,
      carboNeedDuringBike: racePlanForm.carboNeedDuringBike,
      fluidNeedDuringBike: racePlanForm.fluidNeedDuringBike,
      fluidNeedPerHourRun: racePlanForm.fluidNeedPerHourRun,
      fluidNeedDuringRun: racePlanForm.fluidNeedDuringRun,
      weightLossDuringRun: racePlanForm.weightLossDuringRun,
      diffCarboRun: racePlanForm.diffCarboRun,
      diffMoisterRun: racePlanForm.diffMoisterRun,
      difCarbo: racePlanForm.difCarbo,
      difMoister: racePlanForm.difMoister,
      actFluidNeedBike: racePlanForm.actFluidNeedBike,
      actFluidNeedRun: racePlanForm.actFluidNeedRun,
      carboNeedDuringBikeMin: racePlanForm.carboNeedDuringBikeMin,
      carboNeedDuringBikeMax: racePlanForm.carboNeedDuringBikeMax,
      carboNeedDuringRunMin: racePlanForm.carboNeedDuringRunMin,
      carboNeedDuringRunMax: racePlanForm.carboNeedDuringRunMax,
      startGel: racePlanForm.startGel,
      startDrank: racePlanForm.startDrank,
      race: racePlanForm.race,
    });

    this.racesSharedCollection = this.raceService.addRaceToCollectionIfMissing(this.racesSharedCollection, racePlanForm.race);
  }

  protected loadRelationshipsOptions(): void {
    this.raceService
      .query()
      .pipe(map((res: HttpResponse<IRace[]>) => res.body ?? []))
      .pipe(map((races: IRace[]) => this.raceService.addRaceToCollectionIfMissing(races, this.editForm.get('race')!.value)))
      .subscribe((races: IRace[]) => (this.racesSharedCollection = races));
  }

  protected createFromForm(): IRacePlanForm {
    return {
      ...new RacePlanForm(),
      id: this.editForm.get(['id'])!.value,
      comp: this.editForm.get(['comp'])!.value,
      name: this.editForm.get(['name'])!.value,
      selectedOrgGelQuery: this.editForm.get(['selectedOrgGelQuery'])!.value,
      selectedOrgDrankQuery: this.editForm.get(['selectedOrgDrankQuery'])!.value,
      orsBeforeStart: this.editForm.get(['orsBeforeStart'])!.value,
      drinkCarbo: this.editForm.get(['drinkCarbo'])!.value,
      gelCarbo: this.editForm.get(['gelCarbo'])!.value,
      drinkOrgCarbo: this.editForm.get(['drinkOrgCarbo'])!.value,
      gelOrgCarbo: this.editForm.get(['gelOrgCarbo'])!.value,
      sportDrinkOrgBike: this.editForm.get(['sportDrinkOrgBike'])!.value,
      waterOrgBike: this.editForm.get(['waterOrgBike'])!.value,
      gelsOrgBike: this.editForm.get(['gelsOrgBike'])!.value,
      sportDrinkOrgRun: this.editForm.get(['sportDrinkOrgRun'])!.value,
      waterOrgRun: this.editForm.get(['waterOrgRun'])!.value,
      gelsOrgRun: this.editForm.get(['gelsOrgRun'])!.value,
      sportDrinkToTakeBike: this.editForm.get(['sportDrinkToTakeBike'])!.value,
      waterToTakeBike: this.editForm.get(['waterToTakeBike'])!.value,
      extraWaterToTakeBike: this.editForm.get(['extraWaterToTakeBike'])!.value,
      orsToTakeBike: this.editForm.get(['orsToTakeBike'])!.value,
      gelsToTakeBike: this.editForm.get(['gelsToTakeBike'])!.value,
      sportDrinkToTakeRun: this.editForm.get(['sportDrinkToTakeRun'])!.value,
      waterToTakeRun: this.editForm.get(['waterToTakeRun'])!.value,
      extraWaterToTakeRun: this.editForm.get(['extraWaterToTakeRun'])!.value,
      orsToTakeRun: this.editForm.get(['orsToTakeRun'])!.value,
      gelsToTakeRun: this.editForm.get(['gelsToTakeRun'])!.value,
      weightLossDuringBike: this.editForm.get(['weightLossDuringBike'])!.value,
      carboNeedDuringRun: this.editForm.get(['carboNeedDuringRun'])!.value,
      fluidNeedPerHourBike: this.editForm.get(['fluidNeedPerHourBike'])!.value,
      fluidNeedPerHourSwim: this.editForm.get(['fluidNeedPerHourSwim'])!.value,
      carboNeedDuringBike: this.editForm.get(['carboNeedDuringBike'])!.value,
      fluidNeedDuringBike: this.editForm.get(['fluidNeedDuringBike'])!.value,
      fluidNeedPerHourRun: this.editForm.get(['fluidNeedPerHourRun'])!.value,
      fluidNeedDuringRun: this.editForm.get(['fluidNeedDuringRun'])!.value,
      weightLossDuringRun: this.editForm.get(['weightLossDuringRun'])!.value,
      diffCarboRun: this.editForm.get(['diffCarboRun'])!.value,
      diffMoisterRun: this.editForm.get(['diffMoisterRun'])!.value,
      difCarbo: this.editForm.get(['difCarbo'])!.value,
      difMoister: this.editForm.get(['difMoister'])!.value,
      actFluidNeedBike: this.editForm.get(['actFluidNeedBike'])!.value,
      actFluidNeedRun: this.editForm.get(['actFluidNeedRun'])!.value,
      carboNeedDuringBikeMin: this.editForm.get(['carboNeedDuringBikeMin'])!.value,
      carboNeedDuringBikeMax: this.editForm.get(['carboNeedDuringBikeMax'])!.value,
      carboNeedDuringRunMin: this.editForm.get(['carboNeedDuringRunMin'])!.value,
      carboNeedDuringRunMax: this.editForm.get(['carboNeedDuringRunMax'])!.value,
      startGel: this.editForm.get(['startGel'])!.value,
      startDrank: this.editForm.get(['startDrank'])!.value,
      race: this.editForm.get(['race'])!.value,
    };
  }
}
