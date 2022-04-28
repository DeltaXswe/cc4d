import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-date-picker-dialog',
  templateUrl: './date-picker-dialog.component.html',
  styleUrls: ['./date-picker-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DatePickerDialogComponent implements OnInit {
  dateForm: FormGroup;
  startTime = {hour: 12, minute: 0, second: 0};
  endTime = {hour: 12, minute: 0, second: 0};
  constructor(private formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<DatePickerDialogComponent>) {
    this.dateForm = formBuilder.group({
      start: new FormControl(''),
      end: new FormControl('')
    })
  }

  ngOnInit(): void {
  }
  confirm(){
    let data: number[] = [
      Date.parse(this.dateForm.getRawValue().start) + ((this.startTime.hour*3600 + this.startTime.minute*60 + this.startTime.second)*1000),
      Date.parse(this.dateForm.getRawValue().end) + ((this.endTime.hour*3600 + this.endTime.minute*60 + this.endTime.second)*1000)
    ];
    console.log(data);
    this.matDialogRef.close(data);
  }

  cancel(){
    this.matDialogRef.close();
  }
}
