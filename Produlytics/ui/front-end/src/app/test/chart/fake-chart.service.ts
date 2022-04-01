import { Injectable } from '@angular/core';
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {BehaviorSubject, Observable, of} from "rxjs";
import {CharacteristicInfo} from "../../model/chart/characteristic-info";
import {ChartPoint} from "../../model/chart/chart-point";

@Injectable({
  providedIn: "root"
})
export class FakeChartService implements ChartAbstractService {

  fakeInitialPoints = new BehaviorSubject<[CharacteristicInfo, ChartPoint[]]> ([{
    machine: {
      id: 1,
      name: 'miao'
    },
    characteristic: {
      code: '999',
      machine: 1,
      name: 'bau',
      average: 100,
      lowerLimit: -100,
      upperLimit: 300
    }
  }, [{
    createdAtUtc: 100,
    value: 300,
    anomalous: false
  },
  {
    createdAtUtc: 110,
    value: 200,
    anomalous: false
  },
  {
    createdAtUtc: 120,
    value: 240,
    anomalous: false
  },
  {
    createdAtUtc: 130,
    value: 230,
    anomalous: false
  } ]]);

  fakePoints= new BehaviorSubject<ChartPoint[]> ([{
    createdAtUtc: 140,
    value: 220,
    anomalous: true
  }])
  
  constructor() { }

  getInitialPoints(macchina: number, caratteristica: number): Observable<[CharacteristicInfo, ChartPoint[]]> {
    return this.fakeInitialPoints;
  }

  getNextPoints(macchina: number, caratteristica: string, ultimo_utc: number): Observable<ChartPoint[]> {
    return of([]);
    //return this.fakePoints;
  }
}
