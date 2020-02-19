import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CiadminSharedModule } from 'app/shared/shared.module';
import { CIBuildComponent } from './ci-build.component';
import { CIBuildDetailComponent } from './ci-build-detail.component';
import { CIBuildUpdateComponent } from './ci-build-update.component';
import { CIBuildDeleteDialogComponent } from './ci-build-delete-dialog.component';
import { cIBuildRoute } from './ci-build.route';

@NgModule({
  imports: [CiadminSharedModule, RouterModule.forChild(cIBuildRoute)],
  declarations: [CIBuildComponent, CIBuildDetailComponent, CIBuildUpdateComponent, CIBuildDeleteDialogComponent],
  entryComponents: [CIBuildDeleteDialogComponent]
})
export class CiadminCIBuildModule {}
