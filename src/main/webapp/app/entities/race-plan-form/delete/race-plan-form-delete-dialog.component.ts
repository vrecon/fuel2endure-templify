import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRacePlanForm } from '../race-plan-form.model';
import { RacePlanFormService } from '../service/race-plan-form.service';

@Component({
  templateUrl: './race-plan-form-delete-dialog.component.html',
})
export class RacePlanFormDeleteDialogComponent {
  racePlanForm?: IRacePlanForm;

  constructor(protected racePlanFormService: RacePlanFormService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.racePlanFormService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
