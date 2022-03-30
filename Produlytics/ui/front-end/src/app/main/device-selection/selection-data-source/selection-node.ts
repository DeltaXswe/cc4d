import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";


export interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly deviceId: number,
  readonly description: string
}

export class DeviceNode implements SelectionNode {
  public readonly level = 0;
  public readonly expandable = true;
  public readonly deviceId: number;
  public readonly description: string;

  constructor(device: UnarchivedDevice) {
    this.deviceId = device.id;
    this.description = device.name;
  }
}

export class CharacteristicNode implements SelectionNode {
  public readonly level = 1;
  public readonly expandable = false;
  public readonly deviceId: number;
  public readonly description: string;

  public readonly characteristicId: number;

  constructor(
    public readonly parent: SelectionNode,
    characteristic: UnarchivedCharacteristic
  ) {
    this.deviceId = parent.deviceId;
    this.description = characteristic.name;
    this.characteristicId = characteristic.id;
  }
}

