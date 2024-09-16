import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  allergies: string[] = ['Allergie 1', 'Allergie 2'];
  diet: string[] = ['Régime alimentaire 1', 'Régime alimentaire 2'];
  form = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    allergies: new FormControl(''),
    diet: new FormControl(''),

  });

  errorMessage: string | null = null;
  isSubmitting: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  handleSubmit() {
  if (this.form.valid) {
    const { username, password, allergies, diet } = this.form.value;

    if (username && password && Array.isArray(allergies) && Array.isArray(diet)) {
      this.isSubmitting = true;
      this.authService.register(username, password, allergies, diet)
        .subscribe({
          next: (response: string) => {
            console.log('Inscription réussie', response);
            this.router.navigate(['/']);
          },
          error: (err: string) => {
            console.error('Erreur d\'inscription', err);
            this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.';
            return this.errorMessage;
          },
          complete: () => {
            this.isSubmitting = false;
          }
        });
    } else {
      this.errorMessage = 'Veuillez remplir correctement tous les champs.';
    }
  } else {
    this.errorMessage = 'Veuillez remplir correctement tous les champs.';
  }
}
  isDropdownOpen: { [key: string]: boolean } = { allergies: false, diet: false };

  toggleDropdown(type: string): void {
    this.isDropdownOpen[type] = !this.isDropdownOpen[type];
    const dropdown = document.getElementById(`${type}-dropdown`);
    const button = document.querySelector(`button.dropdown-btn[aria-controls="${type}-dropdown"]`);

    if (dropdown) {
      dropdown.classList.toggle('active', this.isDropdownOpen[type]);
      if (button) {
        button.classList.toggle('active', this.isDropdownOpen[type]);
      }
    }
  }
}
