import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ci-rule-group',
        loadChildren: () => import('./ci-rule-group/ci-rule-group.module').then(m => m.CiadminCIRuleGroupModule)
      },
      {
        path: 'ci-rule',
        loadChildren: () => import('./ci-rule/ci-rule.module').then(m => m.CiadminCIRuleModule)
      },
      {
        path: 'ci-build',
        loadChildren: () => import('./ci-build/ci-build.module').then(m => m.CiadminCIBuildModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CiadminEntityModule {}
