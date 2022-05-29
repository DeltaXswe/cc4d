import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewDeviceAbstractService} from "./new-device-abstract.service";
import {DeviceCreationCommand} from "./device-creation-command";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NewDeviceService implements NewDeviceAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  insertDevice(machine: DeviceCreationCommand): Observable<{ id: number }> {
    return this.httpClient.post<{id: number}>(`admin/devices`, machine);
  }

}
