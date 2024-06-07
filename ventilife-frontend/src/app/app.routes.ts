import { Routes } from '@angular/router';
import {DialogComponent} from "./modules/auth/dialog/dialog.component";
import {HomeScreenComponent} from "./modules/home/home-screen/home-screen.component";
import {PatientsComponent} from "./modules/home/patients/patients.component";
import {PatientComponent} from "./modules/home/patient/patient.component";

export const routes: Routes = [
  {path: 'home', component: HomeScreenComponent},
  {path: 'patients', component: PatientsComponent},
  {path: 'patient/:id', component: PatientComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' }

];
