import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { NewDeviceComponent } from './new-device.component';
import {MockSnack, testModules} from "../../../test/utils";
import {BehaviorSubject, Observable} from "rxjs";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {MatDialog} from "@angular/material/dialog";
import {FakeDeviceService, filaioDevice, locomotivaDevice} from "../../../test/device/fake-device.service";
import {DeviceAbstractService} from "../../../model/admin-device/device-abstract.service";
import {NewDeviceAbstractService} from "../../../model/admin-device/new/new-device-abstract.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";

const inserted: CharacteristicCreationCommand = {
  name: 'test-insert',
  autoAdjust: true,
  upperLimit: null,
  lowerLimit: null,
  sampleSize: 9
}

class MockDialogNewCharacteristic {
  open(_?: any, options?: any) {
    return new MockDialogRefNewCharacteristic();
  }
}

class MockDialogRefNewCharacteristic {
  private readonly _afterClosed = new BehaviorSubject<CharacteristicCreationCommand>(inserted);

  constructor() {
    this.close(true);
  }

  public close(value: any) {
    this._afterClosed.next(inserted);
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
    expect(component.characteristics.find(value => value.name === inserted.name)).toBeTruthy();
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
