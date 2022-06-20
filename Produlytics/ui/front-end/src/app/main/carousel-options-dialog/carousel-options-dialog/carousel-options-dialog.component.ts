import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {CarouselOptions} from "./carousel-options.types";

@Component({
  selector: 'app-carousel-options-dialog',
  templateUrl: './carousel-options-dialog.component.html',
  styleUrls: ['./carousel-options-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CarouselOptionsDialogComponent implements OnInit {

  constructor(
    private matDialogRef: MatDialogRef<CarouselOptionsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CarouselOptions
  ) {
  }

  ngOnInit(): void {
  }

  /**
   * Chiude il dialog passando a {@link SelectionComponent} il parametro
   * data contenente le variabili isCarouselOn, isCarouselCycling e carouselInterval.
   */
  confirm(): void {
    this.matDialogRef.close(this.data);
  }

  /**
   * Chiude il dialog senza alcuna operazione aggiuntiva.
   */
  cancel(): void {
    this.matDialogRef.close();
  }
}
