import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCharacteristicDialogComponent } from './new-characteristic-dialog.component';

describe('NewCharacteristicDialogComponent', () => {
  let component: NewCharacteristicDialogComponent;
  let fixture: ComponentFixture<NewCharacteristicDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCharacteristicDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
