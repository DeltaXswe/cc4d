import { Injectable } from '@angular/core';
import {UpdateDeviceAbstractService} from "./update-device-abstract.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UpdateDeviceService implements UpdateDeviceAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  updateDeviceName(id: number, newName: string): Observable<{}> {
    return this.httpClient.put(`admin/devices/${id}/name`, newName);
  }
}
