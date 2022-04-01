import { Injectable } from '@angular/core';
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {BehaviorSubject, Observable, of} from "rxjs";
import {CharacteristicInfo} from "../../model/chart/characteristic-info";
import {ChartPoint} from "../../model/chart/chart-point";

@Injectable({
  providedIn: "root"
})
export class FakeChartService implements ChartAbstractService {

  fakeInitialPoints2 = new BehaviorSubject<[CharacteristicInfo, ChartPoint[]]> ([{
    machine: {
      id: 2,
      name: 'miao'
    },
    characteristic: {
      code: '1',
      machine: 2,
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
  
  fakeInitialPoints3 = new BehaviorSubject<[CharacteristicInfo, ChartPoint[]]> ([{
    machine: {
      id: 3,
      name: 'mooo'
    },
    characteristic: {
      code: '3',
      machine: 3,
      name: 'cowboy',
      average: 100,
      lowerLimit: -100,
      upperLimit: 300
    }
  }, [{
    createdAtUtc: 100,
    value: 400,
    anomalous: false
  },
  {
    createdAtUtc: 110,
    value: 300,
    anomalous: false
  },
  {
    createdAtUtc: 120,
    value: 340,
    anomalous: false
  },
  {
    createdAtUtc: 130,
    value: 330,
    anomalous: false
  } ]]);

  fakePoints= new BehaviorSubject<ChartPoint[]> ([{
    createdAtUtc: 140,
    value: 320,
    anomalous: true
  }])
  constructor() { }

  getInitialPoints(deviceId: number, characteristicId: number): Observable<[CharacteristicInfo, ChartPoint[]]> {
    if(deviceId == 2)
      return this.fakeInitialPoints2;
    else(deviceId == 3)
      return this.fakeInitialPoints3;
  }

  getNextPoints(macchina: number, caratteristica: string, ultimo_utc: number): Observable<ChartPoint[]> {
    return of([]);
    //return this.fakePoints;
  }
}
