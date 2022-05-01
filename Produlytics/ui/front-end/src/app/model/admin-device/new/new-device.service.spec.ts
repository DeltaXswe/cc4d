import { TestBed } from '@angular/core/testing';

import { NewDeviceService } from './new-device.service';

describe('NewDeviceService', () => {
  let service: NewDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NewDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
