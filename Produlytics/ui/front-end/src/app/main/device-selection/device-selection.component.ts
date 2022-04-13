import { Component, OnInit, Output } from '@angular/core';
import { FlatTreeControl } from "@angular/cdk/tree";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import { UnarchivedDeviceAbstractService } from "../../model/device/unarchived-device-abstract.service";
import { SelectionNode } from "./selection-data-source/selection-node";
import { SelectionDataSource } from "./selection-data-source/selection.data-source";
import { EventEmitter } from '@angular/core';
import { MatCheckboxChange } from "@angular/material/checkbox/checkbox";
import { CharacteristicNode } from './selection-data-source/characteristic-node';

// TODO STA CLASSE È TUTTA DA RINOMINARE AAAAAAA (device->characteristic)

@Component({
  selector: 'app-device-selection',
  templateUrl: './device-selection.component.html',
  styleUrls: ['./device-selection.component.css']
})
export class DeviceSelectionComponent implements OnInit {

  treeControl: FlatTreeControl<SelectionNode>;

  dataSource: SelectionDataSource;

  @Output()
  devicesChanged = new EventEmitter<CharacteristicNode[]>();

  private checkedNodes: CharacteristicNode[] = [];

  constructor(
    unarchivedDeviceService: UnarchivedDeviceAbstractService,
    unarchivedCharacteristicService: UnarchivedCharacteristicAbstractService
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

  ngOnInit(): void { }

  hasChildren(_index: number, node: SelectionNode): boolean {
    return node.expandable;
  }

  nodeIsChecked(node: CharacteristicNode): boolean {
    return this.checkedNodes.indexOf(node) >= 0;
  }

  toggleNodeCheck(event: MatCheckboxChange, node: CharacteristicNode): void {
    if (event.checked) {
      this.checkedNodes.push(node)
    } else {
      this.checkedNodes.splice(this.checkedNodes.indexOf(node), 1)
    }
  }

  notifyChange(): void {
    this.devicesChanged.emit(this.checkedNodes);
  }
}
