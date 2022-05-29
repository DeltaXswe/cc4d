import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountFormDialogComponent } from './account-form-dialog.component';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {MockDialogRef, testModules} from "../../../test/utils";
import {AccountSaveCommand} from "../../../model/admin-account/account-save-command";
import {AppModule} from "../../../app.module";
import {FakeAccountService} from "../../../test/account/fake-account.service";
import {SaveAccountAbstractService} from "../../../model/admin-account/save-account-abstract.service";
import {LoginAbstractService} from "../../../model/login/login-abstract.service";
import {SaveAccountService} from "../../../model/admin-account/save-account.service";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {LoginService} from "../../../model/login/login.service";

const alice: Account = {
  username: 'alice',
  administrator: true,
  archived: false
};

const validSaveUser: AccountSaveCommand = {
  username: 'janiel',
  administrator: false,
  password: 'danuary'
};

describe('AccountFormDialogComponent Create Mode', () => {
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

  it('should have username field correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeFalse();
  });

  it('conferma-nuovo-utente', (done) => {
    mockDialogRef.afterClosed()
      .subscribe({
        next: value => {
          expect(value)
            .withContext('esito inserimento')
            .toBeTrue();
        },
        error: done.fail,
        complete: done
      });
    component.formGroup.setValue(validSaveUser);
    component.confirm();
  });

  it('rifiuto-nuovo-utente-username', () => {
    component.formGroup.setValue({
      username: alice.username,
      administrator: false,
      password: 'sbarlafus'
    });
    component.confirm();
    expect(component.formGroup.get('username')?.hasError('duplicateUsername'))
      .toBeTrue();
  })

  it('rifiuto-nuovo-utente-password', () => {
    component.formGroup.setValue({
      username: 'carl',
      administrator: false,
      password: 'carl'
    })
    component.confirm();
    expect(component.formGroup.get('password')?.hasError('invalidPassword'))
      .toBeTrue();
  })

  it('annulla-salva-utente', () => {
    mockDialogRef.afterClosed().subscribe({
      next: value => {
        expect(value).toBeUndefined();
      }
    })
    component.cancel();
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

describe('AccountFormDialogComponent Update Mode', () => {
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

  it('should have username field correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeTrue();
  });

  it('conferma-modifica-utente', (done) => {
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
      username: 'bob',
      administrator: true,
      password: 'banditi1899'
    });
    component.confirm();
  })

  it('rifiuta-modifica-utente', () => {
    component.formGroup.setValue({
      username: 'Robert Paulsen',
      administrator: false,
      password: null
    });
    component.confirm();
    expect(component.formGroup.get('username')?.hasError('userNotFound'))
      .toBeTrue();
  })

  it('abilita-cambio-password', () => {
    component.toggleChangePassword(true);
    expect(component.formGroup.get('password')?.enabled)
      .toBeTrue();
    component.toggleChangePassword(false);
    expect(component.formGroup.get('password')?.disabled)
      .toBeTrue();

  })
});

describe('AccountFormDialogComponent Update Logged User', () => {
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
  });
});

describe('AccountFormDialog New Integration', () => {

  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let saveAccountService: SaveAccountAbstractService;
  let matDialogRef: MatDialogRef<any>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [AccountFormDialogComponent],
        imports: testModules,
        providers: [
          MatDialog,
          {
            provide: MatDialogRef,
            useValue: {
              close: (_?: any) => {}
            }
          },
          SaveAccountService,
          {
            provide: SaveAccountAbstractService,
            useExisting: SaveAccountService
          },
          LoginService,
          {
            provide: LoginAbstractService,
            useExisting: LoginService
          },
          {
            provide: MAT_DIALOG_DATA,
            useValue: null
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(AccountFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    saveAccountService = TestBed.inject(SaveAccountAbstractService);
    matDialogRef = TestBed.inject(MatDialogRef);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('save-account-should-create', () => {
    expect(component).toBeTruthy();
  });

  it('new-account-confirm', () => {
    component.formGroup.setValue(validSaveUser);
    component.confirm();
    const req = httpTestingController.expectOne('admin/accounts');
    expect(req.request.method).toEqual('POST');
    req.flush({});
    httpTestingController.verify();
  });
});

describe('AccountFormDialog Update Integration', () => {

  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let saveAccountService: SaveAccountAbstractService;
  let matDialogRef: MatDialogRef<any>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [AccountFormDialogComponent],
        imports: testModules,
        providers: [
          MatDialog,
          {
            provide: MatDialogRef,
            useValue: {
              close: (_?: any) => {}
            }
          },
          SaveAccountService,
          {
            provide: SaveAccountAbstractService,
            useExisting: SaveAccountService
          },
          LoginService,
          {
            provide: LoginAbstractService,
            useExisting: LoginService
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
    saveAccountService = TestBed.inject(SaveAccountAbstractService);
    matDialogRef = TestBed.inject(MatDialogRef);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('update-account-confirm', () => {
    component.formGroup.setValue({
      username: 'alice',
      administrator: true,
      password: 'banditi1899'
    });
    component.confirm();
    const req = httpTestingController.expectOne('admin/accounts/alice');
    expect(req.request.method).toEqual('PUT');
    req.flush({});
    httpTestingController.verify();
  });
});
