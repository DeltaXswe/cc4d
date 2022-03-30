import { Component, OnInit, Output } from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";
import {DataSource} from "@angular/cdk/collections";
import {CharacteristicNode, DeviceNode, SelectionNode} from "./selection-data-source/selection-node";
import {SelectionDataSource} from "./selection-data-source/selection.data-source";
import {map, tap} from "rxjs";
import { EventEmitter } from '@angular/core';
import { Characteristic } from 'src/app/model/admin-device/characteristic/characteristic';

@Component({
  selector: 'app-device-selection',
  templateUrl: './device-selection.component.html',
  styleUrls: ['./device-selection.component.css']
})
export class DeviceSelectionComponent implements OnInit {
  public readonly treeControl: FlatTreeControl<SelectionNode, SelectionNode>;
  public readonly dataSource: SelectionDataSource;

  @Output()
  public devicesChanged = new EventEmitter<Characteristic[]>();

  checkedNodes: CharacteristicNode[] = [];

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
      unarchivedDeviceService,
      unarchivedCharacteristicService
    );
  }

  ngOnInit(): void {
  }

  public hasChildren(_index: number, node: SelectionNode): boolean {
    return node.expandable;
  }

  nodeIsChecked(node: CharacteristicNode) {
    return this.checkedNodes.indexOf(node) >= 0;
  }

  onSubmit() {
  }
}
