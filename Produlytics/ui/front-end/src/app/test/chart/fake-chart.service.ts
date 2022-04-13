import { Injectable } from '@angular/core';
import { ChartAbstractService } from "../../model/chart/chart-abstract.service";
import { BehaviorSubject, Observable, of } from "rxjs";
import { CharacteristicInfo } from "../../model/chart/characteristic-info";
import { ChartPoint } from "../../model/chart/chart-point";
import { Limits } from '../../model/chart/limits';

@Injectable({
  providedIn: "root"
})
export class FakeChartService implements ChartAbstractService {

  fakeLimits2 = new BehaviorSubject<Limits>({
    lowerLimit: -100,
    upperLimit: 300,
    mean: 100
  });

  fakeLimits3 = new BehaviorSubject<Limits>({
    lowerLimit: -200,
    upperLimit: 400,
    mean: 100
  });

  fakeInitialPoints2 = new BehaviorSubject<ChartPoint[]> ([
  {
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
  } ]);
  
  fakeInitialPoints3 = new BehaviorSubject<ChartPoint[]> ([{
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
  } ]);

  fakePoints= new BehaviorSubject<ChartPoint[]> ([{
    createdAtUtc: 140,
    value: 320,
    anomalous: true
  }])

  constructor() { }

  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPoint[]> {
    if(deviceId == 2)
      return this.fakeInitialPoints2;
    else(deviceId == 3)
      return this.fakeInitialPoints3;
  }

  getNextPoints(macchina: number, caratteristica: number, ultimo_utc: number): Observable<ChartPoint[]> {
    let createdAtUtc: number = ultimo_utc+10;
    let value: number = Math.floor(Math.random() * (500));
    value *= Math.round(Math.random()) ? 1 : -1;
    let anomalous: boolean = Math.random() < 0.5;
    const point: ChartPoint = {
      createdAtUtc: createdAtUtc,
      value: value,
      anomalous: anomalous
    }
    return of([point]);
  }

  getLimits(deviceId: number, characteristicId: number){
    if(deviceId == 2)
      return this.fakeLimits2;
    else(deviceId == 3)
      return this.fakeLimits3;
  }

}
