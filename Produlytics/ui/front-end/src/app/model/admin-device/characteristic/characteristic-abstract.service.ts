import {Observable} from "rxjs";
import {Characteristic} from "./characteristic";
import {CharacteristicCreationCommand} from "../new/characteristic-creation-command";

export abstract class CharacteristicAbstractService {
  public abstract getCharacteristicsByDevice(deviceId: number): Observable<Characteristic[]>;

  public abstract addCharacteristic(deviceId: number, command: CharacteristicCreationCommand): Observable<{ id: number }>;

  public abstract recoverCharacteristic(deviceId: number, id: number): Observable<{}>;

  public abstract archiveCharacteristic(deviceId: number, id: number): Observable<{}>;
}
