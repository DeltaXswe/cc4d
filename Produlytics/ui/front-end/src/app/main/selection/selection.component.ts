import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {UnarchivedDeviceInfo} from '../../model/public-device/unarchived_device_info';
import * as d3 from 'd3';
import { Characteristic } from 'src/app/model/admin-device/characteristic/characteristic';
import {CharacteristicNode} from "../device-selection/selection-data-source/selection-node";
import { ChartComponent } from '../chart/chart.component';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {

  machine: UnarchivedDeviceInfo | undefined;
  characteristics: CharacteristicNode[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(characteristics: CharacteristicNode[]){
    this.characteristics = characteristics;
  }
}
