import {Observable} from "rxjs";
import {UnarchivedCharacteristic} from "./unarchived-characteristic";
import {Injectable} from "@angular/core";

@Injectable()
export abstract class UnarchivedCharacteristicAbstractService {
  public abstract getCharacteristicsByDevice(deviceId: number): Observable<UnarchivedCharacteristic[]>;
}
