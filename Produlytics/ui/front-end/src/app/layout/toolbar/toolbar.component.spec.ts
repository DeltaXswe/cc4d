import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountAbstractService } from 'src/app/model/admin-account/account-abstract.service';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { FakeAccountService } from 'src/app/test/account/fake-account.service';
import { testModules } from 'src/app/test/utils';
import { gianniUser, cosimoUser } from 'src/app/test/account/users';
import { ToolbarComponent } from './toolbar.component';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';
import { Location } from '@angular/common';


describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let fixture: ComponentFixture<ToolbarComponent>;
  let router: Router;
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
        },
        {
          provide: Router,
          useValue:{
            url: '/'
          }
        }
      ]
    })
    .compileComponents();
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('gestione nascosta a non-admin', () => {
    const gestioneMacchine = fixture.debugElement.nativeElement.querySelector('#gm');
    expect(gestioneMacchine).toBeNull();
  })

  it('router home', () =>{
    let home = fixture.debugElement.query(By.css('#gm')).nativeElement;
    home.click();
    expect(location.path()).toBe('/');
  });

  /* it('apri dialog modifica password', fakeAsync(() => {
    component.openPwDialog();
    tick();
    expect
  })) */
});
