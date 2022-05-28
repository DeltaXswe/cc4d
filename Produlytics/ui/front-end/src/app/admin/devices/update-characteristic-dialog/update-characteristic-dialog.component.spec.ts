import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCharacteristicDialogComponent } from './update-characteristic-dialog.component';
import {MockDialogAlwaysConfirm, MockDialogRef, testModules} from "../../../test/utils";
import {ComponentsModule} from "../../../components/components.module";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FakeDeviceService, locomotivaDevice, valvolaDevice} from "../../../test/device/fake-device.service";
import {
  UpdateCharacteristicAbstractService
} from "../../../model/admin-device/characteristic/update-characteristic-abstract.service";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {UpdateCharacteristicService} from "../../../model/admin-device/characteristic/update-characteristic.service";
import {testInsertCharacteristic} from "../new-device/new-device.component.spec";

describe('UpdateCharacteristicDialogComponent', () => {
  let component: UpdateCharacteristicDialogComponent;
  let fixture: ComponentFixture<UpdateCharacteristicDialogComponent>;
  let matDialogRef: MatDialogRef<any>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ...testModules,
        ComponentsModule
      ],
      declarations: [ UpdateCharacteristicDialogComponent ],
      providers: [
        FakeDeviceService,
        {
          provide: UpdateCharacteristicAbstractService,
          useExisting: FakeDeviceService
        },
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: {
            characteristics: []
          }
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(UpdateCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    matDialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('annulla-modifica-caratteristica', (doneFn) => {
    matDialogRef.afterClosed().subscribe(value => {
      expect(value).toBeUndefined();
      doneFn();
    });
    component.cancel();
  });

  it('rifiuta-modifica-caratteristica', () => {
    component.data = {
      deviceId: 1,
      characteristic: valvolaDevice.characteristics[1]
    }
    component.charForm.formGroup.setValue({
      name: 'Pressione',
      autoAdjust: true,
      sampleSize: 30,
      upperLimit: null,
      lowerLimit: null
    });
    component.confirm();
    expect(
      component.charForm.formGroup
        .get('name')?.hasError('duplicateCharacteristicName')
    ).toBeTrue();
  });


  it('conferma-modifica-caratteristica', (doneFn) => {
    matDialogRef.afterClosed().subscribe(value => {
      expect(value).toBeTrue();
      doneFn();
    });
    component.data = {
      deviceId: 1,
      characteristic: valvolaDevice.characteristics[1]
    }
    component.confirm();
  });
});

describe('UpdateCharacteristicDialogComponent Integration', () => {

  let component: UpdateCharacteristicDialogComponent;
  let fixture: ComponentFixture<UpdateCharacteristicDialogComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [UpdateCharacteristicDialogComponent],
        imports: [
          ...testModules,
          ComponentsModule
        ],
        providers: [
          UpdateCharacteristicService,
          {
            provide: UpdateCharacteristicAbstractService,
            useExisting: UpdateCharacteristicService
          },
          MockDialogRef,
          {
            provide: MatDialogRef,
            useExisting: MockDialogRef
          },
          {
            provide: MAT_DIALOG_DATA,
            useValue: {
            }
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(UpdateCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('update-characteristic-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('update-characteristic-confirm', () => {
    const char = locomotivaDevice.characteristics[1];

    component.data = {
      deviceId: locomotivaDevice.id,
      characteristic: locomotivaDevice.characteristics[1]
    };
    component.charForm.formGroup.setValue({
      name: char.name,
      autoAdjust: char.autoAdjust,
      upperLimit: char.upperLimit,
      lowerLimit: char.lowerLimit,
      sampleSize: char.sampleSize
    });
    component.confirm();

    const req = httpTestingController.expectOne(`admin/devices/${locomotivaDevice.id}/characteristics/${char.id}`);
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toEqual({
      name: char.name,
      autoAdjust: char.autoAdjust,
      upperLimit: char.upperLimit,
      lowerLimit: char.lowerLimit,
      sampleSize: char.sampleSize,
      id: char.id,
      deviceId: locomotivaDevice.id,
    })
    req.flush({});
    httpTestingController.verify();
  })
});

