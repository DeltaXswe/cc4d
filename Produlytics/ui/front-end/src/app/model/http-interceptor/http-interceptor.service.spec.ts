import { TestBed } from '@angular/core/testing';

import { XhrInterceptor } from './http-interceptor.service';
import {testModules} from "../../test/utils";

describe('HttpInterceptorService', () => {
  let service: XhrInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: testModules
    });
    service = TestBed.inject(XhrInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
