import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import {HomeModule} from "./modules/home/home.module";
import {AuthModule} from "./modules/auth/auth.module";
import {LayoutModule} from "./modules/layout/layout.module";
import {MatDialogModule} from "@angular/material/dialog";
import {DialogComponent} from "./modules/auth/dialog/dialog.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {TokenInterceptor} from "./modules/auth/tokenInterceptor";

@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppComponent,
    AuthModule,
    HomeModule,
    LayoutModule,
    MatDialogModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    }
  ],
  bootstrap: []
})
export class AppModule { }
