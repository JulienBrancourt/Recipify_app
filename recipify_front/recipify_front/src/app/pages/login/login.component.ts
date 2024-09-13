import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-Login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './Login.component.html',
  styleUrl: './Login.component.css'
})
export class LoginComponent {

  form = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  constructor() {}


  handleSubmit() {
    // const credentials = this.form.value as Pick<User, 'email' | 'password'>;
    // this.authService.login(credentials).subscribe({
    //   next: res => {
    //     if(res) {
    //       this.router.navigate(['/'])
       return console.log("vous etes connecté") 
  }
        
      }

