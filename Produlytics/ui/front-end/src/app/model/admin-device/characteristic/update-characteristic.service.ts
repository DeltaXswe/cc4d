import { Injectable } from '@angular/core';
import {UpdateCharacteristicAbstractService} from "./update-characteristic-abstract.service";
import {CharacteristicUpdateCommand} from "./characteristic-update-command";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UpdateCharacteristicService implements UpdateCharacteristicAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  updateCharacteristic(command: CharacteristicUpdateCommand): Observable<{}> {
    return this.httpClient.put(`admin/devices/${command.deviceId}/characteristics/${command.id}`, command);
  }


}
