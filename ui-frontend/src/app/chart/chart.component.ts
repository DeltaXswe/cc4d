import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import * as d3 from 'd3';
import { Subscription, interval, of } from 'rxjs';
import { concatMap, tap } from 'rxjs/operators';

import { ChartPoint } from './chart-point';
import { ChartService } from './chart.service';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
})
export class ChartComponent implements OnInit, OnDestroy {
  constructor(
    private route: ActivatedRoute,
    private chartService: ChartService
  ) {}

  private macchina = Number(this.route.snapshot.paramMap.get('macchina'));
  caratteristica = String(this.route.snapshot.paramMap.get('caratteristica'));
  nome_macchina!: string;
  private media!: number;
  private limite_min!: number;
  private limite_max!: number;
  private punti: ChartPoint[] = [];

  private updateSubscription?: Subscription;

  ngOnInit(): void {
    this.createChart();
    this.setupInitialPoints();
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }

  setupInitialPoints() {
    this.chartService
      .getInitialPoints(this.macchina, this.caratteristica)
      .subscribe((data) => {
        this.nome_macchina = data.nome_macchina;
        this.media = data.media;
        this.limite_min = data.limite_min;
        this.limite_max = data.limite_max;
        this.punti = data.rilevazioni;
        this.drawChart();
        this.subscribeToUpdates();
      });
  }

  subscribeToUpdates() {
    this.updateSubscription = interval(1000)
      .pipe(
        concatMap(() =>
          this.chartService.getNextPoints(
            this.macchina,
            this.caratteristica,
            this.punti[this.punti.length - 1].creato_il_utc
          )
        )
      )
      .subscribe((rilevazioni) => {
        rilevazioni.forEach((p) => this.punti.push(p));
        this.punti = this.punti.slice(rilevazioni.length);
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
    if (this.punti.length == 0) return;
    const punti = this.punti;

    const delta = Math.floor((this.limite_max - this.limite_min) / 6);

    // TODO: includi i punti in ymin e ymax
    const [ymin, ymax] = d3.extent(punti, (p) => p.valore);
    this.xScale.domain(
      d3.extent(punti, (p) => p.creato_il_utc * 1000) as [number, number]
    );
    this.yScale.domain([
      Math.min(this.limite_min - delta, ...(ymin ? [ymin] : [])),
      Math.max(this.limite_max + delta, ...(ymax ? [ymax] : [])),
    ]);

    this.svg.select<SVGGElement>('.axis-x').call(d3.axisBottom(this.xScale));
    this.svg.select<SVGGElement>('.axis-y').call(d3.axisLeft(this.yScale));

    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('y1', y).attr('y2', y);
    };
    setGuideLine('.line-media', this.yScale(this.media));
    setGuideLine('.line-limite-min', this.yScale(this.limite_min));
    setGuideLine('.line-limite-max', this.yScale(this.limite_max));

    let xp = (p: ChartPoint) => this.xScale(p.creato_il_utc * 1000);
    let yp = (p: ChartPoint) => this.yScale(p.valore);
    this.svg.select('.chart-path').datum(punti).attr('d', d3.line(xp, yp));

    // TODO: Evidenzia i punti anomali
    this.svg
      .select('.chart-points')
      .selectAll('circle')
      .data(punti)
      .join(
        (enter) =>
          enter
            .append('circle')
            .attr('class', (p) =>
              p.anomalo ? 'circle-anomalo' : 'circle-normal'
            ),
        (update) =>
          update.attr('class', (p) =>
            p.anomalo ? 'circle-anomalo' : 'circle-normal'
          ),
        (exit) => exit.remove()
      )
      .attr('cx', xp)
      .attr('cy', yp);
  }
}
