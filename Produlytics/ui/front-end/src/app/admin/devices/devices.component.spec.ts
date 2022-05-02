import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { DevicesComponent } from './devices.component';
import {MockDialogAlwaysConfirm, testModules} from "../../test/utils";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {FakeDeviceService, filaioDevice, locomotivaDevice, valvolaDevice} from "../../test/device/fake-device.service";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {bobUser, cosimoUser} from "../../test/account/users";
import {MatDialog} from "@angular/material/dialog";

describe('DevicesComponent', () => {
  let component: DevicesComponent;
  let fixture: ComponentFixture<DevicesComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ DevicesComponent ],
      providers: [
        FakeDeviceService,
        {
          provide: DeviceAbstractService,
          useExisting: FakeDeviceService
        },
        MockDialogAlwaysConfirm,
        {
          provide: MatDialog,
          useExisting: MockDialogAlwaysConfirm
        }
      ]
    })
    .compileComponents();
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    router.initialNavigation();
    fixture = TestBed.createComponent(DevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('nuova-macchina', fakeAsync(() => {
    // router.navigate(['']);
    component.createDevice();
    tick(); // aspetta che le promesse si soddisfino
    expect(location.path()).toBe('/gestione-macchine/nuova');
  }));

  it('apri-dettaglio-macchina', fakeAsync(() => {
    // router.navigate(['']);
    component.openDeviceDetail(filaioDevice);
    tick(); // aspetta che le promesse si soddisfino
    expect(location.path()).toBe(`/gestione-macchine/${filaioDevice.id}`);
  }));


  it('disattiva-macchina', (doneFn) => {
    component.toggleActivationDevice(filaioDevice);
    component.devices.connect().subscribe(data => {
      if (locomotivaDevice.deactivated) {
        doneFn();
      }
    });
  });

  it('attiva-macchina', (doneFn) => {
    component.toggleActivationDevice(valvolaDevice);
    component.devices.connect().subscribe(data => {
      if (!valvolaDevice.deactivated) {
        doneFn();
      }
    });
  });

  it('archivia-macchina', (doneFn) => {
    component.toggleStatusDevice(locomotivaDevice);
    component.devices.connect().subscribe(data => {
      if (locomotivaDevice.archived) {
        doneFn();
      }
    });
  });

  it('ripristina-macchina', (doneFn) => {
    component.toggleStatusDevice(valvolaDevice);
    component.devices.connect().subscribe(data => {
      if (!valvolaDevice.archived) {
        doneFn();
      }
    });
  });
});
