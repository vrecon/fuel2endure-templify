import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRacePlanForm, RacePlanForm } from '../race-plan-form.model';
import { RacePlanFormService } from '../service/race-plan-form.service';

@Injectable({ providedIn: 'root' })
export class RacePlanFormRoutingResolveService implements Resolve<IRacePlanForm> {
  constructor(protected service: RacePlanFormService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRacePlanForm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((racePlanForm: HttpResponse<RacePlanForm>) => {
          if (racePlanForm.body) {
            return of(racePlanForm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RacePlanForm());
  }
}
