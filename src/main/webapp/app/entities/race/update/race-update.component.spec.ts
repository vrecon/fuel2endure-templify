jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RaceService } from '../service/race.service';
import { IRace, Race } from '../race.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';

import { RaceUpdateComponent } from './race-update.component';

describe('Component Tests', () => {
  describe('Race Management Update Component', () => {
    let comp: RaceUpdateComponent;
    let fixture: ComponentFixture<RaceUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let raceService: RaceService;
    let athleteService: AthleteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RaceUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RaceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RaceUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      raceService = TestBed.inject(RaceService);
      athleteService = TestBed.inject(AthleteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Athlete query and add missing value', () => {
        const race: IRace = { id: 456 };
        const athlete: IAthlete = { id: 32888 };
        race.athlete = athlete;

        const athleteCollection: IAthlete[] = [{ id: 57180 }];
        jest.spyOn(athleteService, 'query').mockReturnValue(of(new HttpResponse({ body: athleteCollection })));
        const additionalAthletes = [athlete];
        const expectedCollection: IAthlete[] = [...additionalAthletes, ...athleteCollection];
        jest.spyOn(athleteService, 'addAthleteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ race });
        comp.ngOnInit();

        expect(athleteService.query).toHaveBeenCalled();
        expect(athleteService.addAthleteToCollectionIfMissing).toHaveBeenCalledWith(athleteCollection, ...additionalAthletes);
        expect(comp.athletesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const race: IRace = { id: 456 };
        const athlete: IAthlete = { id: 25838 };
        race.athlete = athlete;

        activatedRoute.data = of({ race });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(race));
        expect(comp.athletesSharedCollection).toContain(athlete);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Race>>();
        const race = { id: 123 };
        jest.spyOn(raceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ race });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: race }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(raceService.update).toHaveBeenCalledWith(race);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Race>>();
        const race = new Race();
        jest.spyOn(raceService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ race });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: race }));
        saveSubject.complete();

        // THEN
        expect(raceService.create).toHaveBeenCalledWith(race);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Race>>();
        const race = { id: 123 };
        jest.spyOn(raceService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ race });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(raceService.update).toHaveBeenCalledWith(race);
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
