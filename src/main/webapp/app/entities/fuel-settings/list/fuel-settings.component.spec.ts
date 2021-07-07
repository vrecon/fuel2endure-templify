import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FuelSettingsService } from '../service/fuel-settings.service';

import { FuelSettingsComponent } from './fuel-settings.component';

describe('Component Tests', () => {
  describe('FuelSettings Management Component', () => {
    let comp: FuelSettingsComponent;
    let fixture: ComponentFixture<FuelSettingsComponent>;
    let service: FuelSettingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FuelSettingsComponent],
      })
        .overrideTemplate(FuelSettingsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuelSettingsComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FuelSettingsService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fuelSettings?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
