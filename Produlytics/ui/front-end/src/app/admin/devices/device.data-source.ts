import {DataSource} from "@angular/cdk/collections";
import {Device} from "../../model/admin-device/device";
import {BehaviorSubject, Observable} from "rxjs";
import {Sort} from "@angular/material/sort";
import {compare} from "../../../lib/utils";

/**
 * Questa classe rappresenta una semplice sorgente dati per la tabella delle risorse. I dati vengono gestiti
 * da chi la utilizza.
 */
export class DeviceDataSource implements DataSource<Device> {

  private dataStream = new BehaviorSubject<Device[]>([]);

  /**
   * Ereditato da {@link DataSource}. Fornisce la sorgente dati che notifica i cambiamenti.
   *
   * @returns {@link Observable}&lt;{@link Device}[]>
   */
  connect(): Observable<Device[]> {
    return this.dataStream;
  }

  /**
   * Ereditato da {@link DataSource}. Termina il flusso di dati. Essendo una sorgente semplice, non è necessario fare nulla.
   */
  disconnect(): void { }

  /**
   * Notifica l'inserimento di nuovi dati nella tabella.
   *
   * @param data i dati da inserire.
   */
  setData(data: Device[]): void {
    this.dataStream.next(data);
  }

  /**
   * Ordina i dati presenti nella sorgente dati.
   *
   * @param sort i parametri di ordinamento.
   */
  sortData(sort: Sort): void {
    const sorted = this.dataStream.value.sort((a: Device, b: Device) => {
      const asc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name':
          return compare(a.name, b.name, asc);
        default:
          return 0;
      }
    });
    this.setData(sorted);
  }
}
