import { Injectable } from '@angular/core';
import { Characteristic } from './characteristic/characteristic';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Machine } from './machine/machine';

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService {

  constructor(private http: HttpClient,) { }

  getCharacteristics(machine: number): Observable<Characteristic[]>{
    const characteristicsUrl = `/api-web/characteristics/${machine}`;
    return this.http.get<Characteristic[]>(characteristicsUrl);
  }
} 
