import { HttpClient } from '@angular/common/http';
import { HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { LoginService } from 'src/app/model/login/login.service';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { ModifyPwService } from 'src/app/model/modify-pw/modify-pw.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { gianniUser } from 'src/app/test/account/users';
import { MockDialogRef, testModules } from 'src/app/test/utils';

import { ModifyPwComponent } from './modify-pw.component';

describe('ModifyPwComponent', () => {
  let component: ModifyPwComponent;
  let fixture: ComponentFixture<ModifyPwComponent>;
  let mockDialogRef: MatDialogRef<any>;

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
        MockDialogRef,
        {
          provide: MAT_DIALOG_DATA,
          useValue: null
        },
      ]
    })
      .compileComponents();
    localStorage.clear();
    localStorage.setItem('username', 'Gianni')
    fixture = TestBed.createComponent(ModifyPwComponent);
    mockDialogRef = TestBed.inject(MatDialogRef);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should-create', () => {
    expect(component).toBeTruthy();
  });

  it('validità-campi-obbligatori', () => {
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

  it('password-nuova=corrente', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Gianni');
    form.controls['newPasswordRe'].setValue('Gianni');

    expect(form.controls['newPassword'].hasError('mustBeDifferent')).toBeTruthy();
  });

  it('password-nuova!=ripetizione', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Giannni');
    form.controls['newPasswordRe'].setValue('Gianni');
    component.confirm();
    expect(form.controls['newPasswordRe'].hasError('mismatch')).toBeTruthy();
  });

  it('campi-validi', () => {
    let form = component.modifyPw;
    form.controls['oldPassword'].setValue('Gianni');
    form.controls['newPassword'].setValue('Gianni1');
    form.controls['newPasswordRe'].setValue('Gianni1');

    expect(form.valid).toBeTruthy();
  });

  it('conferma-password', (done) => {
    mockDialogRef.afterClosed()
      .subscribe({
        next: data => {
          expect(data)
            .withContext('esito modifica password')
            .toEqual({})
        },
        error: done.fail,
        complete: done
      });
    component.modifyPw.controls['oldPassword'].setValue('Gianni');
    component.modifyPw.controls['newPassword'].setValue('Gianni1');
    component.modifyPw.controls['newPasswordRe'].setValue('Gianni1');
    component.confirm();
  });

  it('annulla-password', () => {
    mockDialogRef.afterClosed()
      .subscribe({
        next: value => {
          expect(value).toBeUndefined();
        }
      })
    component.cancel();
  })
});

describe('ModifyPwComponentIntegration', () => {
  let component: ModifyPwComponent;
  let fixture: ComponentFixture<ModifyPwComponent>;
  let mockDialogRef: MatDialogRef<any>;
  let httpClient: HttpClient;
  let modifyPwService: ModifyPwAbstractService;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ModifyPwComponent],
      providers: [
        {
          provide: ModifyPwAbstractService,
          useExisting: ModifyPwService
        },
        {
          provide: LoginAbstractService,
          useExisting: LoginService
        },
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        MockDialogRef,
        {
          provide: MAT_DIALOG_DATA,
          useValue: null
        },
      ]
    })
      .compileComponents();
    localStorage.clear();
    httpTestingController = TestBed.inject(HttpTestingController);
    modifyPwService = TestBed.inject(ModifyPwAbstractService);
    fixture = TestBed.createComponent(ModifyPwComponent);
    mockDialogRef = TestBed.inject(MatDialogRef);
    localStorage.setItem('username', 'Gianni');
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
  });

  it('modifypwservicePUT', async () => {
    component.modifyPw.controls['oldPassword'].setValue('Gianni');
    component.modifyPw.controls['newPassword'].setValue('Gianni2');
    component.modifyPw.controls['newPasswordRe'].setValue('Gianni2');
    component.confirm();
    const req = httpTestingController.expectOne(`/accounts/${gianniUser.username}/password`);
    expect(req.request.method).toEqual('PUT');
    req.flush({});
    httpTestingController.verify();
  })

  it('modifypwserviceerror', async () => {
    const error = {
      status: 401,
      statusText: 'wrongCurrentPassword'
    }
    component.modifyPw.controls['oldPassword'].setValue('Gianni3');
    component.modifyPw.controls['newPassword'].setValue('Gianni2');
    component.modifyPw.controls['newPasswordRe'].setValue('Gianni2');
    component.confirm();
    const req = httpTestingController.expectOne(`/accounts/${gianniUser.username}/password`);
    expect(req.request.method).toEqual('PUT');
    req.flush('401 error', error);
    httpTestingController.verify();
  })
});
