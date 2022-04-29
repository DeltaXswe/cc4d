import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountFormDialogComponent } from './account-form-dialog.component';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {MockDialogRef, testModules} from "../../../test/utils";
import {AccountSaveCommand} from "../../../model/admin-account/account-save-command";
import {AppModule} from "../../../app.module";
import {FakeAccountService} from "../../../test/account/fake-account.service";
import {SaveAccountAbstractService} from "../../../model/admin-account/save-account-abstract.service";
import {LoginAbstractService} from "../../../model/login/login-abstract.service";

const alice: Account = {
  username: 'alice',
  administrator: true,
  archived: false
};

const validSaveUser: AccountSaveCommand = {
  username: 'bob',
  administrator: false,
  password: 'fossadeileoni'
};

describe('AccountFormDialogComponent new mode', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let mockAccountService: FakeAccountService;
  let mockDialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountFormDialogComponent ],
      imports: testModules,
      providers: [
        FakeAccountService,
        {
          provide: SaveAccountAbstractService,
          useExisting: FakeAccountService
        },
        {
          provide: LoginAbstractService,
          useExisting: FakeAccountService
        },
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: null
        },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    mockAccountService = TestBed.inject(FakeAccountService);
    mockDialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have usernameField correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeFalse();
  });

  it('should accept the data', async (done) => {
    mockDialogRef.afterClosed()
      .subscribe({
        next: value => {
          expect(value)
            .withContext('insertion esito')
            .toBeTrue();
        },
        error: done.fail,
        complete: done
      });
    component.formGroup.setValue(validSaveUser);
    component.confirm();
  });

  it('should have duplicate username error', () => {
    component.formGroup.setValue({
      username: alice.username,
      administrator: false,
      password: 'sbarlafus'
    });
    component.confirm();
    expect(component.formGroup.get('username')?.hasError('duplicateUsername'))
      .toBeTrue();
  })

  it('should have invalid password error', () => {
    component.formGroup.setValue({
      username: 'carl',
      administrator: false,
      password: 'carl'
    })
    component.confirm();
    expect(component.formGroup.get('password')?.hasError('invalidPassword'))
      .toBeTrue();
  })

  it('should cancel the operation', async (done) => {
    component.cancel();
    mockDialogRef.afterClosed().subscribe({
      next: value => {
        expect(value).toBeUndefined();
      },
      error: done.fail,
      complete: done
    })
  })

  it('should remove username error', () => {
    component.formGroup.setValue({
      username: 'alice',
      administrator: false,
      password: 'fossadeileoni'
    });
    component.confirm();
    expect(component.formGroup.get('username')?.hasError('duplicateUsername'))
      .toBeTrue();
    component.formGroup.setValue({
      username: 'alice1',
      administrator: false,
      password: 'fossadeileoni'
    });
    expect(component.formGroup.get('username')?.hasError('duplicateUsername'))
      .toBeFalse();
  })
});

describe('AccountFormDialogComponent edit mode', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let mockDialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [ AccountFormDialogComponent ],
        imports: [
          ...testModules,
          AppModule
        ],
        providers: [
          MockDialogRef,
          {
            provide: MatDialogRef,
            useExisting: MockDialogRef
          },
          {
            provide: MAT_DIALOG_DATA,
            useValue: {account: alice}
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(AccountFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    mockDialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have usernameField correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeTrue();
  });

  it('should update a user', async (done) => {
    mockDialogRef.afterClosed().subscribe({
      next: value => {
        expect(value)
          .withContext('update esito')
          .toBeTrue();
        done();
      },
      error: done.fail
    });
    component.formGroup.setValue({
      username: 'bobby',
      administrator: true,
      password: 'banditi1899'
    });
    component.confirm();
  })

  it('should not find the user', () => {
    component.formGroup.setValue({
      username: 'Robert Paulsen',
      administrator: false,
      password: null
    });
    component.confirm();
    expect(component.formGroup.get('username')?.hasError('userNotFound'))
      .toBeTrue();
  })

  it('should enable and disable the toggle', () => {
    component.toggleChangePassword(true);
    expect(component.formGroup.get('password')?.enabled)
      .toBeTrue();
    component.toggleChangePassword(false);
    expect(component.formGroup.get('password')?.disabled)
      .toBeTrue();

  })
});

describe('AccountFormDialogComponent edit mode with same user', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let mockDialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [ AccountFormDialogComponent ],
        imports: [
          ...testModules,
          AppModule
        ],
        providers: [
          MockDialogRef,
          {
            provide: MatDialogRef,
            useExisting: MockDialogRef
          },
          {
            provide: MAT_DIALOG_DATA,
            useValue: {account: alice}
          }
        ]
      })
      .compileComponents();

    const loginService = TestBed.inject(LoginAbstractService);
    await loginService.login({
      username: 'alice',
      password: 'fossadeileoni',
      rememberMe: false
    }).toPromise();
    fixture = TestBed.createComponent(AccountFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    mockDialogRef = TestBed.inject(MatDialogRef);
  });

  it('should be correctly configured', async () => {
    expect(component.formGroup.get('username')?.disabled).toBeTrue();
  })
})
