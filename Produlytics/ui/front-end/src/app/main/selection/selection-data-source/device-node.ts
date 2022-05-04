import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {SelectionNode} from "./selection-node";


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
