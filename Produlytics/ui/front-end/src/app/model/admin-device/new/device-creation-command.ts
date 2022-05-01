import {CharacteristicCreationCommand} from "./characteristic-creation-command";

export interface DeviceCreationCommand {
  readonly name: string,
  readonly characteristics: CharacteristicCreationCommand[]
}
