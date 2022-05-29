import {Injectable} from "@angular/core";
import {Device} from "./device";
import {Observable} from "rxjs";

@Injectable()
export abstract class DeviceAbstractService {
  public abstract getDevices(): Observable<Device[]>;
  public abstract activateDevice(device: Device): Observable<{}>;
  public abstract deactivateDevice(device: Device): Observable<{}>;
  public abstract archiveDevice(device: Device): Observable<{}>;
  public abstract restoreDevice(device: Device): Observable<{}>;
}
