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

  private characteristicsUrl = '/api/characteristics/${machine}';

  getCharacteristics(machine: number): Observable<Characteristic[]>{
    return this.http.get<Characteristic[]>(this.characteristicsUrl);
  }
} 
