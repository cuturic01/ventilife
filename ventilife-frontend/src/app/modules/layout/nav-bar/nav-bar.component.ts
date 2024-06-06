import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../auth/service/auth.service";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../auth/dialog/dialog.component";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [
    MatToolbarRow,
    MatToolbar
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit{
  role: any;

  constructor(
    private router: Router,
    private authService: AuthService,
    public dialog: MatDialog
  ){

  }
  ngOnInit(): void {
  }

  login(){
    this.dialog.open(DialogComponent, {data: ""})
  }

  goHome() {
    this.router.navigate(['home']);
  }

}
