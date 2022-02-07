import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import * as d3 from 'd3';
import { Subscription, interval, of } from 'rxjs';
import { concatMap } from 'rxjs/operators';
import { CharacteristicInfo } from './characteristic-info';

import { ChartPoint } from './chart-point';
import { ChartService } from './chart.service';
import { Location } from "@angular/common";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent implements OnInit, OnDestroy {

  info!: CharacteristicInfo;
  private points: ChartPoint[] = [];

  private updateSubscription?: Subscription;

  constructor(
    private route: ActivatedRoute,
    private chartService: ChartService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.createChart();
    this.setupInitialPoints();
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }

  setupInitialPoints() {
    const machine = Number(this.route.snapshot.paramMap.get('machine'));
    const characteristic = String(
      this.route.snapshot.paramMap.get('characteristic')
    );
    this.chartService
      .getInitialPoints(machine, characteristic)
      .subscribe(([info, points]) => {
        this.info = info;
        this.points = points;
        this.drawChart();
        this.subscribeToUpdates();
      });
  }

  subscribeToUpdates() {
    this.updateSubscription = interval(1000)
      .pipe(
        concatMap(() =>
          this.chartService.getNextPoints(
            this.info.machine.id,
            this.info.characteristic.code,
            this.points[this.points.length - 1].createdAtUtc
          )
        )
      )
      .subscribe((new_points) => {
        new_points.forEach((p) => this.points.push(p));
        this.points = this.points.slice(new_points.length);
        this.drawChart();
      });
  }

  // TODO: Nomi migliori e separati dai metodi?
  margin = { top: 10, right: 60, bottom: 30, left: 60 };
  get chartWidth(): number {
    const svgWidth = parseInt(d3.select('#d3svg').style('width'), 10);
    return svgWidth - this.margin.left - this.margin.right;
  }
  get chartHeight(): number {
    const svgHeight = parseInt(d3.select('#d3svg').style('height'), 10);
    return svgHeight - this.margin.top - this.margin.bottom;
  }

  // TODO?: Muovi i metodi in una classe separata dove sono sicuro che svg sia inizializzato
  private svg!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private xScale!: d3.ScaleTime<number, number, never>;
  private yScale!: d3.ScaleLinear<number, number, never>;

  createChart() {
    this.svg = d3
      .select('#d3svg')
      .append('svg')
      .append('g')
      .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`);

    this.xScale = d3.scaleTime().range([0, this.chartWidth]);
    this.yScale = d3.scaleLinear().range([this.chartHeight, 0]);

    this.svg
      .append('g')
      .attr('class', 'axis-x')
      .attr('transform', `translate(0, ${this.chartHeight})`);
    this.svg.append('g').attr('class', 'axis-y');

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
  }

  drawChart() {
    if (this.points.length == 0) return;
    const points = this.points;
    const { lowerLimit, upperLimit, average } = this.info.characteristic;
    const delta = Math.floor((upperLimit - lowerLimit) / 6);

    // TODO: includi i punti in ymin e ymax
    const [ymin, ymax] = d3.extent(points, (p) => p.value);
    this.xScale.domain(
      d3.extent(points, (p) => p.createdAtUtc * 1000) as [number, number]
    );
    this.yScale.domain([
      Math.min(lowerLimit - delta, ...(ymin ? [ymin] : [])),
      Math.max(upperLimit + delta, ...(ymax ? [ymax] : [])),
    ]);

    this.svg.select<SVGGElement>('.axis-x').call(d3.axisBottom(this.xScale));
    this.svg.select<SVGGElement>('.axis-y').call(d3.axisLeft(this.yScale));

    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('y1', y).attr('y2', y);
    };
    setGuideLine('.line-media', this.yScale(average));
    setGuideLine('.line-limite-min', this.yScale(lowerLimit));
    setGuideLine('.line-limite-max', this.yScale(upperLimit));

    let xp = (p: ChartPoint) => this.xScale(p.createdAtUtc * 1000);
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

  back() {
    this.location.back();
  }
}
