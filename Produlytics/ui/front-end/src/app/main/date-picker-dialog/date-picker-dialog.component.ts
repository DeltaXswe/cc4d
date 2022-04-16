import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-date-picker-dialog',
  templateUrl: './date-picker-dialog.component.html',
  styleUrls: ['./date-picker-dialog.component.css']
})
export class DatePickerDialogComponent implements OnInit {
  dateForm: FormGroup;

  constructor(formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<DatePickerDialogComponent>) {
    this.dateForm = formBuilder.group({
      start: new FormControl(''),
      end: new FormControl('')
    })
  }

  ngOnInit(): void {
  }
  confirm(){
    this.matDialogRef.close({data: this.dateForm.getRawValue()});
  }

  cancel(){
    this.matDialogRef.close();
  }
}
