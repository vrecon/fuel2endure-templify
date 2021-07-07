import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRace } from '../race.model';
import { RaceService } from '../service/race.service';
import { RaceDeleteDialogComponent } from '../delete/race-delete-dialog.component';

@Component({
  selector: 'jhi-race',
  templateUrl: './race.component.html',
})
export class RaceComponent implements OnInit {
  races?: IRace[];
  isLoading = false;

  constructor(protected raceService: RaceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.raceService.query().subscribe(
      (res: HttpResponse<IRace[]>) => {
        this.isLoading = false;
        this.races = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRace): number {
    return item.id!;
  }

  delete(race: IRace): void {
    const modalRef = this.modalService.open(RaceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.race = race;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
