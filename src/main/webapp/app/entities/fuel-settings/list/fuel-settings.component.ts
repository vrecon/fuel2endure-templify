import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuelSettings } from '../fuel-settings.model';
import { FuelSettingsService } from '../service/fuel-settings.service';
import { FuelSettingsDeleteDialogComponent } from '../delete/fuel-settings-delete-dialog.component';

@Component({
  selector: 'jhi-fuel-settings',
  templateUrl: './fuel-settings.component.html',
})
export class FuelSettingsComponent implements OnInit {
  fuelSettings?: IFuelSettings[];
  isLoading = false;

  constructor(protected fuelSettingsService: FuelSettingsService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.fuelSettingsService.query().subscribe(
      (res: HttpResponse<IFuelSettings[]>) => {
        this.isLoading = false;
        this.fuelSettings = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFuelSettings): number {
    return item.id!;
  }

  delete(fuelSettings: IFuelSettings): void {
    const modalRef = this.modalService.open(FuelSettingsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fuelSettings = fuelSettings;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
