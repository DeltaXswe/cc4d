import { Injectable } from '@angular/core';
import { UnarchivedCharacteristic } from './unarchived-characteristic';
import { Observable } from 'rxjs';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UnarchivedCharacteristicService {

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * Ottiene le caratteristiche per delle ricerche leggere tra le caratteristiche della macchina.
   *
   * */
  getCharacteristics(deviceId: number): Observable<UnarchivedCharacteristic[]>{
    return this.httpClient.get<UnarchivedCharacteristic[]>(`/devices/${deviceId}/characteristics`);
  }
}
