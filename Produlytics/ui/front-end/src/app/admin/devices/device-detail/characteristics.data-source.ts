import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable} from "rxjs";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";

export class CharacteristicsDataSource implements DataSource<Characteristic> {

  private dataStream = new BehaviorSubject<Characteristic[]>([]);
  get data(): Characteristic[] {
    return this.dataStream.value;
  }
  set data(data: Characteristic[]) {
    this.dataStream.next(data);
  }

  connect(collectionViewer: CollectionViewer): Observable<Characteristic[]> {
    return this.dataStream;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataStream.complete();
  }
}
