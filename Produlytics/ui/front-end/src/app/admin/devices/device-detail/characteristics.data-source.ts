import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable} from "rxjs";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";

export class CharacteristicsDataSource implements DataSource<Characteristic> {

  private dataStream = new BehaviorSubject<Characteristic[]>([]);
  get currentData(): Characteristic[] {
    return this.dataStream.value;
  }

  connect(collectionViewer: CollectionViewer): Observable<Characteristic[]> {
    return this.dataStream;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataStream.complete();
  }

  setData(data: Characteristic[]): void {
    this.dataStream.next(data);
  }

}
