import { Component } from '@angular/core';

@Component({
  selector: 'app-home-screen',
  standalone: true,
  imports: [],
  templateUrl: './home-screen.component.html',
  styleUrl: './home-screen.component.css'
})
export class HomeScreenComponent {
  public isImageLoaded: boolean = false;

  constructor() {
  }

  public onImageLoad(): void {
    this.isImageLoaded = true;
  }
}
