import {SelectionDataSource} from "./selection.data-source";
import {NEVER, of, Subject} from "rxjs";
import {FlatTreeControl} from "@angular/cdk/tree";
import {SelectionNode} from "./selection-node";
import {SelectionChange} from "@angular/cdk/collections";
import {UnarchivedDeviceAbstractService} from "../../../model/device/unarchived-device-abstract.service";

describe('SelectionDataSource', () => {

  it('connetti-selezione', () => {
    const changed = new Subject<SelectionChange<SelectionNode>>();
    const treeControl: FlatTreeControl<SelectionNode> = {
      expansionModel: {
        changed
      }
    } as FlatTreeControl<SelectionNode>; // trust me, compiler
    const deviceService: UnarchivedDeviceAbstractService = {
      getDevices(): Observable<UnarchivedDevice[]> {
      }
    };
    const dataSource = new SelectionDataSource(
      treeControl,
      deviceService,
      characteristicService
    );
    dataSource.connect({
      viewChange: NEVER
    });
  });

})
