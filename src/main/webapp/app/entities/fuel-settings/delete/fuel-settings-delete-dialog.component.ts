import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuelSettings } from '../fuel-settings.model';
import { FuelSettingsService } from '../service/fuel-settings.service';

@Component({
  templateUrl: './fuel-settings-delete-dialog.component.html',
})
export class FuelSettingsDeleteDialogComponent {
  fuelSettings?: IFuelSettings;

  constructor(protected fuelSettingsService: FuelSettingsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelSettingsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
