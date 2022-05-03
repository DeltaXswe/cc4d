import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { testModules } from 'src/app/test/utils';
import { gianniUser } from 'src/app/test/account/users';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ LoginComponent ],
      providers: [
        {
          provide: AccountAbstractService,
          useExisting: FakeAccountService
        },
        {
          provide: LoginAbstractService,
          useExisting: FakeAccountService
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('validità campi obbligatori', () => {
    let username = component.loginForm.controls['username'];
    let password = component.loginForm.controls['password'];
    expect(username.valid).toBeFalsy();
    expect(password.valid).toBeFalsy();
    expect(component.loginForm.valid).toBeFalsy();

    username.setValue('');
    password.setValue('');
    
    expect(username.hasError('required')).toBeTruthy();
    expect(password.hasError('required')).toBeTruthy();
  })

  it('validità campo password>6caratteri', () => {
    let password = component.loginForm.controls['password'];

    password.setValue('ciao');

    expect(component.loginForm.valid).toBeFalsy();
    expect(password.hasError('minlength')).toBeTruthy();
  })

  it('form valido con input validi', () => {
    let username = component.loginForm.controls['username'];
    let password = component.loginForm.controls['password'];

    username.setValue(gianniUser.username);
    password.setValue(gianniUser.password);

    expect(component.loginForm.valid).toBeTruthy();
  })
});
