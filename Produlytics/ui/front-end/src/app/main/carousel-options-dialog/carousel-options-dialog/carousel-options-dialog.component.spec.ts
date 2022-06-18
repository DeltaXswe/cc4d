import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselOptionsDialogComponent } from './carousel-options-dialog.component';

describe('CarouselOptionsDialogComponent', () => {
  let component: CarouselOptionsDialogComponent;
  let fixture: ComponentFixture<CarouselOptionsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarouselOptionsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarouselOptionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
