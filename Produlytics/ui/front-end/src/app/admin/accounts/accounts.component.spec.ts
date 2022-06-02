import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsComponent } from './accounts.component';
import {LoginAbstractService} from "../../model/login/login-abstract.service";
import {FakeAccountService} from "../../test/account/fake-account.service";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MockDialogAlwaysConfirm, testModules} from "../../test/utils";
import {MatDialog} from "@angular/material/dialog";
import {aliceUser, bobUser, cosimoUser, users} from "../../test/account/users";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {AccountService} from "../../model/admin-account/account.service";
import {LoginService} from "../../model/login/login.service";
import {AccountEntity} from "../../test/account/account-entity";

describe('AccountsComponent', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountsComponent ],
      imports: testModules,
      providers: [
        FakeAccountService,
        {
          provide: AccountAbstractService,
          useExisting: FakeAccountService
        },
        {
          provide: LoginAbstractService,
          useExisting: FakeAccountService
        },
        MockDialogAlwaysConfirm,
        {
          provide: MatDialog,
          useExisting: MockDialogAlwaysConfirm
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(AccountsComponent);
    const loginService = TestBed.inject(LoginAbstractService);
    await loginService.login({
      username: aliceUser.username,
      password: aliceUser.password,
      rememberMe: false
    }).toPromise();
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('apri-nuovo-utente', (doneFn) => {
    component.openNewAccountDialog();
    component.accounts.connect().subscribe(val => {
      expect(val).toEqual(users);
      doneFn();
    });
  });

  it('apri-modifica-utente', (doneFn) => {
    component.openNewAccountDialog();
    component.accounts.connect().subscribe(val => {
      expect(val).toEqual(users);
      doneFn();
    });
  });

  it('archivia-utente', () => {
    component.toggleStatus(cosimoUser);
    component.accounts.connect().subscribe(data => {
      expect(cosimoUser.archived).toBeTruthy()
    });
  });

  it('ripristina-utente', () => {
    component.toggleStatus(bobUser);
    component.accounts.connect().subscribe(data => {
      expect(bobUser.archived).toBeFalsy()
    });
  });

  it('utente-loggato', () => {
    expect(component.isLoggedUser(aliceUser)).toBeTrue();
    expect(component.isLoggedUser(cosimoUser)).toBeFalse();
  })
});

describe('AccountsComponent Integration', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [AccountsComponent],
        imports: testModules,
        providers: [
          AccountService,
          {
            provide: AccountAbstractService,
            useExisting: AccountService
          },
          LoginService,
          {
            provide: LoginAbstractService,
            useExisting: LoginService
          },
          MockDialogAlwaysConfirm,
          {
            provide: MatDialog,
            useExisting: MockDialogAlwaysConfirm
          }
        ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('accounts-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('accounts-init', () => {
    // oninit called in automatico
    const req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('GET');
    req.flush([]);
    httpTestingController.verify();
  });

  it('accounts-archive', () => {
    let req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('GET');
    req.flush(users);
    component.toggleStatus(aliceUser);
    req = httpTestingController.expectOne('admin/accounts/alice/archived');
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeTrue();
    req.flush({});
    req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('GET');
    req.flush(users);
    httpTestingController.verify();
  });

  it('accounts-recover', () => {
    const bobUser = AccountEntity.CREATE({
      username: 'bob',
      password: 'linkinpark',
      administrator: false
    });
    bobUser.archived = true;
    let req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('GET');
    req.flush([bobUser]);
    component.toggleStatus(bobUser);
    req = httpTestingController.expectOne('admin/accounts/bob/archived');
    expect(req.request.method).toEqual('PUT');
    expect(req.request.body).toBeFalse();
    req.flush({});
    req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('GET');
    req.flush(users);
    httpTestingController.verify();
  });
});
