import {Component, OnInit} from '@angular/core';
import {PatientService} from "../patients/patient.service";
import {Patient} from "../patients/model";

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent implements OnInit{

  minPO2Patient: Patient | undefined;
  maxPCO2Patient: Patient | undefined;

  constructor(private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.patientService.getMinPO2().subscribe({
      next: (result) => {
        this.minPO2Patient = result;
      }
    })

    this.patientService.getMaxPCO2().subscribe({
      next: (result) => {
        this.maxPCO2Patient = result;
      }
    })
  }

}
