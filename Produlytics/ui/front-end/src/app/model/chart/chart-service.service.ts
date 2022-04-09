import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ChartPoint } from './chart-point';
import {ChartAbstractService} from "./chart-abstract.service";
import {HttpClient} from "@angular/common/http";
import { Limits } from './limits';

@Injectable({
  providedIn: 'root'
})
export class ChartService implements ChartAbstractService{

  constructor(private httpClient: HttpClient) {}

  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPoint[]> {
    return this.httpClient.get<ChartPoint[]>(`/devices/${deviceId}/characteristics/${characteristicId}
    /detections?olderThan={olderThan}&newerThan=${(new Date).getTime()}&limit=${100}`);//TODO: da rivedere qui
  }

  getNextPoints(deviceId: number, characteristicId: number, olderThan: number): Observable<ChartPoint[]> {
    return this.httpClient.get<ChartPoint[]>(`/devices/${deviceId}/characteristics/${characteristicId}
    /detections?olderThan=${olderThan}&newerThan={newerThan}&limit={maxNumDetections}`);//TODO: da rivedere qui
  }

  getLimits(deviceId: number, characteristicId: number){
    return this.httpClient.get<Limits>(`/devices/${deviceId}/characteristics/${characteristicId}/limits`)
  }
}
