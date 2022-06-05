import { Injectable } from '@angular/core';
import {DeviceAbstractService} from "./device-abstract.service";
import {Device} from "./device";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DeviceService implements DeviceAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getDevices(): Observable<Device[]> {
    return this.httpClient.get<Device[]>(`admin/devices`);
  }

  deactivateDevice(device: Device): Observable<{}> {
    return this.httpClient.put(`admin/devices/${device.id}/deactivated`, true);
  }

  activateDevice(device: Device): Observable<{}> {
    return this.httpClient.put(`admin/devices/${device.id}/deactivated`, false);
  }

  archiveDevice(device: Device): Observable<{}> {
    return this.httpClient.put(`admin/devices/${device.id}/archived`, true);
  }

  restoreDevice(device: Device): Observable<{}> {
    return this.httpClient.put(`admin/devices/${device.id}/archived`, false);
  }
}
