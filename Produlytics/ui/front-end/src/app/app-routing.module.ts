import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SelectionComponent } from './main/selection/selection.component';
import { AdminRoutingModule } from "./admin/admin-routing.module";
import { LoginComponent } from './main/login/login.component';
import { AuthenticatedUserGuard } from './guards/authenticated-user-guard';
import { LoginGuard } from './guards/login-guard';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: SelectionComponent, canActivate: [AuthenticatedUserGuard]},
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', useHash: true }),
    AdminRoutingModule
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
