import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CIBuildService } from 'app/entities/ci-build/ci-build.service';
import { ICIBuild, CIBuild } from 'app/shared/model/ci-build.model';

describe('Service Tests', () => {
  describe('CIBuild Service', () => {
    let injector: TestBed;
    let service: CIBuildService;
    let httpMock: HttpTestingController;
    let elemDefault: ICIBuild;
    let expectedResult: ICIBuild | ICIBuild[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CIBuildService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CIBuild(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            buildDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CIBuild', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            buildDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            buildDate: currentDate
          },
          returnedFromService
        );

        service.create(new CIBuild()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CIBuild', () => {
        const returnedFromService = Object.assign(
          {
            moduleName: 'BBBBBB',
            moduleVersion: 'BBBBBB',
            branch: 'BBBBBB',
            ciResult: 'BBBBBB',
            sonarResult: 'BBBBBB',
            buildDate: currentDate.format(DATE_TIME_FORMAT),
            sonarProject: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            buildDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CIBuild', () => {
        const returnedFromService = Object.assign(
          {
            moduleName: 'BBBBBB',
            moduleVersion: 'BBBBBB',
            branch: 'BBBBBB',
            ciResult: 'BBBBBB',
            sonarResult: 'BBBBBB',
            buildDate: currentDate.format(DATE_TIME_FORMAT),
            sonarProject: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            buildDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CIBuild', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
