import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectionComponent } from './selection.component';
import {DeviceMock, FakeDeviceService} from "../../test/device/fake-device.service";
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";
import {FakeUnarchivedService} from "../../test/unarchived/fake-unarchived.service";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import {testModules} from "../../test/utils";
import {DeviceNode} from "./selection-data-source/device-node";
import {CharacteristicNode} from "./selection-data-source/characteristic-node";

describe('DeviceSelectionComponent', () => {
  let component: SelectionComponent;
  let fixture: ComponentFixture<SelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ SelectionComponent ],
      providers: [
        FakeDeviceService,
        {
          provide: UnarchivedDeviceAbstractService,
          useExisting: FakeDeviceService
        },
        FakeUnarchivedService,
        {
          provide: UnarchivedCharacteristicAbstractService,
          useExisting: FakeUnarchivedService
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('nodo-ha-figli', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocità',
        autoAdjust: false,
        sampleSize: null,
        upperLimit: 90, // miglia orarie
        lowerLimit: 0,
        archived: false
      },
      {
        id: 2,
        name: 'Calore caldaia',
        autoAdjust: true,
        sampleSize: 100,
        upperLimit: null,
        lowerLimit: null,
        archived: true
      },
      {
        id: 3,
        name: 'Carbone utilizzato',
        autoAdjust: true,
        sampleSize: 150,
        upperLimit: null,
        lowerLimit: null,
        archived: false
      }
    ]);
    const adHoc = new DeviceNode(locomotivaDevice);
    expect(component.hasChildren(adHoc.level, adHoc)).toBeTrue();
  });

  it('nodo-non-ha-figli', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocità',
        autoAdjust: false,
        sampleSize: null,
        upperLimit: 90, // miglia orarie
        lowerLimit: 0,
        archived: false
      },
      {
        id: 2,
        name: 'Calore caldaia',
        autoAdjust: true,
        sampleSize: 100,
        upperLimit: null,
        lowerLimit: null,
        archived: true
      },
      {
        id: 3,
        name: 'Carbone utilizzato',
        autoAdjust: true,
        sampleSize: 150,
        upperLimit: null,
        lowerLimit: null,
        archived: false
      }
    ]);
    const machine = new DeviceNode(locomotivaDevice);
    const adHoc = new CharacteristicNode(machine, locomotivaDevice.characteristics[0]);
    expect(component.hasChildren(adHoc.level, adHoc)).toBeFalse();
  });

  it('nodo-selezionato', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocità',
        autoAdjust: false,
        sampleSize: null,
        upperLimit: 90, // miglia orarie
        lowerLimit: 0,
        archived: false
      },
      {
        id: 2,
        name: 'Calore caldaia',
        autoAdjust: true,
        sampleSize: 100,
        upperLimit: null,
        lowerLimit: null,
        archived: true
      },
      {
        id: 3,
        name: 'Carbone utilizzato',
        autoAdjust: true,
        sampleSize: 150,
        upperLimit: null,
        lowerLimit: null,
        archived: false
      }
    ]);
    const machine = new DeviceNode(locomotivaDevice);
    const adHoc = new CharacteristicNode(machine, locomotivaDevice.characteristics[0]);
    component.checkNode(adHoc);
    expect(component.nodeIsChecked(adHoc)).toBeTrue();
  });

  it('nodo-deselezionato', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocità',
        autoAdjust: false,
        sampleSize: null,
        upperLimit: 90, // miglia orarie
        lowerLimit: 0,
        archived: false
      },
      {
        id: 2,
        name: 'Calore caldaia',
        autoAdjust: true,
        sampleSize: 100,
        upperLimit: null,
        lowerLimit: null,
        archived: true
      },
      {
        id: 3,
        name: 'Carbone utilizzato',
        autoAdjust: true,
        sampleSize: 150,
        upperLimit: null,
        lowerLimit: null,
        archived: false
      }
    ]);
    const machine = new DeviceNode(locomotivaDevice);
    const charNodes = locomotivaDevice.characteristics.map(c => new CharacteristicNode(machine, c));
    for (const char of charNodes) {
      component.checkNode(char);
    }
    const adHoc = charNodes[1];
    component.uncheckNode(adHoc);
    expect(component.nodeIsChecked(adHoc)).toBeFalse();
  });

  it('checked-nodes', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocità',
        autoAdjust: false,
        sampleSize: null,
        upperLimit: 90, // miglia orarie
        lowerLimit: 0,
        archived: false
      },
      {
        id: 2,
        name: 'Calore caldaia',
        autoAdjust: true,
        sampleSize: 100,
        upperLimit: null,
        lowerLimit: null,
        archived: true
      },
      {
        id: 3,
        name: 'Carbone utilizzato',
        autoAdjust: true,
        sampleSize: 150,
        upperLimit: null,
        lowerLimit: null,
        archived: false
      }
    ]);
    const machine = new DeviceNode(locomotivaDevice);
    const charNodes = locomotivaDevice.characteristics.map(c => new CharacteristicNode(machine, c));
    for (const char of charNodes) {
      component.checkNode(char);
    }
    component.notifyChange();
    for (const node of component.checkedNodes) {
      expect(charNodes).toContain(node);
    }
  });
});
