import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ChartPointReturn } from './chart-point-return';
import {ChartAbstractService} from "./chart-abstract.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import { Limits } from './limits';

@Injectable({
  providedIn: 'root'
})
export class ChartService implements ChartAbstractService{

  constructor(private httpClient: HttpClient) {}

  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPointReturn> {
    const paramsObj = {
      olderThan: (new Date).getTime(),
      limit: 100
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj})
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`,
      {params});
  }

  getNextPoints(deviceId: number, characteristicId: number, olderThan: number): Observable<ChartPointReturn> {
    const paramsObj = {
      newerThan: (new Date).getTime(),
      limit: 10 //?
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj})
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`
    , {params});
  }

  getLimits(deviceId: number, characteristicId: number){
    return this.httpClient.get<Limits>(`/devices/${deviceId}/characteristics/${characteristicId}/limits`)
  }

  getOldPoints(start: number, end: number, deviceId: number, characteristicId: number): Observable<ChartPointReturn> {
    const limit = (end - start)/1000;
    const paramsObj = {
      olderThan: end,
      limit: limit
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj});
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`, {params});
  }
}