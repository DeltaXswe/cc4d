import { Injectable } from '@angular/core';
import { Characteristic } from './characteristic';
import { Observable } from 'rxjs';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService {

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * Ottiene le caratteristiche per delle ricerche leggere tra le caratteristiche della macchina.
   *
   * */
  getCharacteristics(deviceId: number): Observable<Characteristic[]>{
    return this.httpClient.get<Characteristic[]>(`/devices/${deviceId}/characteristics`);
  }
}
