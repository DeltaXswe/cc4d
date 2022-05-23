import {SelectionDataSource} from "./selection.data-source";
import {NEVER, Subject} from "rxjs";
import {FlatTreeControl} from "@angular/cdk/tree";
import {SelectionNode} from "./selection-node";
import {SelectionChange} from "@angular/cdk/collections";
import {FakeDeviceService} from "../../../test/device/fake-device.service";
import {UnarchivedDeviceAbstractService} from "../../../model/device/unarchived-device-abstract.service";
import {
  UnarchivedCharacteristicAbstractService
} from "../../../model/characteristic/unarchived-characteristic-abstract.service";

describe('SelectionDataSource', () => {
  let dataSource: SelectionDataSource;
  let changed: Subject<SelectionChange<SelectionNode>>;
  let treeControl: FlatTreeControl<SelectionNode>;
  let deviceService: UnarchivedDeviceAbstractService;
  let characteristicService: UnarchivedCharacteristicAbstractService;

  beforeEach(() => {
    changed = new Subject<SelectionChange<SelectionNode>>();
    treeControl = {
      expansionModel: {
        changed
      }
    } as FlatTreeControl<SelectionNode>; // trust me, compiler
    deviceService = new FakeDeviceService();
    characteristicService = new FakeDeviceService();
    dataSource = new SelectionDataSource(
      treeControl,
      deviceService,
      characteristicService
    );
  });


  it('connetti-selezione', () => {

    const observable = dataSource.connect({
      viewChange: NEVER
    });

    changed.next()

    observable.subscribe(value => {

    });

  });

})
