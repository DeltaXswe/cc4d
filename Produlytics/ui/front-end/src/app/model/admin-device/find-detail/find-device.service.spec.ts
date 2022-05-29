import { TestBed } from '@angular/core/testing';

import { FindDeviceService } from './find-device.service';
import {testModules} from "../../../test/utils";

describe('FindDeviceService', () => {
  let service: FindDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(FindDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
