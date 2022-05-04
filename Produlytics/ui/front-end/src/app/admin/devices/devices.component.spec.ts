import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { DevicesComponent } from './devices.component';
import {MockDialogAlwaysConfirm, testModules} from "../../test/utils";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {
  devices,
  FakeDeviceService,
  filaioDevice,
  locomotivaDevice,
  valvolaDevice
} from "../../test/device/fake-device.service";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {HttpTestingController} from "@angular/common/http/testing";
import {HttpClient} from "@angular/common/http";
import {RouterTestingModule} from "@angular/router/testing";
import {DeviceService} from "../../model/admin-device/device.service";

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

describe('DevicesComponent Integration', () => {
  let component: DevicesComponent;
  let fixture: ComponentFixture<DevicesComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [DevicesComponent],
        imports: testModules,
        providers: [
          DeviceService,
          {
            provide: DeviceAbstractService,
            useExisting: DeviceService
          },
          MockDialogAlwaysConfirm,
          {
            provide: MatDialog,
            useExisting: MockDialogAlwaysConfirm
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(DevicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('devices-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('devices-init', () => {
    // oninit called in automatico
    const req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('devices-archive', () => {
    let req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    component.toggleStatusDevice(filaioDevice);
    req = httpTestingController.expectOne(`admin/devices/${filaioDevice.id}/archived`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeTrue();
    req.flush({});
    req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    httpTestingController.verify();
  });

  it('devices-recover', () => {
    let req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    component.toggleStatusDevice(valvolaDevice);
    req = httpTestingController.expectOne(`admin/devices/${valvolaDevice.id}/archived`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeFalse();
    req.flush({});
    req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    httpTestingController.verify();
  });

  it('devices-deactivate', () => {
    let req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    component.toggleActivationDevice(filaioDevice);
    req = httpTestingController.expectOne(`admin/devices/${filaioDevice.id}/deactivated`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeTrue();
    req.flush({});
    req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    httpTestingController.verify();
  });

  it('devices-activate', () => {
    let req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    component.toggleActivationDevice(valvolaDevice);
    req = httpTestingController.expectOne(`admin/devices/${valvolaDevice.id}/deactivated`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeFalse();
    req.flush({});
    req = httpTestingController.expectOne('admin/devices');
    expect(req.request.method).toEqual('GET');
    req.flush(devices);
    httpTestingController.verify();
  });
});
