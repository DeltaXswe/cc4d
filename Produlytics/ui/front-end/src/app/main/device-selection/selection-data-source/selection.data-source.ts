import {CollectionViewer, DataSource, SelectionChange} from "@angular/cdk/collections";
import {BehaviorSubject, map, merge, Observable, of, tap} from "rxjs";
import {FlatTreeControl} from "@angular/cdk/tree";
import {DeviceNode, SelectionNode} from "./selection-node";

export class SelectionDataSource implements DataSource<SelectionNode> {
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

  disconnect(collectionViewer: CollectionViewer): void { }

  private handleChange(change: SelectionChange<SelectionNode>) {
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
              console.log(this.cache);
            }
          })
        );
    }
    observable.subscribe(nodes => {
      data.splice(index + 1, 0, ...nodes);
      this.dataStream.next(data);
    });
  }

  private removeCharacteristics(node: SelectionNode) {
    const data = this.dataStream.value;
    const index = data.indexOf(node);
    if (index < 0) { return; }
    let count = 0;
    // partendo dal primo successivo, conto quanti sono i figli e i nipoti del nodo
    for (let i = index + 1; i < data.length && node.level < data[i].level; count++, i++) {}
    data.splice(index + 1, count);
    this.dataStream.next(data);
  }
}
