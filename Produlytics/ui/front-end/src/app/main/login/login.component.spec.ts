import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { testModules } from 'src/app/test/utils';
import { gianniUser } from 'src/app/test/account/users';

import { LoginComponent } from './login.component';
import { CookieService } from 'ngx-cookie-service';
import { Injector, ReflectiveInjector } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/model/login/login.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let cookieService: CookieService;
  let router = {
    navigate: jasmine.createSpy('navigate')
  };
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let loginService: LoginAbstractService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ LoginComponent ],
      providers: [
        {
          provide: AccountAbstractService,
          useExisting: FakeAccountService
        },
        {
          provide: LoginAbstractService,
          useExisting: LoginService
        },
        CookieService,
        {
          provide: Router,
          useValue: router
        },
        LoginService
      ]
    })
    .compileComponents();
  });

  beforeEach(async () => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    cookieService = TestBed.inject(CookieService);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    loginService = TestBed.inject(LoginAbstractService)
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('validità campi obbligatori', () => {
    let username = component.loginForm.controls['username'];
    let password = component.loginForm.controls['password'];
    expect(username.valid).toBeFalsy();
    expect(password.valid).toBeFalsy();
    expect(component.loginForm.valid).toBeFalsy();

    username.setValue('');
    password.setValue('');
    
    expect(username.hasError('required')).toBeTruthy();
    expect(password.hasError('required')).toBeTruthy();
  })

  it('validità campo password>6caratteri', () => {
    let password = component.loginForm.controls['password'];

    password.setValue('ciao');

    expect(component.loginForm.valid).toBeFalsy();
    expect(password.hasError('minlength')).toBeTruthy();
  })

  it('form valido con input validi', () => {
    let username = component.loginForm.controls['username'];
    let password = component.loginForm.controls['password'];

    username.setValue(gianniUser.username);
    password.setValue(gianniUser.password);

    expect(component.loginForm.valid).toBeTruthy();
  })

  it('con cookie vado direttamente alla dashboard', () => {
    cookieService.deleteAll();
    cookieService.set('PRODULYTICS_RM', 'valore');
    component.ngOnInit();
    expect(router.navigate).toHaveBeenCalledWith(['/']); 
  })

  it('chiamata a loginservice', () => {
    component.loginForm.controls['username'].setValue(gianniUser.username);
    component.loginForm.controls['password'].setValue(gianniUser.password);
    component.loginForm.controls['rememberMe'].setValue(true);
    component.onSubmit();
    const req = httpTestingController.expectOne('/login?remember-me=true');
    expect(req.request.method).toEqual('GET');
    req.flush({});
    httpTestingController.verify();
  });
});
