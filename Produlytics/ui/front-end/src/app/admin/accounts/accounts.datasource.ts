import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Account} from "../../model/admin-account/account";
import {BehaviorSubject, Observable} from "rxjs";
import {Sort} from "@angular/material/sort";
import {compare} from '../../../lib/utils';

export class AccountsDatasource extends DataSource<Account> {
  private dataStream = new BehaviorSubject<Account[]>([]);

  connect(collectionViewer: CollectionViewer): Observable<Account[]> {
    return this.dataStream;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataStream.complete();
  }

  setData(data: Account[]): void {
    this.dataStream.next(data);
  }

  sortData(sort: Sort) {
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
