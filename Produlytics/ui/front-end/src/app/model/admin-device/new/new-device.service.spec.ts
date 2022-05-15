import { TestBed } from '@angular/core/testing';

import { NewDeviceService } from './new-device.service';
import {testModules} from "../../../test/utils";

describe('NewDeviceService', () => {
  let service: NewDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(NewDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
