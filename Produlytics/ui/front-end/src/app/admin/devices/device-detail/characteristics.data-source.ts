import {DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable} from "rxjs";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";

/**
 * Sorgente dati da visualizzare nella tabella delle caratteristiche.
 */
export class CharacteristicsDataSource implements DataSource<Characteristic> {

  private dataStream = new BehaviorSubject<Characteristic[]>([]);

  /**
   * Restituisce l'insieme di caratteristiche da mostrare.
   *
   * @return {@link Characteristic[]}.
   */
  get data(): Characteristic[] {
    return this.dataStream.value;
  }

  /**
   * Notifica il nuovo insieme di caratteristiche da mostrare nella tabella.
   *
   * @param data l'insieme di dati da mostrare.
   */
  set data(data: Characteristic[]) {
    this.dataStream.next(data);
  }


  /**
   * Ereditato da {@link DataSource}. La sorgente dati asincrona a cui connettersi.
   *
   * @return {@link Observable}&lt;{@link Characteristic}>
   */
  connect(): Observable<Characteristic[]> {
    return this.dataStream;
  }

  /**
   * Ereditato da {@link DataSource}. Questo metodo disconnette dalla sorgente dati asincrona. Essendo una semplice sorgente dati, non occorre fare
   * nulla.
   */
  disconnect(): void {
  }
}
