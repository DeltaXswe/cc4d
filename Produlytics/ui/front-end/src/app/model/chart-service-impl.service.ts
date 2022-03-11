import { Injectable } from '@angular/core';

import { Observable, zip } from 'rxjs';

import { ChartPoint } from './chart-point';
import { CharacteristicInfo } from './characteristic-info';
import {AppService} from "./app.service";
import {ChartAbstractService} from "./repositories/chart-abstract.service";

@Injectable()
export class ChartService implements ChartAbstractService{
  constructor(private appService: AppService) {}

  getInitialPoints(
    macchina: number,
    caratteristica: string
  ): Observable<[CharacteristicInfo, ChartPoint[]]> {
    const info_url = `/characteristics/${macchina}/${caratteristica}`;
    const info = this.appService.get<CharacteristicInfo>(info_url);

    const points_url = `/detections/${macchina}/${caratteristica}`;
    const points = this.appService.get<ChartPoint[]>(points_url);

    return zip(info, points);
  }

  getNextPoints(
    macchina: number,
    caratteristica: string,
    ultimo_utc: number
  ): Observable<ChartPoint[]> {
    const url = `/detections/${macchina}/${caratteristica}?createdAfter=${ultimo_utc}`;
    return this.appService.get<ChartPoint[]>(url);
  }
}
