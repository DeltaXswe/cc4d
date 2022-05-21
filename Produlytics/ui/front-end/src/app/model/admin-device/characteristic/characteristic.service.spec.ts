import { TestBed } from '@angular/core/testing';

import { CharacteristicService } from './characteristic.service';
import {testModules} from "../../../test/utils";

describe('CharacteristicService', () => {
  let service: CharacteristicService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(CharacteristicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
