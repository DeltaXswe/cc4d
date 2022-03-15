import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyPwComponent } from './modify-pw.component';

describe('ModifyPwComponent', () => {
  let component: ModifyPwComponent;
  let fixture: ComponentFixture<ModifyPwComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyPwComponent ]
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
});
