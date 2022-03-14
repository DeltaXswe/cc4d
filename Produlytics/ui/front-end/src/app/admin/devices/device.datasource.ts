import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Device} from "../../model/admin-device/device";
import {BehaviorSubject, Observable} from "rxjs";
import {Sort} from "@angular/material/sort";
import {compare} from "../../../lib/utils";

export class DeviceDatasource implements DataSource<Device> {

  private dataStream = new BehaviorSubject<Device[]>([]);

  connect(collectionViewer: CollectionViewer): Observable<Device[]> {
    return this.dataStream;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataStream.complete();
  }

  setData(data: Device[]): void {
    this.dataStream.next(data);
  }

  sortData(sort: Sort) {
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
