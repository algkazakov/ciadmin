import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICIBuild } from 'app/shared/model/ci-build.model';

type EntityResponseType = HttpResponse<ICIBuild>;
type EntityArrayResponseType = HttpResponse<ICIBuild[]>;

@Injectable({ providedIn: 'root' })
export class CIBuildService {
  public resourceUrl = SERVER_API_URL + 'api/ci-builds';

  constructor(protected http: HttpClient) {}

  create(cIBuild: ICIBuild): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cIBuild);
    return this.http
      .post<ICIBuild>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cIBuild: ICIBuild): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cIBuild);
    return this.http
      .put<ICIBuild>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICIBuild>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICIBuild[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cIBuild: ICIBuild): ICIBuild {
    const copy: ICIBuild = Object.assign({}, cIBuild, {
      buildDate: cIBuild.buildDate && cIBuild.buildDate.isValid() ? cIBuild.buildDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.buildDate = res.body.buildDate ? moment(res.body.buildDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cIBuild: ICIBuild) => {
        cIBuild.buildDate = cIBuild.buildDate ? moment(cIBuild.buildDate) : undefined;
      });
    }
    return res;
  }
}
