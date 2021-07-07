import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FuelSettingsComponent } from '../list/fuel-settings.component';
import { FuelSettingsDetailComponent } from '../detail/fuel-settings-detail.component';
import { FuelSettingsUpdateComponent } from '../update/fuel-settings-update.component';
import { FuelSettingsRoutingResolveService } from './fuel-settings-routing-resolve.service';

const fuelSettingsRoute: Routes = [
  {
    path: '',
    component: FuelSettingsComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuelSettingsDetailComponent,
    resolve: {
      fuelSettings: FuelSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuelSettingsUpdateComponent,
    resolve: {
      fuelSettings: FuelSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuelSettingsUpdateComponent,
    resolve: {
      fuelSettings: FuelSettingsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fuelSettingsRoute)],
  exports: [RouterModule],
})
export class FuelSettingsRoutingModule {}
