import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: 'gestione-macchine',
    loadChildren: () => import('./devices/devices.module').then(m => m.DevicesModule)
    // canActivate: AdminGuard // TODO later
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class AdminModule { }
