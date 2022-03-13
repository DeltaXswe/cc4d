import { Injectable } from '@angular/core';
import {ListUnarchivedDevicesAbstractService} from "../../model/public-device/list-unarchived-devices-abstract.service";
import {Observable, of} from "rxjs";
import {UnarchivedDeviceInfo} from "../../model/public-device/unarchived_device_info";

@Injectable()
export class FakeUnarchivedDeviceService implements ListUnarchivedDevicesAbstractService{

  constructor() { }

  getUnarchivedDevices(): Observable<UnarchivedDeviceInfo[]> {
    return of([
      {
        id: 1,
        name: 'Macchina 1'
      },
      {
        id: 2,
        name: 'Macchina 2'
      },
      {
        id: 3,
        name: 'Macchina 3'
      }
    ]);
  }
}
