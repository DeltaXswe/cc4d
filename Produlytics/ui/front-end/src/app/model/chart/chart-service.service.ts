import { Injectable } from '@angular/core';

import { Observable, zip } from 'rxjs';

import { ChartPoint } from './chart-point';
import { CharacteristicInfo } from './characteristic-info';
import {ChartAbstractService} from "./chart-abstract.service";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ChartService implements ChartAbstractService{

  constructor(private httpClient: HttpClient) {}

  getInitialPoints(
    macchina: number,
    caratteristica: string
  ): Observable<[CharacteristicInfo, ChartPoint[]]> {
    const info = this.httpClient.get<CharacteristicInfo>(`/characteristics/${macchina}/${caratteristica}`);
    const points = this.httpClient.get<ChartPoint[]>(`/detections/${macchina}/${caratteristica}`);

    return zip(info, points);
  }

  getNextPoints(
    macchina: number,
    caratteristica: string,
    ultimo_utc: number
  ): Observable<ChartPoint[]> {
    return this.httpClient.get<ChartPoint[]>(`/detections/${macchina}/${caratteristica}?createdAfter=${ultimo_utc}`);
  }
}
