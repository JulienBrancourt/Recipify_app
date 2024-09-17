import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgForOf],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit{
  // Initialisation des allergies et des régimes alimentaires
  allergies: string[] = [];
  diet: string[] = [];

  // Utilisation de FormArray pour les allergies et régimes alimentaires
  form = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(3)]),
    allergies: new FormArray([]),  // FormArray pour les allergies
    diet: new FormArray([]),       // FormArray pour les régimes alimentaires
  });

  ngOnInit() {
    this.allergies = ['Allergie 1', 'Allergie 2', 'Allergie 3'];
    this.diet= ['Régime alimentaire 1', 'Régime alimentaire 2', 'Régime alimentaire 3'];
    this.initializeFormArray('allergies', this.allergies);
    this.initializeFormArray('diet', this.diet);
  }

  initializeFormArray(controlName: string, values: string[]) {
    const formArray = this.form.get(controlName) as FormArray;
    values.forEach(() => formArray.push(new FormControl(false))); // false pour chaque checkbox non cochée
  }
  errorMessage: string | null = null;
  isSubmitting = false;

  constructor(private authService: AuthService, private router: Router) {}
  // Dropdown state management
  isDropdownOpen: { [key: string]: boolean } = { allergies: false, diet: false };

  // Méthode pour gérer la soumission du formulaire
  handleSubmit() {
    if (this.form.valid) {
      const {username, password} = this.form.value;
      const allergies = this.getSelectedItems(this.form.get('allergies') as FormArray, this.allergies);
      const diet = this.getSelectedItems(this.form.get('diet') as FormArray, this.diet);

      if (username && password) {
        this.isSubmitting = true;
        this.authService.register(username, password, allergies, diet).subscribe({
          next: (response: string) => {
            console.log('Inscription réussie', response);
            this.router.navigate(['/']);
          },
          error: (err: string) => {
            console.error('Erreur d\'inscription', err);
            this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.';
            this.isSubmitting = false;
          },
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

  // Méthode pour basculer une sélection dans un FormArray
  toggleSelection(event: any, formArray: FormArray) {
    const value = event.target.value;
    if (event.target.checked) {
      formArray.push(new FormControl(value));
    } else {
      const index = formArray.controls.findIndex(ctrl => ctrl.value === value);
      formArray.removeAt(index);
    }
  }

  toggleDropdown(type: string): void {
    this.isDropdownOpen[type] = !this.isDropdownOpen[type];
  }

}

