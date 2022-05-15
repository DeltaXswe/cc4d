import { TestBed } from '@angular/core/testing';

import { SaveAccountService } from './save-account.service';
import {testModules} from "../../test/utils";

describe('SaveAccountService', () => {
  let service: SaveAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(SaveAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
