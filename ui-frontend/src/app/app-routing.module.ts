import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SelectionComponent } from './selection/selection.component';
import { ChartComponent } from './chart/chart.component';

const routes: Routes = [
  { path: '', component: SelectionComponent },
  { path: 'grafico/:macchina/:caratteristica', component: ChartComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
