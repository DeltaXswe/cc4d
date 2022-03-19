import { Injectable } from '@angular/core';
import {ListUnarchivedDevicesAbstractService} from "../../model/public-device/list-unarchived-devices-abstract.service";
import {Observable, of, throwError} from "rxjs";
import {UnarchivedDeviceInfo} from "../../model/public-device/unarchived_device_info";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {Device} from "../../model/admin-device/device";
import {DeviceCreationCommand} from "../../model/admin-device/new/device-creation-command";
import {NewDeviceAbstractService} from "../../model/admin-device/new/new-device-abstract.service";
import {FindDeviceAbstractService} from "../../model/admin-device/find-detail/find-device-abstract.service";
import {CharacteristicAbstractService} from "../../model/admin-device/characteristic/characteristic-abstract.service";
import {Characteristic} from "../../model/admin-device/characteristic/characteristic";
import {CharacteristicCreationCommand} from "../../model/admin-device/new/characteristic-creation-command";
import {UpdateDeviceAbstractService} from "../../model/admin-device/update/update-device-abstract.service";
import {
  UpdateCharacteristicAbstractService
} from "../../model/admin-device/characteristic/update-characteristic-abstract.service";
import {CharacteristicUpdateCommand} from "../../model/admin-device/characteristic/characteristic-update-command";

class CharacteristicMock implements Characteristic {
  id: number;
  name: string;
  archived: boolean;
  autoAdjust: boolean;
  lowerLimit: number | null;
  upperLimit: number | null;
  sampleSize: number | null;

  constructor(characteristic: Characteristic) {
    this.id = characteristic.id;
    this.name = characteristic.name;
    this.archived = characteristic.archived;
    this.autoAdjust = characteristic.autoAdjust;
    this.lowerLimit = characteristic.lowerLimit;
    this.upperLimit = characteristic.upperLimit;
    this.sampleSize = characteristic.sampleSize;
  }
}

class DeviceMock implements Device {

   id: number;
   name: string;
   apiKey: string;
   archived: boolean;
   deactivated: boolean;
   characteristics: CharacteristicMock[] = [];

  constructor(device: Device, chars?: Characteristic[]) {
    this.id = device.id;
    this.name = device.name;
    this.apiKey = device.apiKey;
    this.archived = device.archived;
    this.deactivated = device.deactivated;
    if (chars) {
      this.characteristics = chars.map(char => new CharacteristicMock(char));
    }
  }
}

@Injectable({
  providedIn: 'root'
})
export class FakeDeviceService implements
  ListUnarchivedDevicesAbstractService,
  DeviceAbstractService,
  NewDeviceAbstractService,
  FindDeviceAbstractService,
  CharacteristicAbstractService,
  UpdateDeviceAbstractService,
  UpdateCharacteristicAbstractService
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
  ].map(device => new DeviceMock(device, [
    {
      id: 1,
      name: 'Valvola',
      archived: false,
      autoAdjust: false,
      lowerLimit: 10,
      upperLimit: 20,
      sampleSize: null
    },
    {
      id: 2,
      name: 'Isola',
      archived: true,
      autoAdjust: true,
      lowerLimit: null,
      upperLimit: null,
      sampleSize: null
    },
    {
      id: 3,
      name: 'Asola',
      archived: false,
      autoAdjust: true,
      lowerLimit: null,
      upperLimit: null,
      sampleSize: 12
    }
  ]));

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
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  archiveDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.archived = true;
      return of({});
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  deactivateDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.deactivated = true;
      return of({});
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  restoreDevice(device: Device): Observable<{}> {
    const source = this.devices.find(source => device.id === source.id);
    if (source) {
      source.archived = false;
      return of({});
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
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

  findDeviceById(id: number): Observable<Device> {
    const device = this.devices.find(device => device.id === id);
    if (device) {
      return of(device);
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  getCharacteristicsByDevice(deviceId: number): Observable<Characteristic[]> {
    const device = this.devices.find(device => device.id === deviceId);
    if (device?.characteristics) {
      return of(device?.characteristics);
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  addCharacteristic(deviceId: number, command: CharacteristicCreationCommand): Observable<{ id: number }> {
    const device = this.devices.find(device => device.id === deviceId);
    if (!device) {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
    if (device?.characteristics.find(char => char.name === command.name)) {
      return throwError({
        errorCode: 'duplicateCharacteristicName'
      });
    } else {
      // TODO refine
      const newId = Math.max(...device.characteristics.map(device => device.id)) + 1;
      const inserted = new CharacteristicMock({
        id: newId,
        name: command.name,
        archived: false,
        autoAdjust: command.autoAdjust,
        sampleSize: command.sampleSize,
        upperLimit: command.upperLimit,
        lowerLimit: command.lowerLimit
      });
      device.characteristics.push(inserted);
      return of(inserted)
    }
  }

  archiveCharacteristic(deviceId: number, id: number): Observable<{}> {
    try {

      const device = this.devices.find(device => device.id === deviceId)!;
      const char = device.characteristics.find(char => char.id === id)!;
      char.archived = true;
      return of({});
    } catch (err) {
      return throwError({
        errorCode: 'characteristicNotFound'
      });
    }
  }

  recoverCharacteristic(deviceId: number, id: number): Observable<{}> {
    try {
      const device = this.devices.find(device => device.id === deviceId)!;
      const char = device.characteristics.find(char => char.id === id)!;
      char.archived = false;
      return of({});
    } catch (err) {
      return throwError({
        errorCode: 'characteristicNotFound'
      });
    }
  }

  updateDeviceName(id: number, newName: string): Observable<{}> {
    const device = this.devices.find(device => device.id === id);
    if (device) {
      const sameName = this.devices.find(device => device.name === newName);
      if (!sameName) {
        device.name = newName;
        return of({});
      } else {
        return throwError({
          errorCode: 'duplicateDeviceName'
        });
      }
    } else {
      return throwError({
        errorCode: 'deviceNotFound'
      });
    }
  }

  updateCharacteristic(command: CharacteristicUpdateCommand): Observable<{}> {
    const device = this.devices.find(device => device.id === command.deviceId);
    if (device) {
      const sameName = device.characteristics.find(char => char.name === command.name);
      if (sameName) {
        return throwError({
          errorCode: 'duplicateCharacteristicName'
        });
      }
      const existing = device.characteristics.find(char => char.id === command.id);
      if (!existing) {
        return throwError({
          errorCode: 'characteristicNotFound'
        });
      } else {
        existing.name = command.name;
        existing.autoAdjust = command.autoAdjust;
        existing.lowerLimit = command.lowerLimit;
        existing.upperLimit = command.upperLimit;
        existing.sampleSize = command.sampleSize;
        return of({});
      }
    } else {
      return throwError({
        errorCode: 'characteristicNotFound'
      });
    }
  }

}
