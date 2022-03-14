import { Injectable } from '@angular/core';
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {BehaviorSubject, Observable, of} from "rxjs";
import {CharacteristicInfo} from "../../model/chart/characteristic-info";
import {ChartPoint} from "../../model/chart/chart-point";

@Injectable({
  providedIn: "root"
})
export class FakeChartService implements ChartAbstractService {

  fakeInitialPoints = new BehaviorSubject<[CharacteristicInfo, ChartPoint[]]>([{
    machine: {
      id: 1,
      name: 'miao'
    },
    characteristic: {
      code: '999',
      machine: 1,
      name: 'bau',
      average: 1,
      lowerLimit: -10,
      upperLimit: 10
    }
  }, []]);

  constructor() { }

  getInitialPoints(macchina: number, caratteristica: number): Observable<[CharacteristicInfo, ChartPoint[]]> {
    return this.fakeInitialPoints;
  }

  getNextPoints(macchina: number, caratteristica: string, ultimo_utc: number): Observable<ChartPoint[]> {
    return of([]);
  }
}
