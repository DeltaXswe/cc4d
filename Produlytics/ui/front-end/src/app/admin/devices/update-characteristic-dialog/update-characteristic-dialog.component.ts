import {Component, Inject, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {startWith} from "rxjs";
import {
  UpdateCharacteristicAbstractService
} from "../../../model/admin-device/characteristic/update-characteristic-abstract.service";
import {CharacteristicFormComponent} from "../characteristic-form/characteristic-form.component";

@Component({
  selector: 'app-update-characteristic-dialog',
  templateUrl: './update-characteristic-dialog.component.html',
  styleUrls: ['./update-characteristic-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UpdateCharacteristicDialogComponent implements OnInit {
  @ViewChild('charForm') charForm!: CharacteristicFormComponent;

  constructor(
    private updateCharacteristicService: UpdateCharacteristicAbstractService,
    private dialogRef: MatDialogRef<UpdateCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      readonly deviceId: number,
      readonly characteristic: Characteristic,
    },
    formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
  }

  cancel() {
    this.dialogRef.close();
  }

  confirm(): void {
    const rawValue = this.charForm.requireData();
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
            this.charForm.duplicateNameBehavior.next(true);
          }
        }
      });
  }
}
