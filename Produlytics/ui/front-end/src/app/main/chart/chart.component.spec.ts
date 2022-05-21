import {ComponentFixture, fakeAsync, inject, TestBed, tick} from '@angular/core/testing';
import {MockDialogAlwaysConfirm, MockDialogRef, testModules} from 'src/app/test/utils';

import {ChartComponent} from "./chart.component";
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {FakeChartService} from "../../test/chart/fake-chart.service";
import {ChartService} from "../../model/chart/chart.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EMPTY, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import any = jasmine.any;
import {some} from "d3";
import {ChartPointReturn} from "../../model/chart/chart-point-return";

describe('ChartComponent', () => {
  let component: ChartComponent;
  let fixture: ComponentFixture<ChartComponent>;
  let chartService: ChartAbstractService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        testModules
      ],
      declarations: [ChartComponent],
      providers: [
        {
          provide: ChartAbstractService,
          useExisting: FakeChartService
        }
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(ChartComponent);
    chartService = TestBed.inject(ChartAbstractService);
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

    fixture.detectChanges();
  });

  it('should-create', () => {
    expect(component).toBeTruthy();
  });

  it('ngAfterViewInit', async () => {
    const spyData = spyOn(component, 'getData');
    const spyCreate = spyOn(component, 'createChart');
    const spySubscribe = spyOn(component, 'subscribeToUpdates');
    component.ngAfterViewInit();
    expect(spyData).toHaveBeenCalledWith(component.currentNode.device.id, component.currentNode.id)
    expect(spyCreate).toHaveBeenCalled()
    expect(spySubscribe).toHaveBeenCalled()
  });

  it('createChart', () => {
    const spyDraw = spyOn(component, 'drawChart');
    let elements = fixture.nativeElement.querySelectorAll('.line-media');
    component.ngAfterViewInit();
    expect(elements).not.toBeNull();
    expect(spyDraw).toHaveBeenCalled();
  });

  it('clearChart', async () => {
    component.clearChart();
    fixture.detectChanges();
    let gElements = fixture.nativeElement.parentNode.querySelectorAll('.line-media');
    expect(gElements.length).toEqual(0);
  });

  it('dialog-datepicker', () => {
    const spyDialog = spyOn(component.dialog, 'open').and.returnValue({
      afterClosed: () =>
        of({data: {start: 100000, end: 1500000}})
    } as any);
    component.openDialog();
    expect(spyDialog).toHaveBeenCalled();
  });

  it('refresh', () => {
    const spyClear = spyOn(component, "clearChart");
    const spyData = spyOn(component, "getData");
    const spyCreate = spyOn(component, "createChart");
    const spySubscribe = spyOn(component, "subscribeToUpdates");
    component.refresh();
    expect(spyClear).toHaveBeenCalled();
    expect(spyData).toHaveBeenCalled();
    expect(spyCreate).toHaveBeenCalled();
    expect(spySubscribe).toHaveBeenCalled();
  })
});

describe('ChartComponentIntegration', () => {
  let component: ChartComponent;
  let fixture: ComponentFixture<ChartComponent>;
  let mockDialogRef: MatDialogRef<any>;
  let chartService: ChartAbstractService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        testModules
      ],
      declarations: [ChartComponent],
      providers: [
        {
          provide: ChartAbstractService,
          useExisting: ChartService
        },
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        MockDialogRef,
        {
          provide: MAT_DIALOG_DATA,
          useValue: null
        },
      ]
    })
      .compileComponents();
    httpTestingController = TestBed.inject(HttpTestingController);
    chartService = TestBed.inject(ChartAbstractService);
    fixture = TestBed.createComponent(ChartComponent);
    httpClient = TestBed.inject(HttpClient);
    mockDialogRef = TestBed.inject(MatDialogRef);
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
    fixture.detectChanges();
  });

  it('should-create', () => {
    expect(component).toBeTruthy();
  });

  it('getData', () => {
    const reqLimits = httpTestingController.expectOne(`/devices/3/characteristics/1/limits`);
    expect(reqLimits.request.method).toEqual('GET');
    reqLimits.flush({});

    const reqPoints = httpTestingController
      .expectOne((request) => request.url === "/devices/3/characteristics/1/detections");
    expect(reqPoints.request.method).toEqual('GET');
    reqPoints.flush({});
    httpTestingController.verify();
  });

  it('subscribeToUpdates', fakeAsync (() => {
    tick();
    component.clearChart();
    fixture.detectChanges();
    const reqPoints1 = httpTestingController
      .expectOne((request) => request.url === "/devices/3/characteristics/1/detections");
    expect(reqPoints1.request.method).toEqual('GET');
    reqPoints1.flush({
      detections: [
        {
          creationTime: 11000000,
          value: 500,
          outlier: false
        }],
      nextOld: 1000000,
      nextNew: 1000000
    });
    const reqLimits = httpTestingController.expectOne(`/devices/3/characteristics/1/limits`);
    expect(reqLimits.request.method).toEqual('GET');
    reqLimits.flush({});
    tick();

    component.subscribeToUpdates();
    tick(1000);
    const reqPoints2 = httpTestingController
      .expectOne((request) => request.url === "/devices/3/characteristics/1/detections");
    expect(reqPoints2.request.method).toEqual('GET');
    reqPoints2.flush({
      detections: [
        {
          creationTime: 11000000,
          value: 500,
          outlier: false
        }],
      nextOld: 1000000,
      nextNew: 1000000
    });
    component.clearChart();
    tick();
    httpTestingController.verify();
  }));
});
