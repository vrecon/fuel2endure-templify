import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRace, Race } from '../race.model';

import { RaceService } from './race.service';

describe('Service Tests', () => {
  describe('Race Service', () => {
    let service: RaceService;
    let httpMock: HttpTestingController;
    let elemDefault: IRace;
    let expectedResult: IRace | IRace[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RaceService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        date: currentDate,
        name: 'AAAAAAA',
        logging: 'AAAAAAA',
        weight: 0,
        length: 0,
        temperature: 0,
        comp: 0,
        swimDuration: 0,
        bikeDuration: 0,
        runDuration: 0,
        gelCarbo: 0,
        drinkCarbo: 0,
        drinkOrgCarbo: 0,
        gelOrgCarbo: 0,
        gelsbike: 0,
        gelsrun: 0,
        selectedOrgGelQuery: false,
        selectedOrgDrankQuery: false,
        sportDrinkOrgBike: 0,
        waterOrgBike: 0,
        gelsOrgBike: 0,
        sportDrinkOrgRun: 0,
        waterOrgRun: 0,
        gelsOrgRun: 0,
        orsBeforeStart: 0,
        sportDrinkToTakeBike: 0,
        waterToTakeBike: 0,
        extraWaterToTakeBike: 0,
        orsToTakeBike: 0,
        gelsToTakeBike: 0,
        sportDrinkToTakeRun: 0,
        waterToTakeRun: 0,
        extraWaterToTakeRun: 0,
        orsToTakeRun: 0,
        carboNeedDuringBikeMin: 0,
        carboNeedDuringBikeMax: 0,
        carboNeedDuringRunMin: 0,
        carboNeedDuringRunMax: 0,
        diffCarboRun: 0,
        diffMoisterRun: 0,
        difCarbo: 0,
        difMoister: 0,
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
        actFluidNeedBike: 0,
        actFluidNeedRun: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Race', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new Race()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Race', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            date: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            logging: 'BBBBBB',
            weight: 1,
            length: 1,
            temperature: 1,
            comp: 1,
            swimDuration: 1,
            bikeDuration: 1,
            runDuration: 1,
            gelCarbo: 1,
            drinkCarbo: 1,
            drinkOrgCarbo: 1,
            gelOrgCarbo: 1,
            gelsbike: 1,
            gelsrun: 1,
            selectedOrgGelQuery: true,
            selectedOrgDrankQuery: true,
            sportDrinkOrgBike: 1,
            waterOrgBike: 1,
            gelsOrgBike: 1,
            sportDrinkOrgRun: 1,
            waterOrgRun: 1,
            gelsOrgRun: 1,
            orsBeforeStart: 1,
            sportDrinkToTakeBike: 1,
            waterToTakeBike: 1,
            extraWaterToTakeBike: 1,
            orsToTakeBike: 1,
            gelsToTakeBike: 1,
            sportDrinkToTakeRun: 1,
            waterToTakeRun: 1,
            extraWaterToTakeRun: 1,
            orsToTakeRun: 1,
            carboNeedDuringBikeMin: 1,
            carboNeedDuringBikeMax: 1,
            carboNeedDuringRunMin: 1,
            carboNeedDuringRunMax: 1,
            diffCarboRun: 1,
            diffMoisterRun: 1,
            difCarbo: 1,
            difMoister: 1,
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
            actFluidNeedBike: 1,
            actFluidNeedRun: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Race', () => {
        const patchObject = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            logging: 'BBBBBB',
            temperature: 1,
            comp: 1,
            runDuration: 1,
            gelCarbo: 1,
            drinkOrgCarbo: 1,
            gelsbike: 1,
            gelsrun: 1,
            selectedOrgGelQuery: true,
            gelsOrgBike: 1,
            sportDrinkOrgRun: 1,
            waterOrgRun: 1,
            sportDrinkToTakeBike: 1,
            waterToTakeBike: 1,
            orsToTakeBike: 1,
            gelsToTakeBike: 1,
            extraWaterToTakeRun: 1,
            carboNeedDuringBikeMax: 1,
            diffMoisterRun: 1,
            gelsToTakeRun: 1,
            weightLossDuringBike: 1,
            fluidNeedPerHourSwim: 1,
            fluidNeedDuringBike: 1,
            actFluidNeedBike: 1,
            actFluidNeedRun: 1,
          },
          new Race()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Race', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            date: currentDate.format(DATE_FORMAT),
            name: 'BBBBBB',
            logging: 'BBBBBB',
            weight: 1,
            length: 1,
            temperature: 1,
            comp: 1,
            swimDuration: 1,
            bikeDuration: 1,
            runDuration: 1,
            gelCarbo: 1,
            drinkCarbo: 1,
            drinkOrgCarbo: 1,
            gelOrgCarbo: 1,
            gelsbike: 1,
            gelsrun: 1,
            selectedOrgGelQuery: true,
            selectedOrgDrankQuery: true,
            sportDrinkOrgBike: 1,
            waterOrgBike: 1,
            gelsOrgBike: 1,
            sportDrinkOrgRun: 1,
            waterOrgRun: 1,
            gelsOrgRun: 1,
            orsBeforeStart: 1,
            sportDrinkToTakeBike: 1,
            waterToTakeBike: 1,
            extraWaterToTakeBike: 1,
            orsToTakeBike: 1,
            gelsToTakeBike: 1,
            sportDrinkToTakeRun: 1,
            waterToTakeRun: 1,
            extraWaterToTakeRun: 1,
            orsToTakeRun: 1,
            carboNeedDuringBikeMin: 1,
            carboNeedDuringBikeMax: 1,
            carboNeedDuringRunMin: 1,
            carboNeedDuringRunMax: 1,
            diffCarboRun: 1,
            diffMoisterRun: 1,
            difCarbo: 1,
            difMoister: 1,
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
            actFluidNeedBike: 1,
            actFluidNeedRun: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Race', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRaceToCollectionIfMissing', () => {
        it('should add a Race to an empty array', () => {
          const race: IRace = { id: 123 };
          expectedResult = service.addRaceToCollectionIfMissing([], race);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(race);
        });

        it('should not add a Race to an array that contains it', () => {
          const race: IRace = { id: 123 };
          const raceCollection: IRace[] = [
            {
              ...race,
            },
            { id: 456 },
          ];
          expectedResult = service.addRaceToCollectionIfMissing(raceCollection, race);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Race to an array that doesn't contain it", () => {
          const race: IRace = { id: 123 };
          const raceCollection: IRace[] = [{ id: 456 }];
          expectedResult = service.addRaceToCollectionIfMissing(raceCollection, race);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(race);
        });

        it('should add only unique Race to an array', () => {
          const raceArray: IRace[] = [{ id: 123 }, { id: 456 }, { id: 53306 }];
          const raceCollection: IRace[] = [{ id: 123 }];
          expectedResult = service.addRaceToCollectionIfMissing(raceCollection, ...raceArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const race: IRace = { id: 123 };
          const race2: IRace = { id: 456 };
          expectedResult = service.addRaceToCollectionIfMissing([], race, race2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(race);
          expect(expectedResult).toContain(race2);
        });

        it('should accept null and undefined values', () => {
          const race: IRace = { id: 123 };
          expectedResult = service.addRaceToCollectionIfMissing([], null, race, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(race);
        });

        it('should return initial array if no Race is added', () => {
          const raceCollection: IRace[] = [{ id: 123 }];
          expectedResult = service.addRaceToCollectionIfMissing(raceCollection, undefined, null);
          expect(expectedResult).toEqual(raceCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
