import { TestBed } from '@angular/core/testing';

import { FakeChartService } from './fake-chart.service';

describe('FakeChartService', () => {
  let service: FakeChartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FakeChartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
