import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { DeviceDetailComponent } from './device-detail.component';
import {MockDialogAlwaysConfirm, MockSnack, testModules} from "../../../test/utils";
import {
  FakeDeviceService,
  filaioDevice,
  locomotivaDevice
} from "../../../test/device/fake-device.service";
import {
  CharacteristicAbstractService
} from "../../../model/admin-device/characteristic/characteristic-abstract.service";
import {UpdateDeviceAbstractService} from "../../../model/admin-device/update/update-device-abstract.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BehaviorSubject, Observable} from "rxjs";
import {CharacteristicUpdateCommand} from "../../../model/admin-device/characteristic/characteristic-update-command";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MockDialogNewCharacteristic, testInsertCharacteristic} from "../new-device/new-device.component.spec";


class MockDialogUpdateCharacteristic {
  open(_?: any, options?: any) {
    return new MockDialogRefUpdateCharacteristic();
  }
}

const testUpdateCharacteristic = {
  name: 'Errori filato',
  sampleSize: 30,
  upperLimit: null,
  lowerLimit: null,
  autoAdjust: true,
  deviceId: filaioDevice.id
};

class MockDialogRefUpdateCharacteristic {
  private readonly _afterClosed = new BehaviorSubject<boolean>(false);

  constructor() {
    const toUpdate = filaioDevice.characteristics[0];
    Object.assign(toUpdate, testUpdateCharacteristic);
    this.close(true);
  }

  public close(value: any) {
    this._afterClosed.next(true);
  }

  public afterClosed(): Observable<any> {
    return this._afterClosed;
  }
}

describe('DeviceDetailComponent New', () => {
  let component: DeviceDetailComponent;
  let fixture: ComponentFixture<DeviceDetailComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ DeviceDetailComponent ],
      providers: [
        FakeDeviceService,
        {
          provide: CharacteristicAbstractService,
          useExisting: FakeDeviceService
        },
        {
          provide: UpdateDeviceAbstractService,
          useExisting: FakeDeviceService
        },
        MockSnack,
        {
          provide: MatSnackBar,
          useExisting: MockSnack
        },
        MockDialogNewCharacteristic,
        {
          provide: MatDialog,
          useExisting: MockDialogNewCharacteristic
        },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: {
                device: filaioDevice
              }
            }
          }
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('aggiorna-nome-macchina', fakeAsync(() => {
    component.deviceNameForm.setValue({
      name: 'Filaio a mano'
    });
    component.updateDeviceName();
    tick();
    expect(component.deviceNameForm.hasError('duplicateDeviceName')).toBeFalse();
    expect(filaioDevice.name).toEqual('Filaio a mano');
  }));

  it('rifiuta-aggiorna-nome-macchina', () => {
    const originalName = filaioDevice.name;
    component.deviceNameForm.setValue({
      name: locomotivaDevice.name
    });
    component.updateDeviceName();
    expect(component.deviceNameForm.get('name')?.hasError('duplicateDeviceName')).toBeTrue();
    expect(filaioDevice.name).toEqual(originalName);
  });

  it('aggiungi-caratteristica', (doneFn) => {
    component.openNewCharacteristicDialog();
    component.characteristics.connect().subscribe(value => {
      expect(value.find(val => val.name === testInsertCharacteristic.name)).toBeTruthy();
      doneFn();
    });
  });
});


describe('DeviceDetailComponent Update', () => {
  let component: DeviceDetailComponent;
  let fixture: ComponentFixture<DeviceDetailComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        imports: testModules,
        declarations: [DeviceDetailComponent],
        providers: [
          FakeDeviceService,
          {
            provide: CharacteristicAbstractService,
            useExisting: FakeDeviceService
          },
          {
            provide: UpdateDeviceAbstractService,
            useExisting: FakeDeviceService
          },
          MockSnack,
          {
            provide: MatSnackBar,
            useExisting: MockSnack
          },
          MockDialogUpdateCharacteristic,
          {
            provide: MatDialog,
            useExisting: MockDialogUpdateCharacteristic
          },
          {
            provide: ActivatedRoute,
            useValue: {
              snapshot: {
                data: {
                  device: filaioDevice
                }
              }
            }
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(DeviceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('aggiorna-caratteristica', (doneFn) => {
    component.openUpdateCharacteristicFormDialog(filaioDevice.characteristics[0]);
    component.characteristics.connect().subscribe(value => {
      expect(value.find(char => char.name === testUpdateCharacteristic.name)).toBeTruthy();
      doneFn();
    })
  });
});

describe('DeviceDetailComponent Confirm', () => {
  let component: DeviceDetailComponent;
  let fixture: ComponentFixture<DeviceDetailComponent>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        imports: testModules,
        declarations: [ DeviceDetailComponent ],
        providers: [
          FakeDeviceService,
          {
            provide: CharacteristicAbstractService,
            useExisting: FakeDeviceService
          },
          {
            provide: UpdateDeviceAbstractService,
            useExisting: FakeDeviceService
          },
          MockSnack,
          {
            provide: MatSnackBar,
            useExisting: MockSnack
          },
          MockDialogAlwaysConfirm,
          {
            provide: MatDialog,
            useExisting: MockDialogAlwaysConfirm
          },
          {
            provide: ActivatedRoute,
            useValue: {
              snapshot: {
                data: {
                  device: locomotivaDevice
                }
              }
            }
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(DeviceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('ripristina-caratteristica', () => {
    const archivedChar = locomotivaDevice.characteristics[1];
    component.toggleCharacteristicStatus(archivedChar);
    expect(archivedChar.archived).toBeFalse();
  });

  it('archivia-caratteristica', () => {
    const archivedChar = locomotivaDevice.characteristics[0];
    component.toggleCharacteristicStatus(archivedChar);
    expect(archivedChar.archived).toBeTrue();
  });
});
