import {Observable} from "rxjs";
import {ChartPoint} from "./chart-point";
import {Injectable} from "@angular/core";
import { Limits } from "./limits";
import { ChartPointReturn } from "./chart-point-return";

@Injectable()
export abstract class ChartAbstractService {
  abstract getInitialPoints(
    macchina: number,
    caratteristica: number
  ): Observable<ChartPointReturn>;

  abstract getNextPoints(
    macchina: number,
    caratteristica: number,
    ultimo_utc: number
  ): Observable<ChartPointReturn>;

  abstract getLimits(deviceId: number, characteristicId: number): Observable<Limits>;

  abstract getOldPoints(start: string, end: string, deviceId: number, characteristicId: number): Observable<ChartPointReturn>;
}
