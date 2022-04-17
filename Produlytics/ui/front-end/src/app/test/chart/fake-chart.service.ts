import { Injectable } from '@angular/core';
import { ChartAbstractService } from "../../model/chart/chart-abstract.service";
import { BehaviorSubject, Observable, of } from "rxjs";
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
    epoch: 1650067200000,
    value: 300,
    anomalous: false
  },
  {
    epoch: 1650067201000,
    value: 200,
    anomalous: false
  },
  {
    epoch: 1650067202000,
    value: 240,
    anomalous: false
  },
  {
    epoch: 1650067203000,
    value: 230,
    anomalous: false
  } ]);
  
  fakeInitialPoints3 = new BehaviorSubject<ChartPoint[]> ([{
    epoch: 1650067200000,
    value: 400,
    anomalous: false
  },
  {
    epoch: 1650067201000,
    value: 300,
    anomalous: false
  },
  {
    epoch: 1650067202000,
    value: 340,
    anomalous: false
  },
  {
    epoch: 1650067203000,
    value: 330,
    anomalous: false
  } ]);

  constructor() { }

  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPoint[]> {
    if(deviceId == 2)
      return this.fakeInitialPoints2;
    else(deviceId == 3)
      return this.fakeInitialPoints3;
  }

  getNextPoints(macchina: number, caratteristica: number, latestEpoch: number): Observable<ChartPoint[]> {
    let epoch: number = latestEpoch+1000;
    let value: number = Math.floor(Math.random() * (500));
    value *= Math.round(Math.random()) ? 1 : -1;
    let anomalous: boolean = Math.random() < 0.5;
    const point: ChartPoint = {
      epoch: epoch,
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

  getOldPoints(start: any, end: any): Observable<ChartPoint[]>{
    let points: ChartPoint[] = [];
     for (let i = Date.parse(start); i < Date.parse(start)+100000; i+= 1000){  //commentato perchè è un ciclo grosso come una casa
      let value: number = Math.floor(Math.random() * (500));
      value *= Math.round(Math.random()) ? 1 : -1;
      let point: ChartPoint = {
        epoch: i,
        value: value,
        anomalous: false
      }
      points.push(point);
    } 
    return of(points);
  }

}
