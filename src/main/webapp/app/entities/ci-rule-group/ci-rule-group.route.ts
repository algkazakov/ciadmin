import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICIRuleGroup, CIRuleGroup } from 'app/shared/model/ci-rule-group.model';
import { CIRuleGroupService } from './ci-rule-group.service';
import { CIRuleGroupComponent } from './ci-rule-group.component';
import { CIRuleGroupDetailComponent } from './ci-rule-group-detail.component';
import { CIRuleGroupUpdateComponent } from './ci-rule-group-update.component';

@Injectable({ providedIn: 'root' })
export class CIRuleGroupResolve implements Resolve<ICIRuleGroup> {
  constructor(private service: CIRuleGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICIRuleGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cIRuleGroup: HttpResponse<CIRuleGroup>) => {
          if (cIRuleGroup.body) {
            return of(cIRuleGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CIRuleGroup());
  }
}

export const cIRuleGroupRoute: Routes = [
  {
    path: '',
    component: CIRuleGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRuleGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CIRuleGroupDetailComponent,
    resolve: {
      cIRuleGroup: CIRuleGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRuleGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CIRuleGroupUpdateComponent,
    resolve: {
      cIRuleGroup: CIRuleGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRuleGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CIRuleGroupUpdateComponent,
    resolve: {
      cIRuleGroup: CIRuleGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIRuleGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
