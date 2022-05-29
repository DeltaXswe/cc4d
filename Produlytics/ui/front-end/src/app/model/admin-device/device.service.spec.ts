import { TestBed } from '@angular/core/testing';

import { DeviceService } from './device.service';
import {testModules} from "../../test/utils";

describe('DeviceService', () => {
  let service: DeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(DeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
