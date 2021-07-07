import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRacePlanForm, RacePlanForm } from '../race-plan-form.model';

import { RacePlanFormService } from './race-plan-form.service';

describe('Service Tests', () => {
  describe('RacePlanForm Service', () => {
    let service: RacePlanFormService;
    let httpMock: HttpTestingController;
    let elemDefault: IRacePlanForm;
    let expectedResult: IRacePlanForm | IRacePlanForm[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RacePlanFormService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        comp: 'AAAAAAA',
        name: 'AAAAAAA',
        selectedOrgGelQuery: false,
        selectedOrgDrankQuery: false,
        orsBeforeStart: 0,
        drinkCarbo: 0,
        gelCarbo: 0,
        drinkOrgCarbo: 0,
        gelOrgCarbo: 0,
        sportDrinkOrgBike: 0,
        waterOrgBike: 0,
        gelsOrgBike: 0,
        sportDrinkOrgRun: 0,
        waterOrgRun: 0,
        gelsOrgRun: 0,
        sportDrinkToTakeBike: 0,
        waterToTakeBike: 0,
        extraWaterToTakeBike: 0,
        orsToTakeBike: 0,
        gelsToTakeBike: 0,
        sportDrinkToTakeRun: 0,
        waterToTakeRun: 0,
        extraWaterToTakeRun: 0,
        orsToTakeRun: 0,
        gelsToTakeRun: 0,
        weightLossDuringBike: 0,
        carboNeedDuringRun: 0,
        fluidNeedPerHourBike: 0,
        fluidNeedPerHourSwim: 0,
        carboNeedDuringBike: 0,
        fluidNeedDuringBike: 0,
        fluidNeedPerHourRun: 0,
        fluidNeedDuringRun: 0,
        weightLossDuringRun: 0,
        diffCarboRun: 0,
        diffMoisterRun: 0,
        difCarbo: 0,
        difMoister: 0,
        actFluidNeedBike: 0,
        actFluidNeedRun: 0,
        carboNeedDuringBikeMin: 0,
        carboNeedDuringBikeMax: 0,
        carboNeedDuringRunMin: 0,
        carboNeedDuringRunMax: 0,
        startGel: 0,
        startDrank: 0,
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

      it('should create a RacePlanForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RacePlanForm()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RacePlanForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comp: 'BBBBBB',
            name: 'BBBBBB',
            selectedOrgGelQuery: true,
            selectedOrgDrankQuery: true,
            orsBeforeStart: 1,
            drinkCarbo: 1,
            gelCarbo: 1,
            drinkOrgCarbo: 1,
            gelOrgCarbo: 1,
            sportDrinkOrgBike: 1,
            waterOrgBike: 1,
            gelsOrgBike: 1,
            sportDrinkOrgRun: 1,
            waterOrgRun: 1,
            gelsOrgRun: 1,
            sportDrinkToTakeBike: 1,
            waterToTakeBike: 1,
            extraWaterToTakeBike: 1,
            orsToTakeBike: 1,
            gelsToTakeBike: 1,
            sportDrinkToTakeRun: 1,
            waterToTakeRun: 1,
            extraWaterToTakeRun: 1,
            orsToTakeRun: 1,
            gelsToTakeRun: 1,
            weightLossDuringBike: 1,
            carboNeedDuringRun: 1,
            fluidNeedPerHourBike: 1,
            fluidNeedPerHourSwim: 1,
            carboNeedDuringBike: 1,
            fluidNeedDuringBike: 1,
            fluidNeedPerHourRun: 1,
            fluidNeedDuringRun: 1,
            weightLossDuringRun: 1,
            diffCarboRun: 1,
            diffMoisterRun: 1,
            difCarbo: 1,
            difMoister: 1,
            actFluidNeedBike: 1,
            actFluidNeedRun: 1,
            carboNeedDuringBikeMin: 1,
            carboNeedDuringBikeMax: 1,
            carboNeedDuringRunMin: 1,
            carboNeedDuringRunMax: 1,
            startGel: 1,
            startDrank: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a RacePlanForm', () => {
        const patchObject = Object.assign(
          {
            comp: 'BBBBBB',
            selectedOrgGelQuery: true,
            drinkOrgCarbo: 1,
            waterOrgBike: 1,
            waterOrgRun: 1,
            orsToTakeBike: 1,
            sportDrinkToTakeRun: 1,
            extraWaterToTakeRun: 1,
            orsToTakeRun: 1,
            weightLossDuringBike: 1,
            fluidNeedPerHourSwim: 1,
            carboNeedDuringBike: 1,
            fluidNeedDuringBike: 1,
            fluidNeedPerHourRun: 1,
            fluidNeedDuringRun: 1,
            weightLossDuringRun: 1,
            diffCarboRun: 1,
            diffMoisterRun: 1,
            difMoister: 1,
            actFluidNeedRun: 1,
            carboNeedDuringBikeMin: 1,
            carboNeedDuringBikeMax: 1,
            startGel: 1,
            startDrank: 1,
          },
          new RacePlanForm()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RacePlanForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comp: 'BBBBBB',
            name: 'BBBBBB',
            selectedOrgGelQuery: true,
            selectedOrgDrankQuery: true,
            orsBeforeStart: 1,
            drinkCarbo: 1,
            gelCarbo: 1,
            drinkOrgCarbo: 1,
            gelOrgCarbo: 1,
            sportDrinkOrgBike: 1,
            waterOrgBike: 1,
            gelsOrgBike: 1,
            sportDrinkOrgRun: 1,
            waterOrgRun: 1,
            gelsOrgRun: 1,
            sportDrinkToTakeBike: 1,
            waterToTakeBike: 1,
            extraWaterToTakeBike: 1,
            orsToTakeBike: 1,
            gelsToTakeBike: 1,
            sportDrinkToTakeRun: 1,
            waterToTakeRun: 1,
            extraWaterToTakeRun: 1,
            orsToTakeRun: 1,
            gelsToTakeRun: 1,
            weightLossDuringBike: 1,
            carboNeedDuringRun: 1,
            fluidNeedPerHourBike: 1,
            fluidNeedPerHourSwim: 1,
            carboNeedDuringBike: 1,
            fluidNeedDuringBike: 1,
            fluidNeedPerHourRun: 1,
            fluidNeedDuringRun: 1,
            weightLossDuringRun: 1,
            diffCarboRun: 1,
            diffMoisterRun: 1,
            difCarbo: 1,
            difMoister: 1,
            actFluidNeedBike: 1,
            actFluidNeedRun: 1,
            carboNeedDuringBikeMin: 1,
            carboNeedDuringBikeMax: 1,
            carboNeedDuringRunMin: 1,
            carboNeedDuringRunMax: 1,
            startGel: 1,
            startDrank: 1,
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

      it('should delete a RacePlanForm', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRacePlanFormToCollectionIfMissing', () => {
        it('should add a RacePlanForm to an empty array', () => {
          const racePlanForm: IRacePlanForm = { id: 123 };
          expectedResult = service.addRacePlanFormToCollectionIfMissing([], racePlanForm);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(racePlanForm);
        });

        it('should not add a RacePlanForm to an array that contains it', () => {
          const racePlanForm: IRacePlanForm = { id: 123 };
          const racePlanFormCollection: IRacePlanForm[] = [
            {
              ...racePlanForm,
            },
            { id: 456 },
          ];
          expectedResult = service.addRacePlanFormToCollectionIfMissing(racePlanFormCollection, racePlanForm);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a RacePlanForm to an array that doesn't contain it", () => {
          const racePlanForm: IRacePlanForm = { id: 123 };
          const racePlanFormCollection: IRacePlanForm[] = [{ id: 456 }];
          expectedResult = service.addRacePlanFormToCollectionIfMissing(racePlanFormCollection, racePlanForm);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(racePlanForm);
        });

        it('should add only unique RacePlanForm to an array', () => {
          const racePlanFormArray: IRacePlanForm[] = [{ id: 123 }, { id: 456 }, { id: 79777 }];
          const racePlanFormCollection: IRacePlanForm[] = [{ id: 123 }];
          expectedResult = service.addRacePlanFormToCollectionIfMissing(racePlanFormCollection, ...racePlanFormArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const racePlanForm: IRacePlanForm = { id: 123 };
          const racePlanForm2: IRacePlanForm = { id: 456 };
          expectedResult = service.addRacePlanFormToCollectionIfMissing([], racePlanForm, racePlanForm2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(racePlanForm);
          expect(expectedResult).toContain(racePlanForm2);
        });

        it('should accept null and undefined values', () => {
          const racePlanForm: IRacePlanForm = { id: 123 };
          expectedResult = service.addRacePlanFormToCollectionIfMissing([], null, racePlanForm, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(racePlanForm);
        });

        it('should return initial array if no RacePlanForm is added', () => {
          const racePlanFormCollection: IRacePlanForm[] = [{ id: 123 }];
          expectedResult = service.addRacePlanFormToCollectionIfMissing(racePlanFormCollection, undefined, null);
          expect(expectedResult).toEqual(racePlanFormCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
