jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFuelSettings, FuelSettings } from '../fuel-settings.model';
import { FuelSettingsService } from '../service/fuel-settings.service';

import { FuelSettingsRoutingResolveService } from './fuel-settings-routing-resolve.service';

describe('Service Tests', () => {
  describe('FuelSettings routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FuelSettingsRoutingResolveService;
    let service: FuelSettingsService;
    let resultFuelSettings: IFuelSettings | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FuelSettingsRoutingResolveService);
      service = TestBed.inject(FuelSettingsService);
      resultFuelSettings = undefined;
    });

    describe('resolve', () => {
      it('should return IFuelSettings returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuelSettings = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFuelSettings).toEqual({ id: 123 });
      });

      it('should return new IFuelSettings if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuelSettings = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFuelSettings).toEqual(new FuelSettings());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FuelSettings })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuelSettings = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFuelSettings).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
