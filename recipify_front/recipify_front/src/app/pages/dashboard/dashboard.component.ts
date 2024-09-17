import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {NgForOf} from "@angular/common";
import {HttpClient} from "@angular/common/http";



@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class dashboardComponent implements OnInit {
  ingredientForm: FormGroup;

  constructor(private fb: FormBuilder,private http: HttpClient, private router: Router ) {
    this.ingredientForm = this.fb.group({
      ingredients: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.addInput(); // Ajoute un champ par défaut au démarrage
  }

  get ingredients(): FormArray {
    return this.ingredientForm.get('ingredients') as FormArray;
  }

  addInput(): void {
    const ingredientGroup = this.fb.group({
      name: [''],
      quantity: [''],
      unit: [[]],
      expiration: ['']
    });
    this.ingredients.push(ingredientGroup);
  }

  unit = [
    'GRAMMES',
    'KILOGRAMMES',
    'LITRES',
    'CENTILITRES',
    'MILLILITRES',
    'CUILLERES_A_SOUPE',
    'CUILLERES_A_CAFE',
    'TASSE',
    'VERRE',
    'PINCEE',
    'GOUTTE'
  ];

  onSubmit(): void {
    const formIngredientData = this.ingredientForm.value; // Récupération des données du formulaire
    console.log(formIngredientData);
    //TODO compléter l'url
    const url = 'https://example.com/api/APIàcompléter';

    // Envoi de la requête HTTP POST avec les données du formulaire
    this.http.post(url, formIngredientData).subscribe({
      next: response =>console.log('Form submitted successfully', response),
      error: err => (console.error('Error submitting form : ', err))
      }
    );
  }

  goToPage() {
    this.router.navigate(['/recette']);
  }
}



