import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsComponent } from './accounts.component';
import {LoginAbstractService} from "../../model/login/login-abstract.service";
import {FakeAccountService} from "../../test/account/fake-account.service";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MockDialog, testModules} from "../../test/utils";
import {MatDialog} from "@angular/material/dialog";

describe('AccountsComponent', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountsComponent ],
      imports: [
        ...testModules
      ],
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
        {
          provide: MatDialog,
          useExisting: MockDialog
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(AccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
