import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";

// l'interfaccia con i dati che servono in visualizzazione e selezione
export interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly id: number,
  readonly name: string
}

export class DeviceNode implements SelectionNode {
  public readonly level = 0;
  public readonly expandable = true;
  public readonly name: string;
  public readonly id: number;

  constructor(
    device: UnarchivedDevice
  ) {
    this.name = device.name;
    this.id = device.id;
  }
}

export class CharacteristicNode implements SelectionNode {
  public readonly level = 1;
  public readonly expandable = false;
  public readonly name: string;
  public readonly id: number;

  constructor(
    public readonly device: SelectionNode,
    characteristic: UnarchivedCharacteristic
  ) {
    this.name = characteristic.name;
    this.id = characteristic.id;
  }
}

