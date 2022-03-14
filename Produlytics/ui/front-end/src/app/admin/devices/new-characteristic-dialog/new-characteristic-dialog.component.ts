import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CharacteristicCreationCommand} from "../../../model/admin-device/characteristic_creation_command";
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-new-characteristic-dialog',
  templateUrl: './new-characteristic-dialog.component.html',
  styleUrls: ['./new-characteristic-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NewCharacteristicDialogComponent implements OnInit {

  formGroup: FormGroup;

  constructor(
    private matDialogRef: MatDialogRef<NewCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      readonly characteristics: CharacteristicCreationCommand[]
    },
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl('', [Validators.required, control => {
        return this.data.characteristics.find(characteristic => characteristic.name === control.value)
          ? {duplicateCharacteristicName: true}
          : null;
      }]),
      autoAdjust: new FormControl(false),
      upperLimit: new FormControl('', Validators.required),
      lowerLimit: new FormControl('', Validators.required),
      sampleSize: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.formGroup.get('autoAdjust')?.valueChanges.subscribe(selected => {
      if (selected) {
        this.formGroup.get('upperLimit')?.removeValidators(Validators.required);
        this.formGroup.get('upperLimit')?.updateValueAndValidity();
        this.formGroup.get('lowerLimit')?.removeValidators(Validators.required);
        this.formGroup.get('lowerLimit')?.updateValueAndValidity();
      } else {
        this.formGroup.get('upperLimit')?.setValidators(Validators.required);
        this.formGroup.get('upperLimit')?.updateValueAndValidity();
        this.formGroup.get('lowerLimit')?.setValidators(Validators.required);
        this.formGroup.get('lowerLimit')?.updateValueAndValidity();
      }
    });
  }

  cancel(): void {
    this.matDialogRef.close();
  }

  confirm(): void {
    const rawValue = this.formGroup.getRawValue();
    if (rawValue.autoAdjust) {
      rawValue.upperLimit = null;
      rawValue.lowerLimit = null;
    } else {
      rawValue.sampleSize = null;
    }
    this.matDialogRef.close(this.formGroup.getRawValue());
  }
}
