import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';
import {testModules} from "../../test/utils";

describe('LoginService', () => {
  let service: LoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(LoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
