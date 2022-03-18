import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCharacteristicDialogComponent } from './update-characteristic-dialog.component';

describe('UpdateCharacteristicDialogComponent', () => {
  let component: UpdateCharacteristicDialogComponent;
  let fixture: ComponentFixture<UpdateCharacteristicDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCharacteristicDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
