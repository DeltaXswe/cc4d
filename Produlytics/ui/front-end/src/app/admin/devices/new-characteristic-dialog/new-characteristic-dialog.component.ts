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
      upperLimit: new FormControl(''),
      lowerLimit: new FormControl(''),
      autoAdjust: new FormControl(false),
      sampleSize: new FormControl('')
    });
  }

  ngOnInit(): void {
    const autoAdjustField = this.formGroup.get('autoAdjust')!;
    this.formGroup.get('upperLimit')?.setValidators(this.limitValidator(autoAdjustField));
    this.formGroup.get('lowerLimit')?.setValidators(this.limitValidator(autoAdjustField));
    this.formGroup.get('upperLimit')?.setValidators(control => {
      if (autoAdjustField?.value) {
        return {
          autoAdjustOff: null
        };
      } else {
        return {
          autoAdjustOff: control.value && control.value !== '' ? null : true
        };
      }
    });
  }

  cancel(): void {
    this.matDialogRef.close();
  }

  confirm(): void {
    this.matDialogRef.close(this.formGroup.getRawValue());
  }

  private limitValidator(autoAdjustField: AbstractControl) {
    return (control: AbstractControl) => {
      if (autoAdjustField?.value) {
        return {
          autoAdjustOn: control.value && control.value !== '' ? true : null
        };
      } else {
        return {
          autoAdjustOn: null
        };
      }
    }
  }
}
