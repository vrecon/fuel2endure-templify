import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RacePlanFormComponent } from '../list/race-plan-form.component';
import { RacePlanFormDetailComponent } from '../detail/race-plan-form-detail.component';
import { RacePlanFormUpdateComponent } from '../update/race-plan-form-update.component';
import { RacePlanFormRoutingResolveService } from './race-plan-form-routing-resolve.service';

const racePlanFormRoute: Routes = [
  {
    path: '',
    component: RacePlanFormComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RacePlanFormDetailComponent,
    resolve: {
      racePlanForm: RacePlanFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RacePlanFormUpdateComponent,
    resolve: {
      racePlanForm: RacePlanFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RacePlanFormUpdateComponent,
    resolve: {
      racePlanForm: RacePlanFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(racePlanFormRoute)],
  exports: [RouterModule],
})
export class RacePlanFormRoutingModule {}
