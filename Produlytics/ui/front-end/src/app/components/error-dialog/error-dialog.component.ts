import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ErrorDialogComponent implements OnInit {

  constructor(
    private matDialogRef: MatDialogRef<ErrorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string }
  ) { }

  ngOnInit(): void {
  }

  close(): void {
    this.matDialogRef.close();
  }

}
