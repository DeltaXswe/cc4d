import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ChartPointReturn } from './chart-point-return';
import { ChartAbstractService } from "./chart-abstract.service";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Limits } from './limits';

@Injectable({
  providedIn: 'root'
})
/**
 * Questo service si occupa di effettuare richieste HTTP al back-end per
 * ottenere i dati necessari a visualizzare i grafici
 */
export class ChartService implements ChartAbstractService{

  private static readonly SHIFT_SIZE = 8 * 60 * 60 * 1000; // 8 ore

  constructor(private httpClient: HttpClient) {}

  /**
   * Effettua una richiesta HTTP GET al back-end chiedendo le 100 rilevazioni più recenti
   * @param deviceId L'id della macchina
   * @param characteristicId L'id della caratteristica
   * @returns Un {@link Observable} contente le rilevazioni richieste
   */
  getInitialPoints(deviceId: number, characteristicId: number): Observable<ChartPointReturn> {
    const lastShift = Date.now() - ChartService.SHIFT_SIZE;
    /*
     * Se si caricano le ultime 100 sempre, se c'è un grande lasso temporale dove la macchina è stata spenta (con
     * grande intendo 1 giorno, quindi se la macchina rimane spenta per esempio durante il weekend questa cosa succede)
     * il grafo di d3 diventa veramente oneroso per il client perché diventa STRAGROSSO.
     *
     * Visto che granularità = 1/s e le rilevazioni più vecchie di 14 ormai sono storico, se ignoriamo le rilevazioni
     * prima delle ultime 8 ore dovrebbe andare bene comunque all'utente finale.
     * */
    const paramsObj = {
      newerThan: lastShift,
      limit: 100
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj})
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`,
      {params});
  }

  /**
   * Effettua una richiesta HTTP GET al back-end chiedendo le ultime 15 rilevazioni che già si
   * hanno più una nuova rilevazione.
   * @param deviceId L'id della macchina
   * @param characteristicId L'id della caratteristica
   * @param newerThan La coordinata temporale rispetto alla quale le rilevazioni ottenute saranno
   * più recenti
   * @returns Un {@link Observable} contente la rilevazione richiesta
   */
  getNextPoints(deviceId: number, characteristicId: number, newerThan: number): Observable<ChartPointReturn> {
    const paramsObj = {
      newerThan: newerThan,
      limit: 16
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj})
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`
    , {params});
  }

  /**
   * Effettua una richiesta HTTP GET al back-end chiedendo i limiti della caratteristica passata come parametro
   * @param deviceId L'id della macchina
   * @param characteristicId L'id della caratteristica
   * @return Un {@link Observable} contente i limiti superiore, inferiore e la media
   */
  getLimits(deviceId: number, characteristicId: number): Observable<Limits> {
    return this.httpClient.get<Limits>(`/devices/${deviceId}/characteristics/${characteristicId}/limits`)
  }

  /**
   * Effettua una richiesta HTTP GET al back-end chiedendo un determinato numero di rilevazioni
   * comprese fra {@link start} e {@link end}
   * @param start L'istante di tempo rispetto al quale tutte le rilevazioni ottenute saranno più vecchie
   * @param end L'istante di tempo rispetto al quale tutte le rilevazioni ottenute saranno più recenti
   * @param deviceId L'id della macchina
   * @param characteristicId L'id della caratteristica
   * @returns Un {@link Observable} contenente le rilevazioni richieste
   */
  getOldPoints(start: number, end: number, deviceId: number, characteristicId: number): Observable<ChartPointReturn> {
    const limit = (end - start)/1000;
    const paramsObj = {
      olderThan: end,
      limit: limit
    }
    const params: HttpParams = new HttpParams({fromObject: paramsObj});
    return this.httpClient.get<ChartPointReturn>(`/devices/${deviceId}/characteristics/${characteristicId}/detections`, {params});
  }
}
