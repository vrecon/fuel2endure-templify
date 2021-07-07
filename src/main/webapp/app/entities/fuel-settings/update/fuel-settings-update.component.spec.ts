jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FuelSettingsService } from '../service/fuel-settings.service';
import { IFuelSettings, FuelSettings } from '../fuel-settings.model';

import { FuelSettingsUpdateComponent } from './fuel-settings-update.component';

describe('Component Tests', () => {
  describe('FuelSettings Management Update Component', () => {
    let comp: FuelSettingsUpdateComponent;
    let fixture: ComponentFixture<FuelSettingsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fuelSettingsService: FuelSettingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FuelSettingsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FuelSettingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuelSettingsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fuelSettingsService = TestBed.inject(FuelSettingsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fuelSettings: IFuelSettings = { id: 456 };

        activatedRoute.data = of({ fuelSettings });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fuelSettings));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuelSettings>>();
        const fuelSettings = { id: 123 };
        jest.spyOn(fuelSettingsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fuelSettings });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fuelSettings }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fuelSettingsService.update).toHaveBeenCalledWith(fuelSettings);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuelSettings>>();
        const fuelSettings = new FuelSettings();
        jest.spyOn(fuelSettingsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fuelSettings });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fuelSettings }));
        saveSubject.complete();

        // THEN
        expect(fuelSettingsService.create).toHaveBeenCalledWith(fuelSettings);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FuelSettings>>();
        const fuelSettings = { id: 123 };
        jest.spyOn(fuelSettingsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ fuelSettings });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fuelSettingsService.update).toHaveBeenCalledWith(fuelSettings);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
