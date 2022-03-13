import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountFormDialogComponent } from './account-form-dialog.component';

describe('AccountFormDialogComponent', () => {
  let component: AccountFormDialogComponent;
  let fixture: ComponentFixture<AccountFormDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountFormDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
