import { TestBed } from '@angular/core/testing';

import { XhrInterceptor } from './http-interceptor.service';

describe('HttpInterceptorService', () => {
  let service: XhrInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(XhrInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
