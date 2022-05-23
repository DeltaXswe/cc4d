import { TestBed } from '@angular/core/testing';

import { XhrInterceptor } from './http-interceptor.service';
import {testModules} from "../../test/utils";
import {HTTP_INTERCEPTORS, HttpClient, HttpErrorResponse} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {LoginAbstractService} from "../login/login-abstract.service";
import {LoginService} from "../login/login.service";

describe('HttpInterceptorService', () => {
  let interceptor: XhrInterceptor;
  let httpTestingController: HttpTestingController;
  let httpClient: HttpClient;
  let loginService: LoginAbstractService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        testModules,
        HttpClientTestingModule
      ],
      providers: [
        XhrInterceptor,
        {
          provide:HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true
        },
        {
          provide: LoginAbstractService,
          useClass: LoginService
        }
      ]
    });
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    interceptor = TestBed.inject(XhrInterceptor);
    loginService = TestBed.inject(LoginAbstractService);
    localStorage.setItem('accessToken', 'accessToken :D');
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('positive-http-call', () => {
    httpClient.get('/target').subscribe();
    const req = httpTestingController.expectOne('/target');
    req.flush('');
    httpTestingController.verify();
    expect(req.request.headers.get('X-Auth-Token')).toEqual(
      'accessToken :D');
  })

  it('401-http-call', () => {
    let logoutSpy = spyOn(loginService, 'logout');
    const errMsg = '401 error';
    const mockErrorResponse = {status: 401, statusText: 'unauthorized'};
    httpClient.get('/target').subscribe(res => fail('Ci dovrebbe essere un 401 qui...'));
    let req = httpTestingController.expectOne('/target');
    req.flush(errMsg, mockErrorResponse);
    httpTestingController.verify();
    expect(logoutSpy).toHaveBeenCalledTimes(1);
  })

  it('other-error-http-call', () => {
    let logoutSpy = spyOn(loginService, 'logout');
    const errMsg = '404 error';
    const mockErrorResponse = {status: 404, statusText: 'errore strano'};
    httpClient
      .get('/target')
      .subscribe({
        next: () => fail('Ci dovrebbe essere un 404 qui...'),
        error: (error: HttpErrorResponse) => {
        expect(error.status).toEqual(404);
        expect(error.error).toEqual('404 error');
      }});
    let req = httpTestingController.expectOne('/target');
    req.flush(errMsg, mockErrorResponse);
    httpTestingController.verify();
    expect(logoutSpy).toHaveBeenCalledTimes(0);
  })


});
