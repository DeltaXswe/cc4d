import { Injectable } from '@angular/core';
import {ListUnarchivedDevicesAbstractService} from "../../model/public-device/list-unarchived-devices-abstract.service";
import {Observable, of, throwError} from "rxjs";
import {UnarchivedDeviceInfo} from "../../model/public-device/unarchived_device_info";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {Device} from "../../model/admin-device/device";
import {NewDeviceService} from "../../model/admin-device/new-device.service";
import {DeviceCreationCommand} from "../../model/admin-device/device-creation-command";
import {NewDeviceAbstractService} from "../../model/admin-device/new-device-abstract.service";

class DeviceMock {

   id: number;
   name: string;
   apiKey: string;
   archived: boolean;
   deactivated: boolean;

  constructor(device: Device) {
    this.id = device.id;
    this.name = device.name;
    this.apiKey = device.apiKey;
    this.archived = device.archived;
    this.deactivated = device.deactivated;
  }

  build(): Device {
    return this;
  }
}

@Injectable({
  providedIn: 'root'
})
export class FakeDeviceService implements
  ListUnarchivedDevicesAbstractService,
  DeviceAbstractService,
  NewDeviceAbstractService
{
  private devices: DeviceMock[] = [
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
  ].map(device => new DeviceMock(device));

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

  insertDevice(deviceCreationCommand: DeviceCreationCommand): Observable<{ id: number }> {
    const nextId = Math.max(...this.devices.map(device => device.id)) + 1;
    const newDevice = new DeviceMock({
      id: nextId,
      name: deviceCreationCommand.name,
      apiKey: 'BULABULA',
      deactivated: false,
      archived: false
    });
    this.devices.push(newDevice);
    return of(newDevice);
  }


}
