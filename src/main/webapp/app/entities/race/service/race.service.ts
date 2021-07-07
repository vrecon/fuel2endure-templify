import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRace, getRaceIdentifier } from '../race.model';

export type EntityResponseType = HttpResponse<IRace>;
export type EntityArrayResponseType = HttpResponse<IRace[]>;

@Injectable({ providedIn: 'root' })
export class RaceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/races');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(race: IRace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(race);
    return this.http
      .post<IRace>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(race: IRace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(race);
    return this.http
      .put<IRace>(`${this.resourceUrl}/${getRaceIdentifier(race) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(race: IRace): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(race);
    return this.http
      .patch<IRace>(`${this.resourceUrl}/${getRaceIdentifier(race) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRace>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRace[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRaceToCollectionIfMissing(raceCollection: IRace[], ...racesToCheck: (IRace | null | undefined)[]): IRace[] {
    const races: IRace[] = racesToCheck.filter(isPresent);
    if (races.length > 0) {
      const raceCollectionIdentifiers = raceCollection.map(raceItem => getRaceIdentifier(raceItem)!);
      const racesToAdd = races.filter(raceItem => {
        const raceIdentifier = getRaceIdentifier(raceItem);
        if (raceIdentifier == null || raceCollectionIdentifiers.includes(raceIdentifier)) {
          return false;
        }
        raceCollectionIdentifiers.push(raceIdentifier);
        return true;
      });
      return [...racesToAdd, ...raceCollection];
    }
    return raceCollection;
  }

  protected convertDateFromClient(race: IRace): IRace {
    return Object.assign({}, race, {
      date: race.date?.isValid() ? race.date.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((race: IRace) => {
        race.date = race.date ? dayjs(race.date) : undefined;
      });
    }
    return res;
  }
}
