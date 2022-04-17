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
  private xAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private yAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  
  constructor(
    private chartService: ChartAbstractService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.createChart();
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }

  // TODO: Nomi migliori e separati dai metodi?
  margin = { top: 10, right: 60, bottom: 30, left: 60 };
  get chartWidth(): number {
    const svgWidth = parseInt(d3.select('.d3svg').style('width'), 10);
    return svgWidth- this.margin.left - this.margin.right;
  }
  get chartHeight(): number {
    const svgHeight = parseInt(d3.select('.d3svg').style('height'), 10);
    return svgHeight - this.margin.top - this.margin.bottom;
  }
  openDialog(){
    const dialogRef = this.dialog.open(DatePickerDialogComponent);
    dialogRef.afterClosed().subscribe(res => {
      if (res){
        this.updateSubscription?.unsubscribe();
        this.clearChart();
        this.chartService.getOldPoints(res.data.start, res.data.end)
          .subscribe({
            next: (points) => this.points = points
          });
        this.createChart();
      }
    });
  }
  // TODO?: Muovi i metodi in una classe separata dove sono sicuro che svg sia inizializzato
  private svg!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private xScale!: d3.ScaleTime<number, number, never>;
  private yScale!: d3.ScaleLinear<number, number, never>;

  createChart() {
      this.svg = d3
        .selectAll(`#d3svg${this.index}`)
        .append('svg')
        .append('g')
        .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`);
    let xxx = document.querySelector('.chart-container');
      this.xScale = d3.scaleTime().range([0, this.chartWidth]);
      this.yScale = d3.scaleLinear().range([this.chartHeight, 0]);

      this.xAxis = this.svg
        .append('g')
        .attr('class', 'axis-x')
        .attr('transform', `translate(0, ${this.chartHeight})`);

      this.yAxis = this.svg.append('g').attr('class', 'axis-y');

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
      this.svg
      .append("text")
      .attr("class", "title")
      .attr("x", this.chartWidth / 2)
      .attr("y", this.margin.top)
      .text(`${this.currentNode?.device.name} - ` + `${this.currentNode?.name}`);
      this.setupInitialPoints(this.currentNode?.device.id, this.currentNode?.id);
  }

  setupInitialPoints(deviceId: number, characteristicId: number) {
    if (this.points.length == 0){
    this.chartService
      .getInitialPoints(deviceId, characteristicId)
      .subscribe((points) => {
        this.points = points;
        this.drawChart();
        this.subscribeToUpdates();
      });
    } else{
      this.drawChart();
    }
  }

  drawChart() {
    if (this.points.length == 0) return;
    const points = this.points;
    this.chartService.getLimits(this.currentNode.device.id, this.currentNode.id)
      .subscribe({
        next: limit => this.limits = limit,
        error: () => console.log('ciao')  //TODO: da rivedere qui
      });
    const delta = Math.floor((this.limits.upperLimit - this.limits.lowerLimit) / 6);

    // TODO: includi i punti in ymin e ymax
    const [ymin, ymax] = d3.extent(points, (p) => p.value);

    this.xScale.domain(
      d3.extent(points, (p) => p.epoch) as [number, number]
    );

    this.yScale.domain([
      Math.min(this.limits.lowerLimit - delta, ...(ymin ? [ymin] : [])),
      Math.max(this.limits.upperLimit + delta, ...(ymax ? [ymax] : [])),
    ]);
    this.xAxis.call(d3.axisBottom(this.xScale));
    this.yAxis.call(d3.axisLeft(this.yScale));

    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('y1', y).attr('y2', y);
    };
    setGuideLine('.line-media', this.yScale(this.limits.mean));
    setGuideLine('.line-limite-min', this.yScale(this.limits.lowerLimit));
    setGuideLine('.line-limite-max', this.yScale(this.limits.upperLimit));

    let xp = (p: ChartPoint) => this.xScale(p.epoch);
    let yp = (p: ChartPoint) => this.yScale(p.value);
    this.svg.select('.chart-path').datum(points).attr('d', d3.line(xp, yp));
    // TODO: Evidenzia i punti anomali
    this.svg
      .select('.chart-points')
      .selectAll('circle')
      .data(points)
      .join(
        (enter) =>
          enter
            .append('circle')
            .attr('class', (p) =>
              p.anomalous ? 'circle-anomalo' : 'circle-normal'
            ),
        (update) =>
          update.attr('class', (p) =>
            p.anomalous ? 'circle-anomalo' : 'circle-normal'
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
            this.points[this.points.length - 1].epoch
          )
        )
      )
      .subscribe((new_points) => {
        new_points.forEach((p) => this.points.push(p));
        this.points = this.points.slice(new_points.length);
        this.drawChart();
      });
  }
  clearChart(): void{
    let svg = d3.select(`#d3svg${this.index}`);
    svg.selectAll("*").remove();
  }
}
