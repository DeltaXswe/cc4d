import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { CharacteristicNode } from '../device-selection/selection-data-source/characteristic-node';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {

  colspan?: number;
  rowspan?: number;
  height: string = '84vh';
  showCarousel: boolean = true;
  characteristics: CharacteristicNode[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(characteristics: CharacteristicNode[]){
    switch (true) {
      case characteristics.length == 0:
        this.height = '84vh';
        this.colspan = 2;
        this.rowspan = 2;
        break;
        case characteristics.length == 1:
        this.height = '42vh';
        this.colspan = 2;
        this.rowspan = 2;
        break;
      case characteristics.length == 2:
        this.height = '42vh';
        this.colspan = 1;
        this.rowspan = 2;
        break;
      case characteristics.length > 2:
        this.height = '42vh';
        this.colspan = 1;
        this.rowspan = 1;
        break;
    }
    this.characteristics = characteristics.slice();
  }
}
