jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRacePlanForm, RacePlanForm } from '../race-plan-form.model';
import { RacePlanFormService } from '../service/race-plan-form.service';

import { RacePlanFormRoutingResolveService } from './race-plan-form-routing-resolve.service';

describe('Service Tests', () => {
  describe('RacePlanForm routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RacePlanFormRoutingResolveService;
    let service: RacePlanFormService;
    let resultRacePlanForm: IRacePlanForm | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RacePlanFormRoutingResolveService);
      service = TestBed.inject(RacePlanFormService);
      resultRacePlanForm = undefined;
    });

    describe('resolve', () => {
      it('should return IRacePlanForm returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRacePlanForm = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRacePlanForm).toEqual({ id: 123 });
      });

      it('should return new IRacePlanForm if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRacePlanForm = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRacePlanForm).toEqual(new RacePlanForm());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RacePlanForm })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRacePlanForm = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRacePlanForm).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
