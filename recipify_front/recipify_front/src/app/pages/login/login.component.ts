import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { AuthService } from "../../service/auth.service";
import {LoadingCardComponent} from "../../components/loading-card/loading-card.component";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    LoadingCardComponent
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  isLoading: boolean = false;

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

      this.isSubmitting = true;
      this.isLoading = true;
      this.authService.login(username!, password!)
        .subscribe({
          next: (response: { token: string }) => {
          },
          error: (err: any) => {
            this.errorMessage = 'Login failed. Please check your credentials.';
            this.isSubmitting = false;
            return this.errorMessage

          },
          complete: () => {
            setTimeout(() => {
              this.isLoading = false;
            }, 5000);
            this.isSubmitting = false; // Stop loading after complete
            this.router.navigate(['/dashboard']);
          }
        });
    } else {
      this.errorMessage = 'Please fill in all fields.';
    }
  }
}
