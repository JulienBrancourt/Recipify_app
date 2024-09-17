import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-form-recette',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './form-recette.component.html',
  styleUrl: './form-recette.component.css'
})
export class FormRecetteComponent implements OnInit{

  recetteForm: FormGroup;

  constructor(private fb: FormBuilder,private http: HttpClient) {
    this.recetteForm = this.fb.group({

    })
  }


  addInput() {
  }

  ngOnInit(): void {
    this.addInput();
  }

  onSubmit() {

  }
}


