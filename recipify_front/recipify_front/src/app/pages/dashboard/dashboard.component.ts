import { Component } from '@angular/core';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class dashboardComponent {

  constructor(private router: Router) { }

  form = new FormGroup({
    ingredientName: new FormControl(''),
    quantity: new FormControl(''),
  });

  ingredients: string[] = [''];


  addInput() {new FormControl ('')
    
  }

  submitIngredients () {
    console.log("vos ingrédients sont soumis")
  }

    // Fonction pour rediriger vers une autre page
  goToPage() {
    this.router.navigate(['/recette']);
  }  

}

