import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavBarComponent} from "./modules/layout/nav-bar/nav-bar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ventilife-frontend';
}
