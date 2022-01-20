import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, zip } from 'rxjs';

import { ChartPoint } from './chart-point';
import { CharacteristicInfo } from './characteristic-info';

@Injectable({
  providedIn: 'root',
})
export class ChartService {
  constructor(private http: HttpClient) {}

  getInitialPoints(
    macchina: number,
    caratteristica: string
  ): Observable<[CharacteristicInfo, ChartPoint[]]> {
    const info_url = `/apiweb/characteristics/${macchina}/${caratteristica}`;
    const info = this.http.get<CharacteristicInfo>(info_url);

    const points_url = `/apiweb/detections/${macchina}/${caratteristica}`;
    const points = this.http.get<ChartPoint[]>(points_url);

    return zip(info, points);
  }

  getNextPoints(
    macchina: number,
    caratteristica: string,
    ultimo_utc: number
  ): Observable<ChartPoint[]> {
    const url = `/apiweb/detections/${macchina}/${caratteristica}?createdAfter=${ultimo_utc}`;
    return this.http.get<ChartPoint[]>(url);
  }
}
