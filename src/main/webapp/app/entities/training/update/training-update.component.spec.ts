jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TrainingService } from '../service/training.service';
import { ITraining, Training } from '../training.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';

import { TrainingUpdateComponent } from './training-update.component';

describe('Component Tests', () => {
  describe('Training Management Update Component', () => {
    let comp: TrainingUpdateComponent;
    let fixture: ComponentFixture<TrainingUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let trainingService: TrainingService;
    let athleteService: AthleteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TrainingUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TrainingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainingUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      trainingService = TestBed.inject(TrainingService);
      athleteService = TestBed.inject(AthleteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Athlete query and add missing value', () => {
        const training: ITraining = { id: 456 };
        const athlete: IAthlete = { id: 15521 };
        training.athlete = athlete;

        const athleteCollection: IAthlete[] = [{ id: 84756 }];
        jest.spyOn(athleteService, 'query').mockReturnValue(of(new HttpResponse({ body: athleteCollection })));
        const additionalAthletes = [athlete];
        const expectedCollection: IAthlete[] = [...additionalAthletes, ...athleteCollection];
        jest.spyOn(athleteService, 'addAthleteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ training });
        comp.ngOnInit();

        expect(athleteService.query).toHaveBeenCalled();
        expect(athleteService.addAthleteToCollectionIfMissing).toHaveBeenCalledWith(athleteCollection, ...additionalAthletes);
        expect(comp.athletesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const training: ITraining = { id: 456 };
        const athlete: IAthlete = { id: 23539 };
        training.athlete = athlete;

        activatedRoute.data = of({ training });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(training));
        expect(comp.athletesSharedCollection).toContain(athlete);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Training>>();
        const training = { id: 123 };
        jest.spyOn(trainingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ training });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: training }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(trainingService.update).toHaveBeenCalledWith(training);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Training>>();
        const training = new Training();
        jest.spyOn(trainingService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ training });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: training }));
        saveSubject.complete();

        // THEN
        expect(trainingService.create).toHaveBeenCalledWith(training);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Training>>();
        const training = { id: 123 };
        jest.spyOn(trainingService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ training });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(trainingService.update).toHaveBeenCalledWith(training);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAthleteById', () => {
        it('Should return tracked Athlete primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAthleteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
