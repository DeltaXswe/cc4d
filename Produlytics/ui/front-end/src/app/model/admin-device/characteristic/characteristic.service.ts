import { Injectable } from '@angular/core';
import {CharacteristicAbstractService} from "./characteristic-abstract.service";
import {Observable} from "rxjs";
import {Characteristic} from "./characteristic";
import {HttpClient} from "@angular/common/http";
import {CharacteristicCreationCommand} from "../new/characteristic-creation-command";

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService implements CharacteristicAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getCharacteristicsByDevice(deviceId: number): Observable<Characteristic[]> {
    return this.httpClient.get<Characteristic[]>(`admin/devices/${deviceId}/characteristics`);
  }

  addCharacteristic(deviceId: number, command: CharacteristicCreationCommand): Observable<{ id: number }> {
    return this.httpClient.post<{id: number}>(`admin/devices/{deviceId}/characteristics`, command);
  }

  recoverCharacteristic(deviceId: number, id: number): Observable<{}> {
    return this.httpClient.put(`admin/devices/${deviceId}/characteristics/${id}/archived`, false);
  }

  archiveCharacteristic(deviceId: number, id: number): Observable<{}> {
    return this.httpClient.put(`admin/devices/${deviceId}/characteristics/${id}/archived`, true);
  }
}
