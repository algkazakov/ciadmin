import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CiadminSharedModule } from 'app/shared/shared.module';
import { CIRuleGroupComponent } from './ci-rule-group.component';
import { CIRuleGroupDetailComponent } from './ci-rule-group-detail.component';
import { CIRuleGroupUpdateComponent } from './ci-rule-group-update.component';
import { CIRuleGroupDeleteDialogComponent } from './ci-rule-group-delete-dialog.component';
import { cIRuleGroupRoute } from './ci-rule-group.route';

@NgModule({
  imports: [CiadminSharedModule, RouterModule.forChild(cIRuleGroupRoute)],
  declarations: [CIRuleGroupComponent, CIRuleGroupDetailComponent, CIRuleGroupUpdateComponent, CIRuleGroupDeleteDialogComponent],
  entryComponents: [CIRuleGroupDeleteDialogComponent]
})
export class CiadminCIRuleGroupModule {}
