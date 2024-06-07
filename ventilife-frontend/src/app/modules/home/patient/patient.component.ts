import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import patients, {ChangeRecord, PatientData, StablePatientParams} from "../patients/model";
import {ActivatedRoute} from "@angular/router";
import {PatientService} from "../patients/patient.service";
import {DecimalPipe} from "@angular/common";

@Component({
  selector: 'app-patient',
  standalone: true,
  imports: [
    FormsModule,
    DecimalPipe
  ],
  templateUrl: './patient.component.html',
  styleUrl: './patient.component.css'
})
export class PatientComponent implements OnInit{

  constructor(private route: ActivatedRoute, private patientService: PatientService) {
  }

  patient: any;

  getBetter(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.getBetter(name).subscribe({
      next: (result) => {
        this.patient.patient = result;
      }
    })
  }

  getWorse(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.getWorse(name).subscribe({
      next: (result) => {
        this.patient.patient = result;
      }
    })
  }

  badInhalation(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.badInhalation(name).subscribe({
      next: (result) => {
        this.patient.patient = result;
      }
    })
  }

  changeMode(patientData: PatientData) {

  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.patientService.getPatientData(params['id']).subscribe({
        next: (result) => {
          this.patient = result;
        }
      })

    });
  }
}
