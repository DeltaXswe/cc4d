import {Observable} from "rxjs";
import {ChartPoint} from "./chart-point";
import {Injectable} from "@angular/core";
import { Limits } from "./limits";

@Injectable()
export abstract class ChartAbstractService {
  abstract getInitialPoints(
    macchina: number,
    caratteristica: number
  ): Observable<ChartPoint[]>;

  abstract getNextPoints(
    macchina: number,
    caratteristica: number,
    ultimo_utc: number
  ): Observable<ChartPoint[]>;

  abstract getLimits(deviceId: number, characteristicId: number): Observable<Limits>;

  abstract getOldPoints(start: any, end: any): Observable<ChartPoint[]>;
}
