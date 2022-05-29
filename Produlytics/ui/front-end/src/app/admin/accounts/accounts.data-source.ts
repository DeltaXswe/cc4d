import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Account} from "../../model/admin-account/account";
import {BehaviorSubject, Observable} from "rxjs";
import {Sort} from "@angular/material/sort";
import {compare} from '../../../lib/utils';

/**
 * Modella la visualizzazione dei dati degli account per una mat-table.
 */
export class AccountsDataSource extends DataSource<Account> {

  private dataStream = new BehaviorSubject<Account[]>([]);

  /**
   * Ereditato da {@link DataSource}. Restituisce la sorgente dati a cui agganciarsi.
   */
  connect(): Observable<Account[]> {
    return this.dataStream;
  }

  /**
   * Ereditato da {@link DataSource}. Siccome la sorgente dati è semplice, in questo caso non occorre fare niente.
   */
  disconnect(): void {

  }

  /**
   * Notifica alla sorgente dati di visualizzare gli utenti passati come parametro.
   *
   * @param data gli account da visualizzare.
   */
  setData(data: Account[]): void {
    this.dataStream.next(data);
  }

  /**
   * Ordina i dati da visualizzare come specificato nel parametro {@link Sort}.
   *
   * @param sort
   */
  sortData(sort: Sort): void {
    const sorted = this.dataStream.value.sort((a: Account, b: Account) => {
      const asc = sort.direction === 'asc';
      switch (sort.active) {
        case 'username':
          return compare(a.username, b.username, asc);
        case 'administrator':
          return compare(a.administrator, b.administrator, asc);
        default:
          return 0;
      }
    });
    this.setData(sorted);
  }
}
