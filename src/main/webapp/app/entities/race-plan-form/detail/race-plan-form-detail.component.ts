import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRacePlanForm } from '../race-plan-form.model';

@Component({
  selector: 'jhi-race-plan-form-detail',
  templateUrl: './race-plan-form-detail.component.html',
})
export class RacePlanFormDetailComponent implements OnInit {
  racePlanForm: IRacePlanForm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ racePlanForm }) => {
      this.racePlanForm = racePlanForm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
