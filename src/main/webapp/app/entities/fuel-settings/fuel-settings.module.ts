import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FuelSettingsComponent } from './list/fuel-settings.component';
import { FuelSettingsDetailComponent } from './detail/fuel-settings-detail.component';
import { FuelSettingsUpdateComponent } from './update/fuel-settings-update.component';
import { FuelSettingsDeleteDialogComponent } from './delete/fuel-settings-delete-dialog.component';
import { FuelSettingsRoutingModule } from './route/fuel-settings-routing.module';

@NgModule({
  imports: [SharedModule, FuelSettingsRoutingModule],
  declarations: [FuelSettingsComponent, FuelSettingsDetailComponent, FuelSettingsUpdateComponent, FuelSettingsDeleteDialogComponent],
  entryComponents: [FuelSettingsDeleteDialogComponent],
})
export class FuelSettingsModule {}
