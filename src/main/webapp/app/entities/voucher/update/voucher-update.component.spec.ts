jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoucherService } from '../service/voucher.service';
import { IVoucher, Voucher } from '../voucher.model';

import { VoucherUpdateComponent } from './voucher-update.component';

describe('Component Tests', () => {
  describe('Voucher Management Update Component', () => {
    let comp: VoucherUpdateComponent;
    let fixture: ComponentFixture<VoucherUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voucherService: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoucherUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voucherService = TestBed.inject(VoucherService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const voucher: IVoucher = { id: 456 };

        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voucher));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = { id: 123 };
        jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucher }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voucherService.update).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = new Voucher();
        jest.spyOn(voucherService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucher }));
        saveSubject.complete();

        // THEN
        expect(voucherService.create).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = { id: 123 };
        jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voucherService.update).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
