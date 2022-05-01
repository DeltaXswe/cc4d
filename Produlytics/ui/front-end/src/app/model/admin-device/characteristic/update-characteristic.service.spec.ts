import { TestBed } from '@angular/core/testing';

import { UpdateCharacteristicService } from './update-characteristic.service';

describe('UpdateCharacteristicService', () => {
  let service: UpdateCharacteristicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateCharacteristicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
