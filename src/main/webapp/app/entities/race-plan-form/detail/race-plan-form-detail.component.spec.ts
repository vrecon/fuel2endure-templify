import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RacePlanFormDetailComponent } from './race-plan-form-detail.component';

describe('Component Tests', () => {
  describe('RacePlanForm Management Detail Component', () => {
    let comp: RacePlanFormDetailComponent;
    let fixture: ComponentFixture<RacePlanFormDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RacePlanFormDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ racePlanForm: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RacePlanFormDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RacePlanFormDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load racePlanForm on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.racePlanForm).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
