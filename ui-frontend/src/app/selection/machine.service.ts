import { Injectable } from '@angular/core';
import { Machine } from './machine/machine';
import { MACHINES } from './machine/mock-mac';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  constructor(private http: HttpClient,) { }

  //private machinesUrl = '/macchine';

  getMachines(): Observable<Machine[]>{
    const machines = of(MACHINES);
    return machines;
    //return this.http.get<Machine[]>(this.machinesUrl)
  }
}
