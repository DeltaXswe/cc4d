import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { FlatTreeControl } from "@angular/cdk/tree";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import { UnarchivedDeviceAbstractService } from "../../model/device/unarchived-device-abstract.service";
import { SelectionNode } from './selection-data-source/selection-node';
import { SelectionDataSource } from './selection-data-source/selection.data-source';
import { CharacteristicNode } from './selection-data-source/characteristic-node';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css']
})
export class SelectionComponent implements OnInit {
  @Output()
  devicesChanged = new EventEmitter<CharacteristicNode[]>();

  treeControl: FlatTreeControl<SelectionNode>;
  dataSource: SelectionDataSource;

  checkedNodes: CharacteristicNode[] = [];

  private pendingSelection: CharacteristicNode[] = [];

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
    return this.pendingSelection.indexOf(node) >= 0;
  }

  checkNode(node: CharacteristicNode): void {
    this.pendingSelection.push(node);
  }

  uncheckNode(node: CharacteristicNode): void {
    this.pendingSelection.splice(this.pendingSelection.indexOf(node), 1);
  }

  notifyChange(): void {
    this.checkedNodes = [];
    setTimeout(() => {
      // l'abc dell'hack in angular - spesso il runtime di angular ha bisogno di "aspettare" un momento
      this.checkedNodes = this.pendingSelection.slice();
    });
  }
}
