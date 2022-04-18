import { ChartPoint } from "./chart-point";

export interface ChartPointReturn {
   chartPoints: ChartPoint[]
   nextOld?: number
   nextNew: number
}