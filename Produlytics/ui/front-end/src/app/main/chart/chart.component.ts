import { AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';

import * as d3 from 'd3';
import { Subscription, interval } from 'rxjs';
import { concatMap } from 'rxjs/operators';

import { ChartPoint } from '../../model/chart/chart-point';
import { ChartAbstractService } from "../../model/chart/chart-abstract.service";
import { CharacteristicNode } from '../device-selection/selection-data-source/characteristic-node';
import { Limits } from '../../model/chart/limits';
import { MatDialog } from '@angular/material/dialog';
import { DatePickerDialogComponent } from '../date-picker-dialog/date-picker-dialog.component';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent implements OnInit, OnDestroy, AfterViewInit {

  @Input()
  currentNode!: CharacteristicNode;

  @Input()
  index: number = 0;

  private limits!: Limits;
  private points: ChartPoint[] = [];
  private updateSubscription?: Subscription;
  private nextNew: number = 0;
  private xAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private yAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  constructor(
    private chartService: ChartAbstractService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.getData(this.currentNode?.device.id, this.currentNode?.id);
    if (this.points){
      this.createChart();
      this.subscribeToUpdates();
    }
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }

  margin = { top: 10, right: 60, bottom: 60, left: 60 };
  get chartWidth(): number {
    if (this.points.length < 100) {
      return 1100;
    } else {
      return 1100 + this.points.length*10;
    }
  }
  get chartHeight(): number {
    const svgHeight = parseInt(d3.select(`#d3svg${this.index}`).style('height'), 10);
    return svgHeight - this.margin.top - this.margin.bottom;
  }

  openDialog(){
    const dialogRef = this.dialog.open(DatePickerDialogComponent);
    dialogRef.afterClosed().subscribe(res => {
      if (res){
        this.updateSubscription?.unsubscribe();
        this.clearChart();
        this.chartService.getOldPoints(res[0], res[1], this.currentNode?.device.id, this.currentNode?.id)
          .subscribe({
            next: (points) => this.points = points.detections
          });
        this.createChart();
        this.drawChart();
      }
    });
  }

  private svg!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private svgy!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private xScale!: d3.ScaleTime<number, number, never>;
  private yScale!: d3.ScaleLinear<number, number, never>;

  createChart() {
      this.svg = d3
        .select(`#d3svg${this.index}`)
        .style('width', this.chartWidth)
        .style('height', 550 + 'px')
        .append('g')
        .attr('transform', `translate(0, ${this.margin.top})`);
      
      this.svgy = d3
        .select(`#vertical${this.index}`)
        .style('width', 100 + 'px')
        .style('height', 550 + 'px')
        .append('g')
        .attr('transform', `translate(30, ${this.margin.top})`);

      this.xScale = d3.scaleTime().range([0, this.chartWidth]);
      this.yScale = d3.scaleLinear().range([this.chartHeight, 0]);

      this.xAxis = this.svg.append('g')
        .attr("transform", `translate(0, ${this.chartHeight})`);
      
      this.yAxis = this.svgy.append('g');
      
      const createGuideLine = (cls: string) => {
        this.svg
          .append('line')
          .attr('class', cls)
          .attr('x1', 0)
          .attr('x2', this.chartWidth);
      };
      createGuideLine('line-media');
      createGuideLine('line-limite line-limite-min');
      createGuideLine('line-limite line-limite-max');
      
      this.svg.append('path').attr('class', 'chart-path');
      this.svg.append('g').attr('class', 'chart-points');

      this.drawChart();
  }

  getData(deviceId: number, characteristicId: number){

    this.chartService
      .getInitialPoints(deviceId, characteristicId)
      .subscribe((points) => {
        this.points = points.detections;
        this.nextNew = points.nextNew;
      });

      this.chartService.getLimits(this.currentNode.device.id, this.currentNode.id)
      .subscribe({
        next: limits => this.limits = limits,
        error: () => console.log('ciao')  //TODO: da rivedere qui
      });
  }

  drawChart() {
    if (this.points.length == 0) {
      return;
    }
    
    const delta = Math.floor((this.limits.upperLimit - this.limits.lowerLimit) / 6);

    const [ymin, ymax] = d3.extent(this.points, (p) => p.value);

    this.xScale.domain(
      d3.extent(this.points, (p) => new Date(p.creationTime)) as [Date, Date]
    );

    this.yScale.domain([
      Math.min(this.limits.lowerLimit - delta, ...(ymin ? [ymin] : [])),
      Math.max(this.limits.upperLimit + delta, ...(ymax ? [ymax] : [])),
    ]);
    
    this.xAxis
      .call(d3.axisBottom(this.xScale))
      .select('.tick')
      .attr('transform', `translate(10, 0)`);

    this.yAxis.call(d3.axisLeft(this.yScale));
    
    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('y1', y).attr('y2', y);
    };
    setGuideLine('.line-media', this.yScale(this.limits.mean));
    setGuideLine('.line-limite-min', this.yScale(this.limits.lowerLimit));
    setGuideLine('.line-limite-max', this.yScale(this.limits.upperLimit));

    let xp = (p: ChartPoint) => this.xScale(p.creationTime);
    let yp = (p: ChartPoint) => this.yScale(p.value);
    this.svg.select('.chart-path').datum(this.points).attr('d', d3.line(xp, yp));
    // TODO: Evidenzia i punti anomali
    this.svg
      .select('.chart-points')
      .selectAll('circle')
      .data(this.points)
      .join(
        (enter) =>
          enter
            .append('circle')
            .attr('class', (p) =>
              p.outlier ? 'circle-anomalo' : 'circle-normal'
            ),
        (update) =>
          update.attr('class', (p) =>
            p.outlier ? 'circle-anomalo' : 'circle-normal'
          ),
        (exit) => exit.remove()
      )
      .attr('cx', xp)
      .attr('cy', yp);
  }

  subscribeToUpdates() {
    this.updateSubscription = interval(1000)
      .pipe(
        concatMap(() =>
          this.chartService.getNextPoints(
            this.currentNode.device.id,
            this.currentNode.id,
            this.nextNew
          )
        )
      )
      .subscribe((new_points) => {
        new_points.detections.forEach((p) => this.points.push(p));
        if(this.points.length > 100) {
          this.points = this.points.slice(this.points.length - 100);
        }
        this.nextNew = new_points.nextNew;
        this.drawChart();
      });
  }
  refresh(){
    this.clearChart();
    this.getData(this.currentNode?.device.id, this.currentNode?.id);
    this.createChart();
    this.subscribeToUpdates();
  }
  clearChart(): void{
    let svg = d3.select(`#d3svg${this.index}`);
    let svgy = d3.select(`#vertical${this.index}`)
    this.updateSubscription?.unsubscribe();
    this.points = [];
    svg.selectAll("*").remove();
    svgy.selectAll("*").remove();
  }
}
