import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map, Observable } from 'rxjs';

import { ChartPoint } from './chart-point';
import { InitialChartPoints } from './initial-chart-points';

@Injectable({
  providedIn: 'root',
})
export class ChartService {
  constructor(private http: HttpClient) {}

  getInitialPoints(
    macchina: number,
    caratteristica: string
  ): Observable<InitialChartPoints> {
    // TODO: porta 8080
    // const url = `/rilevazioni/${macchina}/${caratteristica}`;
    // return this.http.get<InitialChartPoints>(url);

    const url = `assets/sample_data.csv`;
    return this.http.get(url, { responseType: 'text' }).pipe(
      map((csv) => {
        const rilevazioni: ChartPoint[] = csv
          .split('\n')
          .slice(1)
          .slice(0, 100)
          .map((line) => {
            const [creato_il_utc, valore] = line.split(',');
            return {
              creato_il_utc: new Date(creato_il_utc).getTime() / 1000,
              valore: Number(valore),
              anomalo: new Date(creato_il_utc).getTime() % 13 == 0,
            };
          });
        return {
          nome_macchina: 'Nome macchina',
          media: 400,
          limite_min: 300,
          limite_max: 500,
          rilevazioni,
        };
      })
    );
  }

  getNextPoints(
    macchina: number,
    caratteristica: string,
    ultimo_utc: number
  ): Observable<ChartPoint[]> {
    // TODO: porta 8080
    // const url = `/rilevazioni/${macchina}/${caratteristica}?createdAfter=${ultimo_utc}`;
    // return this.http.get<ChartPoint[]>(url);

    const url = `assets/sample_data.csv`;
    return this.http.get(url, { responseType: 'text' }).pipe(
      map((csv) => {
        const points = csv.split('\n').slice(1);
        const idx = points.findIndex((line) => {
          return new Date(line.split(',')[0]).getTime() / 1000 > ultimo_utc;
        });
        return points.slice(idx, idx + 3).map((line) => {
          const [creato_il_utc, valore] = line.split(',');
          return {
            creato_il_utc: new Date(creato_il_utc).getTime() / 1000,
            valore: Number(valore),
            anomalo: new Date(creato_il_utc).getTime() % 13 == 0,
          };
        });
      })
    );
  }
}
