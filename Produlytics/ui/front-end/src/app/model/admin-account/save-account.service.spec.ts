import { TestBed } from '@angular/core/testing';

import { SaveAccountService } from './save-account.service';

describe('SaveAccountService', () => {
  let service: SaveAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaveAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
