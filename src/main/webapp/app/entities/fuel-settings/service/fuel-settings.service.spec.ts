import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFuelSettings, FuelSettings } from '../fuel-settings.model';

import { FuelSettingsService } from './fuel-settings.service';

describe('Service Tests', () => {
  describe('FuelSettings Service', () => {
    let service: FuelSettingsService;
    let httpMock: HttpTestingController;
    let elemDefault: IFuelSettings;
    let expectedResult: IFuelSettings | IFuelSettings[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FuelSettingsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        carbohydratesGel: 0,
        carbohydratesSportDrank: 0,
        selectedOwnGelItem: 0,
        selectedOwnDrinkItem: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FuelSettings', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FuelSettings()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FuelSettings', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            carbohydratesGel: 1,
            carbohydratesSportDrank: 1,
            selectedOwnGelItem: 1,
            selectedOwnDrinkItem: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FuelSettings', () => {
        const patchObject = Object.assign(
          {
            carbohydratesGel: 1,
            selectedOwnDrinkItem: 1,
          },
          new FuelSettings()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FuelSettings', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            carbohydratesGel: 1,
            carbohydratesSportDrank: 1,
            selectedOwnGelItem: 1,
            selectedOwnDrinkItem: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FuelSettings', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFuelSettingsToCollectionIfMissing', () => {
        it('should add a FuelSettings to an empty array', () => {
          const fuelSettings: IFuelSettings = { id: 123 };
          expectedResult = service.addFuelSettingsToCollectionIfMissing([], fuelSettings);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fuelSettings);
        });

        it('should not add a FuelSettings to an array that contains it', () => {
          const fuelSettings: IFuelSettings = { id: 123 };
          const fuelSettingsCollection: IFuelSettings[] = [
            {
              ...fuelSettings,
            },
            { id: 456 },
          ];
          expectedResult = service.addFuelSettingsToCollectionIfMissing(fuelSettingsCollection, fuelSettings);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FuelSettings to an array that doesn't contain it", () => {
          const fuelSettings: IFuelSettings = { id: 123 };
          const fuelSettingsCollection: IFuelSettings[] = [{ id: 456 }];
          expectedResult = service.addFuelSettingsToCollectionIfMissing(fuelSettingsCollection, fuelSettings);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fuelSettings);
        });

        it('should add only unique FuelSettings to an array', () => {
          const fuelSettingsArray: IFuelSettings[] = [{ id: 123 }, { id: 456 }, { id: 81036 }];
          const fuelSettingsCollection: IFuelSettings[] = [{ id: 123 }];
          expectedResult = service.addFuelSettingsToCollectionIfMissing(fuelSettingsCollection, ...fuelSettingsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fuelSettings: IFuelSettings = { id: 123 };
          const fuelSettings2: IFuelSettings = { id: 456 };
          expectedResult = service.addFuelSettingsToCollectionIfMissing([], fuelSettings, fuelSettings2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fuelSettings);
          expect(expectedResult).toContain(fuelSettings2);
        });

        it('should accept null and undefined values', () => {
          const fuelSettings: IFuelSettings = { id: 123 };
          expectedResult = service.addFuelSettingsToCollectionIfMissing([], null, fuelSettings, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fuelSettings);
        });

        it('should return initial array if no FuelSettings is added', () => {
          const fuelSettingsCollection: IFuelSettings[] = [{ id: 123 }];
          expectedResult = service.addFuelSettingsToCollectionIfMissing(fuelSettingsCollection, undefined, null);
          expect(expectedResult).toEqual(fuelSettingsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
