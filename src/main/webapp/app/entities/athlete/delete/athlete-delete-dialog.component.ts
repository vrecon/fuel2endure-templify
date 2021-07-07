import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthlete } from '../athlete.model';
import { AthleteService } from '../service/athlete.service';

@Component({
  templateUrl: './athlete-delete-dialog.component.html',
})
export class AthleteDeleteDialogComponent {
  athlete?: IAthlete;

  constructor(protected athleteService: AthleteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
