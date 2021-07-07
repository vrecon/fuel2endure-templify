import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'athlete',
        data: { pageTitle: 'fuel2EndureApp.athlete.home.title' },
        loadChildren: () => import('./athlete/athlete.module').then(m => m.AthleteModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'fuel2EndureApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      {
        path: 'fuel-settings',
        data: { pageTitle: 'fuel2EndureApp.fuelSettings.home.title' },
        loadChildren: () => import('./fuel-settings/fuel-settings.module').then(m => m.FuelSettingsModule),
      },
      {
        path: 'race',
        data: { pageTitle: 'fuel2EndureApp.race.home.title' },
        loadChildren: () => import('./race/race.module').then(m => m.RaceModule),
      },
      {
        path: 'race-plan-form',
        data: { pageTitle: 'fuel2EndureApp.racePlanForm.home.title' },
        loadChildren: () => import('./race-plan-form/race-plan-form.module').then(m => m.RacePlanFormModule),
      },
      {
        path: 'training',
        data: { pageTitle: 'fuel2EndureApp.training.home.title' },
        loadChildren: () => import('./training/training.module').then(m => m.TrainingModule),
      },
      {
        path: 'voucher',
        data: { pageTitle: 'fuel2EndureApp.voucher.home.title' },
        loadChildren: () => import('./voucher/voucher.module').then(m => m.VoucherModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
