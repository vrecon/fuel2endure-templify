import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuelSettings } from '../fuel-settings.model';

@Component({
  selector: 'jhi-fuel-settings-detail',
  templateUrl: './fuel-settings-detail.component.html',
})
export class FuelSettingsDetailComponent implements OnInit {
  fuelSettings: IFuelSettings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelSettings }) => {
      this.fuelSettings = fuelSettings;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
