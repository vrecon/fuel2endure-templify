import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraining } from '../training.model';
import { TrainingService } from '../service/training.service';

@Component({
  templateUrl: './training-delete-dialog.component.html',
})
export class TrainingDeleteDialogComponent {
  training?: ITraining;

  constructor(protected trainingService: TrainingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
