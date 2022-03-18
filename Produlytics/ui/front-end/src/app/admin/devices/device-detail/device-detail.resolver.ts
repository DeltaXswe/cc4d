import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Device} from "../../../model/admin-device/device";
import {Injectable} from "@angular/core";
import {FindDeviceAbstractService} from "../../../model/admin-device/find-detail/find-device-abstract.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DeviceDetailResolver implements Resolve<Device> {

  constructor(
    private findDeviceService: FindDeviceAbstractService
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Device> {
    return this.findDeviceService.findDeviceById(route.params['id']);
  }

}
