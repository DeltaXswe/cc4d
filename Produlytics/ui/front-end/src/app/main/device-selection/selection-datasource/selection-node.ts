import {map, Observable, of, tap} from "rxjs";
import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {
  UnarchivedCharacteristicAbstractService
} from "../../../model/characteristic/unarchived-characteristic-abstract.service";
import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";

export interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly criteria: Observable<SelectionNode[]>,
  readonly name: string,
  readonly loading: boolean
}

export class DeviceNode implements SelectionNode {
  public readonly level = 0;
  public readonly expandable = true;
  public readonly name: string;

  private _loading = false;
  get loading() {
    return this._loading;
  }

  get criteria(): Observable<CharacteristicNode[]> {
    this._loading = true;
    return this.service.getCharacteristicsByDevice(this.device.id).pipe(
      map(
        values => values.map(value => new CharacteristicNode(value))
      ),
      tap({
        complete: () => {
          this._loading = false;
        }
      })
    );
  }

  constructor(
    private readonly device: UnarchivedDevice,
    private readonly service: UnarchivedCharacteristicAbstractService
  ) {
    this.name = device.name;
  }
}

export class CharacteristicNode implements SelectionNode {
  public readonly level = 1;
  public readonly expandable = false;
  public readonly name: string;
  public readonly loading = false;
  public readonly criteria = of([]);
  constructor(
    public readonly characteristic: UnarchivedCharacteristic
  ) {
    this.name = characteristic.name;
  }
}
