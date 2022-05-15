import { TestBed } from '@angular/core/testing';

import { ModifyPwService } from './modify-pw.service';
import {testModules} from "../../test/utils";

describe('ModifyPwService', () => {
  let service: ModifyPwService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(ModifyPwService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
