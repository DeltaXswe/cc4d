import { AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';

import * as d3 from 'd3';
import { Subscription, interval } from 'rxjs';
import { concatMap } from 'rxjs/operators';

import { ChartPoint } from '../../model/chart/chart-point';
import { ChartAbstractService } from "../../model/chart/chart-abstract.service";
import { CharacteristicNode } from '../selection/selection-data-source/characteristic-node';
import { Limits } from '../../model/chart/limits';
import { MatDialog } from '@angular/material/dialog';
import { DatePickerDialogComponent } from '../date-picker-dialog/date-picker-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    public dialog: MatDialog,
    private matSnackBar: MatSnackBar
  ) { }

  ngOnInit(): void {}

  /**
   * Chiama tutti i metodi necessari a creare il grafico dopo che la view
   * è stata inizilizzata.
   */
  ngAfterViewInit(): void {
    console.log(this.currentNode);
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

  /**
   * @returns la larghezza del grafico.
   * Se il nuero di punti da rappresentare è minore o uguale a 100, la imposta
   * in modo da occupare tutto il div che contiene il grafico, altrimenti lo allunga
   * permettendo di uno scroll orizzontale.
   */
  get chartWidth(): number {
    if (this.points.length <= 100) {
      return 1100;
    } else {
      return 1100 + this.points.length*10;
    }
  }

  /**
   * @returns la altezza del grafico.
   */
  get chartHeight(): number {
    const svgHeight = parseInt(d3.select(`#d3svg${this.index}`).style('height'), 10);
    return svgHeight - this.margin.top - this.margin.bottom;
  }
  /**
   * Apre {@link DatePickerDialogComponent} e resta in attesa dei dati.
   * In caso di risposta tenta di ottenere le rilevazioni comprese fra gli estremi
   * temporali ottenuti, tramite un service che implementa {@link ChartAbstractService}.
   * In caso di successo cancella la corrente rappresentazione e ne crea un'altra
   * con le rilevazioni ottenute.
   */
  openDialog(): void{
    const dialogRef = this.dialog.open(DatePickerDialogComponent, { panelClass: "date" });
    dialogRef.afterClosed().subscribe(res => {
      if (res){
        this.updateSubscription?.unsubscribe();
        this.clearChart();
        this.chartService.getOldPoints(res.start, res.end, this.currentNode?.device.id, this.currentNode?.id)
          .subscribe({
            next: (points) => {
              this.points = points.detections
              this.createChart();
              this.drawChart();
            },
            error: () => this.matSnackBar.open('Caratteristica non trovata', 'Ok')
          });
      }
    });
  }

  private svg!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private svgy!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private xScale!: d3.ScaleTime<number, number, never>;
  private yScale!: d3.ScaleLinear<number, number, never>;

  /**
   * Inizializza gli attributi della classe, chiamando poi {@link drawChart()} per
   * disegnare il grafico.
   */
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
      createGuideLine('line-zona-c-up');
      createGuideLine('line-zona-c-down');
      createGuideLine('line-zona-b-up');
      createGuideLine('line-zona-b-down');
      
      
      this.svg.append('path').attr('class', 'chart-path');
      this.svg.append('g').attr('class', 'chart-points');
      this.drawChart();
  }

  /**
   * Tramite un service che implementa {@link ChartAbstractService}, cerca di
   * ottenere prima i punti iniziali e poi i limiti e la media del grafico.
   * In caso di errore notifica l'utente con {@link MatSnackBar}
   * @param deviceId l'identificativo della macchina
   * @param characteristicId l'identificativo della caratteristica
   */
  getData(deviceId: number, characteristicId: number){
    this.chartService
      .getInitialPoints(deviceId, characteristicId)
      .subscribe({
        next: (points) => {
        this.points = points.detections;
        this.nextNew = points.nextNew;
        },
        error: () => this.matSnackBar.open('Caratteristica non trovata', 'Ok')
      });

      this.chartService.getLimits(this.currentNode.device.id, this.currentNode.id)
      .subscribe({
        next: limits => this.limits = limits,
        error: () => this.matSnackBar.open('Caratteristica non trovata', 'Ok')
      });
  }

  /**
   * Rappresenta gli elementi precedentemente inizializzati da {@link createChart()}
   */
  drawChart(): void {
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
      .call(d3.axisBottom(this.xScale).ticks(d3.timeSecond.every(5)))
      .select('.tick')
      .attr('transform', `translate(10, 0)`);

    this.yAxis.call(d3.axisLeft(this.yScale));

    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('y1', y).attr('y2', y);
    };
    setGuideLine('.line-media', this.yScale(this.limits.mean));
    setGuideLine('.line-limite-min', this.yScale(this.limits.lowerLimit));
    setGuideLine('.line-limite-max', this.yScale(this.limits.upperLimit));

    const deviation = (this.limits.upperLimit - this.limits.mean)/3;

    setGuideLine('.line-zona-c-up', this.yScale(this.limits.mean + deviation));
    setGuideLine('.line-zona-c-down', this.yScale(this.limits.mean - deviation));
    setGuideLine('.line-zona-b-up', this.yScale(this.limits.mean + 2*deviation));
    setGuideLine('.line-zona-b-down', this.yScale(this.limits.mean - 2*deviation));

    let xp = (p: ChartPoint) => this.xScale(p.creationTime);
    let yp = (p: ChartPoint) => this.yScale(p.value);
    this.svg.select('.chart-path').datum(this.points).attr('d', d3.line(xp, yp));

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

  /**
   * Ogni secondo tenta di ottenere nuove rilevazioni tramite un service che
   * implementa {@link ChartAbstractService}.
   * In caso vengano ottenute, le aggiunge a {@link points}.
   * Se points è però troppo lunga vengono aggiunte le nuove rilevazioni
   * e rimosse rilevazioni vecchie in modo che si rappresentino non più
   * di 100 punti.
   */
  subscribeToUpdates(): void {
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
      .subscribe({ next: (new_points) => {
        if (new_points){
          new_points.detections.forEach((p) => this.points.push(p));
          this.points = this.points.slice();
          if(this.points.length > 100) {
            this.points = this.points.slice(this.points.length - 100);
          }
          this.nextNew = new_points.nextNew;
          this.drawChart();
      }},
      error: () => this.matSnackBar.open('Caratteristica non trovata', 'Ok')
      });
  }

  /**
   * Elimina completamente la rappresentazione del grafico.
   */
   clearChart(): void{
    this.updateSubscription?.unsubscribe();
    this.svg.selectAll("*").remove();
    this.svgy.selectAll("*").remove();
  }

  /**
   * Oltre a eliminare la rappresentazione del grafico, lo reiniizializza.
   */
  refresh(): void{
    this.clearChart();
    this.getData(this.currentNode?.device.id, this.currentNode?.id);
    this.createChart();
    this.subscribeToUpdates();
  }
}
