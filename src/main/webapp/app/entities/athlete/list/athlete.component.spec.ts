import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AthleteService } from '../service/athlete.service';

import { AthleteComponent } from './athlete.component';

describe('Component Tests', () => {
  describe('Athlete Management Component', () => {
    let comp: AthleteComponent;
    let fixture: ComponentFixture<AthleteComponent>;
    let service: AthleteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AthleteComponent],
      })
        .overrideTemplate(AthleteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AthleteService);

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
      expect(comp.athletes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
