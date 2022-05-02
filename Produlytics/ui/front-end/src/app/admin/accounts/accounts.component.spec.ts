import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsComponent } from './accounts.component';
import {LoginAbstractService} from "../../model/login/login-abstract.service";
import {FakeAccountService} from "../../test/account/fake-account.service";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MockDialogAlwaysConfirm, testModules} from "../../test/utils";
import {MatDialog} from "@angular/material/dialog";
import {aliceUser, bobUser, cosimoUser, users} from "../../test/account/users";

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
    component.accounts.connect().subscribe(() => {
      doneFn();
    });
  });

  it('apri-modifica-utente', (doneFn) => {
    component.openNewAccountDialog();
    component.accounts.connect().subscribe(doneFn);
  });

  it('archivia-utente', (doneFn) => {
    component.toggleStatus(cosimoUser);
    component.accounts.connect().subscribe(data => {
      if (cosimoUser.archived) {
        doneFn();
      }
    });
  });

  it('ripristina-utente', (doneFn) => {
    component.toggleStatus(bobUser);
    component.accounts.connect().subscribe(data => {
      if (!bobUser.archived) {
        doneFn();
      }
    });
  });

  it('utente-loggato', () => {
    expect(component.isLoggedUser(aliceUser)).toBeTrue();
    expect(component.isLoggedUser(cosimoUser)).toBeFalse();
  })
});
