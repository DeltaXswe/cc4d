import { Injectable } from '@angular/core';
import {ListUnarchivedDevicesAbstractService} from "../../model/public-device/list-unarchived-devices-abstract.service";
import {Observable, of, throwError} from "rxjs";
import {UnarchivedDeviceInfo} from "../../model/public-device/unarchived_device_info";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {Device} from "../../model/admin-device/device";

@Injectable({
  providedIn: 'root'
})
export class FakeDeviceService implements
  ListUnarchivedDevicesAbstractService,
  DeviceAbstractService
{
  private devices: Device[] = [
    {
      id: 1,
      name: 'Macchina 1',
      archived: true,
      deactivated: true,
      apiKey: 'AAA'
    },
    {
      id: 2,
      name: 'Macchina 2',
      archived: false,
      deactivated: false,
      apiKey: 'BBB'
    },
    {
      id: 3,
      name: 'Macchina 3',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }
  ];

  constructor() { }

  getUnarchivedDevices(): Observable<UnarchivedDeviceInfo[]> {
    return of(this.devices);
  }


  getDevices(): Observable<Device[]> {
    return of(this.devices);
  }

  activateDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.deactivated = false;
      return of({});
    } else {
      return throwError('Macchina non trovata')
    }
  }

  archiveDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.archived = true;
      return of({});
    } else {
      return throwError('Macchina non trovata')
    }
  }

  deactivateDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.deactivated = true;
      return of({});
    } else {
      return throwError('Macchina non trovata')
    }
  }

  restoreDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.archived = false;
      return of({});
    } else {
      return throwError('Macchina non trovata')
    }
  }
}
