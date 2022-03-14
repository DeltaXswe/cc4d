import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CharacteristicCreationCommand} from "../../../model/admin-device/characteristic_creation_command";
import {MatDialog} from "@angular/material/dialog";
import {NewCharacteristicDialogComponent} from "../new-characteristic-dialog/new-characteristic-dialog.component";

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
}
