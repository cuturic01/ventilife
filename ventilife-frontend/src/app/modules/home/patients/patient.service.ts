import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ModeMessage, Patient, PatientData, PatientResponse} from "./model";
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

  getBetter(name: string): Observable<PatientResponse> {
    return this.http.get<PatientResponse>(environment.apiHost + "simulation/get-better/" + name)
  }

  getWorse(name: string): Observable<PatientResponse> {
    return this.http.get<PatientResponse>(environment.apiHost + "simulation/get-worse/" + name)
  }

  badInhalation(name: string): Observable<PatientResponse> {
    return this.http.get<PatientResponse>(environment.apiHost + "simulation/bad-inhalation/" + name)
  }

  getMinPO2(): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-min-pO2")
  }

  getMaxPCO2(): Observable<Patient> {
    return this.http.get<Patient>(environment.apiHost + "simulation/get-max-pCO2")
  }

  changeMode(patientId: string, mode: string): Observable<ModeMessage> {
    return this.http.get<ModeMessage>(environment.apiHost + "simulation/change-mode/" + patientId + "/" + mode)
  }


}
