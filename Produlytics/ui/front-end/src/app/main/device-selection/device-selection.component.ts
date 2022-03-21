import { Component, OnInit } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {CollectionViewer, DataSource, SelectionChange} from "@angular/cdk/collections";
import {BehaviorSubject, map, Observable, merge, of, tap} from "rxjs";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import {UnarchivedCharacteristic} from "../../model/characteristic/unarchived-characteristic";
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";
import {UnarchivedDevice} from "../../model/device/unarchived-device";

interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly criteria: Observable<SelectionNode[]>,
  readonly name: string,
  readonly loading: boolean
}

class DeviceNode implements SelectionNode {
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

class CharacteristicNode implements SelectionNode {
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

class SelectionDataSource implements DataSource<SelectionNode> {
  private readonly dataStream = new BehaviorSubject<SelectionNode[]>([]);
  private cache = new Map<SelectionNode, SelectionNode[]>();

  constructor(
    private treeControl: FlatTreeControl<SelectionNode>,
    private dataInitializer: Observable<DeviceNode[]>
  ) {
    this.dataInitializer.subscribe(values => {
      this.dataStream.next(values);
    });
  }

  connect(collectionViewer: CollectionViewer): Observable<SelectionNode[]> {
    this.treeControl.expansionModel.changed
      .subscribe(change => {
        if (change.added || change.removed) {
          this.handleChange(change);
        }
      });
    return merge(
      collectionViewer.viewChange,
      this.dataStream
    ).pipe(
      map(
        () => this.dataStream.value
      )
    );
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }


  private handleChange(change: SelectionChange<SelectionNode>) {
    if (change.added) {
      change.added.forEach(node => this.addCharacteristics(node))
    }
    if (change.removed) {
      change.removed
        .slice() // shallow copy
        .reverse()
        .forEach(node => this.removeCharacteristic(node))
    }
  }

  private addCharacteristics(node: SelectionNode) {
    const data = this.dataStream.value;
    const index = data.indexOf(node);
    if (index < 0) { return; }
    let observable: Observable<SelectionNode[]>;
    if (this.cache.has(node)) {
      observable = of(this.cache.get(node)!);
    } else {
      observable = node.criteria
        .pipe(
          tap({
            next: values => {
              this.cache.set(node, values);
            }
          })
        );
    }
    observable.subscribe(nodes => {
      data.splice(index + 1, 0, ...nodes);
      this.dataStream.next(data);
    });
  }

  private removeCharacteristic(node: SelectionNode) {
    const data = this.dataStream.value;
    const index = data.indexOf(node);
    if (index < 0) { return; }
    let count = 0;
    for (let i = count + 1; i < data.length && node.level < data[i].level; count++, i++) {}
    data.splice(index + 1, count);
    this.dataStream.next(data);
  }
}

@Component({
  selector: 'app-device-selection',
  templateUrl: './device-selection.component.html',
  styleUrls: ['./device-selection.component.css']
})
export class DeviceSelectionComponent implements OnInit {
  public readonly treeControl: FlatTreeControl<SelectionNode, SelectionNode>;
  public readonly dataSource: DataSource<SelectionNode>;

  private _loading = true;
  chechedNodes: SelectionNode[] = [];

  public get loading() {
    return this._loading;
  }

  constructor(
    private unarchivedDeviceService: UnarchivedDeviceAbstractService,
    private unarchivedCharacteristicService: UnarchivedCharacteristicAbstractService
  ) {
    this.treeControl = new FlatTreeControl<SelectionNode>(
      node => node.level,
      node => node.expandable
    );
    this.dataSource = new SelectionDataSource(
      this.treeControl,
      unarchivedDeviceService.getDevices()
        .pipe(
          map(values => values.map(value => new DeviceNode(value, unarchivedCharacteristicService))),
          tap({
            complete: () => {
              this._loading = false;
            }
          })
        )
    );
  }

  ngOnInit(): void {
  }

  public hasChildren(_index: number, node: SelectionNode): boolean {
    return node.expandable;
  }

  nodeIsChecked(node: SelectionNode) {
    return this.chechedNodes.indexOf(node) >= 0;
  }
}
