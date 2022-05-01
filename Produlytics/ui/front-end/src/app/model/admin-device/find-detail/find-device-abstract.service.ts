import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Device} from "../device";

@Injectable()
export abstract class FindDeviceAbstractService {
  public abstract findDeviceById(id: number): Observable<Device>;
}
