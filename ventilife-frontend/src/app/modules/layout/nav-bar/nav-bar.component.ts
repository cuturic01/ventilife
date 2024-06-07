import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../auth/service/auth.service";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../auth/dialog/dialog.component";
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    MatToolbarRow,
    MatToolbar,
    NgIf,
    MatButton
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit{

  user: any = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    public dialog: MatDialog
  ){

  }
  ngOnInit(): void {
    this.authService.userLoggedState$.subscribe({
      next: value => {
        this.user = value;
        this.authService.checkTokenValidity()
      }
    });
  }

  login(){
    this.dialog.open(DialogComponent, {data: ""})
  }

  goHome() {
    this.router.navigate(['home']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['home']);
  }

  patients() {
    this.router.navigate(['patients']);
  }
}
