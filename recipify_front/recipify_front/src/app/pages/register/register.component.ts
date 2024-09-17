import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {NgForOf, NgIf} from "@angular/common";
import {GetDataService} from "../../service/getData.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgForOf],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  // Initialisation des allergies et des régimes alimentaires
  allergies: string[] = [];
  diets: string[] = [];

  // Utilisation de FormArray pour les allergies et régimes alimentaires
  form = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(3)]),
    allergies: new FormArray([]),  // FormArray pour les allergies
    diets: new FormArray([]),       // FormArray pour les régimes alimentaires
  });

  constructor(private authService: AuthService, private router: Router, private dataService: GetDataService) {}

  // Méthode pour initialiser les FormArrays dynamiquement
  initializeFormArray(controlName: string, values: string[]) {
    const formArray = this.form.get(controlName) as FormArray;
    formArray.clear();  // Nettoie le FormArray avant d'ajouter les contrôles
    values.forEach(() => formArray.push(new FormControl(false))); // false pour chaque checkbox non cochée
  }

  errorMessage: string | null = null;
  isSubmitting = false;


  allergyDisplayMap: { [key: string]: string } = {
    'gluten': 'Gluten',
    'lactose': 'Lactose',
    'arachides': 'Arachides',
    'fruits_a_coques': 'Fruits à coque',
    'oeufs': 'Œufs',
    'poisson': 'Poisson',
    'soja': 'Soja',
    'crustaces': 'Crustacés',
    'moutarde': 'Moutarde'
  };

  dietDisplayMap: { [key: string]: string } = {
    'vegetarien': 'Végétarien',
    'vegan': 'Végan',
    'sans_gluten': 'Sans gluten',
    'sans_lactose': 'Sans lactose',
    'paleo': 'Paléo',
    'keto': 'Keto (cétogène)',
    'halal': 'Halal',
    'kasher': 'Kasher',
    'pescetarien': 'Pescétarien',
    'flexitarien': 'Flexitarien',
    'crudivore': 'Crudivore',
    'sans_sucre': 'Sans sucre',
    'fruitarien': 'Fruitarien'
  };

  getDisplayName(map: { [key: string]: string }, value: string): string {
    return map[value] || value;
  }

  ngOnInit() {
    // Récupérer les allergies depuis l'API
    this.dataService.getAllergies().subscribe({
      next: (allergies) => {
        this.allergies = allergies.map((a: any) => a.allergyName);  // Conserve les valeurs techniques
        this.initializeFormArray('allergies', this.allergies);  // Initialiser le FormArray pour les allergies
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des allergies:', err);
        this.errorMessage = "Impossible de charger les allergies.";
      },
      complete: () => console.log('Requête complétée.')
    });

    this.dataService.getDiets().subscribe({
      next: (diets) => {
        this.diets = diets.map((d: any) => d.dietName);  // Conserve les valeurs techniques
        this.initializeFormArray('diets', this.diets);  // Initialiser le FormArray pour les régimes alimentaires
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des régimes:', err);
        this.errorMessage = "Impossible de charger les régimes.";
      },
      complete: () => console.log('Requête complétée.')
    });
  }

  // Méthode pour gérer la soumission du formulaire
  handleSubmit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;
      const allergies = this.getSelectedItems(this.form.get('allergies') as FormArray, this.allergies);
      const diets = this.getSelectedItems(this.form.get('diets') as FormArray, this.diets);

      console.log('diets', diets)
      console.log('allergies', allergies)
      if (username && password) {
        this.isSubmitting = true;
        this.authService.register(username, password, allergies, diets).subscribe({
          next: (response: string) => {
            console.log('Inscription réussie', response);
            this.router.navigate(['/']);
          },
          error: (err: string) => {
            console.error('Erreur d\'inscription', err);
            this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.';
            this.isSubmitting = false;
          }
        });
      }
    }
  }

  // Fonction pour obtenir les éléments sélectionnés
  getSelectedItems(formArray: FormArray, availableItems: string[]): string[] {
    return formArray.controls
      .map((control, i) => (control.value ? availableItems[i] : null))
      .filter(item => item !== null) as string[];
  }

  // Dropdown state management
  isDropdownOpen: { [key: string]: boolean } = { allergies: false, diets: false };

  toggleDropdown(type: string): void {
    this.isDropdownOpen[type] = !this.isDropdownOpen[type];
  }
}
