import { TestBed } from '@angular/core/testing';

import { UpdateCharacteristicService } from './update-characteristic.service';
import {testModules} from "../../../test/utils";

describe('UpdateCharacteristicService', () => {
  let service: UpdateCharacteristicService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(UpdateCharacteristicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
