import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRace } from '../race.model';

@Component({
  selector: 'jhi-race-detail',
  templateUrl: './race-detail.component.html',
})
export class RaceDetailComponent implements OnInit {
  race: IRace | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ race }) => {
      this.race = race;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
