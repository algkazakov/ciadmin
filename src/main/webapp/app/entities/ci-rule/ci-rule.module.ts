import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CiadminSharedModule } from 'app/shared/shared.module';
import { CIRuleComponent } from './ci-rule.component';
import { CIRuleDetailComponent } from './ci-rule-detail.component';
import { CIRuleUpdateComponent } from './ci-rule-update.component';
import { CIRuleDeleteDialogComponent } from './ci-rule-delete-dialog.component';
import { cIRuleRoute } from './ci-rule.route';

@NgModule({
  imports: [CiadminSharedModule, RouterModule.forChild(cIRuleRoute)],
  declarations: [CIRuleComponent, CIRuleDetailComponent, CIRuleUpdateComponent, CIRuleDeleteDialogComponent],
  entryComponents: [CIRuleDeleteDialogComponent]
})
export class CiadminCIRuleModule {}
