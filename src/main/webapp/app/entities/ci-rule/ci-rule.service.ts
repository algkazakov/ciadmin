import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICIRule } from 'app/shared/model/ci-rule.model';

type EntityResponseType = HttpResponse<ICIRule>;
type EntityArrayResponseType = HttpResponse<ICIRule[]>;

@Injectable({ providedIn: 'root' })
export class CIRuleService {
  public resourceUrl = SERVER_API_URL + 'api/ci-rules';

  constructor(protected http: HttpClient) {}

  create(cIRule: ICIRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cIRule);
    return this.http
      .post<ICIRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cIRule: ICIRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cIRule);
    return this.http
      .put<ICIRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICIRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICIRule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cIRule: ICIRule): ICIRule {
    const copy: ICIRule = Object.assign({}, cIRule, {
      modifyDate: cIRule.modifyDate && cIRule.modifyDate.isValid() ? cIRule.modifyDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.modifyDate = res.body.modifyDate ? moment(res.body.modifyDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cIRule: ICIRule) => {
        cIRule.modifyDate = cIRule.modifyDate ? moment(cIRule.modifyDate) : undefined;
      });
    }
    return res;
  }
}
