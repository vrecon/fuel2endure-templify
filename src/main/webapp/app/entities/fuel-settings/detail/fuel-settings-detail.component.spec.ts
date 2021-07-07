import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FuelSettingsDetailComponent } from './fuel-settings-detail.component';

describe('Component Tests', () => {
  describe('FuelSettings Management Detail Component', () => {
    let comp: FuelSettingsDetailComponent;
    let fixture: ComponentFixture<FuelSettingsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FuelSettingsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fuelSettings: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FuelSettingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuelSettingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fuelSettings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fuelSettings).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
