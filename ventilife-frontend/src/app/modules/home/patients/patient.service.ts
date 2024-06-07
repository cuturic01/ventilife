import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Patient, PatientData} from "./model";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  getPatientsData(): Observable<PatientData[]> {
    return this.http.get<PatientData[]>(environment.apiHost + "simulation/patients-data")
  }

  getPatientData(id: string): Observable<PatientData> {
    return this.http.get<PatientData>(environment.apiHost + "simulation/patient-data/" + id)
  }

  getBetter(name: string): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-better/" + name)
  }

  getWorse(name: string): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-worse/" + name)
  }

  badInhalation(name: string): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/bad-inhalation/" + name)
  }

  getMinPO2(): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-min-pO2")
  }

  getMaxPCO2(): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-max-pCO2")
  }


}
