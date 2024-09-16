import { Component } from '@angular/core';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-loading-card',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './loading-card.component.html',
  styleUrl: './loading-card.component.css'
})
export class LoadingCardComponent {
  isLoading = true;
  constructor() {
    setTimeout(() => {
      this.isLoading = false;
    }, 5000);
  }
}
