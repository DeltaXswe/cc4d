import { Injectable } from '@angular/core';
import { ChartAbstractService } from "../../model/chart/chart-abstract.service";
import { BehaviorSubject, interval, map, Observable, of } from "rxjs";
import { ChartPoint } from "../../model/chart/chart-point";
import { Limits } from '../../model/chart/limits';
import { ChartPointReturn } from '../../model/chart/chart-point-return';
import { now } from 'd3';

@Injectable({
  providedIn: "root"
})
export class FakeChartService implements ChartAbstractService {

  fakeLimits2 = new BehaviorSubject<Limits>({
    lowerLimit: -300,
    upperLimit: 300,
    mean: 0
  });

  fakeLimits3 = new BehaviorSubject<Limits>({
    lowerLimit: -300,
    upperLimit: 300,
    mean: 0
  });

  fakeInitialPoints2 = new BehaviorSubject<ChartPointReturn> ({
  detections: [
  {
    creationTime: 1650067200000,
    value: 300,
    outlier: false
  },
  {
    creationTime: 1650067201000,
    value: 200,
    outlier: false
  },
  {
    creationTime: 1650067202000,
    value: 240,
    outlier: false
  },
  {
    creationTime: 1650067203000,
    value: 230,
    outlier: false
  }],
  nextOld: 1650067200000,
  nextNew: 1650067203000
  });
  
  fakeInitialPoints3 = new BehaviorSubject<ChartPointReturn> ({
  detections:[
  {
    creationTime: 1650067200000,
    value: 400,
    outlier: false
  },
  {
    creationTime: 1650067201000,
    value: 300,
    outlier: false
  },
  {
    creationTime: 1650067202000,
    value: 340,
    outlier: false
  },
  {
    creationTime: 1650067203000,
    value: 330,
    outlier: false
  }],
  nextOld: 1650067200000,
  nextNew: 1650067203000
  });

  constructor() { 
    /* interval(1000).subscribe(() => {
    
      let creationTime: number = Date.now();
      let value: number = Math.floor(Math.random() * (500));
      value *= Math.round(Math.random()) ? 1 : -1;
      let outlier: boolean = Math.random() < 0.5;
      const point: ChartPointReturn = {
        detections: [
          ],
        nextOld: creationTime,
        nextNew: creationTime
      }
      const points = this.fakeInitialPoints2.value.detections;
      points.push({
        creationTime: creationTime,
        value: value,
        outlier: outlier
      })
      point.detections = points;
      this.fakeInitialPoints2.next(point)
    }); */
  }

  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPointReturn> {
    if(deviceId == 2)
      return this.fakeInitialPoints2;
    else(deviceId == 3)
      return this.fakeInitialPoints3;
  }

  getNextPoints(macchina: number, caratteristica: number, latestcreationTime: number): Observable<ChartPointReturn> {
    let creationTime: number = latestcreationTime+1000;
    let value: number = Math.floor(Math.random() * (500));
    value *= Math.round(Math.random()) ? 1 : -1;
    let outlier: boolean = Math.random() < 0.5;
    const point: ChartPointReturn = {
      detections: [
      {
        creationTime: creationTime,
        value: value,
        outlier: outlier
      }],
      nextOld: creationTime,
      nextNew: creationTime
    }

    return of(point);
  }

  getLimits(deviceId: number, characteristicId: number){
    if(deviceId == 2)
      return this.fakeLimits2;
    else(deviceId == 3)
      return this.fakeLimits3;
  }

  getOldPoints(start: number, end: number, deviceId: number, characteristicId: number): Observable<ChartPointReturn>{
    let points: ChartPoint[] = [];
    console.log(start);
    console.log(end);
     for (let i = start; i < end; i+= 1000){
      let value: number = Math.floor(Math.random() * (500));
      value *= Math.round(Math.random()) ? 1 : -1;
      let point: ChartPoint = {
        creationTime: i,
        value: value,
        outlier: false
      }
      points.push(point);
    } 
    const point: ChartPointReturn = {
      detections: points,
      nextNew: 100
    }
    return of(point);
  }

}
