import { ComponentFixture, TestBed } from '@angular/core/testing';
import { testModules } from 'src/app/test/utils';

import {ChartComponent} from "./chart.component";
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {FakeChartService} from "../../test/chart/fake-chart.service";

describe('ChartComponent', () => {
  let component: ChartComponent;
  let fixture: ComponentFixture<ChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        testModules
      ],
      declarations: [ ChartComponent ],
      providers: [
        {
          provide: ChartAbstractService,
          useExisting: FakeChartService
        }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartComponent);
    component = fixture.componentInstance;
    component.currentNode = {
      device: {
        expandable: true,
        id: 3,
        level: 0,
        name: 'testDevice'
      },
      expandable: false,
      id: 1,
      level: 1,
      name: 'testCharacteristic'
    }
    component.index = 1;
    fixture.detectChanges(); // trigger initial data binding
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('prova', () => {
    expect(component.index).toEqual(1);
  })
});
