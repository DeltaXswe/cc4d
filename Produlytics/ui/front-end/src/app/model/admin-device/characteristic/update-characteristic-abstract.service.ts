import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {CharacteristicUpdateCommand} from "./characteristic-update-command";

@Injectable()
export abstract class UpdateCharacteristicAbstractService {

  public abstract updateCharacteristic(command: CharacteristicUpdateCommand): Observable<{}>;
}
