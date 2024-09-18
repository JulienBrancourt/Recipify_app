import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { AuthService } from "../../service/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  errorMessage: string | null = null;
  isSubmitting: boolean = false; // Linked to loading state

  constructor(private authService: AuthService, private router: Router) { }

  handleSubmit() {
    if (this.form.valid) {
      const username = this.form.get('username')?.value;
      const password = this.form.get('password')?.value;

      this.isSubmitting = true; // Start loading
      this.authService.login(username!, password!)
        .subscribe({
          next: (response: { token: string }) => {
            // console.log('Login successful, token:', response.token);
            this.router.navigate(['/dashboard']);
          },
          error: (err: any) => {
            this.errorMessage = 'Login failed. Please check your credentials.';
            this.isSubmitting = false;
            return this.errorMessage

          },
          complete: () => {
            this.isSubmitting = false; // Stop loading after complete
          }
        });
    } else {
      this.errorMessage = 'Please fill in all fields.';
    }
  }
}
