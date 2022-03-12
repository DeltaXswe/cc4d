import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SelectionComponent } from './main/selection/selection.component';
import { ChartComponent } from './main/chart/chart.component';

const routes: Routes = [
  { path: '', component: SelectionComponent },
  { path: 'chart/:machine/:characteristic', component: ChartComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
    // canActivate: AdminGuard // TODO later
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
