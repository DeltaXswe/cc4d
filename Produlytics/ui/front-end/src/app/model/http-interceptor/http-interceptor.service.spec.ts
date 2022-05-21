import { TestBed } from '@angular/core/testing';

import { XhrInterceptor } from './http-interceptor.service';
import {testModules} from "../../test/utils";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('HttpInterceptorService', () => {
  let service: XhrInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        testModules,
        HttpClientTestingModule,

      ],
      providers: [
        XhrInterceptor,
        {provide:HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
      ]
    });
    service = TestBed.inject(XhrInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
