import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Characteristic} from "../../../../model/admin-device/characteristic/characteristic";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-characteristic-dialog',
  templateUrl: './update-characteristic-dialog.component.html',
  styleUrls: ['./update-characteristic-dialog.component.css']
})
export class UpdateCharacteristicDialogComponent implements OnInit {
  private formGroup: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<UpdateCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      deviceId: number,
      characteristic: Characteristic,

    },
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl(data.characteristic.name, Validators.required),

    })
  }

  ngOnInit(): void {
  }

  cancel() {
    this.dialogRef.close();
  }
}
