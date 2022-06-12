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

describe('AuthenticatedUserGuard', () => {
  let authGuard: AuthenticatedUserGuard;
  let loginService: LoginAbstractService;
  let router: Router;

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
    sessionStorage.clear();
    localStorage.clear();
    localStorage.setItem('accessToken', 'accessToken :D');
  });

  afterEach(() => {
    sessionStorage.clear();
    localStorage.clear();
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
    localStorage.clear();
    const url = router.parseUrl('/login');
    const canActivate = authGuard.canActivate();
    if (canActivate === true) { // se non è true è un observable
      fail();
    } else {
      canActivate.subscribe(value => {
        expect(value).toEqual(url);
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
      localStorage.clear();
      localStorage.setItem('accessToken', 'accessToken :D');
      localStorage.setItem('admin', 'true');
    });

    afterEach(() => {
      localStorage.clear();
    })

    it('should-create', () => {
      expect(adminGuard).toBeTruthy();
    });

    it('admin-canActivate', () => {
      const canActivate = adminGuard.canActivate();
      expect(canActivate).toBeTruthy();
    });

    it('not-admin-canActivate', () => {
      localStorage.removeItem('admin');
      const url = router.parseUrl('');
      const canActivate = adminGuard.canActivate();
      expect(canActivate).toEqual(url);
    });
  });

    describe('LoginGuard', () => {
      let loginGuard: LoginGuard;
      let loginService: LoginAbstractService;
      let router: Router;

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
        localStorage.clear();
      });

      afterEach(() => {
        localStorage.clear();
      })

      it('should-create', () => {
        expect(loginGuard).toBeTruthy();
      });

      it('not-authenticated-canActivate', () => {
        const canActivate = loginGuard.canActivate();
        expect(canActivate).toBeTruthy();
      });

      it('authenticated-canActivate', () => {
        localStorage.setItem('accessToken', 'accessToken :D');
        const url = router.parseUrl('');
        const canActivate = loginGuard.canActivate();
        if (canActivate instanceof Observable) {
          canActivate.subscribe(value => {
            expect(value).toEqual(url);
          });
        } else {
          expect(canActivate).toEqual(url);
        }
      });
});
