import {CollectionViewer, DataSource, SelectionChange} from "@angular/cdk/collections";
import {BehaviorSubject, merge, Observable, of, tap} from "rxjs";
import { map } from "rxjs/operators";
import {FlatTreeControl} from "@angular/cdk/tree";
import {UnarchivedDeviceAbstractService} from "../../../model/device/unarchived-device-abstract.service";
import {
  UnarchivedCharacteristicAbstractService
} from "../../../model/characteristic/unarchived-characteristic-abstract.service";
import {SelectionNode} from "./selection-node";
import {CharacteristicNode} from "./characteristic-node";
import {DeviceNode} from "./device-node";

export class SelectionDataSource implements DataSource<SelectionNode> {

  private dataStream = new BehaviorSubject<SelectionNode[]>([]);

  private cache = new Map<SelectionNode, CharacteristicNode[]>();

  private loadingNodes = new Set<SelectionNode>();

  private _loading = true;

  public get loading() {
    return this._loading;
  }

  constructor(
    private treeControl: FlatTreeControl<SelectionNode>,
    private unarchivedDeviceService: UnarchivedDeviceAbstractService,
    private unarchivedCharacteristicService: UnarchivedCharacteristicAbstractService
  ) {
    this.unarchivedDeviceService.getDevices()
      .subscribe(values => {
        this.dataStream.next(values.map(
          device => new DeviceNode(device)
        ));
        this._loading = false;
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

  disconnect(collectionViewer: CollectionViewer): void { }

  isNodeLoading(node: SelectionNode): boolean {
    return this.loadingNodes.has(node);
  }

  private handleChange(change: SelectionChange<SelectionNode>): void {
    if (change.added) {
      change.added.forEach(node => this.addCharacteristics(node))
    }
    if (change.removed) {
      change.removed
        .slice() // shallow copy
        .reverse()
        .forEach(node => this.removeCharacteristics(node))
    }
  }

  private addCharacteristics(node: SelectionNode): void {
    const data = this.dataStream.value;
    const index = data.indexOf(node);
    if (index < 0) { return; }
    this.getChildrenForNode(node)
      .subscribe(nodes => {
        data.splice(index + 1, 0, ...nodes);
        this.dataStream.next(data);
      });
  }

  private removeCharacteristics(node: SelectionNode): void {
    const data = this.dataStream.value;
    const index = data.indexOf(node);
    if (index < 0) { return; }
    let count = 0;
    // partendo dal primo successivo, conto quanti sono i figli e i nipoti del nodo (da togliere)
    for (let i = index + 1; i < data.length && node.level < data[i].level; count++, i++) {}
    data.splice(index + 1, count);
    this.dataStream.next(data);
  }

  private getChildrenForNode(node: SelectionNode): Observable<SelectionNode[]> {
    let observable: Observable<CharacteristicNode[]>;
    this.loadingNodes.add(node);
    if (this.cache.has(node)) {
      observable = of(this.cache.get(node)!);
    } else {
      observable = this.unarchivedCharacteristicService.getCharacteristicsByDevice(node.id)
        .pipe(
          map(values => values.map(char => new CharacteristicNode(node, char))),
          tap(values => {
            this.cache.set(node, values);
          })
        );
    }
    return observable.pipe(
      tap(() => {
        // rimuove il mat spinner
        this.loadingNodes.delete(node);
      })
    );
  }
}
