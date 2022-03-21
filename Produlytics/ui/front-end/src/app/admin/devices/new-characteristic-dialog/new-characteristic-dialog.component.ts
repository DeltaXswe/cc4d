import {Component, Inject, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {CharacteristicFormComponent} from "../../../components/characteristic-form/characteristic-form.component";

@Component({
  selector: 'app-new-characteristic-dialog',
  templateUrl: './new-characteristic-dialog.component.html',
  styleUrls: ['./new-characteristic-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class NewCharacteristicDialogComponent implements OnInit {

  @ViewChild('charForm') charForm!: CharacteristicFormComponent;

  constructor(
    private matDialogRef: MatDialogRef<NewCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      readonly characteristics: CharacteristicCreationCommand[]
    }
  ) {

  }

  ngOnInit(): void {
  }

  cancel(): void {
    this.matDialogRef.close();
  }

  confirm(): void {
    const rawData = this.charForm.requireData();
    if (this.data.characteristics.find(char => char.name === rawData.name)) {
      this.charForm.duplicateNameBehavior.next(true);
    } else {
      this.matDialogRef.close(rawData);
    }
  }
}
