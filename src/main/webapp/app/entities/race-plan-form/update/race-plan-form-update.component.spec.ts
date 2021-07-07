jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RacePlanFormService } from '../service/race-plan-form.service';
import { IRacePlanForm, RacePlanForm } from '../race-plan-form.model';
import { IRace } from 'app/entities/race/race.model';
import { RaceService } from 'app/entities/race/service/race.service';

import { RacePlanFormUpdateComponent } from './race-plan-form-update.component';

describe('Component Tests', () => {
  describe('RacePlanForm Management Update Component', () => {
    let comp: RacePlanFormUpdateComponent;
    let fixture: ComponentFixture<RacePlanFormUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let racePlanFormService: RacePlanFormService;
    let raceService: RaceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RacePlanFormUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RacePlanFormUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RacePlanFormUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      racePlanFormService = TestBed.inject(RacePlanFormService);
      raceService = TestBed.inject(RaceService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Race query and add missing value', () => {
        const racePlanForm: IRacePlanForm = { id: 456 };
        const race: IRace = { id: 30521 };
        racePlanForm.race = race;

        const raceCollection: IRace[] = [{ id: 46617 }];
        jest.spyOn(raceService, 'query').mockReturnValue(of(new HttpResponse({ body: raceCollection })));
        const additionalRaces = [race];
        const expectedCollection: IRace[] = [...additionalRaces, ...raceCollection];
        jest.spyOn(raceService, 'addRaceToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ racePlanForm });
        comp.ngOnInit();

        expect(raceService.query).toHaveBeenCalled();
        expect(raceService.addRaceToCollectionIfMissing).toHaveBeenCalledWith(raceCollection, ...additionalRaces);
        expect(comp.racesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const racePlanForm: IRacePlanForm = { id: 456 };
        const race: IRace = { id: 1937 };
        racePlanForm.race = race;

        activatedRoute.data = of({ racePlanForm });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(racePlanForm));
        expect(comp.racesSharedCollection).toContain(race);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RacePlanForm>>();
        const racePlanForm = { id: 123 };
        jest.spyOn(racePlanFormService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ racePlanForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: racePlanForm }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(racePlanFormService.update).toHaveBeenCalledWith(racePlanForm);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RacePlanForm>>();
        const racePlanForm = new RacePlanForm();
        jest.spyOn(racePlanFormService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ racePlanForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: racePlanForm }));
        saveSubject.complete();

        // THEN
        expect(racePlanFormService.create).toHaveBeenCalledWith(racePlanForm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RacePlanForm>>();
        const racePlanForm = { id: 123 };
        jest.spyOn(racePlanFormService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ racePlanForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(racePlanFormService.update).toHaveBeenCalledWith(racePlanForm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRaceById', () => {
        it('Should return tracked Race primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRaceById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
