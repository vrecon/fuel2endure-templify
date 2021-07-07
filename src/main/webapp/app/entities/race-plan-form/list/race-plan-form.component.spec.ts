import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RacePlanFormService } from '../service/race-plan-form.service';

import { RacePlanFormComponent } from './race-plan-form.component';

describe('Component Tests', () => {
  describe('RacePlanForm Management Component', () => {
    let comp: RacePlanFormComponent;
    let fixture: ComponentFixture<RacePlanFormComponent>;
    let service: RacePlanFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RacePlanFormComponent],
      })
        .overrideTemplate(RacePlanFormComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RacePlanFormComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RacePlanFormService);

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
      expect(comp.racePlanForms?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
