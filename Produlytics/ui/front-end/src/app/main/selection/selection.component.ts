import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UnarchivedDeviceInfo} from '../../model/public-device/unarchived_device_info';
import * as d3 from 'd3'; 
import { Characteristic } from 'src/app/model/admin-device/characteristic/characteristic';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {
  machine: UnarchivedDeviceInfo | undefined;
  characteristics: Characteristic[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(characteristics: Characteristic[]){
    this.characteristics = characteristics;
  }
}
