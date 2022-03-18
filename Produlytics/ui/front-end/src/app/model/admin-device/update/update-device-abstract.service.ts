import {Observable} from "rxjs";

export abstract class UpdateDeviceAbstractService {
  public abstract updateDeviceName(id: number, newName: string): Observable<{}>;
}
