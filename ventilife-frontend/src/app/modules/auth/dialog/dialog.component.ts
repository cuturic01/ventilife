import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogContent, MatDialogRef} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [
    MatDialogContent,
    MatFormField,
    FormsModule,
    MatInput,
    MatButton,
    NgIf,
    MatError,
    MatLabel
  ],
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit{

  constructor(public dialogRef: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public parent: string) {
  }

  showLoginForm: boolean = true;

  loginData = {
    email: '',
    password: ''
  };

  registerData = {
    name: '',
    surname: '',
    email: '',
    password: ''
  };

  onLoginSubmit() {
    // Handle login form submission
    console.log('Login Data:', this.loginData);
  }

  onRegisterSubmit() {
    // Handle registration form submission
    console.log('Registration Data:', this.registerData);
  }

  ngOnInit(): void {
  }

  changeForm() {
    this.showLoginForm = !this.showLoginForm;
  }
}
