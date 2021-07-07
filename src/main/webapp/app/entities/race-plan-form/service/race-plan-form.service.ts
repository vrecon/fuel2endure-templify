import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRacePlanForm, getRacePlanFormIdentifier } from '../race-plan-form.model';

export type EntityResponseType = HttpResponse<IRacePlanForm>;
export type EntityArrayResponseType = HttpResponse<IRacePlanForm[]>;

@Injectable({ providedIn: 'root' })
export class RacePlanFormService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/race-plan-forms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(racePlanForm: IRacePlanForm): Observable<EntityResponseType> {
    return this.http.post<IRacePlanForm>(this.resourceUrl, racePlanForm, { observe: 'response' });
  }

  update(racePlanForm: IRacePlanForm): Observable<EntityResponseType> {
    return this.http.put<IRacePlanForm>(`${this.resourceUrl}/${getRacePlanFormIdentifier(racePlanForm) as number}`, racePlanForm, {
      observe: 'response',
    });
  }

  partialUpdate(racePlanForm: IRacePlanForm): Observable<EntityResponseType> {
    return this.http.patch<IRacePlanForm>(`${this.resourceUrl}/${getRacePlanFormIdentifier(racePlanForm) as number}`, racePlanForm, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRacePlanForm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRacePlanForm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRacePlanFormToCollectionIfMissing(
    racePlanFormCollection: IRacePlanForm[],
    ...racePlanFormsToCheck: (IRacePlanForm | null | undefined)[]
  ): IRacePlanForm[] {
    const racePlanForms: IRacePlanForm[] = racePlanFormsToCheck.filter(isPresent);
    if (racePlanForms.length > 0) {
      const racePlanFormCollectionIdentifiers = racePlanFormCollection.map(
        racePlanFormItem => getRacePlanFormIdentifier(racePlanFormItem)!
      );
      const racePlanFormsToAdd = racePlanForms.filter(racePlanFormItem => {
        const racePlanFormIdentifier = getRacePlanFormIdentifier(racePlanFormItem);
        if (racePlanFormIdentifier == null || racePlanFormCollectionIdentifiers.includes(racePlanFormIdentifier)) {
          return false;
        }
        racePlanFormCollectionIdentifiers.push(racePlanFormIdentifier);
        return true;
      });
      return [...racePlanFormsToAdd, ...racePlanFormCollection];
    }
    return racePlanFormCollection;
  }
}
