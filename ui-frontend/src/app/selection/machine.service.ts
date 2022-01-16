import { Injectable } from '@angular/core';
import { Machine } from './machine/machine';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  constructor(private http: HttpClient,) { }

  private machinesUrl = '/api/machines';

  getMachines(): Observable<Machine[]>{
    return this.http.get<Machine[]>(this.machinesUrl)
  }
}
