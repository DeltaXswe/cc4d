import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FindDeviceAbstractService} from "./find-device-abstract.service";
import {Observable} from "rxjs";
import {Device} from "../device";

@Injectable({
  providedIn: 'root'
})
export class FindDeviceService implements FindDeviceAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  findDeviceById(id: number): Observable<Device> {
    return this.httpClient.get<Device>(`admin/devices/${id}`);
  }
}
