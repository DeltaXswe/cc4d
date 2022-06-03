import {SelectionDataSource} from "./selection.data-source";
import {delay, NEVER, Observable, of, Subject} from "rxjs";
import {FlatTreeControl} from "@angular/cdk/tree";
import {SelectionNode} from "./selection-node";
import {SelectionChange} from "@angular/cdk/collections";
import {FakeDeviceService, locomotivaDevice} from "../../../test/device/fake-device.service";
import {UnarchivedDeviceAbstractService} from "../../../model/device/unarchived-device-abstract.service";
import {
  UnarchivedCharacteristicAbstractService
} from "../../../model/characteristic/unarchived-characteristic-abstract.service";
import {CharacteristicNode} from "./characteristic-node";
import {DeviceNode} from "./device-node";
import {UnarchivedDevice} from "../../../model/device/unarchived-device";
import {UnarchivedCharacteristic} from "../../../model/characteristic/unarchived-characteristic";

describe('SelectionDataSource', () => {
  let dataSource: SelectionDataSource;
  let changed: Subject<SelectionChange<SelectionNode>>;
  let treeControl: FlatTreeControl<SelectionNode>;
  let expansionModel: any;
  let deviceService: UnarchivedDeviceAbstractService;
  let characteristicService: UnarchivedCharacteristicAbstractService;
  let tornioDevice: UnarchivedDevice;
  let tornioChars: UnarchivedCharacteristic[];
  let pompaDevice: UnarchivedDevice;
  let pompaChars: UnarchivedCharacteristic[];


  beforeEach(() => {
    changed = new Subject<SelectionChange<SelectionNode>>();
    expansionModel = {
      changed
    };
    treeControl = { expansionModel } as FlatTreeControl<SelectionNode>; // trust me, compiler
    tornioDevice = {
      name: 'Tornio',
      id: 1
    };
    tornioChars = [
      { id: 1, name: 'Revolver' },
      { id: 2, name: 'Boccole' },
    ];
    pompaDevice = {
      name: 'Pompa a immersione',
      id: 2
    };
    pompaChars = [
      { id: 1, name: 'Ventola' },
      { id: 2, name: 'Velocità aspirazione' }
    ];

    deviceService = {
      getDevices(): Observable<UnarchivedDevice[]> {
        return of([tornioDevice, pompaDevice]);
      }
    };
    characteristicService = {
      getCharacteristicsByDevice(deviceId: number): Observable<UnarchivedCharacteristic[]> {
        if (deviceId === 1) {
          return of(tornioChars);
        } else {
          return of(pompaChars);
        }
      }
    };
    dataSource = new SelectionDataSource(
      treeControl,
      deviceService,
      characteristicService
    );
  });


  it('connetti-selezione', (doneFn) => {

    const subject = new Subject<any>();

    const observable = dataSource.connect({
      viewChange: subject
    });

    subject.next(true);

    observable.subscribe(value => {
      expect(value).toEqual([new DeviceNode(tornioDevice), new DeviceNode(pompaDevice)]);
      doneFn();
    });
  });

  it('apri-macchina', (doneFn) => {

    const observable = dataSource.connect({
      viewChange: NEVER
    });

    const tornio = (dataSource as any).dataStream.value[0];

    changed.next({
      source: expansionModel,
      removed: [],
      added: [tornio]
    });

    const ids = [tornio, pompaDevice, ...tornioChars].map(node => node.id);

    observable.subscribe(values => {
      for (const value of values) {
        expect(ids).toContain(value.id)
      }
      doneFn();
    });

  });

  it('chiudi-macchina', doneFn => {
    const observable = dataSource.connect({
      viewChange: NEVER
    });

    const tornio = (dataSource as any).dataStream.value[0];
    const pompa = (dataSource as any).dataStream.value[1];

    changed.next({
      source: expansionModel,
      removed: [],
      added: [tornio, pompa]
    });

    changed.next({
      source: expansionModel,
      removed: [tornio],
      added: []
    });


    const ids = [tornio, pompaDevice, ...pompaChars].map(node => node.id);

    observable.subscribe(values => {
      for (const value of values) {
        expect(ids).toContain(value.id)
      }
      doneFn();
    });
  });

});
