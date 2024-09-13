import { Component } from '@angular/core';
import { FormArray, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class dashboardComponent {

  constructor(private router: Router) { }

  ingredients: string[] = [''];


  addIngredient() {
    this.ingredients.push(''); 

  }

    // Fonction pour rediriger vers une autre page
  goToPage() {
    this.router.navigate(['/recette']);
  }  

}

