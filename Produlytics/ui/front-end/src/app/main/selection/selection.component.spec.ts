import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceSelectionComponent } from './device-selection.component';
import {FakeDeviceService} from "../../test/device/fake-device.service";
import {UnarchivedDeviceAbstractService} from "../../model/device/unarchived-device-abstract.service";

describe('DeviceSelectionComponent', () => {
  let component: DeviceSelectionComponent;
  let fixture: ComponentFixture<DeviceSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceSelectionComponent ],
      providers: [
        FakeDeviceService,
        {
          provide: UnarchivedDeviceAbstractService,
          useExisting: FakeDeviceService
        },

      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
