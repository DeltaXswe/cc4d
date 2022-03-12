import {Observable} from "rxjs";
import {CharacteristicInfo} from "./characteristic-info";
import {ChartPoint} from "./chart-point";
import {Injectable} from "@angular/core";

@Injectable()
export abstract class ChartAbstractService {
  abstract getInitialPoints(
    macchina: number,
    caratteristica: number
  ): Observable<[CharacteristicInfo, ChartPoint[]]>;

  abstract getNextPoints(
    macchina: number,
    caratteristica: string,
    ultimo_utc: number
  ): Observable<ChartPoint[]>;
}
