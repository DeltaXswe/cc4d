import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Device} from "../../../model/admin-device/device";
import {Injectable} from "@angular/core";
import {FindDeviceAbstractService} from "../../../model/admin-device/find-detail/find-device-abstract.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
/**
 * Modella le interrogazioni asincrone da fare prima del caricamento della pagina di dettaglio della macchina.
 */
export class DeviceDetailResolver implements Resolve<Device> {

  constructor(
    private findDeviceService: FindDeviceAbstractService
  ) {
  }

  /**
   * Ereditato da {@link Resolve}. Ottiene la macchina indicata come parametro dalla {@link ActivatedRouteSnapshot}
   * attivata. Si interfaccia con un servizio che implementa {@link FindDeviceAbstractService}.
   *
   * @param route il link url attivato per arrivare a questa pagina.
   */
  resolve(route: ActivatedRouteSnapshot): Observable<Device> {
    return this.findDeviceService.findDeviceById(Number(route.params['id']));
  }

}
