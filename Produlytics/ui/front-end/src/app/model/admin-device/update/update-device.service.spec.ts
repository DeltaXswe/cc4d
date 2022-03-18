import { TestBed } from '@angular/core/testing';

import { UpdateDeviceService } from './update-device.service';

describe('UpdateDeviceService', () => {
  let service: UpdateDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
