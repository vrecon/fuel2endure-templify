import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelSettings, FuelSettings } from '../fuel-settings.model';
import { FuelSettingsService } from '../service/fuel-settings.service';

@Injectable({ providedIn: 'root' })
export class FuelSettingsRoutingResolveService implements Resolve<IFuelSettings> {
  constructor(protected service: FuelSettingsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuelSettings> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fuelSettings: HttpResponse<FuelSettings>) => {
          if (fuelSettings.body) {
            return of(fuelSettings.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FuelSettings());
  }
}
