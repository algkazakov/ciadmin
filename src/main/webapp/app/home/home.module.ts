import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CiadminSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [CiadminSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class CiadminHomeModule {}
