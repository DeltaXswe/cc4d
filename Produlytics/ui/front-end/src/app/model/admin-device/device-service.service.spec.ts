import { TestBed } from '@angular/core/testing';

import { DeviceServiceService } from './device-service.service';

describe('DeviceServiceService', () => {
  let service: DeviceServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
