import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RacePlanFormComponent } from './list/race-plan-form.component';
import { RacePlanFormDetailComponent } from './detail/race-plan-form-detail.component';
import { RacePlanFormUpdateComponent } from './update/race-plan-form-update.component';
import { RacePlanFormDeleteDialogComponent } from './delete/race-plan-form-delete-dialog.component';
import { RacePlanFormRoutingModule } from './route/race-plan-form-routing.module';

@NgModule({
  imports: [SharedModule, RacePlanFormRoutingModule],
  declarations: [RacePlanFormComponent, RacePlanFormDetailComponent, RacePlanFormUpdateComponent, RacePlanFormDeleteDialogComponent],
  entryComponents: [RacePlanFormDeleteDialogComponent],
})
export class RacePlanFormModule {}
