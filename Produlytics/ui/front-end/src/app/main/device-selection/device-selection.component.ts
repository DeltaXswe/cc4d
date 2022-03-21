import { Component, OnInit } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";
import {DataSource} from "@angular/cdk/collections";
import {DeviceNode, SelectionNode} from "./selection-datasource/selection-node";
import {SelectionDataSource} from "./selection-datasource/selection.data-source";
import {map, tap} from "rxjs";

@Component({
  selector: 'app-device-selection',
  templateUrl: './device-selection.component.html',
  styleUrls: ['./device-selection.component.css']
})
export class DeviceSelectionComponent implements OnInit {
  public readonly treeControl: FlatTreeControl<SelectionNode, SelectionNode>;
  public readonly dataSource: DataSource<SelectionNode>;

  private _loading = true;
  checkedNodes: SelectionNode[] = [];

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
    return this.checkedNodes.indexOf(node) >= 0;
  }
}
