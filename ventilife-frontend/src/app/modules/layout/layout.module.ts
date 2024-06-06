import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    NavBarComponent,
    MatToolbarModule,
    MatButtonModule
  ]
})
export class LayoutModule { }
