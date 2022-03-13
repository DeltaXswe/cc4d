import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {
    path: 'gestione-macchine',
    loadChildren: () => import('./devices/devices.module').then(m => m.DevicesModule)
    // canActivate: AdminGuard // TODO later
  },
  {
    path: 'gestione-utenti',
    loadChildren: () => import('./accounts/accounts.module').then(m => m.AccountsModule)
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
export class AdminRoutingModule { }
