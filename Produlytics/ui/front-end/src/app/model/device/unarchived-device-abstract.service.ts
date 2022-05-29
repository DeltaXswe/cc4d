import {Observable} from "rxjs";
import {UnarchivedDevice} from "./unarchived-device";

export abstract class UnarchivedDeviceAbstractService {
  public abstract getDevices(): Observable<UnarchivedDevice[]>;
}
