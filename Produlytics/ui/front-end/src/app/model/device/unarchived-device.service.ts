import { Injectable } from '@angular/core';
import {UnarchivedDeviceAbstractService} from "./unarchived-device-abstract.service";
import {Observable} from "rxjs";
import {UnarchivedDevice} from "./unarchived-device";
import {UnarchivedDeviceInfo} from "../public-device/unarchived_device_info";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UnarchivedDeviceService implements UnarchivedDeviceAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getDevices(): Observable<UnarchivedDeviceInfo[]> {
    return this.httpClient.get<UnarchivedDeviceInfo[]>('/devices');
  }


}
