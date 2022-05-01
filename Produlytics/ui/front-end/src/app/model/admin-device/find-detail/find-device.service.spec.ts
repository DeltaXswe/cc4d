import { TestBed } from '@angular/core/testing';

import { FindDeviceService } from './find-device.service';

describe('FindDeviceService', () => {
  let service: FindDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FindDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
