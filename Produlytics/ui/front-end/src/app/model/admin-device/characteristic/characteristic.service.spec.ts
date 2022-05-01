import { TestBed } from '@angular/core/testing';

import { CharacteristicService } from './characteristic.service';

describe('CharacteristicService', () => {
  let service: CharacteristicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CharacteristicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
