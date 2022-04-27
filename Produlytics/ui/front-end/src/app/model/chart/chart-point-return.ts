import { ChartPoint } from "./chart-point";

export interface ChartPointReturn {
   detections: ChartPoint[]
   nextOld?: number
   nextNew: number
}