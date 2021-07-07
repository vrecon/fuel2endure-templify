jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FuelSettingsService } from '../service/fuel-settings.service';

import { FuelSettingsDeleteDialogComponent } from './fuel-settings-delete-dialog.component';

describe('Component Tests', () => {
  describe('FuelSettings Management Delete Component', () => {
    let comp: FuelSettingsDeleteDialogComponent;
    let fixture: ComponentFixture<FuelSettingsDeleteDialogComponent>;
    let service: FuelSettingsService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FuelSettingsDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(FuelSettingsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuelSettingsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FuelSettingsService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
