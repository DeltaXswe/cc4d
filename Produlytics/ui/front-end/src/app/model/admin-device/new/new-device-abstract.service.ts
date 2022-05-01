import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {DeviceCreationCommand} from "./device-creation-command";

@Injectable()
export abstract class NewDeviceAbstractService {

  public abstract insertDevice(device: DeviceCreationCommand): Observable<{id: number}>;
}
