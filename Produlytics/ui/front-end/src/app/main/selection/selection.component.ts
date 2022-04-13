import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UnarchivedDeviceInfo} from '../../model/public-device/unarchived_device_info';
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
  showCarousel: boolean = true;
  machine: UnarchivedDeviceInfo | undefined;
  characteristics: CharacteristicNode[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(characteristics: CharacteristicNode[]){
    switch (true) {
      case characteristics.length < 2:
        this.colspan = 2;
        this.rowspan = 2;
        break;
      case characteristics.length == 2:
        this.colspan = 2;
        this.rowspan = 1;
        break;
      case characteristics.length > 2:
        this.colspan = 1;
        this.rowspan = 1;
        break;
    }
    this.characteristics = characteristics.slice();
  }
}
