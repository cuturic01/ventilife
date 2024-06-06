import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogContent, MatDialogRef} from "@angular/material/dialog";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {LoginCredentials, UserRegistrationData} from "../model";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";

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
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit{

  constructor(public dialogRef: MatDialogRef<DialogComponent>,
              @Inject(MAT_DIALOG_DATA) public parent: string,
              private authService: AuthService,
              private router : Router,) {
  }

  showLoginForm: boolean = true;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.minLength(6), Validators.required]),
  });

  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    surname: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.minLength(6), Validators.required]),
  });

  // loginData = {
  //   email: '',
  //   password: ''
  // };

  // registerData = {
  //   name: '',
  //   surname: '',
  //   email: '',
  //   password: ''
  // };

  onLoginSubmit() {
    let email = this.loginForm.value.email;
    let password = this.loginForm.value.password;
    if (this.loginForm.valid && email !== null && email !== undefined && password !== null && password !== undefined) {
      let loginCredentials : LoginCredentials = {
        email: email,
        password: password
      }
      this.authService.login(loginCredentials).subscribe({
        next: (result) => {
          localStorage.setItem('user', JSON.stringify(result));
          this.authService.setUserLogged();
          this.router.navigate(['home']);
          this.dialogRef.close();
        }, error: (error) => {
          if (error.error !== null){
            console.log(error);
            alert(error.error.message);
          } else {
            alert("Login Failed!");
          }
        }
      })

    }
  }

  onRegisterSubmit() {
    if (this.registerForm.valid) {
      let userRegistrationData: UserRegistrationData = {
        name: this.registerForm.value.name,
        surname: this.registerForm.value.surname,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
      }
      this.authService.register(userRegistrationData).subscribe({
        next: (result) =>{
          alert("Registration successful!");
          this.dialogRef.close()
        },
        error: (error) => {
          alert(error.error.message);
        }
      })
    }

  }

  ngOnInit(): void {
  }

  changeForm() {
    this.showLoginForm = !this.showLoginForm;
  }
}
