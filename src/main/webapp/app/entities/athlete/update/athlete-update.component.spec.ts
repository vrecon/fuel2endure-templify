jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AthleteService } from '../service/athlete.service';
import { IAthlete, Athlete } from '../athlete.model';
import { IFuelSettings } from 'app/entities/fuel-settings/fuel-settings.model';
import { FuelSettingsService } from 'app/entities/fuel-settings/service/fuel-settings.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

import { AthleteUpdateComponent } from './athlete-update.component';

describe('Component Tests', () => {
  describe('Athlete Management Update Component', () => {
    let comp: AthleteUpdateComponent;
    let fixture: ComponentFixture<AthleteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let athleteService: AthleteService;
    let fuelSettingsService: FuelSettingsService;
    let userService: UserService;
    let voucherService: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AthleteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AthleteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      athleteService = TestBed.inject(AthleteService);
      fuelSettingsService = TestBed.inject(FuelSettingsService);
      userService = TestBed.inject(UserService);
      voucherService = TestBed.inject(VoucherService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call fuelSettings query and add missing value', () => {
        const athlete: IAthlete = { id: 456 };
        const fuelSettings: IFuelSettings = { id: 4397 };
        athlete.fuelSettings = fuelSettings;

        const fuelSettingsCollection: IFuelSettings[] = [{ id: 92143 }];
        jest.spyOn(fuelSettingsService, 'query').mockReturnValue(of(new HttpResponse({ body: fuelSettingsCollection })));
        const expectedCollection: IFuelSettings[] = [fuelSettings, ...fuelSettingsCollection];
        jest.spyOn(fuelSettingsService, 'addFuelSettingsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(fuelSettingsService.query).toHaveBeenCalled();
        expect(fuelSettingsService.addFuelSettingsToCollectionIfMissing).toHaveBeenCalledWith(fuelSettingsCollection, fuelSettings);
        expect(comp.fuelSettingsCollection).toEqual(expectedCollection);
      });

      it('Should call User query and add missing value', () => {
        const athlete: IAthlete = { id: 456 };
        const internalUser: IUser = { id: 39692 };
        athlete.internalUser = internalUser;

        const userCollection: IUser[] = [{ id: 38203 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [internalUser];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Voucher query and add missing value', () => {
        const athlete: IAthlete = { id: 456 };
        const voucher: IVoucher = { id: 9923 };
        athlete.voucher = voucher;

        const voucherCollection: IVoucher[] = [{ id: 18258 }];
        jest.spyOn(voucherService, 'query').mockReturnValue(of(new HttpResponse({ body: voucherCollection })));
        const additionalVouchers = [voucher];
        const expectedCollection: IVoucher[] = [...additionalVouchers, ...voucherCollection];
        jest.spyOn(voucherService, 'addVoucherToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(voucherService.query).toHaveBeenCalled();
        expect(voucherService.addVoucherToCollectionIfMissing).toHaveBeenCalledWith(voucherCollection, ...additionalVouchers);
        expect(comp.vouchersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const athlete: IAthlete = { id: 456 };
        const fuelSettings: IFuelSettings = { id: 86415 };
        athlete.fuelSettings = fuelSettings;
        const internalUser: IUser = { id: 67829 };
        athlete.internalUser = internalUser;
        const voucher: IVoucher = { id: 91562 };
        athlete.voucher = voucher;

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(athlete));
        expect(comp.fuelSettingsCollection).toContain(fuelSettings);
        expect(comp.usersSharedCollection).toContain(internalUser);
        expect(comp.vouchersSharedCollection).toContain(voucher);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = { id: 123 };
        jest.spyOn(athleteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: athlete }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(athleteService.update).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = new Athlete();
        jest.spyOn(athleteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: athlete }));
        saveSubject.complete();

        // THEN
        expect(athleteService.create).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = { id: 123 };
        jest.spyOn(athleteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(athleteService.update).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFuelSettingsById', () => {
        it('Should return tracked FuelSettings primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFuelSettingsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackVoucherById', () => {
        it('Should return tracked Voucher primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVoucherById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
