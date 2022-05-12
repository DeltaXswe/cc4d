import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import {MockDialogAlwaysConfirm, testModules} from 'src/app/test/utils';
import { ToolbarComponent } from './toolbar.component';
import { Location } from '@angular/common';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import {MatDialog} from "@angular/material/dialog";
import {LoginService} from "../../model/login/login.service";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpClient} from "@angular/common/http";
import {of} from "rxjs";


describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let loginService: LoginAbstractService;
  let fixture: ComponentFixture<ToolbarComponent>;
  let httpTestingController: HttpTestingController;
  let httpClient: HttpClient;

  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        testModules
      ],
      declarations: [ ToolbarComponent ],
      providers: [
        MockDialogAlwaysConfirm,
        {
          provide: MatDialog,
          useExisting: MockDialogAlwaysConfirm
        },
        {
          provide: LoginAbstractService,
          useExisting: LoginService
        }
      ]
    })
    .compileComponents();
    localStorage.clear();
    localStorage.setItem('username', 'Cosimo');
    localStorage.setItem('accessToken', 'Cosimo');
    //localStorage.setItem('admin', 'Cosimo');
    httpTestingController = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    loginService = TestBed.inject(LoginAbstractService);
    location = TestBed.inject(Location);
    component = fixture.componentInstance;
    fixture.detectChanges();
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

  it('router home', fakeAsync(() =>{
    let home = fixture.debugElement.nativeElement.querySelector('#produlytics');
    tick();
    location.go('gestione-macchine');
    home.click();
    tick();
    expect(location.path()).toBe('/');
  }));

  it('apri dialog modifica password', () => {
    const spyDialog = spyOn(component.dialog, 'open').and.returnValue({
      afterClosed: () =>
        of({data: {}})
    } as any);
    component.openPwDialog();
    expect(spyDialog).toHaveBeenCalled();
  });

  it('esegui il logout',  () => {
    component.logout();
    const req = httpTestingController.expectOne('/logout');
    expect(req.request.method).toEqual('POST');
    req.flush({});
    httpTestingController.verify();
  });
});
