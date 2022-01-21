import { Injectable } from '@angular/core';
import { Characteristic } from './characteristic/characteristic';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Machine } from './machine/machine';
import {AppService} from "../app.service";

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
