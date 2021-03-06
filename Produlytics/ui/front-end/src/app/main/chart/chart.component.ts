import {AfterViewInit, Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';

import * as d3 from 'd3';
import {interval, Subscription} from 'rxjs';
import {concatMap} from 'rxjs/operators';

import {ChartPoint} from '../../model/chart/chart-point';
import {ChartAbstractService} from "../../model/chart/chart-abstract.service";
import {CharacteristicNode} from '../selection/selection-data-source/characteristic-node';
import {Limits} from '../../model/chart/limits';
import {MatDialog} from '@angular/material/dialog';
import {DatePickerDialogComponent} from '../date-picker-dialog/date-picker-dialog.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {NotificationService} from "../../utils/notification.service";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ChartComponent implements OnInit, OnDestroy, AfterViewInit {


  private static MAX_DETECTIONS_CHANGEABLE = 15;

  @Input()
  currentNode!: CharacteristicNode;

  @Input()
  index: number = 0;

  private isChartShowingOldPoints: Boolean = false;
  public limits!: Limits;
  private points: ChartPoint[] = [];
  private updateSubscription?: Subscription;
  public nextNew: number = 0;
  private xAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private yAxis!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  constructor(
    private chartService: ChartAbstractService,
    public dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {}

  /**
   * Chiama tutti i metodi necessari a creare il grafico dopo che la view
   * è stata inizilizzata.
   */
  ngAfterViewInit(): void {
    this.getData(this.currentNode?.device.id, this.currentNode?.id);
    this.createChart();
    this.drawChart();
    this.subscribeToUpdates();
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }

  margin = { top: 10, right: 60, bottom: 60, left: 60 };

  /**
   * @returns la larghezza del grafico.
   * Se il nuero di punti da rappresentare è minore o uguale a 100, la imposta
   * in modo da occupare tutto il div che contiene il grafico, altrimenti lo allunga
   * permettendo l'uso di uno scroll orizzontale.
   */
  get chartWidth(): number {
    if (this.points.length === 0) {
      return 1100;
    } else if (this.isChartShowingOldPoints){
      return Math.max((this.points[this.points.length-1].creationTime - this.points[0].creationTime)*5/1000, 1100);
    } else {
      return Math.max((Date.now() - this.points[0].creationTime)*5/1000, 1100);
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
              this.points = points.detections;
              this.isChartShowingOldPoints = true;
              this.createChart();
              this.drawChart();
            },
            error: () => this.notificationService.unexpectedError('Caratteristica non trovata')
          });
      }
    });
  }

  private svg!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private svgy!: d3.Selection<SVGGElement, unknown, HTMLElement, any>;
  private xScale!: d3.ScaleTime<number, number, never>;
  private yScale!: d3.ScaleLinear<number, number, never>;

  /**
   * Inizializza gli attributi della classe.
   */
  createChart() {
      this.svg = d3
        .selectAll(`#d3svg${this.index}`)
        .style('width', this.chartWidth + 'px')
        .style('height', 550 + 'px')
        .append('g')
        .attr('transform', `translate(0, ${this.margin.top})`);

      this.svgy = d3
        .selectAll(`#vertical${this.index}`)
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
          .attr('id', `${cls}${this.index}`)
          .attr('x1', 0)
          .attr('x2', this.chartWidth);
      };

      createGuideLine('line-media');
      createGuideLine('line-limite-min');
      createGuideLine('line-limite-max');
      createGuideLine('line-zona-c-up');
      createGuideLine('line-zona-c-down');
      createGuideLine('line-zona-b-up');
      createGuideLine('line-zona-b-down');


      this.svg.append('path').attr('class', 'chart-path');
      this.svg.append('g').attr('class', 'chart-points');
  }

  /**
   * Tramite un service che implementa {@link ChartAbstractService}, cerca di
   * ottenere prima i punti iniziali e poi i limiti e la media del grafico.
   * In caso di errore notifica l'utente con un messaggio di errore.
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
        error: () => this.notificationService.unexpectedError('Caratteristica non trovata')
      });

      this.chartService.getLimits(this.currentNode.device.id, this.currentNode.id)
      .subscribe({
        next: limits => this.limits = limits,
        error: () => this.notificationService.unexpectedError('Caratteristica non trovata')
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

    d3.selectAll(`#d3svg${this.index}`).style('width', this.chartWidth);

    this.xScale = d3.scaleTime().range([0, this.chartWidth]);

    if(this.isChartShowingOldPoints) {
      this.xScale.domain(
        d3.extent(this.points, (p) => new Date(p.creationTime)) as [Date, Date]
      );
    } else {
      this.xScale.domain(
        [new Date(this.points[0].creationTime), new Date()]
      );
    }

    this.yScale.domain([
      Math.min(this.limits.lowerLimit - delta, ...(ymin ? [ymin] : [])),
      Math.max(this.limits.upperLimit + delta, ...(ymax ? [ymax] : [])),
    ]);

    this.xAxis.call(d3.axisBottom<Date>(this.xScale)
        .ticks(d3.timeSecond.every(5))
        .tickFormat(function(date) {
          if (d3.timeMinute(date) < date) {
            return d3.timeFormat('%S:')(date);
          } else if (d3.timeHour(date) < date) {
            return d3.timeFormat('%H:%M')(date);
          } else {
            return d3.timeFormat('%d/%m')(date);
          }
        })
      )
      .select('.tick')
      .attr('transform', `translate(10, 0)`);

    this.yAxis.call(d3.axisLeft(this.yScale));

    const setGuideLine = (cls: string, y: number) => {
      this.svg.select(cls).attr('x1', 0).attr('x2', this.chartWidth).attr('y1', y).attr('y2', y);
    };
    setGuideLine(`#line-media${this.index}`, this.yScale(this.limits.mean));
    setGuideLine(`#line-limite-min${this.index}`, this.yScale(this.limits.lowerLimit));
    setGuideLine(`#line-limite-max${this.index}`, this.yScale(this.limits.upperLimit));

    const deviation = (this.limits.upperLimit - this.limits.mean)/3;

    setGuideLine(`#line-zona-c-up${this.index}`, this.yScale(this.limits.mean + deviation));
    setGuideLine(`#line-zona-c-down${this.index}`, this.yScale(this.limits.mean - deviation));
    setGuideLine(`#line-zona-b-up${this.index}`, this.yScale(this.limits.mean + 2*deviation));
    setGuideLine(`#line-zona-b-down${this.index}`, this.yScale(this.limits.mean - 2*deviation));

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
   * Oltre alle nuove rilevazioni vengono anche richieste le 15 più recenti,
   * in modo da visualizzare correttamente le anomalie di alcune carte di controllo.
   * In caso vengano ottenute, le aggiunge a {@link points}.
   * Se points è però troppo lunga vengono aggiunte le nuove rilevazioni
   * e rimosse rilevazioni vecchie in modo che si rappresentino non più
   * di 100 punti.
   */
  subscribeToUpdates(): void {
    this.updateSubscription = interval(1000)
      .pipe(
        concatMap(() => {
          let ultimoUtc;
          if (this.points.length === 0) {
            ultimoUtc = (new Date()).getTime();
          } else if (this.points.length < ChartComponent.MAX_DETECTIONS_CHANGEABLE) {
            ultimoUtc = this.points[0].creationTime
          } else {
            ultimoUtc = this.points[this.points.length-ChartComponent.MAX_DETECTIONS_CHANGEABLE].creationTime;
          }
          return this.chartService.getNextPoints(
            this.currentNode.device.id,
            this.currentNode.id,
            ultimoUtc
          )
        })
      )
      .subscribe({
        next: (new_points) => {
          if (new_points) {
            new_points.detections.forEach(element => {
              if (element.creationTime > this.nextNew) {
                this.points.push(element);
              }
            });
            for (let i = 0; i < new_points.detections.length; i++) {
              this.points[this.points.length-new_points.detections.length+i] = new_points.detections[i];
            }
            this.points = this.points.slice();
            if(this.points.length > 100) {
              this.points = this.points.slice(this.points.length - 100);
            }
            this.nextNew = new_points.nextNew;
            this.drawChart();
          }
        },
        error: () => this.notificationService.unexpectedError('Caratteristica non trovata')
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
    this.isChartShowingOldPoints = false;
    this.getData(this.currentNode?.device.id, this.currentNode?.id);
    this.createChart();
    this.drawChart();
    this.subscribeToUpdates();
  }
}
