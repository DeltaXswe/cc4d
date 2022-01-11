import { ChartPoint } from './chart-point';

export interface InitialChartPoints {
  nome_macchina: string;
  media: number;
  limite_min: number;
  limite_max: number;
  rilevazioni: ChartPoint[],
}
