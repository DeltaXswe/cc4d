import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacteristicFormComponent } from './characteristic-form.component';

describe('CharacteristicFormComponent', () => {
  let component: CharacteristicFormComponent;
  let fixture: ComponentFixture<CharacteristicFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CharacteristicFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CharacteristicFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
