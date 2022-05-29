import {ComponentFixture, discardPeriodicTasks, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { NewDeviceComponent } from './new-device.component';
import {MockSnack, testModules} from "../../../test/utils";
import {BehaviorSubject, Observable} from "rxjs";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {MatDialog} from "@angular/material/dialog";
import {FakeDeviceService, filaioDevice, locomotivaDevice} from "../../../test/device/fake-device.service";
import {NewDeviceAbstractService} from "../../../model/admin-device/new/new-device-abstract.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {NewDeviceService} from "../../../model/admin-device/new/new-device.service";

export const testInsertCharacteristic: CharacteristicCreationCommand = {
  name: 'test-insert',
  autoAdjust: true,
  upperLimit: null,
  lowerLimit: null,
  sampleSize: 9
}

export class MockDialogNewCharacteristic {
  open(_?: any, options?: any) {
    return new MockDialogRefNewCharacteristic();
  }
}

class MockDialogRefNewCharacteristic {
  private readonly _afterClosed = new BehaviorSubject<CharacteristicCreationCommand>(testInsertCharacteristic);

  constructor() {
    this.close(true);
  }

  public close(value: any) {
    this._afterClosed.next(testInsertCharacteristic);
  }

  public afterClosed(): Observable<any> {
    return this._afterClosed;
  }
}

describe('NewDeviceComponent', () => {
  let component: NewDeviceComponent;
  let fixture: ComponentFixture<NewDeviceComponent>;
  let router: Router;
  let location: Location;
  let fakeDeviceService: FakeDeviceService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewDeviceComponent ],
      imports: testModules,
      providers: [
        FakeDeviceService,
        {
          provide: NewDeviceAbstractService,
          useExisting: FakeDeviceService
        },
        MockDialogNewCharacteristic,
        {
          provide: MatDialog,
          useExisting: MockDialogNewCharacteristic
        },
        MockSnack,
        {
          provide: MatSnackBar,
          useExisting: MockSnack
        }
      ]

    })
    .compileComponents();

    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    router.initialNavigation();
    fakeDeviceService = TestBed.inject(FakeDeviceService);
    fixture = TestBed.createComponent(NewDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('aggiungi-caratteristica-nuova-macchina', () => {
    component.openNewCharacteristicDialog();
    expect(component.characteristics.find(value => value.name === testInsertCharacteristic.name)).toBeTruthy();
  });

  it('conferma-inserisci-macchina', fakeAsync(() => {
    const id = fakeDeviceService.getNextId();
    component.formGroup.setValue({
      name: 'insert-test'
    });
    component.characteristics = filaioDevice.characteristics;
    component.insertDevice();
    tick();
    expect(location.path()).toBe(`/gestione-macchine/${id}`);
  }));

  it('rifiuta-inserisci-macchina', () => {
    component.formGroup.setValue({
      name: locomotivaDevice.name
    });
    component.insertDevice();
    expect(component.formGroup.get('name')?.hasError('duplicateDeviceName')).toBeTrue();
  })
});

describe('NewDeviceComponent Integration', () => {

  let component: NewDeviceComponent;
  let fixture: ComponentFixture<NewDeviceComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let router: Router;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [NewDeviceComponent],
        imports: testModules,
        providers: [
          MockDialogNewCharacteristic,
          {
            provide: MatDialog,
            useExisting: MockDialogNewCharacteristic
          },
          MatSnackBar,
          NewDeviceService,
          {
            provide: NewDeviceAbstractService,
            useExisting: NewDeviceService
          },
          MockSnack,
          {
            provide: MatSnackBar,
            useExisting: MockSnack
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(NewDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
  });

  it('new-device-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('new-device-save', fakeAsync(() => {
    component.formGroup.setValue({
      name: 'Nuova'
    });
    component.openNewCharacteristicDialog();
    component.openNewCharacteristicDialog();
    component.insertDevice();
    const req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('POST');
    req.flush({id: 4});
    httpTestingController.verify();
    tick();
    expect(location.path()).toBe('/gestione-macchine/4');
  }))
});

