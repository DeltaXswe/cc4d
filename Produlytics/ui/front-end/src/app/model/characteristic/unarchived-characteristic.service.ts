import { Injectable } from '@angular/core';
import { UnarchivedCharacteristic } from './unarchived-characteristic';
import { Observable } from 'rxjs';
import {HttpClient} from "@angular/common/http";
import {UnarchivedCharacteristicAbstractService} from "./unarchived-characteristic-abstract.service";

@Injectable({
  providedIn: 'root'
})
export class UnarchivedCharacteristicService implements UnarchivedCharacteristicAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * Ottiene le caratteristiche per delle ricerche leggere tra le caratteristiche della macchina.
   *
   * */
  getCharacteristicsByDevice(deviceId: number): Observable<UnarchivedCharacteristic[]>{
    return this.httpClient.get<UnarchivedCharacteristic[]>(`/devices/${deviceId}/characteristics`);
  }
}
