import { Injectable } from '@angular/core';
import { UnarchivedDeviceInfo } from './unarchived_device_info';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {ListUnarchivedDevicesAbstractService} from "./list-unarchived-devices-abstract.service";

@Injectable()
export class UnarchivedDeviceService implements ListUnarchivedDevicesAbstractService {

  constructor(private httpClient: HttpClient) { }

  getUnarchivedDevices(): Observable<UnarchivedDeviceInfo[]> {
    return this.httpClient.get<UnarchivedDeviceInfo[]>('/devices');
  }
}
