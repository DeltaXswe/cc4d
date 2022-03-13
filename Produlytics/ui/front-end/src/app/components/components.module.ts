import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatDialogModule} from "@angular/material/dialog";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';



@NgModule({
  declarations: [
    ConfirmDialogComponent,
    ErrorDialogComponent
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    FlexLayoutModule,
    MatButtonModule
  ]
})
export class ComponentsModule { }
