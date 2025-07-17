import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import patients, {ChangeRecord, PatientData, StablePatientParams} from "../patients/model";
import {ActivatedRoute} from "@angular/router";
import {PatientService} from "../patients/patient.service";
import {DecimalPipe, NgForOf} from "@angular/common";
import {MatFormField} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-patient',
  standalone: true,
  imports: [
    FormsModule,
    DecimalPipe,
    MatFormField,
    MatSelect,
    MatOption,
    NgForOf,
    MatRadioGroup,
    MatRadioButton
  ],
  templateUrl: './patient.component.html',
  styleUrl: './patient.component.css'})
export class PatientComponent implements OnInit{

  constructor(private route: ActivatedRoute,
              private patientService: PatientService,
              private snackBar: MatSnackBar) {
  }

  patient: any;
  selectedMode: string = '';
  modes: string[] = ['CPAP', 'APRV', 'SIMV', 'KMV_AC'];

  getBetter(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.getBetter(name).subscribe({
      next: (result) => {
        this.patient.patient = result.patient;
        this.patient.changeRecord = result.changeRecord;
        this.snackBar.open(result.responseMessage.message, "OK", {
          duration: 10000,
          horizontalPosition: "end",
          panelClass: ['green-snackbar']
        });
      }
    })
  }

  getWorse(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.getWorse(name).subscribe({
      next: (result) => {
        this.patient.patient = result.patient;
        this.patient.changeRecord = result.changeRecord;
        this.snackBar.open(result.responseMessage.message, "OK", {
          duration: 10000,
          horizontalPosition: "end",
          panelClass: ['green-snackbar']
        });
      }
    })
  }

  badInhalation(patientData: PatientData) {
    let name = patientData.patient.name.split(' ').map(word => word.toLowerCase())[0];
    this.patientService.badInhalation(name).subscribe({
      next: (result) => {
        this.patient.patient = result.patient;
        this.snackBar.open(result.responseMessage.message, "OK", {
          duration: 10000,
          horizontalPosition: "end",
          panelClass: ['green-snackbar']
        });
      }
    })
  }

  changeMode(patient: any, mode: string) {

    this.patientService.changeMode(patient.patient.id, mode).subscribe({
      next: (result) => {
        let msg = `Changing mode to: ${mode}.\n ${result.modeConfirmation}\n`
        if (result.recommendedMode) {
          msg += `Recommended mode is ${result.recommendedMode}.`
        }
        this.snackBar.open(msg, "OK", {
          duration: 10000,
          panelClass: ['green-snackbar']
        });
      }
    })
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.patientService.getPatientData(params['id']).subscribe({
        next: (result) => {
          this.patient = result;
          this.selectedMode = this.patient.patient.respiratorMode;
        }
      })

    });
  }
}
