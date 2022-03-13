import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DevicesComponent } from './devices.component';
import {RouterModule, Routes} from "@angular/router";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatDialogModule} from "@angular/material/dialog";

const routes: Routes = [
  {
    path: '',
    component: DevicesComponent
  }
]

@NgModule({
  declarations: [
    DevicesComponent
  ],
    imports: [
      CommonModule,
      RouterModule.forChild(routes),
      FlexLayoutModule,
      MatButtonModule,
      MatTableModule,
      MatIconModule,
      MatToolbarModule,
      MatSnackBarModule,
      MatDialogModule
    ]
})
export class DevicesModule { }
