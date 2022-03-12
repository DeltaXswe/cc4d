import { Injectable } from '@angular/core';
import { Machine } from './machine';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  constructor(private httpClient: HttpClient) { }

  getMachines(): Observable<Machine[]>{
    return this.httpClient.get<Machine[]>('/devices');
  }
}
