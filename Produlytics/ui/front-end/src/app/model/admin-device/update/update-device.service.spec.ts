import { TestBed } from '@angular/core/testing';

import { UpdateDeviceService } from './update-device.service';
import {testModules} from "../../../test/utils";

describe('UpdateDeviceService', () => {
  let service: UpdateDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(UpdateDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
