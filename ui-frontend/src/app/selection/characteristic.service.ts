import { Injectable } from '@angular/core';
import { Characteristic } from './characteristic/characteristic';
import { CHARACTERISTICS } from './characteristic/mock-char';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService {

  constructor(private http: HttpClient,) { }

  //private characteristcsUrl = '/caratteristiche';

  getCharacteristics(): Observable<Characteristic[]>{
    const characteristics = of(CHARACTERISTICS);
    return characteristics;
    //return this.http.get<Characteristic[]>(this.characteristicsUrl)
  }
}
