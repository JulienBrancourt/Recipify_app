import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  form = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  errorMessage: string | null = null;
  isSubmitting: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}


  handleSubmit() {
    if (this.form.valid) {
      const email = this.form.get('email')?.value;
      const password = this.form.get('password')?.value;

      this.isSubmitting = true;
      this.authService.login(email!, password!)
        .subscribe({
          next: (response: string) => {
            console.log('Connexion réussie', response);

            this.router.navigate(['/dashboard']);
          },
          error: (err: string) => {
            console.error('Erreur de connexion', err);
            this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos identifiants.';
          },
          complete: () => {
            this.isSubmitting = false;
          }
        });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
    }
  }
}
