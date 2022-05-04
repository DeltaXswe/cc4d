import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCharacteristicDialogComponent } from './update-characteristic-dialog.component';
import {MockDialogRef, testModules} from "../../../test/utils";
import {ComponentsModule} from "../../../components/components.module";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FakeDeviceService} from "../../../test/device/fake-device.service";
import {
  UpdateCharacteristicAbstractService
} from "../../../model/admin-device/characteristic/update-characteristic-abstract.service";

describe('UpdateCharacteristicDialogComponent', () => {
  let component: UpdateCharacteristicDialogComponent;
  let fixture: ComponentFixture<UpdateCharacteristicDialogComponent>;

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
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
