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

  form = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });

  errorMessage: string | null = null;
  isSubmitting: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  handleSubmit() {
    if (this.form.valid) {
      const { username, password } = this.form.value;

      this.isSubmitting = true;
      this.authService.register(username!, password!)
        .subscribe({
          next: (response: string) => {
            console.log('Inscription réussie', response);
            this.router.navigate(['/']);
          },
          error: (err: string) => {
            console.error('Erreur d\'inscription', err);
            this.errorMessage = 'Échec de l\'inscription. Veuillez réessayer.';
          },
          complete: () => {
            this.isSubmitting = false;
          }
        });
    } else {
      this.errorMessage = 'Veuillez remplir correctement tous les champs.';
    }
  }
}
