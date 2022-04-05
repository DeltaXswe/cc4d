import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";


export interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly deviceId: number,
  readonly name: string
}

export class DeviceNode implements SelectionNode {
  public readonly level = 0;
  public readonly expandable = true;
  public readonly deviceId: number;
  public readonly name: string;

  constructor(device: UnarchivedDevice) {
    this.deviceId = device.id;
    this.name = device.name;
  }
}

export class CharacteristicNode implements SelectionNode {
  public readonly level = 1;
  public readonly expandable = false;
  public readonly deviceId: number;
  public readonly name: string;
  public readonly deviceName: string;

  public readonly characteristicId: number;

  constructor(
    public readonly parent: SelectionNode,
    characteristic: UnarchivedCharacteristic
  ) {
    this.deviceId = parent.deviceId;
    this.name = characteristic.name;
    this.characteristicId = characteristic.id;
    this.deviceName = parent.name;
  }
}

