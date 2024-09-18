import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-p403',
  standalone: true,
  imports: [],
  templateUrl: './p403.component.html',
  styleUrl: './p403.component.css'
})
export class P403Component {


  constructor(private router: Router) {
  }
  goHome(): void {
    this.router.navigate(['/dashboard']); // Navigate to the home route
  }
}
