import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { DeviceDetailComponent } from './device-detail.component';
import {MockDialogAlwaysConfirm, MockSnack, testModules} from "../../../test/utils";
import {
  DeviceMock,
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
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MockDialogNewCharacteristic, testInsertCharacteristic} from "../new-device/new-device.component.spec";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {CharacteristicService} from "../../../model/admin-device/characteristic/characteristic.service";
import {UpdateDeviceService} from "../../../model/admin-device/update/update-device.service";
import { ClipboardModule } from '@angular/cdk/clipboard';


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
    component.deviceNameControl.setValue(
      'Filaio a mano'
    );
    component.updateDeviceName();
    tick();
    expect(component.deviceNameControl.hasError('duplicateDeviceName')).toBeFalse();
    expect(filaioDevice.name).toEqual('Filaio a mano');
  }));

  it('rifiuta-aggiorna-nome-macchina', () => {
    const originalName = filaioDevice.name;
    component.deviceNameControl.setValue(
      locomotivaDevice.name
    );
    component.updateDeviceName();
    expect(component.deviceNameControl.hasError('duplicateDeviceName')).toBeTrue();
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

describe('DeviceDetailComponent Integration', () => {

  let component: DeviceDetailComponent;
  let fixture: ComponentFixture<DeviceDetailComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [DeviceDetailComponent],
        imports: testModules,
        providers: [
          CharacteristicService,
          {
            provide: CharacteristicAbstractService,
            useExisting: CharacteristicService
          },
          UpdateDeviceService,
          {
            provide: UpdateDeviceAbstractService,
            useExisting: UpdateDeviceService
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
          },
          MockDialogNewCharacteristic,
          {
            provide: MatDialog,
            useExisting: MockDialogNewCharacteristic
          },
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(DeviceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('devices-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('device-detail-init', () => {
    // oninit called in automatico
    const req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('device-detail-add-char', () => {
    let req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush([]);

    component.openNewCharacteristicDialog();

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(testInsertCharacteristic);
    req.flush({id: 1000});

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('device-detail-archive-char', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocit??',
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
    let req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush(locomotivaDevice.characteristics);

    const char = locomotivaDevice.characteristics[0];
    component.toggleCharacteristicStatus(char);

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics/${char.id}/archived`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeTrue();
    req.flush({id: 1000});

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('device-detail-recover-char', () => {
    const locomotivaDevice = new DeviceMock({
      id: 3,
      name: 'Locomotiva',
      archived: false,
      deactivated: true,
      apiKey: 'CCC'
    }, [
      {
        id: 1,
        name: 'Velocit??',
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
    let req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush(locomotivaDevice.characteristics);

    const char = locomotivaDevice.characteristics[1];
    component.toggleCharacteristicStatus(char);

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics/${char.id}/archived`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeFalse();
    req.flush({id: 1000});

    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('device-detail-name', () => {

    let req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics`);
    expect(req.request.method).toEqual('GET');
    req.flush(locomotivaDevice.characteristics);

    component.deviceNameControl.setValue(
      'Colomotiva'
    );
    component.updateDeviceName();
    req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/name`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual('Colomotiva');
    req.flush({});
    httpTestingController.verify();
  });

});
