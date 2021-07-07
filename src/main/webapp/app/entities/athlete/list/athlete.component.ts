import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthlete } from '../athlete.model';
import { AthleteService } from '../service/athlete.service';
import { AthleteDeleteDialogComponent } from '../delete/athlete-delete-dialog.component';

@Component({
  selector: 'jhi-athlete',
  templateUrl: './athlete.component.html',
})
export class AthleteComponent implements OnInit {
  athletes?: IAthlete[];
  isLoading = false;

  constructor(protected athleteService: AthleteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.athleteService.query().subscribe(
      (res: HttpResponse<IAthlete[]>) => {
        this.isLoading = false;
        this.athletes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAthlete): number {
    return item.id!;
  }

  delete(athlete: IAthlete): void {
    const modalRef = this.modalService.open(AthleteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athlete = athlete;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
