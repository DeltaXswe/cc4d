import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { testModules } from 'src/app/test/utils';
import { ToolbarComponent } from './toolbar.component';
import { Location } from '@angular/common';
import {expect} from "@angular/flex-layout/_private-utils/testing";


fdescribe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let loginService: LoginAbstractService;
  let fixture: ComponentFixture<ToolbarComponent>;

  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ ToolbarComponent ],
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
    localStorage.clear();
    localStorage.setItem('username', 'Cosimo');
    localStorage.setItem('accessToken', 'Cosimo');
    //localStorage.setItem('admin', 'Cosimo');
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    loginService = TestBed.inject(LoginAbstractService);
    location = TestBed.inject(Location);
    fixture.detectChanges();
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('dovrebbe essere loggato', () => {
    expect(component.isLogged()).toBeTrue();
  });

  it('gestione nascosta a non-admin',() => {
    const menu2 = fixture.debugElement.nativeElement.querySelector('#username');
    menu2.click();
    const gestioneMacchine = fixture.nativeElement.parentNode.querySelector('#gm');
    expect(gestioneMacchine).toBeNull();
  });

  it('router home', () =>{
    let home = fixture.debugElement.nativeElement.querySelector('#produlytics');
    location.go('gestione-macchine');
    home.click();
    expect(location.path()).toBe('/');
  });

  it('apri dialog modifica password', () => {
    const openDialogSpy = spyOn(component, 'openPwDialog');
    const menu = fixture.debugElement.nativeElement.querySelector('#username');
    menu.click();
    const modifyPw = fixture.nativeElement.parentNode.querySelector('#pw');
    modifyPw.click();
    expect(openDialogSpy).toHaveBeenCalled();
  });

  it('esegui il logout', () => {
    const spy = spyOn(loginService, 'logout');
    component.logout();
    expect(spy).toHaveBeenCalled();
  });
});
