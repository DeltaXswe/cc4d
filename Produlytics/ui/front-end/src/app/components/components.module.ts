import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatDialogModule} from "@angular/material/dialog";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import {CharacteristicFormComponent} from "./characteristic-form/characteristic-form.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatInputModule} from "@angular/material/input";



@NgModule({
  declarations: [
    ConfirmDialogComponent,
    ErrorDialogComponent,
    CharacteristicFormComponent
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    FlexLayoutModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    MatInputModule
  ],
  exports: [
    ConfirmDialogComponent,
    ErrorDialogComponent,
    CharacteristicFormComponent
  ]
})
export class ComponentsModule { }
