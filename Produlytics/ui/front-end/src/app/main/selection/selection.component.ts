import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { CharacteristicNode } from '../device-selection/selection-data-source/characteristic-node';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {

  showCarousel: boolean = true;
  characteristics: CharacteristicNode[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(characteristics: CharacteristicNode[]){
    this.characteristics = characteristics.slice();
  }
}
