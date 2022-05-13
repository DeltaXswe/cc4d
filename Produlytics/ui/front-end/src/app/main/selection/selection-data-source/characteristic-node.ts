import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";
import {SelectionNode} from "./selection-node";

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
