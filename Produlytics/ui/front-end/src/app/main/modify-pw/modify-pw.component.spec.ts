import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { MockDialogRef, testModules } from 'src/app/test/utils';

import { ModifyPwComponent } from './modify-pw.component';

describe('ModifyPwComponent', () => {
  let component: ModifyPwComponent;
  let fixture: ComponentFixture<ModifyPwComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ ModifyPwComponent ],
      providers: [
        {
          provide: ModifyPwAbstractService,
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
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyPwComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('validità campi obbligatori', () => {
    let form = component.modifyPw;
    expect(form.valid).toBeFalsy();

    form.controls['oldPassword'].setValue('');
    form.controls['newPassword'].setValue('');
    form.controls['newPasswordRe'].setValue('');
    expect(form.controls['oldPassword'].hasError('required')).toBeTruthy();
    expect(form.controls['newPassword'].hasError('mustBeDifferent')).toBeTruthy();
    expect(form.controls['newPasswordRe'].hasError('required')).toBeTruthy();
  });

  it('validita password 6 caratteri', () => {
    let form = component.modifyPw;

    form.controls['oldPassword'].setValue('Paol');
    form.controls['newPassword'].setValue('Paolo');
    form.controls['newPasswordRe'].setValue('Paolo');

    expect(form.controls['newPassword'].hasError('minlength')).toBeTruthy();
  })

  it('password corrente uguale a quella nuova', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Gianni');
    form.controls['newPasswordRe'].setValue('Gianni');

    expect(form.controls['newPassword'].hasError('mustBeDifferent')).toBeTruthy();
  });

  it('password nuova diversa da ripetizione', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Giannni');
    form.controls['newPasswordRe'].setValue('Gianni');

    expect(form.controls['newPasswordRe'].hasError('mismatch')).toBeTruthy();
  });

  it('campi validi', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Gianni1');
    form.controls['newPasswordRe'].setValue('Gianni1');

    expect(form.valid).toBeTruthy();
  });
});
