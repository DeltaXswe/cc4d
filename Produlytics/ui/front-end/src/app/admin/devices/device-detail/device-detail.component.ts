import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Device} from "../../../model/admin-device/device";
import {CharacteristicsDatasource} from "./characteristics.datasource";
import {
  CharacteristicAbstractService
} from "../../../model/admin-device/characteristic/characteristic-abstract.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateDeviceAbstractService} from "../../../model/admin-device/update/update-device-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {NewCharacteristicDialogComponent} from "../new-characteristic-dialog/new-characteristic-dialog.component";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {
  UpdateCharacteristicDialogComponent
} from "./update-characteristic-dialog/update-characteristic-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmDialogComponent} from "../../../components/confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'app-device-detail',
  templateUrl: './device-detail.component.html',
  styleUrls: ['./device-detail.component.css']
})
export class DeviceDetailComponent implements OnInit {

  public device: Device;
  public characteristics = new CharacteristicsDatasource();
  public deviceNameForm: FormGroup;
  displayedColumns = ['name', 'status'];

  constructor(
    private characteristicService: CharacteristicAbstractService,
    private updateDeviceService: UpdateDeviceAbstractService,
    private matDialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private matSnackBar: MatSnackBar,
    formBuilder: FormBuilder
  ) {
    this.device = activatedRoute.snapshot.data['device'];
    this.deviceNameForm = formBuilder.group({
      name: new FormControl(this.device.name, Validators.required)
    });
  }

  ngOnInit(): void {
    this.initTable();
  }

  private initTable() {
    this.characteristicService.getCharacteristicsByDevice(this.device.id)
      .subscribe(result => {
        this.characteristics.setData(result);
      })
  }

  updateDeviceName() {
    const newName = this.deviceNameForm.getRawValue().name;
    this.updateDeviceService.updateDeviceName(this.device.id, newName)
      .subscribe({
        next: () => {
          this.deviceNameForm.get('name')?.setValue(newName);
          this.deviceNameForm.get('name')?.setErrors({duplicateDeviceName: null});
          this.deviceNameForm.get('name')?.updateValueAndValidity();
        },
        error: () => {
          this.deviceNameForm.get('name')?.setErrors({duplicateDeviceName: true});
          this.deviceNameForm.get('name')?.updateValueAndValidity();
        }
      });
  }

  openNewCharacteristicDialog(): void {
    const dialogRef = this.matDialog.open(NewCharacteristicDialogComponent, {
      data: {
        characteristics: this.characteristics.currentData
      }
    });
    dialogRef.afterClosed().subscribe((command: CharacteristicCreationCommand) => {
      if (command) {
        this.characteristicService.addCharacteristic(this.device.id, command)
          .subscribe(() => {
            this.initTable();
          });
      }
    });
  }

  openUpdateCharacteristicFormDialog(characteristic: Characteristic): void {
    const dialogRef = this.matDialog.open(UpdateCharacteristicDialogComponent, {
      data: {
        deviceId: this.device.id,
        characteristic: characteristic
      }
    });
    dialogRef.afterClosed().subscribe(reload => {
      if (reload) {
        this.initTable();
      }
    });
  }

  notifyCopy() {
    this.matSnackBar.open(
      'Chiave copiata negli appunti',
      'Ok'
    )
  }

  toggleCharacteristicStatus(characteristic: Characteristic) {
    if (characteristic.archived) {
      this.characteristicService.recoverCharacteristic(this.device.id, characteristic.id);
    } else {
      const dialogRef = this.matDialog.open(ConfirmDialogComponent, {
        data: {
          message: `La caratteristica ${characteristic.name} con id ${characteristic.id} sarà archiviata. Continuare?`
        }
      });
      dialogRef.afterClosed().subscribe(confirmed => {
        if (confirmed) {
          this.characteristicService.archiveCharacteristic(this.device.id, characteristic.id);
        }
      })
    }
  }
}