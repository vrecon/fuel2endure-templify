import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RaceDetailComponent } from './race-detail.component';

describe('Component Tests', () => {
  describe('Race Management Detail Component', () => {
    let comp: RaceDetailComponent;
    let fixture: ComponentFixture<RaceDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RaceDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ race: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RaceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RaceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load race on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.race).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
