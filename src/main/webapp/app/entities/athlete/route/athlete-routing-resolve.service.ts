import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAthlete, Athlete } from '../athlete.model';
import { AthleteService } from '../service/athlete.service';

@Injectable({ providedIn: 'root' })
export class AthleteRoutingResolveService implements Resolve<IAthlete> {
  constructor(protected service: AthleteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthlete> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((athlete: HttpResponse<Athlete>) => {
          if (athlete.body) {
            return of(athlete.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Athlete());
  }
}
