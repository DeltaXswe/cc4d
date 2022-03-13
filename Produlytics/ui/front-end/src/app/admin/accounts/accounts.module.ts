import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountsComponent } from './accounts.component';
import {RouterModule, Routes} from "@angular/router";
import {MatToolbarModule} from "@angular/material/toolbar";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSnackBarModule} from "@angular/material/snack-bar";

const routes: Routes = [
  {
    path: '',
    component: AccountsComponent
  }
]

@NgModule({
  declarations: [
    AccountsComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatToolbarModule,
    FlexLayoutModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    MatSnackBarModule
  ]
})
export class AccountsModule { }
