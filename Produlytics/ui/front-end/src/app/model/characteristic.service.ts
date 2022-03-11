import { Injectable } from '@angular/core';
import { Characteristic } from './characteristic';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Machine } from './machine';
import {AppService} from "./app.service";

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService {

  constructor(private appService: AppService) { }

  getCharacteristics(machine: number): Observable<Characteristic[]>{
    const characteristicsUrl = `/characteristics/${machine}`;
    return this.appService.get<Characteristic[]>(characteristicsUrl);
  }
}
