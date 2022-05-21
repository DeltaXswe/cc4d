import { TestBed } from '@angular/core/testing';

import { UnarchivedDeviceService } from './unarchived-device.service';
import {testModules} from "../../test/utils";

describe('UnarchivedDeviceService', () => {
  let service: UnarchivedDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(UnarchivedDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
