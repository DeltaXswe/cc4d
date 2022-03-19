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
import {NewDeviceComponent} from "./new-device/new-device.component";
import {
  NewCharacteristicDialogComponent
} from "./new-characteristic-dialog/new-characteristic-dialog.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatListModule} from "@angular/material/list";
import {MatCardModule} from "@angular/material/card";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import { DeviceDetailComponent } from './device-detail/device-detail.component';
import {DeviceDetailResolver} from "./device-detail/device-detail.resolver";
import { UpdateCharacteristicDialogComponent } from './update-characteristic-dialog/update-characteristic-dialog.component';
import {MatChipsModule} from "@angular/material/chips";
import {MatTooltipModule} from "@angular/material/tooltip";
import {ClipboardModule} from "@angular/cdk/clipboard";

const routes: Routes = [
  {
    path: '',
    component: DevicesComponent
  },
  {
    path: 'nuova',
    component: NewDeviceComponent
  },
  {
    path: ':id',
    component: DeviceDetailComponent,
    resolve: {device: DeviceDetailResolver},
  }
];

@NgModule({
  declarations: [
    DevicesComponent,
    NewDeviceComponent,
    NewCharacteristicDialogComponent,
    DeviceDetailComponent,
    UpdateCharacteristicDialogComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FlexLayoutModule,
    MatTableModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatListModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule,
    MatCheckboxModule,
    MatPaginatorModule,
    MatSortModule,
    MatChipsModule,
    MatTooltipModule,
    ClipboardModule
  ]
})
export class DevicesModule { }
