import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRace, Race } from '../race.model';
import { RaceService } from '../service/race.service';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';

@Component({
  selector: 'jhi-race-update',
  templateUrl: './race-update.component.html',
})
export class RaceUpdateComponent implements OnInit {
  isSaving = false;

  athletesSharedCollection: IAthlete[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    name: [],
    logging: [],
    weight: [],
    length: [],
    temperature: [],
    comp: [],
    swimDuration: [],
    bikeDuration: [],
    runDuration: [],
    gelCarbo: [],
    drinkCarbo: [],
    drinkOrgCarbo: [],
    gelOrgCarbo: [],
    gelsbike: [],
    gelsrun: [],
    selectedOrgGelQuery: [],
    selectedOrgDrankQuery: [],
    sportDrinkOrgBike: [],
    waterOrgBike: [],
    gelsOrgBike: [],
    sportDrinkOrgRun: [],
    waterOrgRun: [],
    gelsOrgRun: [],
    orsBeforeStart: [],
    sportDrinkToTakeBike: [],
    waterToTakeBike: [],
    extraWaterToTakeBike: [],
    orsToTakeBike: [],
    gelsToTakeBike: [],
    sportDrinkToTakeRun: [],
    waterToTakeRun: [],
    extraWaterToTakeRun: [],
    orsToTakeRun: [],
    carboNeedDuringBikeMin: [],
    carboNeedDuringBikeMax: [],
    carboNeedDuringRunMin: [],
    carboNeedDuringRunMax: [],
    diffCarboRun: [],
    diffMoisterRun: [],
    difCarbo: [],
    difMoister: [],
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
    actFluidNeedBike: [],
    actFluidNeedRun: [],
    athlete: [],
  });

  constructor(
    protected raceService: RaceService,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ race }) => {
      this.updateForm(race);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const race = this.createFromForm();
    if (race.id !== undefined) {
      this.subscribeToSaveResponse(this.raceService.update(race));
    } else {
      this.subscribeToSaveResponse(this.raceService.create(race));
    }
  }

  trackAthleteById(index: number, item: IAthlete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRace>>): void {
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

  protected updateForm(race: IRace): void {
    this.editForm.patchValue({
      id: race.id,
      date: race.date,
      name: race.name,
      logging: race.logging,
      weight: race.weight,
      length: race.length,
      temperature: race.temperature,
      comp: race.comp,
      swimDuration: race.swimDuration,
      bikeDuration: race.bikeDuration,
      runDuration: race.runDuration,
      gelCarbo: race.gelCarbo,
      drinkCarbo: race.drinkCarbo,
      drinkOrgCarbo: race.drinkOrgCarbo,
      gelOrgCarbo: race.gelOrgCarbo,
      gelsbike: race.gelsbike,
      gelsrun: race.gelsrun,
      selectedOrgGelQuery: race.selectedOrgGelQuery,
      selectedOrgDrankQuery: race.selectedOrgDrankQuery,
      sportDrinkOrgBike: race.sportDrinkOrgBike,
      waterOrgBike: race.waterOrgBike,
      gelsOrgBike: race.gelsOrgBike,
      sportDrinkOrgRun: race.sportDrinkOrgRun,
      waterOrgRun: race.waterOrgRun,
      gelsOrgRun: race.gelsOrgRun,
      orsBeforeStart: race.orsBeforeStart,
      sportDrinkToTakeBike: race.sportDrinkToTakeBike,
      waterToTakeBike: race.waterToTakeBike,
      extraWaterToTakeBike: race.extraWaterToTakeBike,
      orsToTakeBike: race.orsToTakeBike,
      gelsToTakeBike: race.gelsToTakeBike,
      sportDrinkToTakeRun: race.sportDrinkToTakeRun,
      waterToTakeRun: race.waterToTakeRun,
      extraWaterToTakeRun: race.extraWaterToTakeRun,
      orsToTakeRun: race.orsToTakeRun,
      carboNeedDuringBikeMin: race.carboNeedDuringBikeMin,
      carboNeedDuringBikeMax: race.carboNeedDuringBikeMax,
      carboNeedDuringRunMin: race.carboNeedDuringRunMin,
      carboNeedDuringRunMax: race.carboNeedDuringRunMax,
      diffCarboRun: race.diffCarboRun,
      diffMoisterRun: race.diffMoisterRun,
      difCarbo: race.difCarbo,
      difMoister: race.difMoister,
      gelsToTakeRun: race.gelsToTakeRun,
      weightLossDuringBike: race.weightLossDuringBike,
      carboNeedDuringRun: race.carboNeedDuringRun,
      fluidNeedPerHourBike: race.fluidNeedPerHourBike,
      fluidNeedPerHourSwim: race.fluidNeedPerHourSwim,
      carboNeedDuringBike: race.carboNeedDuringBike,
      fluidNeedDuringBike: race.fluidNeedDuringBike,
      fluidNeedPerHourRun: race.fluidNeedPerHourRun,
      fluidNeedDuringRun: race.fluidNeedDuringRun,
      weightLossDuringRun: race.weightLossDuringRun,
      actFluidNeedBike: race.actFluidNeedBike,
      actFluidNeedRun: race.actFluidNeedRun,
      athlete: race.athlete,
    });

    this.athletesSharedCollection = this.athleteService.addAthleteToCollectionIfMissing(this.athletesSharedCollection, race.athlete);
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

  protected createFromForm(): IRace {
    return {
      ...new Race(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      name: this.editForm.get(['name'])!.value,
      logging: this.editForm.get(['logging'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      length: this.editForm.get(['length'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      comp: this.editForm.get(['comp'])!.value,
      swimDuration: this.editForm.get(['swimDuration'])!.value,
      bikeDuration: this.editForm.get(['bikeDuration'])!.value,
      runDuration: this.editForm.get(['runDuration'])!.value,
      gelCarbo: this.editForm.get(['gelCarbo'])!.value,
      drinkCarbo: this.editForm.get(['drinkCarbo'])!.value,
      drinkOrgCarbo: this.editForm.get(['drinkOrgCarbo'])!.value,
      gelOrgCarbo: this.editForm.get(['gelOrgCarbo'])!.value,
      gelsbike: this.editForm.get(['gelsbike'])!.value,
      gelsrun: this.editForm.get(['gelsrun'])!.value,
      selectedOrgGelQuery: this.editForm.get(['selectedOrgGelQuery'])!.value,
      selectedOrgDrankQuery: this.editForm.get(['selectedOrgDrankQuery'])!.value,
      sportDrinkOrgBike: this.editForm.get(['sportDrinkOrgBike'])!.value,
      waterOrgBike: this.editForm.get(['waterOrgBike'])!.value,
      gelsOrgBike: this.editForm.get(['gelsOrgBike'])!.value,
      sportDrinkOrgRun: this.editForm.get(['sportDrinkOrgRun'])!.value,
      waterOrgRun: this.editForm.get(['waterOrgRun'])!.value,
      gelsOrgRun: this.editForm.get(['gelsOrgRun'])!.value,
      orsBeforeStart: this.editForm.get(['orsBeforeStart'])!.value,
      sportDrinkToTakeBike: this.editForm.get(['sportDrinkToTakeBike'])!.value,
      waterToTakeBike: this.editForm.get(['waterToTakeBike'])!.value,
      extraWaterToTakeBike: this.editForm.get(['extraWaterToTakeBike'])!.value,
      orsToTakeBike: this.editForm.get(['orsToTakeBike'])!.value,
      gelsToTakeBike: this.editForm.get(['gelsToTakeBike'])!.value,
      sportDrinkToTakeRun: this.editForm.get(['sportDrinkToTakeRun'])!.value,
      waterToTakeRun: this.editForm.get(['waterToTakeRun'])!.value,
      extraWaterToTakeRun: this.editForm.get(['extraWaterToTakeRun'])!.value,
      orsToTakeRun: this.editForm.get(['orsToTakeRun'])!.value,
      carboNeedDuringBikeMin: this.editForm.get(['carboNeedDuringBikeMin'])!.value,
      carboNeedDuringBikeMax: this.editForm.get(['carboNeedDuringBikeMax'])!.value,
      carboNeedDuringRunMin: this.editForm.get(['carboNeedDuringRunMin'])!.value,
      carboNeedDuringRunMax: this.editForm.get(['carboNeedDuringRunMax'])!.value,
      diffCarboRun: this.editForm.get(['diffCarboRun'])!.value,
      diffMoisterRun: this.editForm.get(['diffMoisterRun'])!.value,
      difCarbo: this.editForm.get(['difCarbo'])!.value,
      difMoister: this.editForm.get(['difMoister'])!.value,
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
      actFluidNeedBike: this.editForm.get(['actFluidNeedBike'])!.value,
      actFluidNeedRun: this.editForm.get(['actFluidNeedRun'])!.value,
      athlete: this.editForm.get(['athlete'])!.value,
    };
  }
}
