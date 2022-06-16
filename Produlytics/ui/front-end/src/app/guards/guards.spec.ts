import {TestBed} from '@angular/core/testing';
import {Router} from '@angular/router';
import {AuthenticatedUserGuard} from './authenticated-user-guard';
import {LoginService} from "../model/login/login.service";
import {LoginAbstractService} from "../model/login/login-abstract.service";
import {RouterTestingModule} from "@angular/router/testing";
import {testModules} from "../test/utils";
import {AdminGuard} from "./admin-guard";
import {LoginGuard} from "./login-guard";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";

describe('AuthenticatedUserGuard', () => {
  let authGuard: AuthenticatedUserGuard;
  let loginService: LoginAbstractService;
  let router: Router;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      providers: [
        AuthenticatedUserGuard,
        {
          provide: LoginAbstractService,
          useExisting: LoginService
        }
      ]
    })
      .compileComponents();
    authGuard = TestBed.inject(AuthenticatedUserGuard);
    loginService = TestBed.inject(LoginAbstractService);
    router = TestBed.inject(Router);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    sessionStorage.clear();
    sessionStorage.clear();
    sessionStorage.setItem('accessToken', 'accessToken :D');
    sessionStorage.setItem('username', 'Roberto');
  });

  afterEach(() => {
    sessionStorage.clear();
    sessionStorage.clear();
  })

  it('should-create', () => {
    expect(authGuard).toBeTruthy();
  });

  it('user-logged-canActivate', () => {
    const canActivate = authGuard.canActivate();
    expect(canActivate).toBeTruthy();
  });

  it('user-not-logged-canActivate', () => {
    sessionStorage.clear();
    sessionStorage.clear();
    const url = router.parseUrl('/login');
    const canActivate = authGuard.canActivate();
    if (canActivate === true) { // se non è true è un observable
      fail();
    } else {
      canActivate.subscribe(value => {
        expect(value).toEqual(url);
      });
      const req = httpTestingController.expectOne('/login?remember-me=true');
      expect(req.request.method).toEqual('GET');
      req.flush({}, { status: 401, statusText: 'Unauthorized' });
    }
  });

  it('user-not-logged-auto-skip-login', () => {
    sessionStorage.clear();
    const canActivate = authGuard.canActivate();
    if (canActivate === true) { // se non è true è un observable
      fail();
    } else {
      canActivate.subscribe(value => {
        expect(value).toBeTrue();
      });
      const req = httpTestingController.expectOne('/login?remember-me=true');
      expect(req.request.method).toEqual('GET');
      req.flush({
        username: 'Roberto',
        administrator: false,
        accessToken: 'accessToken :D'
      });
    }
  });
});

  describe('AdminGuard', () => {
    let adminGuard: AdminGuard;
    let loginService: LoginAbstractService;
    let router: Router;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [
          RouterTestingModule,
          testModules
        ],
        providers: [
          AdminGuard,
          {
            provide: LoginAbstractService,
            useExisting: LoginService
          }
        ]
      })
        .compileComponents();
      adminGuard = TestBed.inject(AdminGuard);
      loginService = TestBed.inject(LoginAbstractService);
      router = TestBed.inject(Router);
      sessionStorage.clear();
      sessionStorage.setItem('accessToken', 'accessToken :D');
      sessionStorage.setItem('admin', 'true');
      sessionStorage.setItem('username', 'Roberto');
    });

    afterEach(() => {
      sessionStorage.clear();
    })

    it('should-create', () => {
      expect(adminGuard).toBeTruthy();
    });

    it('admin-canActivate', () => {
      const canActivate = adminGuard.canActivate();
      expect(canActivate).toBeTruthy();
    });

    it('not-admin-canActivate', () => {
      sessionStorage.removeItem('admin');
      const url = router.parseUrl('');
      const canActivate = adminGuard.canActivate();
      expect(canActivate).toEqual(url);
    });
  });

describe('LoginGuard', () => {
  let loginGuard: LoginGuard;
  let loginService: LoginAbstractService;
  let router: Router;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      providers: [
        LoginGuard,
        {
          provide: LoginAbstractService,
          useExisting: LoginService
        }
      ]
    })
      .compileComponents();
    loginGuard = TestBed.inject(LoginGuard);
    loginService = TestBed.inject(LoginAbstractService);
    router = TestBed.inject(Router);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    sessionStorage.clear();
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('should-create', () => {
    expect(loginGuard).toBeTruthy();
  });

  it('not-authenticated-canActivate', () => {
    sessionStorage.clear();
    const canActivate = loginGuard.canActivate();
    if (canActivate instanceof Observable) {
      canActivate.subscribe(value => {
        expect(value).toBeTrue();
      });
      const req = httpTestingController.expectOne('/login?remember-me=true');
      expect(req.request.method).toEqual('GET');
      req.flush({}, {
        status: 401,
        statusText: 'Unauthorized'
      });
    } else {
      fail();
    }
  });

  it('authenticated-canActivate', () => {
    sessionStorage.setItem('accessToken', 'accessToken :D');
    sessionStorage.setItem('username', 'Roberto');
    const url = router.parseUrl('');
    const canActivate = loginGuard.canActivate();
    if (canActivate instanceof Observable) {
      fail();
    } else {
      expect(canActivate).toEqual(url);
    }
  });

  it('user-not-logged-remain-login', () => {
    sessionStorage.clear();
    const url = router.parseUrl('');
    const canActivate = loginGuard.canActivate();
    if (canActivate instanceof Observable) {
      canActivate.subscribe(value => {
        expect(value).toEqual(url);
      });
      const req = httpTestingController.expectOne('/login?remember-me=true');
      expect(req.request.method).toEqual('GET');
      req.flush({
        username: 'Roberto',
        administrator: false,
        accessToken: 'accessToken :D' // farewell, my friend
      });
    } else {
      fail();
    }
  });
});
