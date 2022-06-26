import {fakeAsync, TestBed, tick} from "@angular/core/testing";
import {testModules} from "../test/utils";
import {HttpInterceptorService} from "./http-interceptor.service";
import {HttpContext, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from "@angular/common/http";
import {Observable, of, throwError} from "rxjs";
import {Location} from "@angular/common";

describe('HttpInterceptorService', () => {
  let httpInterceptorService: HttpInterceptorService;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        imports: testModules
      })
      .compileComponents();
    httpInterceptorService = TestBed.inject(HttpInterceptorService);
    location = TestBed.inject(Location);
  });

  it('should-create', () => {
    expect(httpInterceptorService).toBeTruthy();
  });

  it('intercept-request', fakeAsync(() => {
    httpInterceptorService.intercept({
      url: '/some-url',
      params: new HttpParams(),
      headers: new HttpHeaders(),
      method: 'POST',
      body: {}
    } as any, {
      handle(req: HttpRequest<any>): Observable<HttpEvent<any>> {
        return throwError(() => {
          return {
            status: 401,
              body: 'NON OK',
            url: '/some-url',
            headers: new HttpHeaders(),
            params: new HttpParams(),
          } as any
        });
      }
    }).subscribe({
      error: err => {

      }
    });
    tick();
    expect(location.path()).toBe('/login');
  }));

  it('ignore-request', fakeAsync(() => {
    httpInterceptorService.intercept({
      url: '/some-url',
      params: new HttpParams(),
      headers: new HttpHeaders().set('Skip-Interceptor', 'true'),
      method: 'POST',
      body: {}
    } as any, {
      handle(req: HttpRequest<any>): Observable<HttpEvent<any>> {
        return throwError(() => {
          return {
            status: 401,
            body: 'NON OK',
            url: '/some-url',
            headers: new HttpHeaders(),
            params: new HttpParams(),
          } as any
        });
      }
    }).subscribe({
      error: err => {

      }
    });
    tick();
    expect(location.path()).toBe('');
  }));
})
