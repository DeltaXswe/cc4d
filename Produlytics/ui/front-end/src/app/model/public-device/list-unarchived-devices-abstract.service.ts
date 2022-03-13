import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UnarchivedDeviceInfo} from "./unarchived_device_info";

@Injectable()
export abstract class ListUnarchivedDevicesAbstractService {
  public abstract getUnarchivedDevices(): Observable<UnarchivedDeviceInfo[]>;
}
