import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICIRuleGroup } from 'app/shared/model/ci-rule-group.model';

type EntityResponseType = HttpResponse<ICIRuleGroup>;
type EntityArrayResponseType = HttpResponse<ICIRuleGroup[]>;

@Injectable({ providedIn: 'root' })
export class CIRuleGroupService {
  public resourceUrl = SERVER_API_URL + 'api/ci-rule-groups';

  constructor(protected http: HttpClient) {}

  create(cIRuleGroup: ICIRuleGroup): Observable<EntityResponseType> {
    return this.http.post<ICIRuleGroup>(this.resourceUrl, cIRuleGroup, { observe: 'response' });
  }

  update(cIRuleGroup: ICIRuleGroup): Observable<EntityResponseType> {
    return this.http.put<ICIRuleGroup>(this.resourceUrl, cIRuleGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICIRuleGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICIRuleGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
