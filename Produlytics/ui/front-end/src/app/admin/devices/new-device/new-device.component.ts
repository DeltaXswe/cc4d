import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CharacteristicCreationCommand} from "../../../model/admin-device/characteristic_creation_command";
import {MatDialog} from "@angular/material/dialog";
import {NewCharacteristicDialogComponent} from "../new-characteristic-dialog/new-characteristic-dialog.component";
import {NewDeviceAbstractService} from "../../../model/admin-device/new-device-abstract.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ErrorDialogComponent} from "../../../components/error-dialog/error-dialog.component";
import {DeviceCreationCommand} from "../../../model/admin-device/device-creation-command";

@Component({
  selector: 'app-new-device',
  templateUrl: './new-device.component.html',
  styleUrls: ['./new-device.component.css']
})
export class NewDeviceComponent implements OnInit {
  formGroup: FormGroup;
  characteristics: CharacteristicCreationCommand[] = [];

  constructor(
    private matDialog: MatDialog,
    private newDeviceService: NewDeviceAbstractService,
    private router: Router,
    private matSnackBar: MatSnackBar,
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    const nameField = this.formGroup.get('name');
    if (nameField) {
      nameField.valueChanges.subscribe(() => {
        if (nameField.hasError('duplicateDeviceName')) {
          nameField.setErrors({ duplicateDeviceName: null });
        }
      });
    }
  }

  openNewCharacteristicDialog() {
    const dialogRef = this.matDialog.open(NewCharacteristicDialogComponent, {
      data: {
        characteristics: this.characteristics
      }
    });
    dialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.characteristics.push(value);
      }
    });
  }

  insertDevice(): void {
    const device: DeviceCreationCommand = {
      name: this.formGroup.getRawValue().name,
      characteristics: this.characteristics
    }
    this.newDeviceService.insertDevice(device).subscribe({
      next: value => {
        this.router.navigate(['gestione-macchine', value.id]);
        this.matSnackBar.open(
          'Macchina creata con successo',
          'Ok'
        );
      },
      error: (err: string) => {
        this.matDialog.open(ErrorDialogComponent, {
          data: {
            message: err
          }
        });
      }
    })
  }
}
