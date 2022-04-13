import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountFormDialogComponent } from './account-form-dialog.component';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {sleep, testModules} from "../../../test/utils";
import {AccountSaveCommand} from "../../../model/admin-account/account-save-command";
import {AppModule} from "../../../app.module";
import {FakeAccountService} from "../../../test/account/fake-account.service";

let mockDialogRef: {
  value?: boolean,
  close: (_: any) => void
};

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

const invalidUpdateValidInsertUser: AccountSaveCommand = {
  username: 'clay',
  administrator: true,
  password: null
}

describe('AccountFormDialogComponent new mode', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;
  let mockAccountService: FakeAccountService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountFormDialogComponent ],
      imports: testModules,
      providers: [
        {
          provide: MatDialogRef,
          useValue: mockDialogRef
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
    mockDialogRef = {
      value: undefined,
      close: (value: any) => {
        mockDialogRef.value = value;
      }
    };
    mockAccountService = TestBed.inject(FakeAccountService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have usernameField correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeFalse();
  });

  it('should accept the data', () => {
    component.formGroup.setValue(validSaveUser);
    component.confirm();
    expect(mockDialogRef.value)
      .withContext('valid insert user')
      .toBeTruthy();
  });

  it('should cancel the operation', () => {
    component.cancel();
    expect(mockDialogRef.value).toBeUndefined();
  })
});

describe('AccountFormDialogComponent edit mode', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [ AccountFormDialogComponent ],
        imports: [
          ...testModules,
          AppModule
        ],
        providers: [
          {
            provide: MatDialogRef,
            useValue: mockDialogRef
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
    mockDialogRef = {
      value: undefined,
      close: (value: any) => {
        mockDialogRef.value = value;
      }
    };
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have usernameField correctly initialized', () => {
    const usernameField = component.formGroup.get('username');
    expect(usernameField?.disabled).toBeTrue();
  });
});
