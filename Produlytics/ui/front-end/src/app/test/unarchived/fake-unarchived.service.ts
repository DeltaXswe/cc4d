import { Injectable } from '@angular/core';
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import {delay, map, Observable} from "rxjs";
import {UnarchivedCharacteristic} from "../../model/characteristic/unarchived-characteristic";
import {UnarchivedDevice} from "../../model/device/unarchived-device";
import {FakeDeviceService} from "../device/fake-device.service";

@Injectable({
  providedIn: 'root'
})
export class FakeUnarchivedService implements
  UnarchivedDeviceAbstractService,
  UnarchivedCharacteristicAbstractService
{

  constructor(
    private deviceService: FakeDeviceService
  ) { }

  getCharacteristicsByDevice(deviceId: number): Observable<UnarchivedCharacteristic[]> {
    return this.deviceService.getCharacteristicsByDevice(deviceId)
      .pipe(
        map(chars => chars.filter(char => !char.archived)),
        delay(1000)
      );
  }

  getDevices(): Observable<UnarchivedDevice[]> {
    return this.deviceService.getDevices()
      .pipe(
        map(devs => devs.filter(dev => !dev.archived)),
        delay(1000)
      );
  }


}
