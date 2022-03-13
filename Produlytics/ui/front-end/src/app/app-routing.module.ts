import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SelectionComponent } from './main/selection/selection.component';
import { ChartComponent } from './main/chart/chart.component';
import {AdminRoutingModule} from "./admin/admin-routing.module";

const routes: Routes = [
  { path: '', component: SelectionComponent },
  { path: 'chart/:machine/:characteristic', component: ChartComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' }),
    AdminRoutingModule
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
