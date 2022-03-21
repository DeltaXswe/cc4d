import { TestBed } from '@angular/core/testing';

import { UnarchivedDeviceService } from './unarchived-device.service';

describe('UnarchivedDeviceService', () => {
  let service: UnarchivedDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnarchivedDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
