import { Routes } from '@angular/router';
import {DialogComponent} from "./modules/auth/dialog/dialog.component";
import {HomeScreenComponent} from "./modules/home/home-screen/home-screen.component";

export const routes: Routes = [
  {path: 'home', component: HomeScreenComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' }

];
