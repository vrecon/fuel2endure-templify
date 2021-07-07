import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRacePlanForm } from '../race-plan-form.model';
import { RacePlanFormService } from '../service/race-plan-form.service';
import { RacePlanFormDeleteDialogComponent } from '../delete/race-plan-form-delete-dialog.component';

@Component({
  selector: 'jhi-race-plan-form',
  templateUrl: './race-plan-form.component.html',
})
export class RacePlanFormComponent implements OnInit {
  racePlanForms?: IRacePlanForm[];
  isLoading = false;

  constructor(protected racePlanFormService: RacePlanFormService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.racePlanFormService.query().subscribe(
      (res: HttpResponse<IRacePlanForm[]>) => {
        this.isLoading = false;
        this.racePlanForms = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRacePlanForm): number {
    return item.id!;
  }

  delete(racePlanForm: IRacePlanForm): void {
    const modalRef = this.modalService.open(RacePlanFormDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.racePlanForm = racePlanForm;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
