import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import { AdminGuard } from '../guards/admin-guard';
import { AuthenticatedUserGuard } from '../guards/authenticated-user-guard';

const routes: Routes = [
  {
    path: 'gestione-macchine',
    loadChildren: () => import('./devices/devices.module').then(m => m.DevicesModule),
    canActivate: [AuthenticatedUserGuard, AdminGuard]
  },
  {
    path: 'gestione-utenti',
    loadChildren: () => import('./accounts/accounts.module').then(m => m.AccountsModule),
    canActivate: [AuthenticatedUserGuard, AdminGuard]
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
