import { Injectable } from '@angular/core';
import { Machine } from './machine';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {AppService} from "./app.service";

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  constructor(private appService: AppService) { }

  private machinesUrl = '/machines';

  getMachines(): Observable<Machine[]>{
    return this.appService.get<Machine[]>(this.machinesUrl);
  }
}
