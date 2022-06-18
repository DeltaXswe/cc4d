import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-carousel-options-dialog',
  templateUrl: './carousel-options-dialog.component.html',
  styleUrls: ['./carousel-options-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CarouselOptionsDialogComponent implements OnInit {

  constructor(private matDialogRef: MatDialogRef<CarouselOptionsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  confirm(): void {
    this.matDialogRef.close(this.data);
  }

  cancel(): void {
    this.matDialogRef.close();
  }
}
