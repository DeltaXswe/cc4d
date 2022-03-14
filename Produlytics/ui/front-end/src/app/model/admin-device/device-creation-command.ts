import {CharacteristicCreationCommand} from "./characteristic_creation_command";

export interface DeviceCreationCommand {
  readonly name: string,
  readonly characteristics: CharacteristicCreationCommand[]
}
