import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITraining, Training } from '../training.model';
import { TrainingService } from '../service/training.service';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';

@Component({
  selector: 'jhi-training-update',
  templateUrl: './training-update.component.html',
})
export class TrainingUpdateComponent implements OnInit {
  isSaving = false;

  athletesSharedCollection: IAthlete[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    trainingTypeCode: [],
    duration: [],
    trainingIntensityCode: [],
    temperature: [],
    weightBefore: [],
    weightAfter: [],
    drunk: [],
    eaten: [],
    moistureLossPercentage: [],
    moistureLossPerHour: [],
    defaultMoisterLossPerHour: [],
    deltaMoisterLossPerHour: [],
    excluded: [],
    comments: [],
    carboDrank: [],
    athlete: [],
  });

  constructor(
    protected trainingService: TrainingService,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ training }) => {
      this.updateForm(training);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const training = this.createFromForm();
    if (training.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingService.update(training));
    } else {
      this.subscribeToSaveResponse(this.trainingService.create(training));
    }
  }

  trackAthleteById(index: number, item: IAthlete): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraining>>): void {
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

  protected updateForm(training: ITraining): void {
    this.editForm.patchValue({
      id: training.id,
      date: training.date,
      trainingTypeCode: training.trainingTypeCode,
      duration: training.duration,
      trainingIntensityCode: training.trainingIntensityCode,
      temperature: training.temperature,
      weightBefore: training.weightBefore,
      weightAfter: training.weightAfter,
      drunk: training.drunk,
      eaten: training.eaten,
      moistureLossPercentage: training.moistureLossPercentage,
      moistureLossPerHour: training.moistureLossPerHour,
      defaultMoisterLossPerHour: training.defaultMoisterLossPerHour,
      deltaMoisterLossPerHour: training.deltaMoisterLossPerHour,
      excluded: training.excluded,
      comments: training.comments,
      carboDrank: training.carboDrank,
      athlete: training.athlete,
    });

    this.athletesSharedCollection = this.athleteService.addAthleteToCollectionIfMissing(this.athletesSharedCollection, training.athlete);
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

  protected createFromForm(): ITraining {
    return {
      ...new Training(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      trainingTypeCode: this.editForm.get(['trainingTypeCode'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      trainingIntensityCode: this.editForm.get(['trainingIntensityCode'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      weightBefore: this.editForm.get(['weightBefore'])!.value,
      weightAfter: this.editForm.get(['weightAfter'])!.value,
      drunk: this.editForm.get(['drunk'])!.value,
      eaten: this.editForm.get(['eaten'])!.value,
      moistureLossPercentage: this.editForm.get(['moistureLossPercentage'])!.value,
      moistureLossPerHour: this.editForm.get(['moistureLossPerHour'])!.value,
      defaultMoisterLossPerHour: this.editForm.get(['defaultMoisterLossPerHour'])!.value,
      deltaMoisterLossPerHour: this.editForm.get(['deltaMoisterLossPerHour'])!.value,
      excluded: this.editForm.get(['excluded'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      carboDrank: this.editForm.get(['carboDrank'])!.value,
      athlete: this.editForm.get(['athlete'])!.value,
    };
  }
}
