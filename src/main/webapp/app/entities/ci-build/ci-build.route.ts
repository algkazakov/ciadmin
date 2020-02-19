import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICIBuild, CIBuild } from 'app/shared/model/ci-build.model';
import { CIBuildService } from './ci-build.service';
import { CIBuildComponent } from './ci-build.component';
import { CIBuildDetailComponent } from './ci-build-detail.component';
import { CIBuildUpdateComponent } from './ci-build-update.component';

@Injectable({ providedIn: 'root' })
export class CIBuildResolve implements Resolve<ICIBuild> {
  constructor(private service: CIBuildService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICIBuild> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cIBuild: HttpResponse<CIBuild>) => {
          if (cIBuild.body) {
            return of(cIBuild.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CIBuild());
  }
}

export const cIBuildRoute: Routes = [
  {
    path: '',
    component: CIBuildComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIBuild.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CIBuildDetailComponent,
    resolve: {
      cIBuild: CIBuildResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIBuild.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CIBuildUpdateComponent,
    resolve: {
      cIBuild: CIBuildResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIBuild.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CIBuildUpdateComponent,
    resolve: {
      cIBuild: CIBuildResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ciadminApp.cIBuild.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
