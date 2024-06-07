import {Component, OnInit} from '@angular/core';
import {PatientData} from "./model";
import {NgForOf} from "@angular/common";
import {PatientService} from "./patient.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.css'
})
export class PatientsComponent implements OnInit{

  patients: PatientData[] | undefined;

  constructor(private patientService: PatientService, private router: Router) {
  }

  ngOnInit(): void {
    this.patientService.getPatientsData().subscribe({
      next: (result) => {
        this.patients = result;
      }
    });
  }

  openPatientDetails(patient: PatientData) {
    this.router.navigate(['patient/' + patient.patient.id])
  }
}
