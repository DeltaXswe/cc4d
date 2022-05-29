import { TestBed } from '@angular/core/testing';

import { AccountService } from './account.service';
import {testModules} from "../../test/utils";

describe('AccountService', () => {
  let service: AccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(AccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
