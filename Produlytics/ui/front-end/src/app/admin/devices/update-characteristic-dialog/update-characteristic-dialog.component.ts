import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {startWith} from "rxjs";
import {
  UpdateCharacteristicAbstractService
} from "../../../model/admin-device/characteristic/update-characteristic-abstract.service";

@Component({
  selector: 'app-update-characteristic-dialog',
  templateUrl: './update-characteristic-dialog.component.html',
  styleUrls: ['./update-characteristic-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UpdateCharacteristicDialogComponent implements OnInit {
  formGroup: FormGroup;

  constructor(
    private updateCharacteristicService: UpdateCharacteristicAbstractService,
    private dialogRef: MatDialogRef<UpdateCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      readonly deviceId: number,
      readonly characteristic: Characteristic,
    },
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl(data.characteristic.name, Validators.required),
      autoAdjust: new FormControl(data.characteristic.autoAdjust),
      lowerLimit: new FormControl(data.characteristic.lowerLimit),
      upperLimit: new FormControl(data.characteristic.upperLimit),
      sampleSize: new FormControl(data.characteristic.sampleSize)
    });
  }

  ngOnInit(): void {
    this.formGroup.get('autoAdjust')?.valueChanges
      .pipe(startWith(this.data.characteristic.autoAdjust))
      .subscribe(selected => {
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

  cancel() {
    this.dialogRef.close();
  }

  confirm(): void {
    const rawValue = this.formGroup.getRawValue();
    if (rawValue.autoAdjust) {
      rawValue.upperLimit = null;
      rawValue.lowerLimit = null;
    } else {
      rawValue.sampleSize = null;
    }
    this.updateCharacteristicService.updateCharacteristic({
      deviceId: this.data.deviceId,
      id: this.data.characteristic.id,
      name: rawValue.name,
      autoAdjust: rawValue.autoAdjust,
      upperLimit: rawValue.upperLimit,
      lowerLimit: rawValue.lowerLimit,
      sampleSize: rawValue.sampleSize
    })
      .subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error: err => {
          if (err.errorCode === 'duplicateCharacteristicName') {
            this.formGroup.get('name')?.setErrors({
              duplicateCharacteristicName: true
            });
            this.formGroup.updateValueAndValidity();
          }
        }
      });
  }
}
