import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RaceService } from '../service/race.service';

import { RaceComponent } from './race.component';

describe('Component Tests', () => {
  describe('Race Management Component', () => {
    let comp: RaceComponent;
    let fixture: ComponentFixture<RaceComponent>;
    let service: RaceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RaceComponent],
      })
        .overrideTemplate(RaceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RaceComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RaceService);

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
      expect(comp.races?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
