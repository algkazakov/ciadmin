import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICIRule, CIRule } from 'app/shared/model/ci-rule.model';
import { CIRuleService } from './ci-rule.service';
import { CIRuleComponent } from './ci-rule.component';
import { CIRuleDetailComponent } from './ci-rule-detail.component';
import { CIRuleUpdateComponent } from './ci-rule-update.component';

@Injectable({ providedIn: 'root' })
export class CIRuleResolve implements Resolve<ICIRule> {
  constructor(private service: CIRuleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICIRule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cIRule: HttpResponse<CIRule>) => {
          if (cIRule.body) {
            return of(cIRule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CIRule());
  }
}

export const cIRuleRoute: Routes = [
  {
    path: '',
    component: CIRuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ciadminApp.cIRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CIRuleDetailComponent,
    resolve: {
      cIRule: CIRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CIRuleUpdateComponent,
    resolve: {
      cIRule: CIRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CIRuleUpdateComponent,
    resolve: {
      cIRule: CIRuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
