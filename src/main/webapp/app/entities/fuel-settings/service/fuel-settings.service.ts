import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelSettings, getFuelSettingsIdentifier } from '../fuel-settings.model';

export type EntityResponseType = HttpResponse<IFuelSettings>;
export type EntityArrayResponseType = HttpResponse<IFuelSettings[]>;

@Injectable({ providedIn: 'root' })
export class FuelSettingsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fuelSettings: IFuelSettings): Observable<EntityResponseType> {
    return this.http.post<IFuelSettings>(this.resourceUrl, fuelSettings, { observe: 'response' });
  }

  update(fuelSettings: IFuelSettings): Observable<EntityResponseType> {
    return this.http.put<IFuelSettings>(`${this.resourceUrl}/${getFuelSettingsIdentifier(fuelSettings) as number}`, fuelSettings, {
      observe: 'response',
    });
  }

  partialUpdate(fuelSettings: IFuelSettings): Observable<EntityResponseType> {
    return this.http.patch<IFuelSettings>(`${this.resourceUrl}/${getFuelSettingsIdentifier(fuelSettings) as number}`, fuelSettings, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelSettings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFuelSettingsToCollectionIfMissing(
    fuelSettingsCollection: IFuelSettings[],
    ...fuelSettingsToCheck: (IFuelSettings | null | undefined)[]
  ): IFuelSettings[] {
    const fuelSettings: IFuelSettings[] = fuelSettingsToCheck.filter(isPresent);
    if (fuelSettings.length > 0) {
      const fuelSettingsCollectionIdentifiers = fuelSettingsCollection.map(
        fuelSettingsItem => getFuelSettingsIdentifier(fuelSettingsItem)!
      );
      const fuelSettingsToAdd = fuelSettings.filter(fuelSettingsItem => {
        const fuelSettingsIdentifier = getFuelSettingsIdentifier(fuelSettingsItem);
        if (fuelSettingsIdentifier == null || fuelSettingsCollectionIdentifiers.includes(fuelSettingsIdentifier)) {
          return false;
        }
        fuelSettingsCollectionIdentifiers.push(fuelSettingsIdentifier);
        return true;
      });
      return [...fuelSettingsToAdd, ...fuelSettingsCollection];
    }
    return fuelSettingsCollection;
  }
}
