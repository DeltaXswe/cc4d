import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UnarchivedDeviceInfo} from '../../model/public-device/unarchived_device_info';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {
  machine: UnarchivedDeviceInfo | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
